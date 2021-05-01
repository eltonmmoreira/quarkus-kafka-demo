package com.demo.order;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderRequest {
    @NotNull(message = "Ticker deve ser informado")
    public String ticker;

    @NotNull(message = "Operação deve ser informada")
    public Operation operation;

    @NotNull(message = "Quantidade deve ser informada")
    public BigDecimal quantity;

    @NotNull(message = "Valor deve ser informado")
    public BigDecimal price;

    @NotNull(message = "Usuário deve ser informado")
    public String userName;
}
