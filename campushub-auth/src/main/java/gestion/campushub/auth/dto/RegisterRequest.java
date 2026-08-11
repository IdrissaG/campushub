package gestion.campushub.auth.dto;
import jakarta.validation.constraints.NotBlank;
public record RegisterRequest(
        @NotBlank String email,
        @NotBlank String motDePasse,
        String nom,
        String role
) {}
