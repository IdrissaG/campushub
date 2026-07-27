package sn.sonatel.campushub.entity;

import java.sql.Date;

import sn.sonatel.campushub.enums.TypeFiliere;
import sn.sonatel.campushub.enums.TypeNiveau;

public record Etudiant (
    Long id,
    String nom,
    String prenom,
    TypeFiliere filiere,
    TypeNiveau niveau,
    Integer age,
    Integer anneeEntree
) { }