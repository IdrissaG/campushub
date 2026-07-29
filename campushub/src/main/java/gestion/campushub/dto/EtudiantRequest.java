package gestion.campushub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record EtudiantRequest(
    @NotBlank(message = "Le nom ne peut pas être vide")
    String nom,

    @NotBlank(message = "Le prénom ne peut pas être vide")
    String prenom,

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    LocalDate dateNaissance,

    @NotBlank(message = "La filière est obligatoire")
    String filiere
) {}