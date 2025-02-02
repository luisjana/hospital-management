// Përcakton paketën ku ndodhet kjo klasë shërbimi për menaxhimin e departamenteve
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për të menaxhuar entitetin `Department` dhe për të komunikuar me bazën e të dhënave
import com.example.hospital_management.entity.Department;
import com.example.hospital_management.exception.EntityNotFoundException;
import com.example.hospital_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Kjo klasë shërben për të menaxhuar departamentet në spital.
 * Përdor `DepartmentRepository` për të komunikuar me bazën e të dhënave.
 */
@Service // Shënon këtë klasë si një komponent të Spring që menaxhon logjikën e biznesit për departamentet
public class DepartmentService {

    @Autowired // Injeksion i varësisë për `DepartmentRepository`
    private DepartmentRepository departmentRepository;

    /**
     * Merr të gjitha departamentet nga baza e të dhënave.
     * @return një listë me të gjitha departamentet
     */
    public List<Department> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll(); // Merr të gjitha departamentet
        System.out.println("Departments: " + departments); // Log për debug (përdoret për testim)
        return departments;
    }

    /**
     * Kthen një departament sipas ID-së.
     * @param id ID-ja e departamentit që do të kërkohet
     * @return një `Optional<Department>` që përmban departamentin nëse ekziston
     */
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id); // Kërkon një departament sipas ID-së
    }

    /**
     * Krijon ose përditëson një departament në bazën e të dhënave.
     * @param department objekti `Department` që do të ruhet
     * @return objekti `Department` i ruajtur
     */
    public Department createDepartment(Department department) {
        return departmentRepository.save(department); // Ruajtja e departamentit në bazën e të dhënave
    }

    /**
     * Përditëson një departament ekzistues sipas ID-së.
     * @param id ID-ja e departamentit që do të përditësohet
     * @param department të dhënat e reja të departamentit
     * @return `Optional<Department>` me të dhënat e përditësuara ose `Optional.empty()` nëse nuk ekziston
     */
    public Optional<Department> updateDepartment(Long id, Department department) {
        if (departmentRepository.existsById(id)) { // Kontrollon nëse ekziston departamenti
            department.setId(id); // Vendos ID-në e dhënë në objektin `department`
            return Optional.of(departmentRepository.save(department)); // Përditëson departamentin
        }
        return Optional.empty(); // Kthen një Optional bosh nëse departamenti nuk gjendet
    }

    /**
     * Fshin një departament nga baza e të dhënave sipas ID-së.
     * Nuk lejon fshirjen e departamentit nëse ka pacientë aktivë.
     * @param id ID-ja e departamentit që do të fshihet
     * @return `true` nëse departamenti u fshi me sukses
     * @throws EntityNotFoundException nëse departamenti nuk ekziston
     * @throws IllegalStateException nëse departamenti ka pacientë aktivë
     */
    public boolean deleteDepartment(Long id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id); // Kërkon departamentin sipas ID-së
        if (departmentOpt.isEmpty()) {
            throw new EntityNotFoundException("Department with ID " + id + " not found"); // Nëse nuk ekziston, hedh gabim
        }

        Department department = departmentOpt.get(); // Merr departamentin nga Optional
        if (department.getPatients() != null && !department.getPatients().isEmpty()) {
            throw new IllegalStateException("Cannot delete department with active patients."); // Nuk lejon fshirjen nëse ka pacientë
        }

        departmentRepository.deleteById(id); // Fshin departamentin nga baza e të dhënave
        return true;
    }
}
