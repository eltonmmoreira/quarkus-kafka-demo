package com.demo.order;

import java.math.BigDecimal;

public class OrderEvent {
    public String ticker;
    public Operation operation;
    public BigDecimal quantity;
    public BigDecimal totalPrice;
    public String accountId;

    public OrderEvent(String ticker, Operation operation, BigDecimal quantity,
                      BigDecimal totalPrice, String accountId) {
        this.ticker = ticker;
        this.operation = operation;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
    }
}
