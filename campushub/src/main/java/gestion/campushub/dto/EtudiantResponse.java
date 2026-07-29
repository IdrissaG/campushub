package gestion.campushub.dto;

import java.time.LocalDate;

public record EtudiantResponse(
    String id,
    String nom,
    String prenom,
    LocalDate dateNaissance,
    int age,
    String filiere
) {}