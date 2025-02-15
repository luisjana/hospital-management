package com.example.hospital_management.controller;

import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.service.AdmissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Test per kontrolluesin AdmissionController
@WebMvcTest(AdmissionController.class)
@ContextConfiguration(classes = AdmissionController.class)
class AdmissionControllerTest {

    // MockMvc perdoret per testimin e endpoints te REST API
    @Autowired
    private MockMvc mockMvc;

    // Mock per repository dhe service te perdorur ne controller
    @MockBean
    private AdmissionRepository admissionRepository;
    @MockBean
    private AdmissionService admissionService;

    @Test
    void testGetAllAdmissions() throws Exception {
        // Simulon kthimin e nje liste bosh nga repository
        when(admissionRepository.findAll()).thenReturn(Collections.emptyList());

        // Teston endpointin GET /api/admissions
        mockMvc.perform(get("/api/admissions"))
                .andExpect(status().isOk()); // Kontrollon qe statusi eshte 200 OK
    }

    @Test
    void testGetAdmissionById() throws Exception {
        // Krijon nje objekt Admission me ID 1
        Admission admission = new Admission();
        admission.setId(1L);
        admission.setAdmissionDate(Date.from(LocalDate.of(2024, 5, 10)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()));

        // Simulon kthimin e nje Admission kur kerkohet ID 1
        when(admissionRepository.findById(1L)).thenReturn(Optional.of(admission));

        // Teston endpointin GET /api/admissions/1
        mockMvc.perform(get("/api/admissions/1"))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Kontrollon qe ID eshte 1
    }

    @Test
    void testCreateAdmission() throws Exception {
        // JSON payload per krijimin e nje Admission te ri
        String jsonPayload = """
            {
                "patient": {
                    "id": 1
                },
                "doctor": {
                    "id": 2
                },
                "admissionDate": "2024-05-10"
            }
            """;

        // Simulon kthimin e nje Admission te krijuar me ID 1
        Admission admission = new Admission();
        admission.setId(1L);
        admission.setAdmissionDate(Date.from(LocalDate.of(2024, 5, 10)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()));

        when(admissionService.createAdmission(any(Admission.class))).thenReturn(admission);

        // Teston endpointin POST /api/admissions
        mockMvc.perform(post("/api/admissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated()) // Kontrollon statusin 201 Created
                .andExpect(jsonPath("$.id").value(1)); // Kontrollon qe ID eshte 1
    }

    @Test
    void testUpdateAdmission() throws Exception {
        // Krijon Admission ekzistues dhe te perditesuar
        Admission existingAdmission = new Admission();
        existingAdmission.setId(1L);
        existingAdmission.setAdmissionDate(Date.from(LocalDate.of(2024, 6, 15)
                .atStartOfDay(ZoneId.of("UTC"))
                .toInstant()));

        Admission updatedAdmission = new Admission();
        updatedAdmission.setId(1L);
        updatedAdmission.setAdmissionDate(Date.from(LocalDate.of(2024, 6, 15)
                .atStartOfDay(ZoneId.of("UTC"))
                .toInstant()));

        // JSON payload per perditesim
        String jsonPayload = """
        {
            "patient": {
                "id": 1
            },
            "doctor": {
                "id": 2
            },
            "admissionDate": "2024-06-15"
        }
        """;

        when(admissionRepository.findById(1L)).thenReturn(Optional.of(existingAdmission));
        when(admissionRepository.save(any(Admission.class))).thenReturn(updatedAdmission);

        // Teston endpointin PUT /api/admissions/1
        mockMvc.perform(put("/api/admissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.admissionDate").value(org.hamcrest.Matchers.startsWith("2024-06-15"))); // Kontrollon daten e rifreskuar
    }

    @Test
    void testDeleteAdmission() throws Exception {
        // Simulon ekzistencen e Admission me ID 1
        when(admissionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(admissionRepository).deleteById(1L);

        // Teston endpointin DELETE /api/admissions/1
        mockMvc.perform(delete("/api/admissions/1"))
                .andExpect(status().isNoContent()); // Kontrollon statusin 204 No Content
    }

    @Test
    void testDeleteAdmissionNotFound() throws Exception {
        // Simulon qe nuk ekziston Admission me ID 99
        when(admissionRepository.existsById(99L)).thenReturn(false);

        // Teston endpointin DELETE /api/admissions/99
        mockMvc.perform(delete("/api/admissions/99"))
                .andExpect(status().isNotFound()); // Kontrollon statusin 404 Not Found
    }
}
