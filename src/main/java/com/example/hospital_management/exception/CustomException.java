package com.example.hospital_management.exception; // Përcakton paketën ku ndodhet klasa e gabimeve

// Definimi i një klase për gabime të personalizuara në aplikacion
public class CustomException extends RuntimeException {

    // Konstruktori për të krijuar një gabim me një mesazh specifik
    public CustomException(String message) {
        super(message); // Kalon mesazhin te klasa `RuntimeException`
    }

    // Konstruktori për të krijuar një gabim me një mesazh dhe një shkak (exception)
    public CustomException(String message, Throwable cause) {
        super(message, cause); // Kalon mesazhin dhe shkakun te `RuntimeException`
    }
}
