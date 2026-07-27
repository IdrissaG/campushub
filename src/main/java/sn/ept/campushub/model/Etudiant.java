package sn.ept.campushub.model;

/**
 * Un etudiant du campus.
 *
 * @param id      identifiant unique
 * @param nom     nom complet
 * @param age     age en annees
 * @param filiere filiere suivie (ex. "GIT", "GEE", "GC")
 */
public record Etudiant(Long id, String nom, int age, String filiere) {

    public Etudiant {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("L'age doit etre positif");
        }
        if (filiere == null || filiere.isBlank()) {
            throw new IllegalArgumentException("La filiere est obligatoire");
        }
    }
}