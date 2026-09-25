package com.minegocio.backend.controller;

import com.minegocio.backend.dto.productdto.ProductRequest;
import com.minegocio.backend.dto.productdto.ProductResponse;
import com.minegocio.backend.service.ProductService;
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

    @GetMapping("/{idUser}/allProduct")
    public List<ProductResponse> getAllProducts (@PathVariable UUID idUser){
        return productService.getAllActiveProduct(idUser);
    }

    @GetMapping("/{idProduct}/{idUser}")
    public ProductResponse getProductById (@PathVariable UUID idProduct, @PathVariable UUID idUser){
        return productService.getProductByIdDTO(idProduct, idUser);
    }

    @PostMapping("/{idUser}")
    public ProductResponse createProduct (@RequestBody ProductRequest product, @PathVariable UUID idUser){
        return productService.createProduct(product, idUser);
    }

    @PutMapping("/{id}/{idUser}")
    public ProductResponse updateProduct (@RequestBody ProductRequest product,@PathVariable UUID id, @PathVariable UUID idUser){
        return productService.updateProduct(product, id, idUser);
    }

    @PatchMapping("/{id}/{idUser}")
    public ProductResponse softDelete (@PathVariable UUID id, @PathVariable UUID idUser){
        return productService.softDelete(id, idUser);
    }

}
