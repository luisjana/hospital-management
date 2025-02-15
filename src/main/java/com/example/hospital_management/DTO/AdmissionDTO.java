// Paketa ku ndodhet kjo klasë DTO (Data Transfer Object)
package com.example.hospital_management.DTO;

// Importimi i klasave të nevojshme
import com.example.hospital_management.entity.Admission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

// Anotime të Lombok për të gjeneruar automatikisht getter, setter dhe një konstruktor pa argumente
@Getter                // Gjeneron automatikisht metodat 'get' për të gjitha atributet
@Setter                // Gjeneron automatikisht metodat 'set' për të gjitha atributet
@NoArgsConstructor     // Gjeneron automatikisht një konstruktor pa argumente (default constructor)
public class AdmissionDTO {

    // Atributet që përmbajnë informacionin që do të dërgohet ose merret nga klienti (front-end)
    private Long id;                // ID e pranimit
    private String patientName;     // Emri i pacientit
    private String doctorName;      // Emri i mjekut
    private Date admissionDate;     // Data e pranimit në spital
    private Date dischargeDate;     // Data e lirimit nga spitali

    /**
     * Konstruktor që krijon një objekt `AdmissionDTO` duke përdorur një objekt `Admission`.
     * Ky lloj konstruktori përdoret zakonisht për të konvertuar një entitet (Entity) në një DTO.
     *
     * @param admission Objekti i tipit `Admission` nga i cili do të nxirren të dhënat
     */
    public AdmissionDTO(Admission admission) {
        // Vendos ID e pranimit
        this.id = admission.getId();

        // Vendos emrin e pacientit nëse ekziston, përndryshe 'Unknown'
        this.patientName = admission.getPatient() != null
                ? admission.getPatient().getFirstName() + " " + admission.getPatient().getLastName()
                : "Unknown";

        // Vendos emrin e mjekut nëse ekziston, përndryshe 'Unknown'
        this.doctorName = admission.getDoctor() != null
                ? admission.getDoctor().getFirstName() + " " + admission.getDoctor().getLastName()
                : "Unknown";

        // Vendos datën e pranimit
        this.admissionDate = admission.getAdmissionDate();

        // Vendos datën e lirimit
        this.dischargeDate = admission.getDischargeDate();
    }
}
