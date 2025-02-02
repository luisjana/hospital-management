package com.example.hospital_management.DTO; // Përcakton paketën ku ndodhet klasa DTO

// Importimi i klasave të nevojshme
import com.example.hospital_management.entity.Doctor; // Importimi i entitetit Doctor
import com.fasterxml.jackson.annotation.JsonFormat; // Anotacion për të formatuar fushën birthDate në format të caktuar
import lombok.Getter; // Gjeneron automatikisht metodat `get()`
import lombok.Setter; // Gjeneron automatikisht metodat `set()`

import java.time.LocalDate; // Importimi i LocalDate për fushën birthDate

// DTO (Data Transfer Object) për Doctor
@Getter // Gjeneron automatikisht metodat get për fushat e klasës
@Setter // Gjeneron automatikisht metodat set për fushat e klasës
public class DoctorDTO {

    private Long id; // ID e doktorit
    private String firstName; // Emri i doktorit
    private String lastName; // Mbiemri i doktorit
    private String specialization; // Specializimi i doktorit
    private String phone; // Numri i telefonit të doktorit

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Formaton datën në formatin YYYY-MM-DD
    private LocalDate birthDate; // Data e lindjes së doktorit

    private String departmentName; // Emri i departamentit ku punon doktori në vend të objektit `Department`

    // Konstruktor për të konvertuar nga një objekt `Doctor` në `DoctorDTO`
    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId(); // Inicializon ID e DTO me ID e doktorit
        this.firstName = doctor.getFirstName(); // Inicializon emrin e doktorit
        this.lastName = doctor.getLastName(); // Inicializon mbiemrin e doktorit
        this.specialization = doctor.getSpecialization(); // Inicializon specializimin e doktorit
        this.phone = doctor.getPhone(); // Inicializon numrin e telefonit të doktorit
        this.birthDate = doctor.getBirthDate(); // Inicializon datën e lindjes së doktorit

        // Kontrollon nëse doktori ka një departament, nëse jo, vendos "No Department"
        this.departmentName = (doctor.getDepartment() != null) ? doctor.getDepartment().getName() : "No Department";
    }
}
