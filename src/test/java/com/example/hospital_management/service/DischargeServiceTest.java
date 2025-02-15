// Përcakton paketën ku ndodhet kjo klasë testimi
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për testimin e shërbimit `AdmissionService`
import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.entity.DischargeReason;
import com.example.hospital_management.repository.AdmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasa e testimit për procesin e lirimit të pacientëve (`DischargeService`).
 * Përdor `Mockito` për të simuluar `AdmissionRepository`.
 */
@ExtendWith(MockitoExtension.class) // Aktivizon Mockito për testimin
public class DischargeServiceTest {

    @Mock // Krijon një version të simuluar të `AdmissionRepository`
    private AdmissionRepository admissionRepository;

    @InjectMocks // Injeksion automatik i `AdmissionService`, duke përdorur mock-un e `AdmissionRepository`
    private AdmissionService admissionService;

    private Admission admission; // Objekt për të simuluar një pranim të pacientit

    /**
     * Metoda e përgatitjes së të dhënave për testim.
     * Ekzekutohet para çdo testi (`@BeforeEach`).
     */
    @BeforeEach
    void setUp() {
        admission = new Admission(); // Krijon një objekt `Admission`
        admission.setId(1L); // Vendos ID për pranimin
        admission.setDischargeReason(DischargeReason.RECOVERED); // Vendos arsyen e lirimit
    }

    /**
     * Teston metodën e lirimit të pacientit nga spitali.
     */
    @Test
    void testDischargePatient() {
        // Simulon që `findById(1L)` kthen një pranim me arsyen e lirimit të vendosur
        when(admissionRepository.findById(1L)).thenReturn(Optional.of(admission));

        // Thirr metoda që po testohet
        Optional<Admission> dischargedAdmission = admissionService.getAdmissionById(1L);

        // Sigurohet që rezultati të jetë i pranishëm (pra, jo bosh)
        assertTrue(dischargedAdmission.isPresent());

        // Kontrollon që arsyeja e lirimit është `RECOVERED`
        assertEquals(DischargeReason.RECOVERED, dischargedAdmission.get().getDischargeReason());
    }
}
