package com.example.hospital_management.controller;

import com.example.hospital_management.entity.ClinicalData;
import com.example.hospital_management.service.ClinicalDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

// Test per kontrolluesin ClinicalDataController
class ClinicalDataControllerTest {

    // Mock per sherbimin ClinicalDataService
    @Mock
    private ClinicalDataService clinicalDataService;

    // Injeksion automatik i mocks ne ClinicalDataController
    @InjectMocks
    private ClinicalDataController clinicalDataController;

    // Inicializimi i mocks para secilit test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClinicalData() {
        // Parametrat per testin
        Long patientId = 1L;
        String note = "Pacienti ka dhimbje koke";

        // Mockimi i te dhenave te kthimit
        ClinicalData mockClinicalData = new ClinicalData();
        mockClinicalData.setNote(note);

        // Simulimi i sjelljes se services
        when(clinicalDataService.addClinicalData(patientId, note))
                .thenReturn(mockClinicalData);

        // Thirrja e metodës se kontrolluesit
        ResponseEntity<String> response = clinicalDataController.addClinicalData(patientId, note);

        // Verifikimi i rezultatit
        assertEquals(200, response.getStatusCodeValue()); // Kontrollon statusin 200 OK
        assertEquals("Clinical data added successfully.", response.getBody()); // Kontrollon mesazhin e kthyer
    }

    @Test
    void testGetClinicalDataByPatient() {
        // Parametri per testin
        Long patientId = 1L;

        // Krijimi i nje liste me ClinicalData per test
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setNote("Pacienti me ethe");

        // Simulimi i kthimit te te dhenave nga service
        when(clinicalDataService.getClinicalDataByPatient(patientId))
                .thenReturn(Collections.singletonList(clinicalData));

        // Thirrja e metodës se kontrolluesit
        ResponseEntity<List<ClinicalData>> response = clinicalDataController.getClinicalDataByPatient(patientId);

        // Verifikimi i rezultatit
        assertEquals(200, response.getStatusCodeValue()); // Kontrollon statusin 200 OK
        assertEquals(1, response.getBody().size()); // Kontrollon madhesine e listes
        assertEquals("Pacienti me ethe", response.getBody().get(0).getNote()); // Kontrollon permbajtjen e notes
    }

    @Test
    void testGetClinicalDataByPatient_EmptyList() {
        // Parametri per testin
        Long patientId = 1L;

        // Simulimi i kthimit te nje liste bosh nga service
        when(clinicalDataService.getClinicalDataByPatient(anyLong()))
                .thenReturn(Collections.emptyList());

        // Thirrja e metodës se kontrolluesit
        ResponseEntity<List<ClinicalData>> response = clinicalDataController.getClinicalDataByPatient(patientId);

        // Verifikimi i rezultatit
        assertEquals(200, response.getStatusCodeValue()); // Kontrollon statusin 200 OK
        assertEquals(0, response.getBody().size()); // Kontrollon qe lista eshte bosh
    }
}