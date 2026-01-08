package com.example.inventory;

import com.example.common.OrderSagaEvent;
import com.example.common.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryListener {

    @Autowired
    private KafkaTemplate<String, OrderSagaEvent> kafkaTemplate;

    @KafkaListener(topics = "inventory-request", groupId = "inventory-service")
    public void reserveInventory(OrderSagaEvent event) {

        // simulate inventory success
        kafkaTemplate.send(
                "inventory-success",
                new OrderSagaEvent(event.orderId, SagaStatus.INVENTORY_RESERVED)
        );
    }
}
