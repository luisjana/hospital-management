package com.example.hospital_management.controller;

import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.repository.AdmissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionRepository admissionRepository;

    @GetMapping
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admission> getAdmissionById(@PathVariable Long id) {
        Optional<Admission> admission = admissionRepository.findById(id);
        return admission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Admission> createAdmission(@RequestBody @Valid Admission admission) {
        Admission savedAdmission = admissionRepository.save(admission);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admission> updateAdmission(@PathVariable Long id, @RequestBody @Valid Admission admissionDetails) {
        return admissionRepository.findById(id).map(admission -> {
            admission.setPatient(admissionDetails.getPatient());
            admission.setDoctor(admissionDetails.getDoctor());
            admission.setAdmissionDate(admissionDetails.getAdmissionDate());
            admission.setDischargeDate(admissionDetails.getDischargeDate());
            return ResponseEntity.ok(admissionRepository.save(admission));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        if (admissionRepository.existsById(id)) {
            admissionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
