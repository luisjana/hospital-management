package com.example.hospital_management.controller;

import com.example.hospital_management.DTO.DoctorDTO;// DTO për të transferuar të dhëna nga entiteti Doctor
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.entity.Doctor;// Entiteti që përfaqëson një mjek
import com.example.hospital_management.repository.DepartmentRepository;
import com.example.hospital_management.service.DoctorService; // Shërbimi që menaxhon logjikën e biznesit për Doctor
import org.springframework.beans.factory.annotation.Autowired;// Anotacion për injektimin automatik të varësive
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;// Përdoret për të kthyer përgjigje HTTP me statusin përkatës
import org.springframework.web.bind.annotation.*;// Importimi i anotacioneve REST për Spring Boot

import java.util.List;// Lista për të mbajtur disa objekte DoctorDTO
import java.util.Optional;// Përdoret për të menaxhuar objektet që mund të jenë null


// Kjo klasë është një kontrollues REST që menaxhon mjekët e spitalit
@RestController
@RequestMapping("/api/doctors")// Përcakton rrugën bazë për të gjitha endpoint-et e kësaj klase
public class DoctorController {

    @Autowired
    private DepartmentRepository departmentRepository;
    // Injeksioni automatik i shërbimit të mjekëve
    @Autowired
    private DoctorService doctorService;

    // Endpoint për të marrë të gjithë mjekët dhe kthyer si DTO
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();// Merr të gjithë mjekët nga shërbimi dhe i kthen si DTO
        return ResponseEntity.ok(doctors);// Kthen listën me statusin 200 OK
    }

    // Endpoint për të marrë një mjek të veçantë nga ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        Optional<DoctorDTO> doctor = doctorService.getDoctorById(id); // Kërkon një mjek me ID e dhënë
        return doctor.map(ResponseEntity::ok) // Nëse ekziston, e kthen me status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        if (doctor.getDepartment() == null || doctor.getDepartment().getId() == null) {
            return ResponseEntity.badRequest().body(null); // Kthehet 400 Bad Request nëse departmentId mungon
        }

        Optional<Department> departmentOpt = departmentRepository.findById(doctor.getDepartment().getId());

        if (departmentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Nëse departamenti nuk ekziston, kthehet një gabim
        }

        doctor.setDepartment(departmentOpt.get()); // Vendos departamentin për doktorin
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }


    // Endpoint për të përditësuar një mjek ekzistues
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor)// Përditëson mjekun me ID e dhënë
                .map(ResponseEntity::ok)// Nëse ekziston, e kthen me status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        if (doctorService.deleteDoctor(id)) {
            return ResponseEntity.ok("Doctor deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
        }
    }

}
