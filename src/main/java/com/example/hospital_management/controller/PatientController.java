package com.example.hospital_management.controller;

import com.example.hospital_management.DTO.PatientDTO;// DTO për të transferuar të dhëna nga entiteti Patient
import com.example.hospital_management.entity.Patient; // Entiteti që përfaqëson një pacient
import com.example.hospital_management.service.PatientService;// Shërbimi që menaxhon logjikën e biznesit për pacientët
import jakarta.validation.Valid;// Anotacion për validimin e hyrjeve në kërkesat HTTP
import org.springframework.beans.factory.annotation.Autowired;// Anotacion për injektimin automatik të varësive
import org.springframework.http.HttpStatus;// Përdoret për të përcaktuar statuset HTTP në përgjigje
import org.springframework.http.ResponseEntity;// Përdoret për të kthyer përgjigje HTTP me statusin përkatës
import org.springframework.web.bind.annotation.*; // Importimi i anotacioneve REST për Spring Boot

import java.util.List;// Lista për të mbajtur disa objekte PatientDTO
import java.util.Optional;// Përdoret për të menaxhuar objektet që mund të jenë null
import java.util.stream.Collectors;// Përdoret për të konvertuar lista me Stream API

// Kjo klasë është një kontrollues REST që menaxhon pacientët e spitalit
@RestController
@RequestMapping("/api/patients")// Përcakton rrugën bazë për të gjitha endpoint-et e kësaj klase
public class PatientController {

    // Injeksioni automatik i shërbimit të pacientëve
    @Autowired
    private PatientService patientService;

    // Endpoint për të marrë të gjithë pacientët dhe kthyer si DTO
    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients()// Merr të gjithë pacientët nga shërbimi
                .stream()
                .map(PatientDTO::new)// Konverton çdo `Patient` në një objekt `PatientDTO`
                .collect(Collectors.toList()); // Kthen listën e përpunuar si DTO
    }

    // Endpoint për të marrë një pacient të veçantë nga ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);// Kërkon një pacient me ID e dhënë
        return patient.map(ResponseEntity::ok)// Nëse ekziston, e kthen me status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }

    // Endpoint për të krijuar një pacient të ri
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);// Ruajtja e pacientit në bazën e të dhënave
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);// Kthen statusin 201 Created dhe objektin e ruajtur
    }

    // Endpoint për të përditësuar një pacient ekzistues
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody @Valid Patient patientDetails) {
        return patientService.getPatientById(id)// Kërkon një pacient me ID e dhënë
                .map(existingPatient -> {
                    // Përditëson fushat e pacientit ekzistues me të dhënat e reja
            existingPatient.setFirstName(patientDetails.getFirstName());
            existingPatient.setLastName(patientDetails.getLastName());
            existingPatient.setBirthDate(patientDetails.getBirthDate());
            existingPatient.setGender(patientDetails.getGender());
            existingPatient.setPhone(patientDetails.getPhone());
            existingPatient.setEmail(patientDetails.getEmail());
            existingPatient.setAddress(patientDetails.getAddress());

            return ResponseEntity.ok(patientService.savePatient(existingPatient));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint për të fshirë një pacient nga ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {// Kërkon të fshijë pacientin me ID e dhënë
            return ResponseEntity.noContent().build(); // Nëse fshihet, kthen 204 No Content
        }
        return ResponseEntity.notFound().build();// Nëse nuk gjendet, kthen 404 Not Found
    }
}
