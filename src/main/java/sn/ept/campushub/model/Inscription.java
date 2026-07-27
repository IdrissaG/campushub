package sn.ept.campushub.model;

/**
 * L'inscription d'un etudiant a un cours, avec la note obtenue.
 *
 * @param etudiant l'etudiant inscrit
 * @param cours    le cours suivi
 * @param note     note obtenue sur 20
 */
public record Inscription(Etudiant etudiant, Cours cours, double note) {

    public Inscription {
        if (etudiant == null) {
            throw new IllegalArgumentException("L'etudiant est obligatoire");
        }
        if (cours == null) {
            throw new IllegalArgumentException("Le cours est obligatoire");
        }
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit etre entre 0 et 20");
        }
    }
}