package com.example.hospital_management.service;

import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.repository.AdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    public Optional<Admission> getAdmissionById(Long id) {
        return admissionRepository.findById(id);
    }

    public Admission createAdmission(Admission admission) {
        return admissionRepository.save(admission);
    }

    public boolean deleteAdmission(Long id) {
        if (admissionRepository.existsById(id)) {
            admissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
