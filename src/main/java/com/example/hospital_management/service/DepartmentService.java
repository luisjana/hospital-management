package com.example.hospital_management.service;

import com.example.hospital_management.entity.Department;
import com.example.hospital_management.exception.EntityNotFoundException;
import com.example.hospital_management.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> updateDepartment(Long id, Department department) {
        if (departmentRepository.existsById(id)) {
            department.setId(id);
            return Optional.of(departmentRepository.save(department));
        }
        return Optional.empty();
    }

    public boolean deleteDepartment(Long id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        if (departmentOpt.isEmpty()) {
            throw new EntityNotFoundException("Department with ID " + id + " not found");
        }

        Department department = departmentOpt.get();
        if (department.getPatients() != null && !department.getPatients().isEmpty()) {
            throw new IllegalStateException("Cannot delete department with active patients.");
        }

        departmentRepository.deleteById(id);
        return true;
    }
}
