// Përcakton paketën ku ndodhet kjo ndërfaqe repository
package com.example.hospital_management.repository;

// Importimi i klasave të nevojshme për të krijuar repository me Spring Data JPA
import com.example.hospital_management.entity.Doctor; // Importimi i entitetit `Doctor`
import org.springframework.data.jpa.repository.JpaRepository; // Importimi i `JpaRepository` për të përdorur operacionet CRUD
import org.springframework.stereotype.Repository; // Anotacion për të treguar që kjo ndërfaqe është një repository në Spring

/**
 * Kjo ndërfaqe është një repository për entitetin `Doctor`, e cila përdor Spring Data JPA për të menaxhuar të dhënat.
 * Trashëgon `JpaRepository<Doctor, Long>`, që do të thotë se ofron automatikisht operacionet CRUD.
 */
@Repository // Shënon këtë ndërfaqe si një komponent të Spring që menaxhon të dhënat
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Nuk ka nevojë për implementim manual, JpaRepository ofron automatikisht operacionet CRUD:
    // - findAll() -> Merr të gjithë doktorët nga baza e të dhënave
    // - findById(Long id) -> Kthen një doktor sipas ID-së në një Optional<Doctor>
    // - save(Doctor doctor) -> Shton ose përditëson një doktor në bazën e të dhënave
    // - deleteById(Long id) -> Fshin një doktor sipas ID-së
    // - existsById(Long id) -> Kontrollon nëse një doktor ekziston sipas ID-së
}
