package gestion.campushub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Declare le schema d'authentification JWT aupres d'OpenAPI.
 * Sans cette declaration, Swagger UI n'affiche pas le bouton "Authorize"
 * et il est impossible d'appeler les endpoints proteges depuis la documentation.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CampusHub API",
                version = "1.0",
                description = "API REST de gestion des etudiants, cours et inscriptions. "
                        + "Les endpoints d'ecriture necessitent un token JWT avec le role ADMIN, "
                        + "obtenu via POST /api/auth/login."
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Coller uniquement le token, sans le prefixe 'Bearer '."
)
public class OpenApiConfig {
}
