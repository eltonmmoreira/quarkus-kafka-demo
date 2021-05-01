package com.demo.balance;

import java.math.RoundingMode;
import java.util.function.BiFunction;

public enum Operation {
    BUY((balance, balance2) -> {
        balance.quantity = balance.quantity.add(balance2.quantity);
        balance.totalPrice = balance.totalPrice.add(balance2.totalPrice);
        balance.averagePrice = balance.totalPrice.divide(balance.quantity, RoundingMode.HALF_UP);
        return balance;
    }),
    SALE((balance, balance2) -> {
        balance.quantity = balance.quantity.subtract(balance2.quantity);
        balance.totalPrice = balance.quantity.multiply(balance.averagePrice);
        return balance;
    });

    private final BiFunction<Balance, Balance, Balance> recalculate;

    Operation(BiFunction<Balance, Balance, Balance> recalculate) {
        this.recalculate = recalculate;
    }

    public BiFunction<Balance, Balance, Balance> recalculate() {
        return recalculate;
    }
}