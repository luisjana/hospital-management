package com.example.hospital_management.entity; // Përcakton paketën ku ndodhet klasa entity

// Importimi i klasave të nevojshme për JPA dhe Lombok
import com.fasterxml.jackson.annotation.JsonIgnore; // Anotacion për të shmangur ciklet rekursive në JSON
import jakarta.persistence.*; // Importimi i anotacioneve për bazën e të dhënave (JPA)
import lombok.*; // Importimi i anotacioneve për të automatizuar krijimin e getter/setter dhe konstruktorëve

import java.util.ArrayList; // Përdoret për inicializimin e listës së pacientëve
import java.util.Collections; // Përdoret për kthimin e një liste të zbrazët në vend të `null`
import java.util.List; // Përdoret për ruajtjen e pacientëve të një departamenti

// Anotacion që tregon se kjo është një entitet që do të ruhet në bazën e të dhënave
@Setter
@Entity
@Data // Gjeneron automatikisht metodat `get()`, `set()`, `toString()`, `equals()` dhe `hashCode()`
@NoArgsConstructor // Gjeneron një konstruktor pa parametra
@AllArgsConstructor // Gjeneron një konstruktor me të gjitha fushat si parametra
@Table(name = "departments") // Përcakton emrin e tabelës në bazën e të dhënave
public class Department {

    // Setter për ID-në e departamentit
    // Getter për ID-në e departamentit
    @Getter
    @Id // Përcakton këtë fushë si identifikuesin unik të entitetit
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-inkrementohet nga baza e të dhënave
    private Long id; // Identifikuesi unik për departamentin

    // Setter për emrin e departamentit
    // Getter për emrin e departamentit
    @Getter
    private String name; // Emri i departamentit

    // Setter për listën e pacientëve
    @OneToMany(mappedBy = "department") // Përcakton marrëdhënien një-në-shumë me pacientët
    @JsonIgnore // Shmang ciklet rekursive kur një departament referon pacientët dhe pacientët referojnë departamentin
    private List<Patient> patients = new ArrayList<>(); // Lista e pacientëve që i përkasin këtij departamenti

    // Getter për listën e pacientëve
    public List<Patient> getPatients() {
        return patients == null ? Collections.emptyList() : patients; // Nëse lista është `null`, kthehet një listë bosh
    }

}
