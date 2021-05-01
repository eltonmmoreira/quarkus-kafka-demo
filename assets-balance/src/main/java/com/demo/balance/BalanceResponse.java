package com.demo.balance;

import java.math.BigDecimal;

public class BalanceResponse {
    public String ticker;
    public BigDecimal quantity;
    public BigDecimal totalPrice;
    public BigDecimal averagePrice;
    public String accountId;

    public BalanceResponse(String ticker, BigDecimal quantity, BigDecimal totalPrice, BigDecimal averagePrice, String accountId) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.accountId = accountId;
    }
}
