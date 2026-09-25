package com.minegocio.backend.service;

import com.minegocio.backend.dto.costrecorddto.CostRecordRequest;
import com.minegocio.backend.dto.costrecorddto.CostRecordResponse;
import com.minegocio.backend.entity.CostRecord;
import com.minegocio.backend.repository.CostRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CostRecordService {

    private final CostRecordRepository costRecordRepository;
    private final ProductService productService;
    private final SupplierService supplierService;

    public CostRecordService(CostRecordRepository costRecordRepository, ProductService productService, SupplierService supplierService) {
        this.costRecordRepository = costRecordRepository;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    public List<CostRecordResponse> getAllActiveRecords (UUID idProduct, UUID idUser){
        return costRecordRepository.findByProductIdProductAndProductUserIdUserAndActiveTrue(idProduct, idUser)
                .stream()
                .map(costRecord -> new CostRecordResponse(costRecord.getProduct().getName(), costRecord.getSupplier().getName(), costRecord.getDate(),costRecord.getCostPrice()))
                .toList();
    }

    public CostRecordResponse createCostRecord(CostRecordRequest costRecord, UUID idProduct, UUID idSupplier, UUID idUser){

        CostRecord existingEntity = new CostRecord();

        //Definimos el valor del CostRecord y validamos que el Producto y el Usuario que quiere crear el CostRecord sean el mismo.
        existingEntity.setProduct(
                productService.findProductById(idProduct, idUser)
        );

        existingEntity.setSupplier(
                supplierService.findSupplierById(idSupplier, idUser)
        );

        existingEntity.setDate(LocalDate.now());

        if(costRecord.getCostPrice().compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("El precio no puede ser negativo, ni cero");
        existingEntity.setCostPrice(costRecord.getCostPrice());

        costRecordRepository.save(existingEntity);

        return new CostRecordResponse(existingEntity.getProduct().getName(), existingEntity.getSupplier().getName(), existingEntity.getDate(),existingEntity.getCostPrice());
    }

    public CostRecordResponse getCostRecordById(UUID id){
        CostRecord existingEntity =  costRecordRepository.findById(id).orElseThrow( () -> new RuntimeException("No se encontró el CostRecord con ID: "+ id) );
        return new CostRecordResponse(existingEntity.getProduct().getName(), existingEntity.getSupplier().getName(), existingEntity.getDate(),existingEntity.getCostPrice()) ;
    }

    /*
    * CORRECIÓN--> Este metodo lo vamos a aceptar ya que NO vamos a permitir que el usuario elimine un registro de compra.
    * Van a quedar todos registrados si o si.
    */
    public CostRecordResponse softDeleteCostRecord (UUID idCostRecord, UUID idUser){
        CostRecord existingEntity = costRecordRepository.findByIdCostRecordAndProductUserIdUser(idCostRecord, idUser).orElseThrow( () -> new RuntimeException("No se encotró el registro con ID: " + idCostRecord));
        existingEntity.setActive(false);
        costRecordRepository.save(existingEntity);
        return new CostRecordResponse(existingEntity.getProduct().getName(), existingEntity.getSupplier().getName(), existingEntity.getDate(),existingEntity.getCostPrice());
    }

    //Este metodo también lo vamos a tener oculto ya que la APP por el momento no necesita saber si hay registros eliminados
    /*
    public List<CostRecord> getAllRecords (Integer idProduct, Integer idUser){
        return costRecordRepository.findByProductIdProductAndProductUserIdUser(idProduct, idUser);
    }
    */
}
