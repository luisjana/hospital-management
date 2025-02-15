package com.example.hospital_management.service;

import com.example.hospital_management.entity.ClinicalData;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.ClinicalDataRepository;
import com.example.hospital_management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

// Test per sherbimin ClinicalDataService
class ClinicalDataServiceTest {

    // Injeksion automatik i mocks ne ClinicalDataService
    @InjectMocks
    private ClinicalDataService clinicalDataService;

    // Mock per repository-t
    @Mock
    private ClinicalDataRepository clinicalDataRepository;
    @Mock
    private PatientRepository patientRepository;

    // Inicializimi i mocks para secilit test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClinicalDataSuccess() {
        // Krijon nje pacient testues
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        // Krijon nje objekt ClinicalData per test
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setPatient(patient);
        clinicalData.setNote("Pacienti ka temperaturë të lartë.");

        // Simulon kthimin e pacientit kur kerkohet ID 1
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        // Simulon ruajtjen e ClinicalData
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        // Teston metoden addClinicalData
        ClinicalData result = clinicalDataService.addClinicalData(1L, "Pacienti ka temperaturë të lartë.");

        // Verifikon rezultatin
        assertNotNull(result); // Kontrollon qe objekti nuk eshte null
        assertEquals("Pacienti ka temperaturë të lartë.", result.getNote()); // Kontrollon permbajtjen e notes
    }

    @Test
    void testAddClinicalDataPatientNotFound() {
        // Simulon rastin kur pacienti nuk gjendet
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Teston nese hidhet nje exception kur pacienti nuk gjendet
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clinicalDataService.addClinicalData(1L, "Shënim i ri klinik.");
        });

        // Kontrollon mesazhin e exception-it
        assertEquals("Patient not found", exception.getMessage());
    }
}
