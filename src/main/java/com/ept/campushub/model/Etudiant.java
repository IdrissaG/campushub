package com.ept.campushub.model;

import java.time.LocalDate;

public record Etudiant(
        Long id,
        String nom,
        int age,
        String filiere,
        Niveau niveau,
        LocalDate dateNaissance)
{}
