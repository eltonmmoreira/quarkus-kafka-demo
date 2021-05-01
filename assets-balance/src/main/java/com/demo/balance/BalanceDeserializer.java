package com.demo.balance;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class BalanceDeserializer extends JsonbDeserializer<OrderEvent> {
    public BalanceDeserializer() {
        super(OrderEvent.class);
    }
}
