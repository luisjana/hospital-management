package com.example.hospital_management.DTO; // Përcakton paketën ku ndodhet klasa DTO

// Importimi i klasave të nevojshme
import com.example.hospital_management.entity.Department; // Importimi i entitetit Department
import com.example.hospital_management.entity.Patient; // Importimi i entitetit Patient
import lombok.Getter; // Anotacion për të gjeneruar automatikisht metodat `get`
import lombok.Setter; // Anotacion për të gjeneruar automatikisht metodat `set`

import java.util.List; // Importimi i listës për të mbajtur emrat e pacientëve
import java.util.stream.Collectors; // Importimi i Stream API për transformim të listave

// DTO (Data Transfer Object) për Department
@Getter // Gjeneron automatikisht metodat get për fushat e klasës
@Setter // Gjeneron automatikisht metodat set për fushat e klasës
public class DepartmentDTO {

    private Long id; // ID e departamentit
    private String name; // Emri i departamentit
    private List<String> patients; // Lista e emrave të pacientëve që i përkasin këtij departamenti

    // Konstruktor që konverton një objekt `Department` në `DepartmentDTO`
    public DepartmentDTO(Department department) {
        this.id = department.getId(); // Inicializon ID-në e DTO me ID-në e departamentit
        this.name = department.getName(); // Inicializon emrin e DTO me emrin e departamentit

        // Konverton listën e pacientëve nga entiteti `Department` në një listë të emrave të pacientëve
        this.patients = department.getPatients().stream()
                .map(patient -> patient.getFirstName() + " " + patient.getLastName()) // Ruaj vetëm emrin dhe mbiemrin
                .collect(Collectors.toList()); // Mblidh të dhënat e përpunuara në një listë
    }
}
