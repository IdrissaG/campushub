package gestion.campushub.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record CoursResponse(

        @Schema(description = "Code unique du cours", example = "JAVA101")
        String code,
        @Schema(description = "Nom du cours", example = "Introduction à Java")
        String nom

) {
}
