package com.minegocio.backend.controller;

import com.minegocio.backend.dto.supplierdto.SupplierRequest;
import com.minegocio.backend.dto.supplierdto.SupplierResponse;
import com.minegocio.backend.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{idUser}/allSupplier")
    public List<SupplierResponse> getAllSupplier (@PathVariable UUID idUser){
        return supplierService.getAllActiveSupplier(idUser);
    }

    @GetMapping("/{idSupplier}/{idUser}")
    public SupplierResponse getSupplierById (@PathVariable UUID idSupplier, @PathVariable UUID idUser){
        return supplierService.getSupplierByIdDTO(idSupplier, idUser);
    }

    @PostMapping("/{idUser}")
    public SupplierResponse createSupplier (@RequestBody SupplierRequest supplier, @PathVariable UUID idUser){
        return supplierService.createSupplier(supplier, idUser);
    }

    @PutMapping("/{idSupplier}/{idUser}")
    public SupplierResponse updateSupplier (@RequestBody SupplierRequest supplier,@PathVariable UUID idSupplier, @PathVariable UUID idUser){
        return supplierService.updateSupplier(supplier, idSupplier, idUser);
    }

    @PatchMapping("/{idSupplier}/{idUser}")
    public SupplierResponse softDelete (@PathVariable UUID idSupplier, @PathVariable UUID idUser){
        return supplierService.softDelete(idSupplier, idUser);
    }
}
