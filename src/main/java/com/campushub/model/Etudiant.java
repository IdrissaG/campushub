package com.campushub.model;

public record Etudiant(
    Long id,
    String nom,
    int age,
    String filiere
) {
    public Etudiant {

        if (nom == null || nom.isBlank())
            throw new IllegalArgumentException("Le nom est obligatoire.");

        if (filiere == null || filiere.isBlank())
            throw new IllegalArgumentException("La filière est obligatoire.");
        if (age < 0)
            throw new IllegalArgumentException("L'âge doit être positif.");
    }   
}




