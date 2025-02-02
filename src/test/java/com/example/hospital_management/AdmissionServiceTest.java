// Përcakton paketën ku ndodhet kjo klasë testimi
package com.example.hospital_management;

// Importimi i klasave të nevojshme për testimin e shërbimit `AdmissionService`
import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.repository.PatientRepository;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.AdmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasa e testimit për `AdmissionService`, duke përdorur `Mockito` për të simuluar `AdmissionRepository`.
 */
@ExtendWith(MockitoExtension.class) // Aktivizon Mockito për testimin
public class AdmissionServiceTest {

    @Mock // Krijon një version të simuluar të `PatientRepository`
    private PatientRepository patientRepository;

    @Mock // Krijon një version të simuluar të `DoctorRepository`
    private DoctorRepository doctorRepository;

    @Mock // Krijon një version të simuluar të `AdmissionRepository`
    private AdmissionRepository admissionRepository;

    @InjectMocks // Injeksion automatik i `AdmissionService`, duke përdorur mock-un e `AdmissionRepository`
    private AdmissionService admissionService;

    private Admission admission; // Objekt i pranimit që do të përdoret në teste

    /**
     * Metoda e përgatitjes së të dhënave për testim.
     * Ekzekutohet para çdo testi (`@BeforeEach`).
     */
    @BeforeEach
    void setUp() {
        admission = new Admission(); // Krijon një objekt të ri `Admission`
        admission.setId(1L); // Vendos ID për pranimin
    }

    /**
     * Teston metodën `createAdmission()` për të regjistruar një pranim të ri.
     */
    @Test
    void testCreateAdmission() {
        // Simulo një pacient ekzistues
        Patient patient = new Patient();
        patient.setId(1L);  // Vendos një ID të vlefshme për pacientin
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Simulo një mjek ekzistues
        Doctor doctor = new Doctor();
        doctor.setId(2L);  // Vendos një ID të vlefshme për doktorin
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));

        // Krijo një rast të ri `Admission`
        Admission admission = new Admission();
        admission.setPatient(patient);  // Sigurohu që pacienti nuk është null
        admission.setDoctor(doctor);    // Sigurohu që mjeku nuk është null
        admission.setAdmissionDate(new Date());

        // Simulo ruajtjen në bazën e të dhënave
        when(admissionRepository.save(any(Admission.class))).thenReturn(admission);

        Admission createdAdmission = admissionService.createAdmission(admission);

        // Sigurohu që objekti i krijuar nuk është null
        assertNotNull(createdAdmission);
        assertNotNull(createdAdmission.getPatient());
        assertNotNull(createdAdmission.getDoctor());
    }

    /**
     * Teston metodën `getAdmissionById(Long id)` për të marrë një pranim sipas ID-së.
     */
    @Test
    void testGetAdmissionById() {
        // Simulon sjelljen e `admissionRepository.findById(1L)` duke kthyer një Optional me pranim
        when(admissionRepository.findById(1L)).thenReturn(Optional.of(admission));

        // Thirr metoda që po testohet
        Optional<Admission> foundAdmission = admissionService.getAdmissionById(1L);

        // Sigurohet që rezultati të jetë i pranishëm (pra, jo bosh)
        assertTrue(foundAdmission.isPresent());
    }

    /**
     * Teston metodën `deleteAdmission(Long id)` për të fshirë një pranim sipas ID-së.
     */
    @Test
    void testDeleteAdmission() {
        // Simulon sjelljen e `admissionRepository.existsById(1L)` duke kthyer `true`
        when(admissionRepository.existsById(1L)).thenReturn(true);

        // Thirr metoda që po testohet
        boolean result = admissionService.deleteAdmission(1L);

        // Sigurohet që metoda ka kthyer `true`, që do të thotë se fshirja ishte e suksesshme
        assertTrue(result);
    }
}
