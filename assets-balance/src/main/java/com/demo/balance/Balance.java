package com.demo.balance;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "BALANCE", uniqueConstraints = {
        @UniqueConstraint(
                name = "UNQ_TICKER",
                columnNames = {"TICKER"}
        ),
        @UniqueConstraint(
                name = "UNQ_ACCOUNT_ID",
                columnNames = {"ACCOUNT_ID"}
        )
})
public class Balance extends PanacheEntity {

    @Column(name = "TICKER", nullable = false, length = 10)
    public String ticker;

    @Column(name = "QUANTITY", nullable = false)
    public BigDecimal quantity;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 15, scale = 2)
    public BigDecimal totalPrice;

    @Column(name = "AVERAGE_PRICE", nullable = false, precision = 15, scale = 2)
    public BigDecimal averagePrice;

    @Column(name = "ACCOUNT_ID", nullable = false, length = 35)
    public String accountId;

    @Version
    public Long version;

    public static BalanceBuilder builder() {
        return new BalanceBuilder();
    }

    @Deprecated
    public Balance() {
    }

    public Balance(Long id, String ticker, BigDecimal quantity, BigDecimal totalPrice,
                   BigDecimal averagePrice, String accountId, Long version) {
        super.id = id;
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.averagePrice = averagePrice;
        this.accountId = accountId;
        this.version = version;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", averagePrice=" + averagePrice +
                ", accountId='" + accountId + '\'' +
                ", version=" + version +
                '}';
    }

    static class BalanceBuilder {
        private String ticker;
        private BigDecimal quantity;
        private BigDecimal totalPrice;
        private BigDecimal averagePrice;
        private String accountId;

        public BalanceBuilder ticker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public BalanceBuilder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public BalanceBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public BalanceBuilder averagePrice(BigDecimal averagePrice) {
            this.averagePrice = averagePrice;
            return this;
        }

        public BalanceBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Balance build() {
            return new Balance(null, ticker, quantity, totalPrice, averagePrice, accountId, null);
        }
    }

    @PrePersist
    private void prePersist() {
        Objects.requireNonNull(quantity);
        Objects.requireNonNull(totalPrice);
        if (id == null) {
            averagePrice = totalPrice.divide(quantity, RoundingMode.HALF_UP);
        }
    }

    public static Optional<Balance> findByTickerAndAccountId(String ticker, String accountId) {
        return find("ticker = :ticker and accountId = :accountId",
                Map.of("ticker", ticker, "accountId", accountId)).firstResultOptional();
    }

    public Balance registerBalance(Operation operation) {
        var balance = this;
        var persistedBalanceOptional = findByTickerAndAccountId(balance.ticker, balance.accountId);

        if (persistedBalanceOptional.isPresent()) {
            balance = balance.recalculate(persistedBalanceOptional.get(), operation);
        }

        balance.persist();
        return balance;
    }

    private Balance recalculate(Balance balance, Operation operation) {
        return operation.recalculate().apply(balance, this);
    }
}
