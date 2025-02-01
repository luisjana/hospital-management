package com.example.hospital_management.DTO;

import com.example.hospital_management.entity.Gender;
import com.example.hospital_management.entity.Patient;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String phone;
    private String email;
    private String address;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.birthDate = patient.getBirthDate();
        this.gender = patient.getGender();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.address = patient.getAddress();
    }
}
