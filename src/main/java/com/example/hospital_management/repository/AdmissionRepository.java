// Përcakton paketën ku ndodhet kjo ndërfaqe repository
package com.example.hospital_management.repository;

// Importimi i klasave të nevojshme për të krijuar repository me Spring Data JPA
import com.example.hospital_management.entity.Admission; // Importimi i entitetit `Admission`
import org.springframework.data.jpa.repository.JpaRepository; // Importimi i `JpaRepository` për të përdorur operacionet CRUD
import org.springframework.stereotype.Repository; // Anotacion për të treguar që kjo ndërfaqe është një repository në Spring

import java.util.List;

/**
 * Kjo ndërfaqe është një repository për entitetin `Admission`, e cila përdor Spring Data JPA për të menaxhuar të dhënat.
 * Trashëgon `JpaRepository<Admission, Long>`, që do të thotë se ofron automatikisht operacionet CRUD.
 */
@Repository // Shënon këtë ndërfaqe si një komponent të Spring që menaxhon të dhënat
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    // Nuk ka nevojë për implementim manual, JpaRepository ofron automatikisht operacionet CRUD:
    // - findAll() -> Merr të gjitha pranimet nga baza e të dhënave
    // - findById(Long id) -> Kthen një pranim sipas ID-së në një Optional<Admission>
    // - save(Admission admission) -> Shton ose përditëson një pranim në bazën e të dhënave
    // - deleteById(Long id) -> Fshin një pranim sipas ID-së
    // - existsById(Long id) -> Kontrollon nëse një pranim ekziston sipas ID-së
    // Gjej të gjitha Admission që i përkasin një doktori sipas ID-së
    List<Admission> findByDoctorId(Long doctorId);
}
