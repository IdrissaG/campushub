package com.campushub.model;

public record Inscription(
    Etudiant etudiant,
    Cours cours,
    double note
) {
    public Inscription {

        if (etudiant == null)
            throw new IllegalArgumentException("L'étudiant est obligatoire.");
        if (cours == null)
            throw new IllegalArgumentException("Le cours est obligatoire.");
        if (note < 0 || note > 20)
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20.");
    }
}
