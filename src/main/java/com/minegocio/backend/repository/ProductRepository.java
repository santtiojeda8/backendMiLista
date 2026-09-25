package com.minegocio.backend.repository;

import com.minegocio.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //Aquí armamos las consultas personalizadas a la DB (MySQL).
    /*
    * La primera consulta que vamos a armar es una consulta sola que se divide en 2 partes:
    * 1°: Le pedimos a la DB que busque todos los productos que esten relacionados con el ID de un usuario.
    * 2°: Le decimos que filtre estos productos y nos devuelva los que estan activos( o tengan byte = 1)
    *
    * La consulta nos queda así
    * 1° findByUserIdUser
    * 2° AndActive
    * */

    List<Product> findByUserIdUserAndActiveTrue(UUID idUser);

    Optional<Product> findByIdProductAndUserIdUser(UUID idProduct, UUID userIdUser);
}
