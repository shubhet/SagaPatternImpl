package com.example.order;

import com.example.common.OrderSagaEvent;
import com.example.common.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OrderApplication {

    @Autowired
    private KafkaTemplate<String, OrderSagaEvent> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @PostMapping("/orders")
    public String createOrder() {
        Long orderId = System.currentTimeMillis();
        kafkaTemplate.send(
                "order-created",
                new OrderSagaEvent(orderId, SagaStatus.ORDER_CREATED)
        );
        return "ORDER CREATED " + orderId;
    }
}
