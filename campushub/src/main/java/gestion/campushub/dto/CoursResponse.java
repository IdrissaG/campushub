package gestion.campushub.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Représentation d'un cours retourné par l'API")
public record CoursResponse(
    @Schema(description = "Code unique du cours", example = "DEV-JAVA")
    String code,

    @Schema(description = "Intitulé du cours", example = "Introduction à Spring Boot")
    String nom,

    @Schema(description = "Nombre de crédits accordés", example = "6")
    Integer credits
) {}

// Le role de record CoursResponse est de représenter les données d'un cours qui seront renvoyées au client. Il contient les informations essentielles du cours, telles que le code, le nom et le nombre de crédits associés.


// On ajoute également @Schema pour documenter la structure de la réponse.