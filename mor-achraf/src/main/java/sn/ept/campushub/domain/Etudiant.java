package sn.ept.campushub.domain;

import java.util.Objects;

public record Etudiant(
        Long id,
        String prenom,
        String nom,
        int age,
        Filiere filiere
) {

    public Etudiant {
        Objects.requireNonNull(id, "id est obligatoire");
        Objects.requireNonNull(nom, "nom est obligatoire");
        Objects.requireNonNull(filiere, "filiere est obligatoire");
        if (age <= 0) {
            throw new IllegalArgumentException("age doit etre strictement positif : " + age);
        }
    }

    public String nomComplet() {
        return prenom + " " + nom;
    }
}