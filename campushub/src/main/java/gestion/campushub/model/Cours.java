package gestion.campushub.model;

public record Cours(String code, String nom, int credits) {
    // Le record génère automatiquement les getters code(), nom() et credits()
}