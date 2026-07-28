package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtudiantServiceTest {

    private final EtudiantService service = new EtudiantService(new EtudiantRepository());

    @Test
    void doitCreerUnEtudiantEtLuiAffecterUnId() {
        Etudiant cree = service.create(new Etudiant("Awa", 20, "Informatique"));

        assertEquals("Awa", cree.nom());
        assertEquals(20, cree.age());
        assertEquals("Informatique", cree.filiere());
        assertEquals(1L, cree.id());
    }

    @Test
    void doitListerTousLesEtudiants() {
        service.create(new Etudiant("Awa", 20, "Informatique"));
        service.create(new Etudiant("Moussa", 22, "Gestion"));

        List<Etudiant> etudiants = service.getAll();

        assertEquals(2, etudiants.size());
    }

    @Test
    void doitRetrouverUnEtudiantParId() {
        Etudiant cree = service.create(new Etudiant("Awa", 20, "Informatique"));

        Optional<Etudiant> trouve = service.getById(cree.id());

        assertTrue(trouve.isPresent());
        assertEquals("Awa", trouve.get().nom());
    }

    @Test
    void doitRetournerVideQuandLIdEstInconnu() {
        Optional<Etudiant> trouve = service.getById(999L);

        assertTrue(trouve.isEmpty());
    }

    @Test
    void doitMettreAJourUnEtudiantExistant() {
        Etudiant cree = service.create(new Etudiant("Awa", 20, "Informatique"));

        Optional<Etudiant> misAJour = service.update(cree.id(), new Etudiant("Awa Diop", 21, "Informatique"));

        assertTrue(misAJour.isPresent());
        assertEquals("Awa Diop", misAJour.get().nom());
        assertEquals(21, misAJour.get().age());
    }

    @Test
    void doitRetournerVideQuandOnMetAJourUnIdInconnu() {
        Optional<Etudiant> misAJour = service.update(999L, new Etudiant("Awa", 20, "Informatique"));

        assertTrue(misAJour.isEmpty());
    }

    @Test
    void doitSupprimerUnEtudiantExistant() {
        Etudiant cree = service.create(new Etudiant("Awa", 20, "Informatique"));

        assertTrue(service.delete(cree.id()));
        assertTrue(service.getById(cree.id()).isEmpty());
    }

    @Test
    void doitRetournerFauxQuandOnSupprimeUnIdInconnu() {
        assertFalse(service.delete(999L));
    }
}
