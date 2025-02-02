// Përcakton paketën ku ndodhet kjo klasë shërbimi për menaxhimin e pacientëve
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për të menaxhuar entitetin `Patient` dhe për të komunikuar me bazën e të dhënave
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

/**
 * Kjo klasë shërben për të menaxhuar pacientët në spital.
 * Përdor `PatientRepository` për të komunikuar me bazën e të dhënave.
 */
@Service // Shënon këtë klasë si një komponent të Spring që menaxhon logjikën e biznesit për pacientët
public class PatientService {

    @Autowired // Injeksion i varësisë për `PatientRepository`
    private PatientRepository patientRepository;

    /**
     * Merr të gjithë pacientët nga baza e të dhënave.
     * @return një listë me të gjithë pacientët
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Përdor JpaRepository për të marrë të gjithë pacientët
    }

    /**
     * Krijon ose përditëson një pacient në bazën e të dhënave.
     * @param patient objekti `Patient` që do të ruhet
     * @return objekti `Patient` i ruajtur
     */
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient); // Ruajtja ose përditësimi i pacientit
    }

    /**
     * Kthen një pacient sipas ID-së.
     * @param id ID-ja e pacientit që do të kërkohet
     * @return një `Optional<Patient>` që përmban pacientin nëse ekziston
     */
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id); // Kërkon pacientin sipas ID-së
    }

    /**
     * Fshin një pacient nga baza e të dhënave sipas ID-së.
     * @param id ID-ja e pacientit që do të fshihet
     * @return `true` nëse pacienti u fshi me sukses, përndryshe `false`
     */
    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) { // Kontrollon nëse pacienti ekziston në bazën e të dhënave
            patientRepository.deleteById(id); // Fshin pacientin sipas ID-së
            return true;
        }
        return false; // Nëse pacienti nuk ekziston, kthen `false`
    }
}
