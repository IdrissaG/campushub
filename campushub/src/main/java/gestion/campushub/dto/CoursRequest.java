package gestion.campushub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Données requises pour créer ou modifier un cours")
public record CoursRequest(
    @Schema(description = "Code unique du cours", example = "DEV-JAVA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le code du cours ne peut pas être vide")
    String code,

    @Schema(description = "Intitulé ou nom complet du cours", example = "Introduction à Spring Boot", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom du cours ne peut pas être vide")
    String nom,

    @Schema(description = "Nombre de crédits ECTS associés au cours", example = "6", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Le nombre de crédits est obligatoire")
    @Min(value = 1, message = "Un cours doit avoir au moins 1 crédit")
    Integer credits
) {}

// ici, nous avons ajouté des annotations de validation pour garantir que le code et le nom du cours ne soient pas vides, et que le nombre de crédits soit au moins égal à 1.

// le role de record CoursRequest est de représenter les données d'un cours qui seront reçues du cliet. il contient les informations essentielles du cours, telles que le code, le nom et le nombre de crédits associés.

//On ajoute les annotations @Schema pour décrire chaque champ dans la documentation de l'API.
