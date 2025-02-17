package com.example.order_process.service;

import com.example.order_process.dto.order.OrderRequest;
import com.example.order_process.dto.order.OrderResponse;
import com.example.order_process.model.Order;
import com.example.order_process.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(orderRequest.getUserId());
        order.setItems(orderRequest.getItems());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setStatus("PENDING");

        orderRepository.save(order);

        String cacheKey = "order_" + order.getId();
        redisTemplate.opsForValue().set(cacheKey, order, Duration.ofMinutes(10));

        return convertToResponse(order);
    }

    public OrderResponse getOrderById(String id) {
        String cacheKey = "order_" + id;
        Object cachedOrder = redisTemplate.opsForValue().get(cacheKey);

        Order order;
        if (cachedOrder != null) {
            order = objectMapper.convertValue(cachedOrder, Order.class);
        } else {
            order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
            redisTemplate.opsForValue().set(cacheKey, order, Duration.ofMinutes(10));
        }

        return convertToResponse(order);
    }

    public Page<OrderResponse> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToResponse);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
        redisTemplate.delete("order_" + id);
    }

    public OrderResponse updateOrder(String id, OrderRequest orderRequest) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        Order order = orderOptional.get();
        order.setUserId(orderRequest.getUserId());
        order.setItems(orderRequest.getItems());
        order.setTotalPrice(orderRequest.getTotalPrice());

        orderRepository.save(order);

        String cacheKey = "order_" + order.getId();
        redisTemplate.opsForValue().set(cacheKey, order, Duration.ofMinutes(10));

        return convertToResponse(order);
    }
    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setItems(order.getItems());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        return response;
    }
}
