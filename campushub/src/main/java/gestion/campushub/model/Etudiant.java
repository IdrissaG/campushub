package gestion.campushub.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "etudiant")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private int age;
    private String filiere;

    protected Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email, int age, String filiere) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.filiere = filiere;
    }
}