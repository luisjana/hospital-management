package com.example.hospital_management.controller;

import com.example.hospital_management.entity.Admission;// Entiteti Admission që përfaqëson një pranimin në spital
import com.example.hospital_management.repository.AdmissionRepository;// Repository për të menaxhuar të dhënat e pranimeve
import com.example.hospital_management.service.AdmissionService;
import jakarta.validation.Valid;// Përdoret për të validuar objektet e marra në kërkesat HTTP
import org.springframework.beans.factory.annotation.Autowired;// Anotacion për injektimin automatik të varësive
import org.springframework.http.HttpStatus;// Për të përdorur statuset HTTP në përgjigjet API
import org.springframework.http.ResponseEntity;// Përdoret për të kthyer përgjigje HTTP me statusin përkatës
import org.springframework.web.bind.annotation.*;// Importimi i anotacioneve REST për Spring Boot
import java.util.List;// Lista për të mbajtur disa objekte Admission
import java.util.Optional;// Përdoret për të menaxhuar objektet që mund të jenë null

// Kjo klasë është një kontrollues REST që menaxhon pranimet në spital
@RestController
@RequestMapping("/api/admissions")// Përcakton rrugën bazë për të gjitha endpoint-et e kësaj klase
public class AdmissionController {

    // Repository për të komunikuar me bazën e të dhënave
    @Autowired
    private AdmissionRepository admissionRepository;
    @Autowired
    private AdmissionService admissionService;
    @GetMapping
    // Kthen një listë me të gjitha pranimet nga baza e të dhënave
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    // Endpoint për të marrë një pranim të veçantë nga ID
    @GetMapping("/{id}")
    public ResponseEntity<Admission> getAdmissionById(@PathVariable Long id) {
        Optional<Admission> admission = admissionRepository.findById(id);// Kërkon një pranim me ID e dhënë
        return admission.map(ResponseEntity::ok)// Nëse ekziston, kthen statusin 200 OK dhe objektin
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }
    // Endpoint për të krijuar një pranim të ri
    @PostMapping
    public ResponseEntity<Admission> createAdmission(@RequestBody Admission admission) {
        Admission savedAdmission = admissionService.createAdmission(admission);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmission);
    }

    // Endpoint për të përditësuar një pranim ekzistues
    @PutMapping("/{id}")
    public ResponseEntity<Admission> updateAdmission(@PathVariable Long id, @RequestBody @Valid Admission admissionDetails) {
        return admissionRepository.findById(id) // Kërkon një pranim me ID e dhënë
                .map(admission -> {
                    // Përditëson fushat e pranimit ekzistues me të dhënat e reja
            admission.setPatient(admissionDetails.getPatient());
            admission.setDoctor(admissionDetails.getDoctor());
            admission.setAdmissionDate(admissionDetails.getAdmissionDate());
            admission.setDischargeDate(admissionDetails.getDischargeDate());
            return ResponseEntity.ok(admissionRepository.save(admission));// Ruajtja dhe kthimi i objektit të përditësuar
        }).orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }


    // Endpoint për të fshirë një pranim nga ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        if (admissionRepository.existsById(id)) {// Kontrollon nëse ekziston një pranim me ID e dhënë
            admissionRepository.deleteById(id);// Fshin pranimin nga baza e të dhënave
            return ResponseEntity.noContent().build();// Kthen 204 No Content për të treguar që fshirja ishte e suksesshme
        }
        return ResponseEntity.notFound().build();// Nëse nuk gjendet, kthen 404 Not Found
    }
}
