
package com.example.common;
import java.io.Serializable;
public class OrderSagaEvent implements Serializable {
    public Long orderId;
    public SagaStatus status;
    public OrderSagaEvent() {}
    public OrderSagaEvent(Long o, SagaStatus s) { orderId=o; status=s; }
}
