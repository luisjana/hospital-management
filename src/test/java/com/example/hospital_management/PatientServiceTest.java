package com.example.hospital_management;

import com.example.hospital_management.entity.Department;
import com.example.hospital_management.entity.Gender;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
import com.example.hospital_management.service.PatientService;
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

@ExtendWith(MockitoExtension.class) // Përdorim Mockito për testimin
public class PatientServiceTest {

    @Mock // Simulon repository, nuk përdor një bazë të dhënash reale
    private PatientRepository patientRepository;

    @InjectMocks // Injekton repository-n e simuluar në service
    private PatientService patientService;

    private Patient patient1, patient2;

    @BeforeEach
    void setup() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Cardiology");

        patient1 = new Patient(1L, "John", "Doe", LocalDate.of(1990, 5, 20),
                Gender.MALE, "123-456-7890", "john@example.com", "NYC", department);

        patient2 = new Patient(2L, "Alice", "Smith", LocalDate.of(1995, 7, 20),
                Gender.FEMALE, "987-654-3210", "alice.smith@example.com", "Los Angeles", department);
    }


    @Test
    void testCreatePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient2); // Simulon ruajtjen e pacientit

        Patient savedPatient = patientService.savePatient(patient2);

        assertNotNull(savedPatient, "Saved patient should not be null");
        assertEquals("Alice", savedPatient.getFirstName());
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> mockPatients = Arrays.asList(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(mockPatients); // Simulon kthimin e pacientëve

        List<Patient> patients = patientService.getAllPatients();

        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).getFirstName()); // Kontrollon pacientin e parë
        assertEquals("Alice", patients.get(1).getFirstName()); // Kontrollon pacientin e dytë

        verify(patientRepository, times(1)).findAll(); // Siguron që është thirrur vetëm një herë
    }

    @Test
    public void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1)); // Simulon kërkimin e një pacienti

        Optional<Patient> foundPatient = patientService.getPatientById(1L);

        assertTrue(foundPatient.isPresent());
        assertEquals("John", foundPatient.get().getFirstName()); // Kontrollon emrin e saktë

        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeletePatient() {
        when(patientRepository.existsById(1L)).thenReturn(true); // Simulon ekzistencën e pacientit
        doNothing().when(patientRepository).deleteById(1L); // Simulon fshirjen

        boolean isDeleted = patientService.deletePatient(1L);

        assertTrue(isDeleted);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}
