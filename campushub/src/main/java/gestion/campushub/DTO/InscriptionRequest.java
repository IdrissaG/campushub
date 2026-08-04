package gestion.campushub.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscriptionRequest(
        @Schema(description = "ID de l'étudiant à inscrire", example = "1")
        @NotNull(message = "l'id de l'etudiant est obligatoire")
        Long etudiantId,

        @Schema(description = "ID du cours concerné", example = "1")
        @NotNull(message = "l'id du cours est obligatoire")
        Long coursId,

        @Schema(description = "Note obtenue par l'étudiant", example = "17.0")
        Double note,

        @Schema(description = "Date de l'inscription", example = "2026-08-04")
        LocalDate dateInscription
) {
}
