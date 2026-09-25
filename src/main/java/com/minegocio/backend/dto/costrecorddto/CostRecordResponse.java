package com.minegocio.backend.dto.costrecorddto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CostRecordResponse {
    private String nameProduct, nameSupplier;
    private LocalDate date;
    private BigDecimal costPrice;

    public CostRecordResponse() {
    }

    public CostRecordResponse(String nameProduct, String nameSupplier, LocalDate date, BigDecimal costPrice) {
        this.nameProduct = nameProduct;
        this.nameSupplier = nameSupplier;
        this.date = date;
        this.costPrice = costPrice;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
}
