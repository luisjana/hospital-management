package com.example.hospital_management.repository;


import com.example.hospital_management.entity.ClinicalData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {
    List<ClinicalData> findByPatientId(Long patientId);
}
