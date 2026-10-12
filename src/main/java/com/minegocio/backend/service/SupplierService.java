package com.minegocio.backend.service;

import com.minegocio.backend.dto.supplierdto.SupplierRequest;
import com.minegocio.backend.dto.supplierdto.SupplierResponse;
import com.minegocio.backend.entity.Supplier;
import com.minegocio.backend.exception.ResourceNotFoundException;
import com.minegocio.backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final UserService userService;

    public SupplierService(SupplierRepository supplierRepository, UserService userService) {
        this.supplierRepository = supplierRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllActiveSupplier(UUID idUser) {
        return supplierRepository.findByUserIdUserAndActiveTrue(idUser)
                .stream()
                .map(supplier -> new SupplierResponse(supplier.getIdSupplier(),supplier.getName()))
                .toList();
        /*
        * La línea anterior es un resumen y una simplificación de las siguientes 2 líneas:
        *
        * List<SupplierResponse> supplierResponse =  supplierRepository.findByUserIdUserAndActive(idUser, (byte) 1).stream().map(supplier -> new SupplierResponse(supplier.getName())).toList();
        * return supplierResponse;
        *
        * Básicamente retornamos directamente una lista ya armada de SupplierResponse. la función .toList() del final devuelve una List<SupplierResponse>
        * */
    }

    @Transactional
    public SupplierResponse createSupplier(SupplierRequest supplier, UUID idUser) {

        //Que tengo que resolver aca?
        /*
        * Me llega un objeto de tipo SupplierRequest, el cual solo tiene la prodiedad nombre.
        * Tengo que instanciar/crear un supplier, tomar el nombre del SupplierRequest y asignarselo al nuevo supplier
        */

        Supplier sup = new Supplier();
        sup.setUser(userService.findUserById(idUser));
        sup.setName(supplier.getName());
        supplierRepository.save(sup);

        return new SupplierResponse(sup.getIdSupplier(),sup.getName());
    }

    @Transactional(readOnly = true)
    public SupplierResponse getSupplierByIdDTO (UUID idSupplier, UUID idUser) {
        Supplier existingEntity = supplierRepository.findByIdSupplierAndUserIdUser(idSupplier, idUser).orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con el ID: " + idSupplier));
        return new SupplierResponse(existingEntity.getIdSupplier(),existingEntity.getName());
    }

    //Este metodo devuelve el Supplier completo, lo cual es útil para trabajar con relaciones entre entidades.
    @Transactional(readOnly = true)
    public Supplier findSupplierById( UUID idSupplier, UUID idUser){
        return supplierRepository.findByIdSupplierAndUserIdUser(idSupplier, idUser).orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con el ID: " + idSupplier));
    }

    @Transactional
    public SupplierResponse updateSupplier(SupplierRequest supplier, UUID idSupplier, UUID idUser) {
        Supplier existingEntity = supplierRepository.findByIdSupplierAndUserIdUser(idSupplier, idUser).orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con el ID: " + idSupplier));

        if (supplier.getName() != null) {
            existingEntity.setName(supplier.getName());
        }

        supplierRepository.save(existingEntity);

        return new SupplierResponse(existingEntity.getIdSupplier(),existingEntity.getName());
    }

    @Transactional
    public SupplierResponse softDelete(UUID idSupplier, UUID idUser) {
        Supplier existingEntity = supplierRepository.findByIdSupplierAndUserIdUser(idSupplier, idUser).orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con el ID: " + idSupplier));

        existingEntity.setActive(false);

        supplierRepository.save(existingEntity);

        return new SupplierResponse(existingEntity.getIdSupplier(),existingEntity.getName());
    }
}

