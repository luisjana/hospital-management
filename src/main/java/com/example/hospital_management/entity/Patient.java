package com.example.hospital_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone must be in XXX-XXX-XXXX format")
    private String phone;

    @Email
    private String email;

    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true) // Opsionale
    private Department department;
}
