package com.minegocio.backend.dto.supplierdto;

public class SupplierResponse {
    private String name;

    public SupplierResponse() {
    }

    /*
        Este constructor lo agregamos porque nos va a facilitar crear un SupplierResponse,
        lo vamos a poder crear en una sola línea a la hora de instanciar el SupplierResponse.
        Ej: SupplierResponse supplierResponse = new SupplierResponse(supplier.getName()).
    */
    public SupplierResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
