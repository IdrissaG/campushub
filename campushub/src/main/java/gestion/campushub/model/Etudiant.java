package gestion.campushub.model;

public record Etudiant(Long id, String nom, int age, String filiere) {

    public Etudiant(String nom, int age, String filiere) {
        this(null, nom, age, filiere);
    }
}