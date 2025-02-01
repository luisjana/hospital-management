package com.example.hospital_management;

import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.entity.DischargeReason;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.service.AdmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DischargeServiceTest {

    @Mock
    private AdmissionRepository admissionRepository;

    @InjectMocks
    private AdmissionService admissionService;

    private Admission admission;

    @BeforeEach
    void setUp() {
        admission = new Admission();
        admission.setId(1L);
        admission.setDischargeReason(DischargeReason.RECOVERED);
    }

    @Test
    void testDischargePatient() {
        when(admissionRepository.findById(1L)).thenReturn(Optional.of(admission));
        Optional<Admission> dischargedAdmission = admissionService.getAdmissionById(1L);
        assertTrue(dischargedAdmission.isPresent());
        assertEquals(DischargeReason.RECOVERED, dischargedAdmission.get().getDischargeReason());
    }
}
