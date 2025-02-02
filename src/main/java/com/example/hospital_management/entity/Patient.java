package com.example.hospital_management.entity; // Përcakton paketën ku ndodhet kjo klasë entity

// Importimi i klasave të nevojshme për JPA, validim dhe Lombok
import jakarta.persistence.*; // Importimi i anotacioneve për bazën e të dhënave (JPA)
import jakarta.validation.constraints.*; // Importimi i anotacioneve për validimin e fushave të klasës
import lombok.*; // Importimi i anotacioneve për të automatizuar krijimin e getter/setter dhe konstruktorëve

import java.time.LocalDate; // Importimi i LocalDate për të ruajtur datën e lindjes së pacientit

// Anotacion që tregon se kjo është një entitet që do të ruhet në bazën e të dhënave
@Entity
@Getter // Gjeneron automatikisht metodat get për fushat e klasës
@Setter // Gjeneron automatikisht metodat set për fushat e klasës
@NoArgsConstructor // Gjeneron një konstruktor pa parametra
@AllArgsConstructor // Gjeneron një konstruktor me të gjitha fushat si parametra
@Table(name = "patients") // Përcakton emrin e tabelës në bazën e të dhënave
public class Patient {

    @Id // Përcakton këtë fushë si identifikuesin unik të entitetit
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-inkrementohet nga baza e të dhënave
    private Long id; // Identifikuesi unik për pacientin

    @NotBlank // Siguron që emri nuk mund të jetë bosh ose të përmbajë vetëm hapësira
    private String firstName; // Emri i pacientit

    @NotBlank // Siguron që mbiemri nuk mund të jetë bosh ose të përmbajë vetëm hapësira
    private String lastName; // Mbiemri i pacientit

    @NotNull // Siguron që data e lindjes nuk mund të jetë null
    private LocalDate birthDate; // Data e lindjes së pacientit

    @NotNull // Siguron që gjinia nuk mund të jetë null
    @Enumerated(EnumType.STRING) // Ruajtja e vlerës si string në bazën e të dhënave
    private Gender gender; // Gjinia e pacientit (MALE, FEMALE, OTHER)

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone must be in XXX-XXX-XXXX format") // Validon formatin e numrit të telefonit
    private String phone; // Numri i telefonit të pacientit

    @Email // Siguron që email-i është në format të vlefshëm
    private String email; // Email i pacientit

    private String address; // Adresa e pacientit

    @ManyToOne // Lidhja shumë-në-një (shumë pacientë mund të jenë në një departament)
    @JoinColumn(name = "department_id", nullable = true) // Lidhja me `Department`, por nuk është e detyrueshme
    private Department department; // Departamenti ku është regjistruar pacienti (opsional)
}
