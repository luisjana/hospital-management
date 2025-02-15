// Paketa ku ndodhet kjo klasë entiteti
package com.example.hospital_management.entity;

// Importimi i anotimeve nga Jakarta Persistence dhe Lombok
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Kjo klasë përfaqëson entitetin `ClinicalData` në bazën e të dhënave.
 * Përdoret nga JPA për të krijuar dhe menaxhuar tabelën `clinical_data`.
 */
@Getter // Gjeneron automatikisht të gjitha metodat "get" për atributet
@Entity // Shënon këtë klasë si një entitet për JPA (tabelë në bazën e të dhënave)
@Table(name = "clinical_data") // Përcakton emrin e tabelës në bazën e të dhënave
public class ClinicalData {


    /**
     * ID unike për çdo të dhënë klinike.
     * @Id - Shënon këtë atribut si çelësin primar (Primary Key).
     * @GeneratedValue - Gjeneron automatikisht vlerën e ID-së me strategjinë 'IDENTITY'.
     * @Setter - Gjeneron automatikisht metodën "set" për këtë atribut.
     */
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment për ID
    private Long id;

    /**
     * Lidhja me entitetin `Patient` (Shumë te Një).
     * @ManyToOne - Shënon një marrëdhënie shumë-pacientë me një pacient.
     * @JoinColumn - Përcakton kolonën `patient_id` si çelës i huaj (Foreign Key).
     * nullable = false - Siguron që pacienti është i detyrueshëm.
     * @Setter - Gjeneron automatikisht metodën "set" për këtë atribut.
     */
    @Setter
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false) // Lidhje me tabelën "patient"
    private Patient patient;

    /**
     * Kolona që përmban shënimet klinike të pacientit.
     * @Column - Përcakton karakteristikat e kolonës në bazën e të dhënave.
     * columnDefinition = "TEXT" - Ruhet si tip 'TEXT' për të mbështetur shënime të gjata.
     * nullable = false - Shënimi është i detyrueshëm.
     * @Setter - Gjeneron automatikisht metodën "set" për këtë atribut.
     */
    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String note;

    /**
     * Data dhe ora kur është krijuar shënimi.
     * @Column - Përcakton emrin dhe veçoritë e kolonës.
     * name = "created_at" - Emri i kolonës në bazën e të dhënave.
     * updatable = false - Vlera vendoset vetëm një herë (nuk përditësohet më vonë).
     * Vlera e paracaktuar është koha aktuale në krijim.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();




}
