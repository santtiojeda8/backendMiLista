package com.minegocio.backend.repository;

import com.minegocio.backend.entity.CostRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CostRecordRepository extends JpaRepository<CostRecord, UUID> {
    //Aquí armamos las consultas personalizadas a la DB (MySQL) para los registros de costo.
    // Spring JPA navega: CostRecord -> Product -> User -> idUser
    List<CostRecord> findByProductIdProductAndProductUserIdUserAndActiveTrue (UUID idProduct, UUID idUser);

    /*
    List<CostRecord> findByProductIdProductAndProductUserIdUser (Integer idProduct, Integer idUser);
    */

    Optional<CostRecord> findByIdCostRecordAndProductUserIdUser(UUID idCostRecord, UUID idUser);
}


