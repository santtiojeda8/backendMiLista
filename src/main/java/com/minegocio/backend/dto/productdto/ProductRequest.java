package com.minegocio.backend.dto.productdto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    @Digits(integer = 6, fraction = 2, message = "El precio tiene un formato inválido (máximo 6 dígitos enteros y 2 decimales)")
    private BigDecimal salePrice;

    public ProductRequest(String name, BigDecimal salePrice) {
        this.name = name;
        this.salePrice = salePrice;
    }

    public ProductRequest() {
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
