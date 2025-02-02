package com.example.hospital_management.entity; // Përcakton paketën ku ndodhet klasa entity

// Importimi i klasave të nevojshme për JPA dhe Lombok
import jakarta.persistence.*; // Importimi i anotacioneve për bazën e të dhënave (JPA)
import lombok.*; // Importimi i anotacioneve për të automatizuar krijimin e getter/setter dhe konstruktorëve

import java.util.Date; // Importimi i Date për të ruajtur datat e pranimit dhe lirimit të pacientit

// Anotacion që tregon se kjo është një entitet që do të ruhet në bazën e të dhënave
@Entity
@Getter // Gjeneron automatikisht metodat get për fushat e klasës
@Setter // Gjeneron automatikisht metodat set për fushat e klasës
@NoArgsConstructor // Gjeneron një konstruktor pa parametra
@AllArgsConstructor // Gjeneron një konstruktor me të gjitha fushat si parametra
@Table(name = "admissions") // Përcakton emrin e tabelës në bazën e të dhënave
public class Admission {

    @Id // Përcakton këtë fushë si identifikuesin unik të entitetit
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-inkrementohet nga baza e të dhënave
    private Long id; // Identifikuesi unik për pranimin e pacientit

    @ManyToOne // Përcakton një marrëdhënie shumë-në-një (shumë pranime mund të kenë një pacient)
    @JoinColumn(name = "patient_id", nullable = false) // Përcakton emrin e kolonës së huaj në bazën e të dhënave
    private Patient patient; // Pacienti që është pranuar në spital

    @ManyToOne // Përcakton një marrëdhënie shumë-në-një (shumë pranime mund të kenë një doktor)
    @JoinColumn(name = "doctor_id", nullable = false) // Përcakton emrin e kolonës së huaj në bazën e të dhënave
    private Doctor doctor; // Doktori përgjegjës për pranimin e pacientit

    @Temporal(TemporalType.DATE) // Përcakton që kjo fushë do të ruajë vetëm datën (jo orën)
    private Date admissionDate; // Data e pranimit të pacientit në spital

    @Temporal(TemporalType.DATE) // Përcakton që kjo fushë do të ruajë vetëm datën e lirimit
    private Date dischargeDate; // Data e lirimit të pacientit nga spitali

    @Enumerated(EnumType.STRING) // Ruajtja e vlerave të `DischargeReason` si `String` në bazën e të dhënave
    private DischargeReason dischargeReason; // Arsyeja e lirimit të pacientit
}
