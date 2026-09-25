package com.minegocio.backend.dto.productdto;

import java.math.BigDecimal;

public class ProductRequest {

    private String name;
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
