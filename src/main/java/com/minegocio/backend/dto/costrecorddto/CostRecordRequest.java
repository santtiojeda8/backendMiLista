package com.minegocio.backend.dto.costrecorddto;

import java.math.BigDecimal;

public class CostRecordRequest {
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
