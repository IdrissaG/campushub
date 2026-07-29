package gestion.campushub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record Etudiant(
        Long id,
        @NotBlank(message = "Le nom ne peut pas être vide")
        String nom,
        @NotBlank(message = "Le prénom ne peut pas être vide")
        String prenom,
        @NotBlank(message = "L'email ne peut pas être vide")
        String email,
        @NotBlank(message = "La filière ne peut pas être vide")
        String filiere,
        @Positive(message = "L'âge doit être positif")
        int age
) {}