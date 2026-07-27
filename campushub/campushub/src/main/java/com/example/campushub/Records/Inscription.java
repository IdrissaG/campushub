package com.example.campushub.Records;

public record Inscription(
        Long id,
        Etudiant etudiant,
        Cours cours,
        double note
) {
}
