package com.minegocio.backend.repository;

import com.minegocio.backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    // Aquí armamos las consultas personalizadas a la DB (MySQL) para los proveedores.
    /*
     * Esta consulta nos permite obtener el catálogo privado de proveedores de un comerciante.
     * Funciona en 2 partes:
     * 1°: Le pedimos a la DB que busque todos los proveedores asociados al ID de un usuario específico (userIdUser).
     * 2°: Le decimos que filtre y nos devuelva solo los que están activos (active = 1 / Byte).
     *
     * ¿En qué lo vamos a usar en nuestra aplicación?
     * 1. Para mostrarle al usuario su lista de proveedores disponibles cuando quiera seleccionar a cuál le compró.
     * 2. Para ocultar los proveedores que el usuario haya dado de baja (soft delete), manteniendo la integridad histórica.
     * 3. Para garantizar el aislamiento de datos: el Usuario A jamás podrá ver ni usar los proveedores del Usuario B.
     */

    List<Supplier> findByUserIdUserAndActiveTrue(UUID idUser);

    Optional<Supplier> findByIdSupplierAndUserIdUser(UUID idSupplier, UUID idUser);
}