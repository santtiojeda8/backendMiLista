package com.minegocio.backend.dto.supplierdto;

import java.util.UUID;

public class SupplierResponse {
    private UUID idSupplier;
    private String name;

    public SupplierResponse() {
    }
    /*
        Este constructor lo agregamos porque nos va a facilitar crear un SupplierResponse,
        lo vamos a poder crear en una sola línea a la hora de instanciar el SupplierResponse.
        Ej: SupplierResponse supplierResponse = new SupplierResponse(supplier.getName()).
    */
    public SupplierResponse(UUID idSupplier, String name) {
        this.idSupplier = idSupplier;
        this.name = name;
    }

    public UUID getIdSupplier() {
        return idSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
