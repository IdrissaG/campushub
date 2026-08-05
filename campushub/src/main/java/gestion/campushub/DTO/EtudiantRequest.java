package gestion.campushub.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record EtudiantRequest(
    @Schema(description = "Nom de famille", example = "Diop")
    @NotBlank(message = "Le nom est requis")
    String nom,

    @Schema(description = "Prénom", example = "Awa")
    @NotBlank(message = "Le prénom est requis")
    String prenom,

    @Schema(description = "Email", example = "awa.diop@example.com")
    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est requis")
    String email,

    @Schema(description = "Âge (minimum 15)", example = "21")
    @Min(value = 15, message = "L'âge doit être au minimum 15")
    int age,

    @Schema(description = "Filière", example = "Informatique")
    String filiere
) {}
