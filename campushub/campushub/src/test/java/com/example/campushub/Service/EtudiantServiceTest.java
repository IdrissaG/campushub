package com.example.campushub.Service;

import com.example.campushub.Records.Cours;
import com.example.campushub.Records.Etudiant;
import com.example.campushub.Records.Inscription;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceTest {

    private final EtudiantService service = new EtudiantService();

    @Test
    void testMoyenneAge() {

        List<Etudiant> etudiants = List.of(
                new Etudiant(1L,"Ali","Ba",20,"Informatique"),
                new Etudiant(2L,"Fatou","Diop",22,"Informatique"),
                new Etudiant(3L,"Awa","Fall",18,"Mathématiques")
        );

        assertEquals(20, service.moyenneAge(etudiants));
    }

    @Test
    void testGroupementParFiliere() {

        List<Etudiant> etudiants = List.of(
                new Etudiant(1L,"Ali","Ba",20,"Informatique"),
                new Etudiant(2L,"Fatou","Diop",22,"Informatique"),
                new Etudiant(3L,"Awa","Fall",18,"Mathématiques")
        );

        Map<String,List<Etudiant>> resultat =
                service.grouperParFiliere(etudiants);

        assertEquals(2, resultat.get("Informatique").size());
        assertEquals(1, resultat.get("Mathématiques").size());
    }

    @Test
    void testTop3ParNote() {

        Etudiant e1 = new Etudiant(1L,"Ali","Ba",20,"Info");
        Etudiant e2 = new Etudiant(2L,"Fatou","Diop",21,"Info");
        Etudiant e3 = new Etudiant(3L,"Awa","Fall",22,"Math");
        Etudiant e4 = new Etudiant(4L,"Moussa","Sow",23,"Math");

        Cours java = new Cours(1L,"JAVA","Java",5);

        List<Inscription> inscriptions = List.of(
                new Inscription(1L,e1,java,14, 4),
                new Inscription(2L,e2,java,18, 5),
                new Inscription(3L,e3,java,20, 3),
                new Inscription(4L,e4,java,17, 5)
        );

        List<Inscription> top3 = service.top3ParNote(inscriptions);

        assertEquals(3, top3.size());
        assertEquals(20, top3.get(0).note());
        assertEquals(18, top3.get(1).note());
        assertEquals(17, top3.get(2).note());
    }
}