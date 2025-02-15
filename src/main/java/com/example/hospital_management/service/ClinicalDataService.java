
package com.example.hospital_management.service;

// Importimi i klasave dhe anotimeve të nevojshme
import com.example.hospital_management.entity.ClinicalData;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.ClinicalDataRepository;
import com.example.hospital_management.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Kjo klasë është shtresa e shërbimit (Service Layer) që përmban logjikën e biznesit
 * për menaxhimin e të dhënave klinike.
 */
@Service // E bën këtë klasë një komponent të menaxhueshëm nga Spring (bean)
public class ClinicalDataService {

    // Repositorët për akses në bazën e të dhënave
    private final ClinicalDataRepository clinicalDataRepository;
    private final PatientRepository patientRepository;

    /**
     * Konstruktor për injektimin e varësive (Dependency Injection)
     *
     * @param clinicalDataRepository Repositori për të dhënat klinike
     * @param patientRepository Repositori për pacientët
     */
    public ClinicalDataService(
            ClinicalDataRepository clinicalDataRepository,
            PatientRepository patientRepository) {
        this.clinicalDataRepository = clinicalDataRepository;
        this.patientRepository = patientRepository;
    }


    /**
     * Metodë për të shtuar të dhëna klinike për një pacient.
     *
     * @param patientId ID e pacientit
     * @param note Shënimi klinik
     * @return Objekti `ClinicalData` i ruajtur në bazën e të dhënave
     */
    public ClinicalData addClinicalData(Long patientId, String note) {
        // Kontrollon nëse pacienti ekziston në bazën e të dhënave
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Krijon një objekt të ri ClinicalData
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setPatient(patient); // Vendos pacientin
        clinicalData.setNote(note);       // Vendos shënimin klinik

        // Ruajtja e të dhënave klinike në bazën e të dhënave
        return clinicalDataRepository.save(clinicalData);
    }

    /**
     * Metodë për të marrë të gjitha të dhënat klinike të një pacienti.
     *
     * @param patientId ID e pacientit
     * @return Një listë me të gjitha `ClinicalData` të pacientit
     */
    public List<ClinicalData> getClinicalDataByPatient(Long patientId) {
        // Merr të gjitha të dhënat klinike sipas ID-së së pacientit
        return clinicalDataRepository.findByPatientId(patientId);
    }
}
