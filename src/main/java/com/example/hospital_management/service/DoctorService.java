// Përcakton paketën ku ndodhet kjo klasë shërbimi për menaxhimin e doktorëve
package com.example.hospital_management.service;

// Importimi i klasave të nevojshme për të menaxhuar entitetin `Doctor` dhe për të komunikuar me bazën e të dhënave
import com.example.hospital_management.DTO.DoctorDTO;
import com.example.hospital_management.entity.Admission;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.repository.AdmissionRepository;
import com.example.hospital_management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Kjo klasë shërben për të menaxhuar doktorët në spital.
 * Përdor `DoctorRepository` për të komunikuar me bazën e të dhënave.
 */
@Service // Shënon këtë klasë si një komponent të Spring që menaxhon logjikën e biznesit për doktorët
public class DoctorService {

    @Autowired // Injeksion i varësisë për `DoctorRepository`
    private DoctorRepository doctorRepository;

    @Autowired
    private AdmissionRepository admissionRepository; // Nëse doktori ka admisione të lidhura
    /**
     * Merr të gjithë doktorët nga baza e të dhënave dhe i konverton në `DoctorDTO`.
     * @return një listë me të gjithë doktorët në formatin DTO
     */
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll() // Merr të gjithë doktorët nga baza e të dhënave
                .stream()
                .map(DoctorDTO::new) // Konverton secilin `Doctor` në `DoctorDTO`
                .collect(Collectors.toList()); // Kthen një listë me doktorët e konvertuar
    }

    /**
     * Kthen një doktor sipas ID-së dhe e konverton në `DoctorDTO`.
     * @param id ID-ja e doktorit që do të kërkohet
     * @return një `Optional<DoctorDTO>` që përmban doktorin nëse ekziston
     */
    public Optional<DoctorDTO> getDoctorById(Long id) {
        return doctorRepository.findById(id).map(DoctorDTO::new); // Kërkon doktorin sipas ID-së dhe e konverton në DTO
    }

    /**
     * Krijon ose përditëson një doktor në bazën e të dhënave.
     * @param doctor objekti `Doctor` që do të ruhet
     * @return objekti `Doctor` i ruajtur
     */
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor); // Ruajtja e doktorit në bazën e të dhënave
    }

    /**
     * Përditëson një doktor ekzistues sipas ID-së.
     * @param id ID-ja e doktorit që do të përditësohet
     * @param doctorDetails të dhënat e reja të doktorit
     * @return `Optional<DoctorDTO>` me të dhënat e përditësuara ose `Optional.empty()` nëse nuk ekziston
     */
    public Optional<DoctorDTO> updateDoctor(Long id, Doctor doctorDetails) {
        return doctorRepository.findById(id).map(existingDoctor -> { // Kontrollon nëse ekziston doktori
            existingDoctor.setFirstName(doctorDetails.getFirstName()); // Përditëson emrin e doktorit
            existingDoctor.setLastName(doctorDetails.getLastName()); // Përditëson mbiemrin e doktorit
            existingDoctor.setSpecialization(doctorDetails.getSpecialization()); // Përditëson specializimin
            existingDoctor.setPhone(doctorDetails.getPhone()); // Përditëson numrin e telefonit
            existingDoctor.setBirthDate(doctorDetails.getBirthDate()); // Përditëson datën e lindjes

            doctorRepository.save(existingDoctor); // Ruajtja e të dhënave të përditësuara
            return new DoctorDTO(existingDoctor); // Konverton në DTO dhe e kthen
        });
    }

    /**
     * Fshin një doktor nga baza e të dhënave sipas ID-së.
     * @param id ID-ja e doktorit që do të fshihet
     * @return `true` nëse doktori u fshi me sukses, përndryshe `false`
     */

    public boolean deleteDoctor(Long id) {
        // Kontrollo nëse doktori ekziston
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isEmpty()) {
            return false; // Kthen false nëse doktori nuk ekziston
        }

        // Kontrollo nëse ka admisione të lidhura dhe fshiji ato (opsionale)
        List<Admission> admissions = admissionRepository.findByDoctorId(id);
        if (!admissions.isEmpty()) {
            admissionRepository.deleteAll(admissions);
        }

        // Fshi doktorin
        doctorRepository.deleteById(id);
        return true;
    }
}
