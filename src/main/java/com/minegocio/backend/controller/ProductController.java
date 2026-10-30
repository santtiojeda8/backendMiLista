package com.minegocio.backend.controller;

import com.minegocio.backend.dto.productdto.ProductRequest;
import com.minegocio.backend.dto.productdto.ProductResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/allProduct")
    public ResponseEntity<Page<ProductResponse>> getAllProducts (@AuthenticationPrincipal User logedUser,Pageable pageable){
        return ResponseEntity.ok(productService.getAllActiveProduct(logedUser.getIdUser(), pageable));
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

    //Cambiar ruta
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> searchByName (@RequestParam String name, @AuthenticationPrincipal User logedUser, Pageable pageable){
        return ResponseEntity.ok(productService.searchByName(name, logedUser.getIdUser(), pageable ));
    }


}
