package com.demo.balance;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class StreamBalanceConsumer {

    @Incoming("orders")
    @Blocking
    @Transactional
    public void consumer(OrderEvent event) {
        var balance = Balance.builder()
                .ticker(event.ticker)
                .quantity(event.quantity)
                .totalPrice(event.totalPrice)
                .accountId(event.accountId)
                .build()
                .registerBalance(event.operation);
        System.out.println(balance);
    }
}
