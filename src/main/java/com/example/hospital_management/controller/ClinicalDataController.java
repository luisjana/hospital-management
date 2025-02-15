package com.example.hospital_management.controller;

// Importimi i klasave të nevojshme
import com.example.hospital_management.entity.ClinicalData;
import com.example.hospital_management.service.ClinicalDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Deklarojmë këtë klasë si një REST Controller për të trajtuar kërkesat HTTP
@RestController

// Përcaktojmë rrugën bazë (base URL) për këtë controller
@RequestMapping("/api/clinical-data")
public class ClinicalDataController {

    // Deklarojmë një variabël private finale për shërbimin (service)
    private final ClinicalDataService clinicalDataService;

    /**
     * Konstruktori për të inicializuar ClinicalDataService me anë të injektimit të varësive (Dependency Injection)
     * @param clinicalDataService Shtresa e shërbimit që trajton logjikën e biznesit
     */
    public ClinicalDataController(ClinicalDataService clinicalDataService) {
        this.clinicalDataService = clinicalDataService;
    }

    /**
     * Metodë për të shtuar të dhëna klinike për një pacient specifik
     * Rruga (URL) për këtë metodë: POST /api/clinical-data/patients/{patientId}
     *
     * @param patientId ID e pacientit (merret nga rruga URL)
     * @param note Shënimi i të dhënave klinike (merret nga trupi i kërkesës - request body)
     * @return ResponseEntity me një mesazh suksesi
     */
    @PostMapping("/patients/{patientId}")
    public ResponseEntity<String> addClinicalData(
            @PathVariable Long patientId,         // @PathVariable kap vlerën e patientId nga URL
            @RequestBody String note              // @RequestBody kap vlerën e 'note' nga trupi i kërkesës
    ) {
        // Thirrim shërbimin për të shtuar të dhënat klinike
        clinicalDataService.addClinicalData(patientId, note);

        // Kthejmë një përgjigje (response) me një mesazh suksesi
        return ResponseEntity.ok("Clinical data added successfully.");
    }

    /**
     * Metodë për të marrë të gjitha të dhënat klinike të një pacienti
     * Rruga (URL) për këtë metodë: GET /api/clinical-data/patients/{patientId}
     *
     * @param patientId ID e pacientit (merret nga rruga URL)
     * @return ResponseEntity me një listë të të dhënave klinike të pacientit
     */
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<ClinicalData>> getClinicalDataByPatient(
            @PathVariable Long patientId         // @PathVariable kap vlerën e patientId nga URL
    ) {
        // Marrim listën e të dhënave klinike nga shërbimi
        List<ClinicalData> clinicalDataList = clinicalDataService.getClinicalDataByPatient(patientId);

        // Kthejmë një përgjigje me listën e të dhënave klinike
        return ResponseEntity.ok(clinicalDataList);
    }
}
