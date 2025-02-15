// Përcakton paketën ku ndodhet kjo klasë testimi
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për testimin e shërbimit `DepartmentService`
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Klasa e testimit për `DepartmentService`, duke përdorur `Mockito` për të simuluar `DepartmentRepository`.
 */
@ExtendWith(MockitoExtension.class) // Aktivizon Mockito për testimin
public class DepartmentServiceTest {

    @Mock // Krijon një version të simuluar (mock) të `DepartmentRepository`
    private DepartmentRepository departmentRepository;

    @InjectMocks // Injeksion automatik i `DepartmentService`, duke përdorur mock-un e `DepartmentRepository`
    private DepartmentService departmentService;

    private Department department; // Objekt `Department` për testim

    /**
     * Metoda e përgatitjes së të dhënave për testim.
     * Ekzekutohet para çdo testi (`@BeforeEach`).
     */
    @BeforeEach
    void setUp() {
        department = new Department(); // Krijon një objekt `Department`
        department.setId(1L); // Vendos ID për departamentin
        department.setName("Cardiology"); // Vendos emrin e departamentit
    }

    /**
     * Teston metodën `getAllDepartments()` për të marrë të gjitha departamentet.
     */
    @Test
    void testGetAllDepartments() {
        // Simulon sjelljen e `departmentRepository.findAll()` duke kthyer një listë me një departament
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department));

        // Thirr metoda që po testohet
        List<Department> departments = departmentService.getAllDepartments();

        // Sigurohet që lista e marrë të mos jetë bosh
        assertFalse(departments.isEmpty());
    }

    /**
     * Teston metodën `getDepartmentById(Long id)` për të marrë një departament sipas ID-së.
     */
    @Test
    void testGetDepartmentById() {
        // Simulon sjelljen e `departmentRepository.findById(1L)` duke kthyer një Optional me departament
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Thirr metoda që po testohet
        Optional<Department> foundDepartment = departmentService.getDepartmentById(1L);

        // Sigurohet që rezultati të jetë i pranishëm (pra, jo bosh)
        assertTrue(foundDepartment.isPresent());
    }

    /**
     * Teston metodën `createDepartment(Department department)` për të ruajtur një departament të ri.
     */
    @Test
    void testCreateDepartment() {
        // Simulon sjelljen e `departmentRepository.save(department)` duke kthyer të njëjtin objekt `Department`
        when(departmentRepository.save(department)).thenReturn(department);

        // Thirr metoda që po testohet
        Department savedDepartment = departmentService.createDepartment(department);

        // Sigurohet që objekti i ruajtur nuk është null
        assertNotNull(savedDepartment);
    }

    /**
     * Teston metodën `deleteDepartment(Long id)` për të fshirë një departament sipas ID-së.
     */
    @Test
    void testDeleteDepartment() {
        // Vendos listën e pacientëve si bosh për të lejuar fshirjen e departamentit
        department.setPatients(new ArrayList<>());

        // Simulon sjelljen e `departmentRepository.findById(1L)` duke kthyer një Optional me departament
        lenient().when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Simulon që `existsById(1L)` kthen `true`
        lenient().when(departmentRepository.existsById(1L)).thenReturn(true);

        // Thirr metoda që po testohet
        boolean result = departmentService.deleteDepartment(1L);

        // Sigurohet që metoda ka kthyer `true`, që do të thotë se fshirja ishte e suksesshme
        assertTrue(result);

        // Verifikon që metoda `deleteById(1L)` është thirrur një herë
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}
