package com.demo.order;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@Entity
@Table(name = "ORDERS")
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    public String id;

    @Column(name = "TICKER", nullable = false, length = 10)
    public String ticker;

    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION", nullable = false, length = 6)
    public Operation operation;

    @Column(name = "QUANTITY", nullable = false)
    public BigDecimal quantity;

    @Column(name = "PRICE", nullable = false, precision = 15, scale = 2)
    public BigDecimal price;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 15, scale = 2)
    public BigDecimal totalPrice;

    @Column(name = "DATE_NEGOTIATION", nullable = false)
    public LocalDateTime dateNegotiation;

    @Column(name = "ACCOUNT_ID", nullable = false, length = 35)
    public String accountId;

    private transient Consumer<OrderEvent> postSave;

    @Deprecated
    public Order() {
    }

    public Order(String ticker, Operation operation,
                 BigDecimal quantity, BigDecimal price,
                 String accountId) {
        this.ticker = ticker;
        this.operation = operation;
        this.quantity = quantity;
        this.price = price;
        this.accountId = accountId;
        this.totalPrice = quantity.multiply(price);
    }

    public Order postSave(Consumer<OrderEvent> consumer) {
        this.postSave = consumer;
        return this;
    }

    @Override
    public void persist() {
        super.persist();
        postSave.accept(new OrderEvent(
                ticker,
                operation,
                quantity,
                totalPrice,
                accountId)
        );
    }

    @PrePersist
    private void prePersist() {
        if (dateNegotiation == null) {
            dateNegotiation = LocalDateTime.now();
        }
    }
}
