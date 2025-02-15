package com.example.hospital_management.controller;

import com.example.hospital_management.DTO.DepartmentDTO;// DTO për të transferuar të dhëna nga entiteti Department
import com.example.hospital_management.entity.Department;// Entiteti që përfaqëson një departament
import com.example.hospital_management.service.DepartmentService;// Shërbimi që menaxhon logjikën e biznesit për Department
import org.springframework.beans.factory.annotation.Autowired;// Anotacion për injektimin automatik të varësive
import org.springframework.http.ResponseEntity;// Përdoret për të kthyer përgjigje HTTP me statusin përkatës
import org.springframework.web.bind.annotation.*; // Importimi i anotacioneve REST për Spring Boot

import java.util.List;// Lista për të mbajtur disa objekte DepartmentDTO
import java.util.stream.Collectors;// Përdoret për të konvertuar lista me Stream API

// Kjo klasë është një kontrollues REST që menaxhon departamentet e spitalit
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {


    // Injektimi i shërbimit që menaxhon departamentet
    private final DepartmentService departmentService;

    @Autowired// Konstruktor me injektim të varësisë për `DepartmentService`
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Endpoint për të marrë të gjithë departamentet dhe kthyer si DTO
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments()
                .stream()
                .map(DepartmentDTO::new) // Konverto `Department` në `DepartmentDTO`
                .collect(Collectors.toList());// Kthen listën e përpunuar si DTO

        return ResponseEntity.ok(departments);// Kthen listën me statusin 200 OK
    }

    // Endpoint për të marrë një departament të veçantë nga ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)// Kërkon një departament me ID e dhënë
                .map(department -> ResponseEntity.ok(new DepartmentDTO(department)))// Nëse ekziston, e kthen si DTO me status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }

    // Endpoint për të krijuar një departament të ri
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);// Ruajtja e departamentit në bazën e të dhënave
        return ResponseEntity.ok(createdDepartment);// Kthen statusin 200 OK dhe objektin e ruajtur
    }

    // Endpoint për të përditësuar një departament ekzistues
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department)// Përditëson departamentin
                .map(updatedDepartment -> ResponseEntity.ok(updatedDepartment))// Nëse ekziston, e kthen me status 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());// Nëse nuk gjendet, kthen 404 Not Found
    }
    // Endpoint për të fshirë një departament nga ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        boolean deleted = departmentService.deleteDepartment(id);// Kërkon të fshijë departamentin
        if (deleted) {
            return ResponseEntity.noContent().build();// Nëse fshihet, kthen 204 No Content
        } else {
            return ResponseEntity.notFound().build();// Nëse nuk gjendet, kthen 404 Not Found
        }
    }


}