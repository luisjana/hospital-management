package com.example.hospital_management.controller;

import com.example.hospital_management.DTO.DepartmentDTO;
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Test per kontrolluesin DepartmentController
@WebMvcTest(DepartmentController.class)
@ContextConfiguration(classes = DepartmentController.class)
class DepartmentControllerTest {

    // MockMvc perdoret per testimin e endpoints te REST API
    @Autowired
    private MockMvc mockMvc;

    // Mock per sherbimin DepartmentService
    @MockBean
    private DepartmentService departmentService;

    @Test
    void testGetAllDepartments() throws Exception {
        // Simulon kthimin e nje liste bosh nga service
        when(departmentService.getAllDepartments()).thenReturn(Collections.emptyList());

        // Teston endpointin GET /api/departments
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk()); // Kontrollon statusin 200 OK
    }

    @Test
    void testGetDepartmentById() throws Exception {
        // Krijon nje objekt Department
        Department department = new Department(1L, "Cardiology", Collections.emptyList());

        // Simulon kthimin e nje Department kur kerkohet ID 1
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(department));

        // Teston endpointin GET /api/departments/1
        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.name").value("Cardiology")); // Kontrollon emrin e department
    }

    @Test
    void testCreateDepartment() throws Exception {
        // JSON payload per krijimin e nje Department te ri
        String jsonPayload = """
            {
                "name": "Cardiology"
            }
            """;

        // Simulon kthimin e nje Department te krijuar
        Department createdDepartment = new Department(1L, "Cardiology", Collections.emptyList());

        when(departmentService.createDepartment(any(Department.class))).thenReturn(createdDepartment);

        // Teston endpointin POST /api/departments
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.name").value("Cardiology")); // Kontrollon emrin
    }

    @Test
    void testUpdateDepartment() throws Exception {
        // JSON payload per perditesim
        String jsonPayload = """
            {
                "name": "Neurology"
            }
            """;

        // Simulon kthimin e nje Department te perditesuar
        Department updatedDepartment = new Department(1L, "Neurology", Collections.emptyList());

        when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(Optional.of(updatedDepartment));

        // Teston endpointin PUT /api/departments/1
        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()) // Kontrollon statusin 200 OK
                .andExpect(jsonPath("$.name").value("Neurology")); // Kontrollon emrin e rifreskuar
    }

    @Test
    void testDeleteDepartment() throws Exception {
        // Simulon ekzistencen e Department me ID 1
        when(departmentService.deleteDepartment(1L)).thenReturn(true);

        // Teston endpointin DELETE /api/departments/1
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent()); // Kontrollon statusin 204 No Content
    }

    @Test
    void testDeleteDepartmentNotFound() throws Exception {
        // Simulon qe nuk ekziston Department me ID 99
        when(departmentService.deleteDepartment(99L)).thenReturn(false);

        // Teston endpointin DELETE /api/departments/99
        mockMvc.perform(delete("/api/departments/99"))
                .andExpect(status().isNotFound()); // Kontrollon statusin 404 Not Found
    }
}
