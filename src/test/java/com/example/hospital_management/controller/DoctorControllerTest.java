package com.example.hospital_management.controller;

import com.example.hospital_management.DTO.DoctorDTO;
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.DepartmentRepository;
import com.example.hospital_management.service.DoctorService;
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

// Test per kontrolluesin DoctorController
@WebMvcTest(DoctorController.class)
@ContextConfiguration(classes = DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock per sherbimin DoctorService
    @MockBean
    private DoctorService doctorService;

    // Mock per repository e Department per te shmangur gabimet e injektimit
    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    void testGetAllDoctors() throws Exception {
        // Simulon kthimin e nje liste bosh nga service
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());

        // Teston endpointin GET /api/doctors
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk()); // Kontrollon statusin 200 OK
    }

    @Test
    void testGetDoctorById() throws Exception {
        // Krijon nje DTO per doktorin e testit
        DoctorDTO doctorDTO = new DoctorDTO(new Doctor(1L, "Alice", "Smith", "Cardiology", "1234567890", LocalDate.of(1985, 6, 15), new Department(1L, "Cardiology", null)));

        // Simulon kthimin e doktorit me ID 1
        when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doctorDTO));

        // Teston endpointin GET /api/doctors/1
        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.specialization").value("Cardiology"));
    }

    @Test
    void testCreateDoctor() throws Exception {
        // JSON payload per krijimin e nje doktor te ri
        String jsonPayload = """
            {
                "firstName": "Alice",
                "lastName": "Smith",
                "specialization": "Cardiology",
                "phone": "1234567890",
                "birthDate": "1985-06-15",
                "department": {
                    "id": 1
                }
            }
            """;

        Doctor savedDoctor = new Doctor(1L, "Alice", "Smith", "Cardiology", "1234567890", LocalDate.of(1985, 6, 15), new Department(1L, "Cardiology", null));

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department(1L, "Cardiology", null)));
        when(doctorService.createDoctor(any(Doctor.class))).thenReturn(savedDoctor);

        // Teston endpointin POST /api/doctors
        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated()) // Kontrollon statusin 201 Created
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.specialization").value("Cardiology"));
    }

    @Test
    void testUpdateDoctor() throws Exception {
        // JSON payload per perditesim
        String jsonPayload = """
            {
                "firstName": "Alice",
                "lastName": "Johnson",
                "specialization": "Neurology",
                "phone": "1234567890",
                "birthDate": "1985-06-15",
                "department": {
                    "id": 1
                }
            }
            """;

        DoctorDTO updatedDoctorDTO = new DoctorDTO(new Doctor(1L, "Alice", "Johnson", "Neurology", "1234567890", LocalDate.of(1985, 6, 15), new Department(1L, "Neurology", null)));

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(new Department(1L, "Neurology", null)));
        when(doctorService.updateDoctor(eq(1L), any(Doctor.class))).thenReturn(Optional.of(updatedDoctorDTO));

        // Teston endpointin PUT /api/doctors/1
        mockMvc.perform(put("/api/doctors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.lastName").value("Johnson"))
                .andExpect(jsonPath("$.specialization").value("Neurology"));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        // Simulon suksesin e fshirjes se doktorit me ID 1
        when(doctorService.deleteDoctor(1L)).thenReturn(true);

        // Teston endpointin DELETE /api/doctors/1
        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Doctor deleted successfully."));
    }

    @Test
    void testDeleteDoctorNotFound() throws Exception {
        // Simulon rastin kur doktori me ID 99 nuk gjendet
        when(doctorService.deleteDoctor(99L)).thenReturn(false);

        // Teston endpointin DELETE /api/doctors/99
        mockMvc.perform(delete("/api/doctors/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Doctor not found."));
    }
}