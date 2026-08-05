package gestion.campushub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Informations necessaires pour creer un compte utilisateur")
public record RegisterRequest(

        @Schema(
                description = "Adresse email de l'utilisateur",
                example = "amina.diop@exemple.com"
        )
        @Email(message = "L'email doit etre valide")
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @Schema(
                description = "Mot de passe du compte, au moins 8 caracteres",
                example = "MotDePasse123!"
        )
        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres")
        String motDePasse,

        @Schema(
                description = "Nom affiche de l'utilisateur",
                example = "Amina Diop"
        )
        String nom
) {}
