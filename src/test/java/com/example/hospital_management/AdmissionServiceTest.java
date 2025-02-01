package com.example.hospital_management;

import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.service.AdmissionService;
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
public class AdmissionServiceTest {

    @Mock
    private AdmissionRepository admissionRepository;

    @InjectMocks
    private AdmissionService admissionService;

    private Admission admission;

    @BeforeEach
    void setUp() {
        admission = new Admission();
        admission.setId(1L);
    }

    @Test
    void testGetAllAdmissions() {
        when(admissionRepository.findAll()).thenReturn(Arrays.asList(admission));
        List<Admission> admissions = admissionService.getAllAdmissions();
        assertFalse(admissions.isEmpty());
    }

    @Test
    void testGetAdmissionById() {
        when(admissionRepository.findById(1L)).thenReturn(Optional.of(admission));
        Optional<Admission> foundAdmission = admissionService.getAdmissionById(1L);
        assertTrue(foundAdmission.isPresent());
    }

    @Test
    void testCreateAdmission() {
        when(admissionRepository.save(admission)).thenReturn(admission);
        Admission savedAdmission = admissionService.createAdmission(admission);
        assertNotNull(savedAdmission);
    }

    @Test
    void testDeleteAdmission() {
        when(admissionRepository.existsById(1L)).thenReturn(true);
        boolean result = admissionService.deleteAdmission(1L);
        assertTrue(result);
    }
}
