package com.example.hospital_management;

import com.example.hospital_management.entity.Department;
import com.example.hospital_management.repository.DepartmentRepository;
import com.example.hospital_management.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Cardiology");
    }

    @Test
    void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department));
        List<Department> departments = departmentService.getAllDepartments();
        assertFalse(departments.isEmpty());
    }

    @Test
    void testGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        Optional<Department> foundDepartment = departmentService.getDepartmentById(1L);
        assertTrue(foundDepartment.isPresent());
    }

    @Test
    void testCreateDepartment() {
        when(departmentRepository.save(department)).thenReturn(department);
        Department savedDepartment = departmentService.createDepartment(department);
        assertNotNull(savedDepartment);
    }

    @Test
    void testDeleteDepartment() {
        when(departmentRepository.existsById(1L)).thenReturn(true);
        boolean result = departmentService.deleteDepartment(1L);
        assertTrue(result);
    }
}
