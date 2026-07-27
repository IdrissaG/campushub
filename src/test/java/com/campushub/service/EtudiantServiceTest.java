package com.campushub.service;
import static org.junit.jupiter.api.Assertions.*;
import com.campushub.model.Etudiant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceTest {

    private final EtudiantService service =
            new EtudiantService();


    private final List<Etudiant> etudiants = List.of(
            new Etudiant(1L,"Ali",22,"Info",15),
            new Etudiant(2L,"Fatou",21,"Info",18),
            new Etudiant(3L,"Moussa",24,"Réseaux",17),
            new Etudiant(4L,"Awa",20,"Réseaux",19)
    );


    @Test
    void doitCalculerMoyenneAge() {

        assertEquals(
                21.75,
                service.moyenneAge(etudiants)
        );
    }


    @Test
    void doitGrouperParFiliere() {

        var resultat =
                service.grouperParFiliere(etudiants);

        assertEquals(
                2,
                resultat.get("Info").size()
        );
    }


    @Test
    void doitRetournerTop3() {

        var resultat =
                service.top3ParNote(etudiants);

        assertEquals(3, resultat.size());

        assertEquals(
                "Awa",
                resultat.get(0).nom()
        );
    }
}
