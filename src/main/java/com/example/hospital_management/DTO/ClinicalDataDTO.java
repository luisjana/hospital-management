// Paketa ku ndodhet kjo klasë DTO (Data Transfer Object)
package com.example.hospital_management.DTO;

import java.time.LocalDateTime; // Importimi i klasës për datën dhe kohën

/**
 * Kjo klasë shërben si DTO (Data Transfer Object) për të dhënat klinike (Clinical Data).
 * DTO përdoret për të transferuar të dhëna midis shtresave të aplikacionit (p.sh. midis Controller dhe Frontend).
 */
public class ClinicalDataDTO {

    // Atributet që përmbajnë informacionin që do të dërgohet ose merret nga klienti
    private Long id;                   // ID e të dhënave klinike
    private String note;               // Shënimi klinik
    private LocalDateTime createdAt;   // Koha kur u krijuan të dhënat klinike

    /**
     * Konstruktori për krijimin e një objekti ClinicalDataDTO
     *
     * @param id ID e të dhënave klinike
     * @param note Shënimi klinik
     * @param createdAt Koha e krijimit të të dhënave
     */
    public ClinicalDataDTO(Long id, String note, LocalDateTime createdAt) {
        this.id = id;                // Vendos ID
        this.note = note;            // Vendos shënimin klinik
        this.createdAt = createdAt;  // Vendos datën e krijimit
    }

    /**
     * Merr ID-në e të dhënave klinike
     * @return ID e të dhënave
     */
    public Long getId() {
        return id;
    }

    /**
     * Merr shënimin klinik
     * @return Shënimi klinik
     */
    public String getNote() {
        return note;
    }

    /**
     * Merr datën dhe kohën e krijimit
     * @return Data dhe koha e krijimit
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
