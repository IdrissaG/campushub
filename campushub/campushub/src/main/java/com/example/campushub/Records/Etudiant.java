package com.example.campushub.Records;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record Etudiant(
        Long id,
        String nom,
        String prenom,
        @Min(15) @Max(50) int age,
        String filiere
) {
}
