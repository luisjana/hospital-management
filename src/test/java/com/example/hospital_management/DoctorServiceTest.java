package com.example.hospital_management;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.DoctorService;
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
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
    }

    @Test
    void testGetAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));
        List<Doctor> doctors = doctorService.getAllDoctors();
        assertFalse(doctors.isEmpty());
    }

    @Test
    void testGetDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        Optional<Doctor> foundDoctor = doctorService.getDoctorById(1L);
        assertTrue(foundDoctor.isPresent());
    }

    @Test
    void testCreateDoctor() {
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        assertNotNull(savedDoctor);
    }

    @Test
    void testDeleteDoctor() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        boolean result = doctorService.deleteDoctor(1L);
        assertTrue(result);
    }
}
