package gestion.campushub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Reponse renvoyee apres une connexion reussie")
public record AuthResponse(

        @Schema(
                description = "Token JWT qui sera utilise pour acceder aux routes protegees",
                example = "eyJhbGciOiJIUzI1NiJ9.exemple.signature"
        )
        String token,

        @Schema(
                description = "Role de l'utilisateur connecte",
                example = "ETUDIANT"
        )
        String role
) {}
