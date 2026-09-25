package com.minegocio.backend.service;

import com.minegocio.backend.dto.productdto.ProductRequest;
import com.minegocio.backend.dto.productdto.ProductResponse;
import com.minegocio.backend.entity.Product;
import com.minegocio.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService( ProductRepository productRepository, UserService userService){
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public List<ProductResponse> getAllActiveProduct (UUID idUser){
        return productRepository.findByUserIdUserAndActiveTrue(idUser)
                .stream()
                .map(product -> new ProductResponse(product.getName(), product.getSalePrice()))
                .toList();
    }

    public ProductResponse createProduct (ProductRequest product, UUID idUser){
        //Creamos el nuevo Producto.
        Product p = new Product();

        //Le definimos su creador.
        p.setUser( userService.findUserById(idUser));

        //Le definimos el nombre.
        p.setName(product.getName());

        //Le definimos el precio de venta.
        p.setSalePrice(product.getSalePrice());

        //Guardamos el producto en la Base de Datos.
        productRepository.save(p);

        //Retornamos el DTO solo con los datos que vamos a mostrar.
        return new ProductResponse(p.getName(),p.getSalePrice());
    }

    public ProductResponse getProductByIdDTO (UUID idProduct, UUID idUser){
        Product p =  productRepository.findByIdProductAndUserIdUser(idProduct, idUser).orElseThrow( () -> new RuntimeException("No se encontró el Producto"));
        return new ProductResponse(p.getName(), p.getSalePrice());
    }

    public Product findProductById (UUID idProduct, UUID idUser){
        return productRepository.findByIdProductAndUserIdUser(idProduct, idUser).orElseThrow( () -> new RuntimeException("No se encontró el Producto"));
    }

    public ProductResponse updateProduct (ProductRequest product, UUID idProduct, UUID idUser) {
        Product existingEntity = productRepository.findByIdProductAndUserIdUser(idProduct, idUser).orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + idProduct));

        if(product.getName() != null) existingEntity.setName(product.getName());

        if(product.getSalePrice() != null) existingEntity.setSalePrice(product.getSalePrice());

        productRepository.save(existingEntity);
        return new ProductResponse(existingEntity.getName(), existingEntity.getSalePrice());
    }

    public ProductResponse softDelete (UUID id, UUID idUser){
        Product existingEntity = productRepository.findByIdProductAndUserIdUser(id, idUser).orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));

        existingEntity.setActive(false);

        productRepository.save(existingEntity);

        return new ProductResponse(existingEntity.getName(), existingEntity.getSalePrice());
    }


}
