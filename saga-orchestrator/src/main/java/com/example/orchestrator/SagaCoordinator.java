package com.example.orchestrator;

import com.example.common.OrderSagaEvent;
import com.example.common.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SagaCoordinator{

    @Autowired
    private KafkaTemplate<String, OrderSagaEvent> kafkaTemplate;

    @KafkaListener(topics = "order-created", groupId = "saga-orchestrator")
    public void onOrderCreated(OrderSagaEvent event) {
        kafkaTemplate.send(
                "payment-request",
                new OrderSagaEvent(event.orderId, SagaStatus.PAYMENT_PENDING)
        );
    }

    @KafkaListener(topics = "payment-success", groupId = "saga-orchestrator")
    public void onPaymentSuccess(OrderSagaEvent event) {
        kafkaTemplate.send(
                "inventory-request",
                new OrderSagaEvent(event.orderId, SagaStatus.INVENTORY_PENDING)
        );
    }

    @KafkaListener(topics = "inventory-success", groupId = "saga-orchestrator")
    public void onInventorySuccess(OrderSagaEvent event) {
        System.out.println("✅ ORDER COMPLETED: " + event.orderId);
    }
}
