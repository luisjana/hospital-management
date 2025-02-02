package com.example.hospital_management.exception; // Përcakton paketën ku ndodhet klasa e gabimeve

// Klasa që trajton rastet kur një entitet nuk gjendet në bazën e të dhënave
public class EntityNotFoundException extends RuntimeException {

    // Konstruktori për të krijuar një gabim me një mesazh specifik
    public EntityNotFoundException(String message) {
        super(message); // Kalon mesazhin te klasa `RuntimeException`
    }
}
