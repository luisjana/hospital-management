// Përcakton paketën ku ndodhet kjo klasë testimi
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për testimin e shërbimit `PatientService`
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.entity.Gender;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasa e testimit për `PatientService`, duke përdorur `Mockito` për të simuluar `PatientRepository`.
 */
@ExtendWith(MockitoExtension.class) // Përdorim Mockito për testimin
public class PatientServiceTest {

    @Mock // Simulon repository, nuk përdor një bazë të dhënash reale
    private PatientRepository patientRepository;

    @InjectMocks // Injekton repository-n e simuluar në service
    private PatientService patientService;

    private Patient patient1, patient2; // Dy objekte pacientësh për testim

    /**
     * Metoda e përgatitjes së të dhënave për testim.
     * Ekzekutohet para çdo testi (`@BeforeEach`).
     */
    @BeforeEach
    void setup() {
        // Krijon një departament të zakonshëm për pacientët
        Department department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        // Krijon dy pacientë për testim
        patient1 = new Patient(1L, "John", "Doe", LocalDate.of(1990, 5, 20),
                Gender.MALE, "123-456-7890", "john@example.com", "NYC", department);

        patient2 = new Patient(2L, "Alice", "Smith", LocalDate.of(1995, 7, 20),
                Gender.FEMALE, "987-654-3210", "alice.smith@example.com", "Los Angeles", department);
    }

    /**
     * Teston metodën `savePatient(Patient patient)`, duke krijuar një pacient të ri.
     */
    @Test
    void testCreatePatient() {
        // Simulon sjelljen e `patientRepository.save()` për të kthyer pacientin e ruajtur
        when(patientRepository.save(any(Patient.class))).thenReturn(patient2);

        // Thirr metoda që po testohet
        Patient savedPatient = patientService.savePatient(patient2);

        // Sigurohet që objekti i ruajtur nuk është null
        assertNotNull(savedPatient, "Saved patient should not be null");

        // Sigurohet që emri i pacientit është ruajtur saktë
        assertEquals("Alice", savedPatient.getFirstName());
    }

    /**
     * Teston metodën `getAllPatients()` për të marrë të gjithë pacientët.
     */
    @Test
    public void testGetAllPatients() {
        // Simulon sjelljen e `patientRepository.findAll()` duke kthyer një listë me pacientët
        List<Patient> mockPatients = Arrays.asList(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(mockPatients);

        // Thirr metoda që po testohet
        List<Patient> patients = patientService.getAllPatients();

        // Sigurohet që janë marrë saktë të gjithë pacientët
        assertEquals(2, patients.size());

        // Kontrollon që emrat janë saktë
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals("Alice", patients.get(1).getFirstName());

        // Verifikon që `findAll()` është thirrur vetëm një herë
        verify(patientRepository, times(1)).findAll();
    }

    /**
     * Teston metodën `getPatientById(Long id)` për të marrë një pacient sipas ID-së.
     */
    @Test
    public void testGetPatientById() {
        // Simulon sjelljen e `patientRepository.findById(1L)` duke kthyer një pacient
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));

        // Thirr metoda që po testohet
        Optional<Patient> foundPatient = patientService.getPatientById(1L);

        // Sigurohet që pacienti është i pranishëm
        assertTrue(foundPatient.isPresent());

        // Kontrollon që emri i pacientit është i saktë
        assertEquals("John", foundPatient.get().getFirstName());

        // Verifikon që `findById(1L)` është thirrur vetëm një herë
        verify(patientRepository, times(1)).findById(1L);
    }

    /**
     * Teston metodën `deletePatient(Long id)`, për të fshirë një pacient sipas ID-së.
     */
    @Test
    public void testDeletePatient() {
        // Simulon sjelljen e `patientRepository.existsById(1L)` duke kthyer `true`
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Simulon sjelljen e `deleteById(1L)` (nuk bën asgjë në testim)
        doNothing().when(patientRepository).deleteById(1L);

        // Thirr metoda që po testohet
        boolean isDeleted = patientService.deletePatient(1L);

        // Sigurohet që metoda ka kthyer `true`, që do të thotë se fshirja ishte e suksesshme
        assertTrue(isDeleted);

        // Verifikon që `deleteById(1L)` është thirrur vetëm një herë
        verify(patientRepository, times(1)).deleteById(1L);
    }
}
