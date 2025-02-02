// Përcakton paketën ku ndodhet kjo klasë shërbimi për menaxhimin e pranimet e pacientëve
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për të menaxhuar entitetin `Admission` dhe për të komunikuar me bazën e të dhënave
import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.exception.EntityNotFoundException;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Kjo klasë shërben për të menaxhuar pranimet e pacientëve në spital.
 * Përdor `AdmissionRepository` për të komunikuar me bazën e të dhënave.
 */
@Service // Shënon këtë klasë si një komponent të Spring që menaxhon logjikën e biznesit për pranimet
public class AdmissionService {


    @Autowired // Injeksion i varësisë për `AdmissionRepository`
    private AdmissionRepository admissionRepository;
    @Autowired
    private PatientRepository patientRepository; // Injektimi i PatientRepository

    @Autowired
    private DoctorRepository doctorRepository; // Injektimi i DoctorRepository

    /**
     * Merr të gjitha pranimet nga baza e të dhënave.
     * @return një listë me të gjitha pranimet
     */
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll(); // Përdor JpaRepository për të marrë të gjitha pranimet
    }

    /**
     * Kthen një pranim sipas ID-së.
     * @param id ID-ja e pranimit që do të kërkohet
     * @return një `Optional<Admission>` që përmban pranimin nëse ekziston
     */
    public Optional<Admission> getAdmissionById(Long id) {
        return admissionRepository.findById(id); // Përdor JpaRepository për të marrë pranimin sipas ID-së
    }

    /**
     * Krijon ose përditëson një pranim të pacientit në bazën e të dhënave.
     * @param admission objekti `Admission` që do të ruhet
     * @return objekti `Admission` i ruajtur
     */
    public Admission createAdmission(Admission admission) {
        if (admission.getPatient() == null || admission.getDoctor() == null) {
            throw new IllegalArgumentException("Patient and Doctor must not be null.");
        }

        Optional<Patient> patientOpt = patientRepository.findById(admission.getPatient().getId());
        Optional<Doctor> doctorOpt = doctorRepository.findById(admission.getDoctor().getId());

        if (patientOpt.isEmpty() || doctorOpt.isEmpty()) {
            throw new EntityNotFoundException("Patient or Doctor not found.");
        }

        admission.setPatient(patientOpt.get());
        admission.setDoctor(doctorOpt.get());

        return admissionRepository.save(admission);
    }



    /**
     * Fshin një pranim nga baza e të dhënave sipas ID-së.
     * @param id ID-ja e pranimit që do të fshihet
     * @return `true` nëse pranimi u fshi me sukses, përndryshe `false`
     */
    public boolean deleteAdmission(Long id) {
        if (admissionRepository.existsById(id)) { // Kontrollon nëse pranimi ekziston në bazën e të dhënave
            admissionRepository.deleteById(id); // Fshin pranimin sipas ID-së
            return true;
        }
        return false; // Nëse pranimi nuk ekziston, kthen `false`
    }
}
