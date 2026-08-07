package gestion.campushub.model;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Cours")
@Getter
public class Cours{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //rajout de l'id
    private String code;
    private String nom;

    @OneToMany(mappedBy = "cours")
    private Set<Inscription> inscriptions = new HashSet<>();

    protected Cours(){}
    public Cours (String code, String nom){
        this.code = code;
        this.nom = nom;
    }
}
