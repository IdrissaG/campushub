package gestion.campushub.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record InscriptionResponse(
        @Schema(description = "ID unique de l'inscription", example = "1")
        Long id,

        @Schema(description = "ID de l'étudiant inscrit", example = "1")
        Long etudiantId,

        @Schema(description = "Nom de l'étudiant", example = "Diop")
        String etudiantNom,

        @Schema(description = "Prénom de l'étudiant", example = "Awa")
        String etudiantPrenom,

        @Schema(description = "ID du cours", example = "1")
        Long coursId,

        @Schema(description = "Code du cours", example = "JAVA101")
        String coursCode,

        @Schema(description = "Nom du cours", example = "Introduction à Java")
        String coursNom,

        @Schema(description = "Note obtenue par l'étudiant", example = "17.0")
        Double note,

        @Schema(description = "Date de l'inscription", example = "2026-08-04")
        LocalDate dateInscription
) {
}
