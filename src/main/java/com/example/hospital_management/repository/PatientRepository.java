// Përcakton paketën ku ndodhet kjo ndërfaqe repository
package com.example.hospital_management.repository;

// Importimi i klasave të nevojshme për të krijuar repository me Spring Data JPA
import com.example.hospital_management.entity.Patient; // Importimi i entitetit `Patient`
import org.springframework.data.jpa.repository.JpaRepository; // Importimi i `JpaRepository` për të përdorur operacionet CRUD
import org.springframework.stereotype.Repository; // Anotacion për të treguar që kjo ndërfaqe është një repository në Spring

/**
 * Kjo ndërfaqe është një repository për entitetin `Patient`, e cila përdor Spring Data JPA për të menaxhuar të dhënat.
 * Trashëgon `JpaRepository<Patient, Long>`, që do të thotë se ofron automatikisht operacionet CRUD.
 */
@Repository // Shënon këtë ndërfaqe si një komponent të Spring që menaxhon të dhënat
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
