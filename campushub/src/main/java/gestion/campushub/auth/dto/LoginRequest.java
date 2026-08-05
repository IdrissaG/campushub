package gestion.campushub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Informations necessaires pour se connecter")
public record LoginRequest(

        @Schema(
                description = "Adresse email du compte",
                example = "amina.diop@exemple.com"
        )
        @Email(message = "L'email doit etre valide")
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @Schema(
                description = "Mot de passe du compte",
                example = "MotDePasse123!"
        )
        @NotBlank(message = "Le mot de passe est obligatoire")
        String motDePasse
) {}
