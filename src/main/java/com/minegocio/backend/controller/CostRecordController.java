package com.minegocio.backend.controller;

import com.minegocio.backend.dto.costrecorddto.CostRecordRequest;
import com.minegocio.backend.dto.costrecorddto.CostRecordResponse;
import com.minegocio.backend.service.CostRecordService;
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

    @GetMapping("/{idProduct}/{idUser}/allActiveCostRecord")
    public List<CostRecordResponse> getAllActiveCostRecord (@PathVariable UUID idProduct, @PathVariable UUID idUser){
        return costRecordService.getAllActiveRecords(idProduct, idUser);
    }


    @PostMapping("/{idProduct}/{idSupplier}/{idUser}")
    public CostRecordResponse createCostRecord (@RequestBody CostRecordRequest costRecord, @PathVariable UUID idProduct, @PathVariable UUID idSupplier, @PathVariable UUID idUser){
        return costRecordService.createCostRecord(costRecord, idProduct, idSupplier, idUser);
    }

    @PatchMapping("/{idCostRecord}/{idUser}")
    public CostRecordResponse softDelete (@PathVariable UUID idCostRecord, @PathVariable UUID idUser){
        return costRecordService.softDeleteCostRecord(idCostRecord, idUser);
    }

    // Este endpoint HTTP lo ocultamos porque en realidad no nos es útil por el momento. La app no necesita ver solo un CostRecord por separado.
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
