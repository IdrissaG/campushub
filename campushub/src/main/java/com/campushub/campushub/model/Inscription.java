package com.campushub.campushub.model;

public record Inscription(
    String id,
    Etudiant etudiant,
    Cours cours,
    double note,
    String regime
) {}