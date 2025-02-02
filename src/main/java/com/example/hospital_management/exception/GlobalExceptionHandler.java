package com.example.hospital_management.exception; // Përcakton paketën ku ndodhet klasa e menaxhimit të gabimeve

// Importimi i klasave të nevojshme për trajtimin e gabimeve dhe validimin e të dhënave
import org.springframework.http.HttpStatus; // Përdoret për të përcaktuar statuset HTTP në përgjigje
import org.springframework.http.ResponseEntity; // Përdoret për të ndërtuar një përgjigje HTTP me statusin përkatës
import org.springframework.validation.FieldError; // Përdoret për të trajtuar gabimet në fushat e validimit të të dhënave
import org.springframework.web.bind.MethodArgumentNotValidException; // Përdoret për të kapur gabimet e validimit në inputet e përdoruesit
import org.springframework.web.bind.annotation.ExceptionHandler; // Përdoret për të kapur dhe trajtuar gabime të veçanta
import org.springframework.web.bind.annotation.ResponseStatus; // Përdoret për të përcaktuar statusin HTTP në përgjigje
import org.springframework.web.bind.annotation.RestControllerAdvice; // Kjo klasë vepron si një global exception handler për të gjitha kontrolluesit REST

import java.util.HashMap; // Përdoret për të ndërtuar një hartë me gabime të personalizuara
import java.util.Map; // Përdoret për të ruajtur çiftet e të dhënave (çelës, vlerë) për përgjigjet e gabimeve

// Anotacion që tregon se kjo klasë do të kapë globalisht gabimet në të gjithë aplikacionin
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 1. Trajton të gjitha gabimet e përgjithshme që mund të ndodhin në aplikacion
    @ExceptionHandler(Exception.class) // Kap çdo gabim të llojit Exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Përcakton që përgjigja do të ketë statusin HTTP 500
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        Map<String, String> response = new HashMap<>(); // Krijon një hartë për të mbajtur informacionin e gabimit
        response.put("error", "Internal Server Error"); // Vendos llojin e gabimit
        response.put("message", e.getMessage()); // Vendos mesazhin e gabimit
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Kthen përgjigjen me status 500
    }

    // ✅ 2. Trajton gabimet kur një argument është i pavlefshëm ose nuk përputhet me formatin e kërkuar
    @ExceptionHandler(IllegalArgumentException.class) // Kap gabimet e llojit IllegalArgumentException
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Përcakton që përgjigja do të ketë statusin HTTP 400
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, String> response = new HashMap<>(); // Krijon një hartë për të mbajtur detajet e gabimit
        response.put("error", "Bad Request"); // Vendos llojin e gabimit
        response.put("message", e.getMessage()); // Vendos mesazhin e gabimit
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Kthen përgjigjen me status 400
    }

    // ✅ 3. Trajton gabimet kur një entitet nuk gjendet në databazë
    @ExceptionHandler(EntityNotFoundException.class) // Kap gabimet e llojit EntityNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND) // Përcakton që përgjigja do të ketë statusin HTTP 404
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException e) {
        Map<String, String> response = new HashMap<>(); // Krijon një hartë për të mbajtur detajet e gabimit
        response.put("error", "Not Found"); // Vendos llojin e gabimit
        response.put("message", e.getMessage()); // Vendos mesazhin e gabimit
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Kthen përgjigjen me status 404
    }

    // ✅ 4. Trajton gabimet kur ka probleme me validimin e inputeve të përdoruesit
    @ExceptionHandler(MethodArgumentNotValidException.class) // Kap gabimet e validimit në argumentet e metodave
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Përcakton që përgjigja do të ketë statusin HTTP 400
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // Krijon një hartë për të mbajtur gabimet e validimit

        // Për çdo gabim validimi, e shton në hartë me çelësin emrin e fushës dhe vlerën mesazhin e gabimit
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); // Kthen përgjigjen me status 400
    }
}
