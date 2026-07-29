package gestion.campushub.model;

import java.time.LocalDate;
import java.time.Period;

public record Etudiant(
    String id,
    String nom,
    String prenom,
    LocalDate dateNaissance,
    String filiere
) {
    public int getAge() {
        if (dateNaissance == null) return 0;
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }
}