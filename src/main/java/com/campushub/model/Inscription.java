package com.campushub.model;

public record Inscription(
    Etudiant etudiant,
    Cours cours,
    String dateInscription
) {}
