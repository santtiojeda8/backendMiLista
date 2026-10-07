package com.minegocio.backend.repository;

import com.minegocio.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Metodo personalizado: Para verificar si ya existe un email registrado avion
    boolean existsByEmail(String email);

    //Filtrado por usuarios activos
    List<User> findByActiveTrue() ;

    //Metodo personalizado: Spring genera el SQL "SELECT * FROM user WHERE email = ?" automáticamente
    Optional<User> findByEmail(String email);
}

