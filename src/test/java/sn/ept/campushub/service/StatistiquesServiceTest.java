package sn.ept.campushub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sn.ept.campushub.model.Cours;
import sn.ept.campushub.model.Etudiant;
import sn.ept.campushub.model.Inscription;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesServiceTest {

    private StatistiquesService service;

    private Etudiant amadou;
    private Etudiant fatou;
    private Etudiant moussa;
    private Etudiant awa;
    private Cours java;
    private Cours reseaux;

    @BeforeEach
    void setUp() {
        service = new StatistiquesService();
        amadou  = new Etudiant(1L, "Amadou Ba",   20, "GIT");
        fatou   = new Etudiant(2L, "Fatou Sow",   22, "GIT");
        moussa  = new Etudiant(3L, "Moussa Diop", 24, "GEE");
        awa     = new Etudiant(4L, "Awa Ndiaye",  21, "GC");
        java    = new Cours(1L, "Programmation Java");
        reseaux = new Cours(2L, "Reseaux");
    }

    @Test
    @DisplayName("La moyenne d'age est correcte")
    void moyenneAge_calculeLaMoyenne() {
        List<Etudiant> etudiants = List.of(amadou, fatou, moussa, awa);
        double moyenne = service.moyenneAge(etudiants);
        // (20 + 22 + 24 + 21) / 4 = 21.75
        assertEquals(21.75, moyenne, 0.001);
    }

    @Test
    @DisplayName("Moyenne d'age d'une liste vide vaut 0")
    void moyenneAge_listeVide_renvoieZero() {
        assertEquals(0.0, service.moyenneAge(List.of()), 0.001);
    }

    @Test
    @DisplayName("Le groupement par filiere repartit correctement")
    void grouperParFiliere_regroupeCorrectement() {
        List<Etudiant> etudiants = List.of(amadou, fatou, moussa, awa);
        Map<String, List<Etudiant>> parFiliere = service.grouperParFiliere(etudiants);

        assertEquals(3, parFiliere.size());
        assertEquals(2, parFiliere.get("GIT").size());
        assertTrue(parFiliere.get("GIT").contains(amadou));
        assertTrue(parFiliere.get("GIT").contains(fatou));
    }

    @Test
    @DisplayName("Le top 3 renvoie les 3 meilleures notes en ordre decroissant")
    void top3ParNote_renvoieLesTroisMeilleures() {
        List<Inscription> inscriptions = List.of(
                new Inscription(amadou, java,    12.0),
                new Inscription(fatou,  java,    18.5),
                new Inscription(moussa, reseaux,  9.0),
                new Inscription(awa,    reseaux, 15.0),
                new Inscription(amadou, reseaux, 17.0)
        );
        List<Inscription> top3 = service.top3ParNote(inscriptions);

        assertEquals(3, top3.size());
        assertEquals(18.5, top3.get(0).note(), 0.001);
        assertEquals(17.0, top3.get(1).note(), 0.001);
        assertEquals(15.0, top3.get(2).note(), 0.001);
    }

    @Test
    @DisplayName("Top 3 avec moins de 3 inscriptions renvoie tout")
    void top3ParNote_moinsDeTrois_renvoieToutDisponible() {
        List<Inscription> inscriptions = List.of(
                new Inscription(amadou, java, 14.0),
                new Inscription(fatou,  java, 16.0)
        );
        List<Inscription> top3 = service.top3ParNote(inscriptions);

        assertEquals(2, top3.size());
        assertEquals(16.0, top3.get(0).note(), 0.001);
    }
}