package gestion.campushub.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CoursRequest(
        @Schema(description = "Code unique du cours", example = "JAVA101")
        @NotBlank(message = "le champ code est obligatoire")
        String code,

        @Schema(description = "nom du cours", example = "Introduction à Java")
        @NotBlank(message= "le champ nom est obligatoire")
        String nom

)
{
}
