// Përcakton paketën ku ndodhet kjo klasë testimi
package com.example.hospital_management;

// Importimi i klasave të nevojshme për testimin e shërbimit `DoctorService`
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasa e testimit për `DoctorService`, duke përdorur `Mockito` për të simuluar `DoctorRepository`.
 */
@ExtendWith(MockitoExtension.class) // Aktivizon Mockito për testimin
public class DoctorServiceTest {

    @Mock // Krijon një version të simuluar të `DoctorRepository`
    private DoctorRepository doctorRepository;

    @InjectMocks // Injeksion automatik i `DoctorService`, duke përdorur `DoctorRepository` të simuluar
    private DoctorService doctorService;

    private Doctor doctor; // Objekt `Doctor` për testim

    /**
     * Metoda e përgatitjes së të dhënave për testim.
     * Ekzekutohet para çdo testi (`@BeforeEach`).
     */
    @BeforeEach
    void setUp() {
        doctor = new Doctor(); // Krijon një objekt `Doctor`
        doctor.setId(1L); // Vendos ID për doktorin
        doctor.setFirstName("John"); // Vendos emrin
        doctor.setLastName("Doe"); // Vendos mbiemrin
        doctor.setSpecialization("Cardiology"); // Vendos specializimin
        doctor.setPhone("1234567890"); // Vendos numrin e telefonit
        doctor.setBirthDate(LocalDate.of(1980, 5, 15)); // Vendos datën e lindjes
    }

    /**
     * Teston metodën `getAllDoctors()` për të marrë të gjithë doktorët.
     */
    @Test
    void testGetAllDoctors() {
        // Simulon sjelljen e `doctorRepository.findAll()` duke kthyer një listë me një doktor
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));

        // Thirr metoda që po testohet
        List<?> doctors = doctorService.getAllDoctors();

        // Sigurohet që lista e marrë të mos jetë bosh
        assertFalse(doctors.isEmpty());
    }

    /**
     * Teston metodën `getDoctorById(Long id)` për të marrë një doktor sipas ID-së.
     */
    @Test
    void testGetDoctorById() {
        // Simulon sjelljen e `doctorRepository.findById(1L)` duke kthyer një Optional me doktorin
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        // Thirr metoda që po testohet
        Optional<?> foundDoctor = doctorService.getDoctorById(1L);

        // Sigurohet që rezultati të jetë i pranishëm (pra, jo bosh)
        assertTrue(foundDoctor.isPresent());
    }
}
