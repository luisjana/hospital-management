package com.example.hospital_management.controller;

import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Test per kontrolluesin PatientController
@WebMvcTest(PatientController.class)
@ContextConfiguration(classes = PatientController.class)
class PatientControllerTest {

    // MockMvc perdoret per testimin e endpoints te REST API
    @Autowired
    private MockMvc mockMvc;

    // Mock per sherbimin PatientService
    @MockBean
    private PatientService patientService;

    @Test
    void testGetAllPatients() throws Exception {
        // Simulon kthimin e nje liste bosh nga service
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        // Teston endpointin GET /api/patients
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk()); // Kontrollon statusin 200 OK
    }

    @Test
    void testGetPatientById() throws Exception {
        // Krijon nje objekt Patient per test
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1990, 5, 20),
                null, "123-456-7890", "john@example.com", "NYC", null);

        // Simulon kthimin e pacientit me ID 1
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

        // Teston endpointin GET /api/patients/1
        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk()); // Kontrollon statusin 200 OK
    }

    @Test
    void testCreatePatient() throws Exception {
        // JSON payload per krijimin e nje pacienti te ri
        String jsonPayload = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "birthDate": "1990-05-20",
                "gender": "MALE",
                "phone": "123-456-7890",
                "email": "john@example.com",
                "address": "NYC"
            }
            """;

        // Simulon kthimin e nje pacienti te krijuar
        when(patientService.savePatient(any(Patient.class)))
                .thenReturn(new Patient(1L, "John", "Doe", LocalDate.of(1990, 5, 20),
                        null, "123-456-7890", "john@example.com", "NYC", null));

        // Teston endpointin POST /api/patients
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated()); // Kontrollon statusin 201 Created
    }

    @Test
    void testDeletePatient() throws Exception {
        // Simulon suksesin e fshirjes se pacientit me ID 1
        when(patientService.deletePatient(1L)).thenReturn(true);

        // Teston endpointin DELETE /api/patients/1
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent()); // Kontrollon statusin 204 No Content
    }
}