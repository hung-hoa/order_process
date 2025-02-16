package com.example.order_process.kafka.producer;

import com.example.order_process.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrderCreatedEvent(Order order) {
        log.info("🔹 Sending order to Kafka: {}", order);
        kafkaTemplate.send("order-created", order);
    }
}
