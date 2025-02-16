package com.example.order_process.kafka.consumer;

import com.example.order_process.model.Order;
import com.example.order_process.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "order-created", groupId = "order-group", containerFactory = "orderKafkaListenerContainerFactory")
    public void processOrder(Order order) {
        log.info("✅ Processing order: {}", order);
        order.setStatus("CONFIRMED");
        orderRepository.save(order);
    }
}
