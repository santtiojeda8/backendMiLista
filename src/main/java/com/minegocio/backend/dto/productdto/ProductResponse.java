package com.minegocio.backend.dto.productdto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private UUID idProduct;
    private String name;
    private BigDecimal salePrice;

    public ProductResponse() {
    }

    public ProductResponse(UUID idProduct, String name, BigDecimal salePrice) {
        this.idProduct = idProduct;
        this.name = name;
        this.salePrice = salePrice;
    }

    public UUID getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
