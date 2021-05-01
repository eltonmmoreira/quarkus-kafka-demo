package com.demo.balance;

import java.math.BigDecimal;

public class OrderEvent {
    public String ticker;
    public Operation operation;
    public BigDecimal quantity;
    public BigDecimal totalPrice;
    public String accountId;
}
