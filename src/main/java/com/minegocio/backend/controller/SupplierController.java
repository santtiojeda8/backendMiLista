package com.minegocio.backend.controller;

import com.minegocio.backend.dto.supplierdto.SupplierRequest;
import com.minegocio.backend.dto.supplierdto.SupplierResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/allSupplier")
    public ResponseEntity<List<SupplierResponse>> getAllActiveSupplier (@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(supplierService.getAllActiveSupplier(logedUser.getIdUser()));
    }

    @GetMapping("/{idSupplier}")
    public ResponseEntity<SupplierResponse> getSupplierById (@PathVariable UUID idSupplier, @AuthenticationPrincipal User logedUser){
        return  ResponseEntity.ok(supplierService.getSupplierByIdDTO(idSupplier, logedUser.getIdUser()));
    }

    @PostMapping("/")
    public ResponseEntity<SupplierResponse> createSupplier (@RequestBody SupplierRequest supplier, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(supplierService.createSupplier(supplier, logedUser.getIdUser()));
    }

    @PutMapping("/{idSupplier}")
    public ResponseEntity<SupplierResponse> updateSupplier (@RequestBody SupplierRequest supplier,@PathVariable UUID idSupplier, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(supplierService.updateSupplier(supplier, idSupplier, logedUser.getIdUser()));
    }

    @PatchMapping("/{idSupplier}")
    public ResponseEntity<SupplierResponse> softDelete (@PathVariable UUID idSupplier, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(supplierService.softDelete(idSupplier, logedUser.getIdUser()));
    }
}
