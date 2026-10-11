package com.minegocio.backend.controller;

import com.minegocio.backend.dto.productdto.ProductRequest;
import com.minegocio.backend.dto.productdto.ProductResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/allProduct")
    public ResponseEntity<List<ProductResponse>> getAllProducts (@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(productService.getAllActiveProduct(logedUser.getIdUser()));
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductResponse> getProductById (@PathVariable UUID idProduct, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(productService.getProductByIdDTO(idProduct, logedUser.getIdUser()));
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponse> createProduct (@Valid @RequestBody ProductRequest product, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(productService.createProduct(product, logedUser.getIdUser()));
    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<ProductResponse> updateProduct (@Valid @RequestBody ProductRequest product,@PathVariable UUID idProduct,@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(productService.updateProduct(product, idProduct, logedUser.getIdUser()));
    }

    @PatchMapping("/{idProduct}")
    public ResponseEntity<ProductResponse> softDelete (@PathVariable UUID idProduct, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(productService.softDelete(idProduct, logedUser.getIdUser()));
    }

}
