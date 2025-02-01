package com.example.hospital_management.service;

import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // ✅ Metoda për marrjen e të gjithë pacientëve
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // ✅ Metoda për krijimin ose përditësimin e një pacienti
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // ✅ Metoda për marrjen e një pacienti nga ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }


    // ✅ Metoda për fshirjen e një pacienti nga ID
    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
