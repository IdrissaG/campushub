package gestion.campushub.model;

import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "inscription")
@Getter
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    private Double note;
    private LocalDate dateInscription;
    protected Inscription() {}

    public Inscription(Etudiant etudiant, Cours cours, Double note, LocalDate dateInscription) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.note = note;
        this.dateInscription = dateInscription;
    }

}