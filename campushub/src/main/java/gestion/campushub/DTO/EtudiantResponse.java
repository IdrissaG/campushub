package gestion.campushub.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record EtudiantResponse(
    @Schema(description = "ID unique de l'étudiant")
    Long id,

    @Schema(description = "Nom de famille")
    String nom,

    @Schema(description = "Prénom")
    String prenom,

    @Schema(description = "Email")
    String email,

    @Schema(description = "Âge")
    int age,

    @Schema(description = "Filière")
    String filiere
) {}
