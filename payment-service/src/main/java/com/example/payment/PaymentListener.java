package com.example.payment;

import com.example.common.OrderSagaEvent;
import com.example.common.SagaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    @Autowired
    private KafkaTemplate<String, OrderSagaEvent> kafkaTemplate;

    @KafkaListener(topics = "payment-request", groupId = "payment-service")
    public void processPayment(OrderSagaEvent event) {

        // simulate payment success
        kafkaTemplate.send(
                "payment-success",
                new OrderSagaEvent(event.orderId, SagaStatus.PAYMENT_COMPLETED)
        );
    }
}
