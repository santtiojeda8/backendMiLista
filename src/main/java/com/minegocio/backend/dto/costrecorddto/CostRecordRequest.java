package com.minegocio.backend.dto.costrecorddto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CostRecordRequest {

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    @Digits(integer = 6, fraction = 2, message = "El precio tiene un formato inválido (máximo 6 dígitos enteros y 2 decimales)")
    private BigDecimal costPrice;

    public CostRecordRequest() {
    }

    public CostRecordRequest(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
}
