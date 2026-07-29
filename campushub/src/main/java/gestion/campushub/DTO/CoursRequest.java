package gestion.campushub.DTO;

import jakarta.validation.constraints.NotBlank;

public record CoursRequest(

        @NotBlank(message = "le champ code est obliatoire")
        String code,
        @NotBlank(message= "le champ nom est obligatoire")
        String nom

) {
}
