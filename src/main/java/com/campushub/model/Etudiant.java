package com.campushub.model;

public record Etudiant(
    Long id,
    String nom,
    int age,
    String filiere,
    double note
) {}
