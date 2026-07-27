package sn.ept.campushub.model;

/**
 * Un cours dispense sur le campus.
 *
 * @param id       identifiant unique
 * @param intitule intitule du cours (ex. "Programmation Java")
 */
public record Cours(Long id, String intitule) {

    public Cours {
        if (intitule == null || intitule.isBlank()) {
            throw new IllegalArgumentException("L'intitule est obligatoire");
        }
    }
}