package sn.sonatel.campushub.entity;

import java.sql.Date;

public record Inscription (
    Long id,
    Double note,
    Integer nombreCreditRestant,
    Cours cours,
    Etudiant etudiant
) { }
