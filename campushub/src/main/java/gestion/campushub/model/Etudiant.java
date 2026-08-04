package gestion.campushub.model;

public record Etudiant(
        Long id,
        String nom,
        String prenom,
        String email,
        String filiere,
        int age
) {}