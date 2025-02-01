package com.example.hospital_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.example.hospital_management.controller",
		"com.example.hospital_management.service",
		"com.example.hospital_management.repository",
		"com.example.hospital_management.entity"
})
public class HospitalManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}
}
