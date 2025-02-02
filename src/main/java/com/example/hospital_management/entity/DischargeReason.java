package com.example.hospital_management.entity; // Përcakton paketën ku ndodhet kjo klasë enum

// Definimi i një enum për të përfaqësuar arsyet e lirimit të pacientit
public enum DischargeReason {
    RECOVERED,  // Pacienti është shëruar dhe është liruar nga spitali
    TRANSFERRED, // Pacienti është transferuar në një spital tjetër
    DECEASED // Pacienti ka ndërruar jetë gjatë qëndrimit në spital
}
