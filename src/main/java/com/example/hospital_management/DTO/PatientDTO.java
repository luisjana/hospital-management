package com.example.hospital_management.DTO; // Përcakton paketën ku ndodhet klasa DTO

// Importimi i klasave të nevojshme
import com.example.hospital_management.entity.Gender; // Importimi i enum Gender për të përfaqësuar gjininë
import com.example.hospital_management.entity.Patient; // Importimi i entitetit Patient
import lombok.Getter; // Gjeneron automatikisht metodat `get()`
import lombok.Setter; // Gjeneron automatikisht metodat `set()`
import java.time.LocalDate; // Importimi i LocalDate për fushën birthDate
import lombok.NoArgsConstructor;
// DTO (Data Transfer Object) për Patient
@Getter // Gjeneron automatikisht metodat get për fushat e klasës
@Setter // Gjeneron automatikisht metodat set për fushat e klasës
@NoArgsConstructor
public class PatientDTO {

    private Long id; // ID e pacientit
    private String firstName; // Emri i pacientit
    private String lastName; // Mbiemri i pacientit
    private LocalDate birthDate; // Data e lindjes së pacientit
    private Gender gender; // Gjinia e pacientit (Male, Female, Other)
    private String phone; // Numri i telefonit të pacientit
    private String email; // Email i pacientit
    private String address; // Adresa e pacientit

    // Konstruktor për të konvertuar nga një objekt `Patient` në `PatientDTO`
    public PatientDTO(Patient patient) {
        this.id = patient.getId(); // Inicializon ID e DTO me ID e pacientit
        this.firstName = patient.getFirstName(); // Inicializon emrin e pacientit
        this.lastName = patient.getLastName(); // Inicializon mbiemrin e pacientit
        this.birthDate = patient.getBirthDate(); // Inicializon datën e lindjes së pacientit
        this.gender = patient.getGender(); // Inicializon gjininë e pacientit
        this.phone = patient.getPhone(); // Inicializon numrin e telefonit të pacientit
        this.email = patient.getEmail(); // Inicializon emailin e pacientit
        this.address = patient.getAddress(); // Inicializon adresën e pacientit
    }
}
