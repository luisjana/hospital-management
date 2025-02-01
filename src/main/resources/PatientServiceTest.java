
package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.entity.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PatientServiceTest {

    private final PatientRepository patientRepository = Mockito.mock(PatientRepository.class);
    private final PatientService patientService = new PatientService(patientRepository);

    @Test
    void getAllPatientsTest() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");

        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> patients = patientService.getAllPatients();
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
    }
}
