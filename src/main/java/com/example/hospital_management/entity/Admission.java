package com.example.hospital_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admissions")
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Temporal(TemporalType.DATE)
    private Date admissionDate;

    @Temporal(TemporalType.DATE)
    private Date dischargeDate;

    @Enumerated(EnumType.STRING)
    private DischargeReason dischargeReason;
}

