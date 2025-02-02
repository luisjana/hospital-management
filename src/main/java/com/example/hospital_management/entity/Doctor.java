package com.example.hospital_management.entity; // Përcakton paketën ku ndodhet kjo klasë entity

// Importimi i klasave të nevojshme për JPA, validim dhe Lombok
import jakarta.persistence.*; // Importimi i anotacioneve për bazën e të dhënave (JPA)
import jakarta.validation.constraints.NotBlank; // Importimi i anotacionit për të siguruar që fusha nuk është bosh
import lombok.*; // Importimi i anotacioneve për të automatizuar krijimin e getter/setter dhe konstruktorëve

import java.time.LocalDate; // Importimi i LocalDate për të ruajtur datën e lindjes së doktorit

// Anotacion që tregon se kjo është një entitet që do të ruhet në bazën e të dhënave
@Entity
@Data // Gjeneron automatikisht metodat `get()`, `set()`, `toString()`, `equals()` dhe `hashCode()`
@NoArgsConstructor // Gjeneron një konstruktor pa parametra
@AllArgsConstructor // Gjeneron një konstruktor me të gjitha fushat si parametra
@Table(name = "doctors") // Përcakton emrin e tabelës në bazën e të dhënave
public class Doctor {

    @Id // Përcakton këtë fushë si identifikuesin unik të entitetit
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-inkrementohet nga baza e të dhënave
    private Long id; // Identifikuesi unik për doktorin

    @NotBlank // Siguron që emri nuk mund të jetë bosh
    private String firstName; // Emri i doktorit

    @NotBlank // Siguron që mbiemri nuk mund të jetë bosh
    private String lastName; // Mbiemri i doktorit

    @NotBlank // Siguron që specializimi nuk mund të jetë bosh
    private String specialization; // Specializimi i doktorit (p.sh., Kardiolog, Neurolog, etj.)

    @Column(nullable = false) // Siguron që vlera nuk mund të jetë null në bazën e të dhënave
    private String phone; // Numri i telefonit të doktorit

    @Column(nullable = false) // Siguron që data e lindjes nuk mund të jetë null
    private LocalDate birthDate; // Data e lindjes së doktorit

    @ManyToOne // Lidhja shumë-në-një (shumë doktorë mund të jenë në një departament)
    @JoinColumn(name = "department_id", nullable = false) // Krijon kolonën `department_id` si çelës të huaj
    private Department department; // Departamenti ku punon doktori
}
