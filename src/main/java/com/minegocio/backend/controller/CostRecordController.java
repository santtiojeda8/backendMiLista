package com.minegocio.backend.controller;

import com.minegocio.backend.dto.costrecorddto.CostRecordRequest;
import com.minegocio.backend.dto.costrecorddto.CostRecordResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.service.CostRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/costRecord")
public class CostRecordController {
    private final CostRecordService costRecordService;

    public CostRecordController(CostRecordService costRecordService) {
        this.costRecordService = costRecordService;
    }

    @GetMapping("/{idProduct}/allActiveCostRecord")
    public ResponseEntity<List<CostRecordResponse>> getAllActiveCostRecord (@PathVariable UUID idProduct, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(costRecordService.getAllActiveRecords(idProduct, logedUser.getIdUser()));
    }


    @PostMapping("/{idProduct}/{idSupplier}")
    public ResponseEntity<CostRecordResponse> createCostRecord (@RequestBody CostRecordRequest costRecord, @PathVariable UUID idProduct, @PathVariable UUID idSupplier, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok( costRecordService.createCostRecord(costRecord, idProduct, idSupplier, logedUser.getIdUser()));
    }

    @PatchMapping("/{idCostRecord}")
    public ResponseEntity<CostRecordResponse> softDelete (@PathVariable UUID idCostRecord, @AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(costRecordService.softDeleteCostRecord(idCostRecord, logedUser.getIdUser()));
    }

    // Este endpoint HTTP lo ocultamos porque en realidad no nos es útil por el momento. El usuario no necesita ver solo un CostRecord por separado, puede ver todos.
    /*
    @GetMapping("/{idProduct}/{idUser}/allCostRecord")
    public List<CostRecordResponse> getAllCostRecord (@PathVariable Integer idProduct, @PathVariable Integer idUser){
        return costRecordService.getAllRecords(idProduct, idUser);
    }
    */

    // Este endpoint HTTP lo ocultamos porque en realidad no nos es útil por el momento. La app no necesita ver solo un CostRecord por separado.
    /*
    @GetMapping("/{id}")
    public CostRecordResponse getCostRecordById (@PathVariable Integer id){
        return costRecordService.getCostRecordById(id);
    }
    */
}