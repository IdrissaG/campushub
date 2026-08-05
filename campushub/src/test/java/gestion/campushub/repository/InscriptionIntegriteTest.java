package gestion.campushub.repository;

import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class InscriptionIntegriteTest {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testInscriptionAvecEtudiantInexistantEchoue() {
        Cours cours = coursRepository.save(new Cours("INFO101", "Algorithmique"));

        // On récupère une référence vers un étudiant qui n'existe PAS en base
        // getReference ne déclenche pas de requête immédiate, l'erreur
        // apparaîtra seulement au flush/commit -> exactement ce qu'on veut tester
        Etudiant etudiantInexistant = entityManager.getReference(Etudiant.class, 9999L);

        Inscription inscription = new Inscription(etudiantInexistant, cours, 15.0, LocalDate.now());

        assertThrows(DataIntegrityViolationException.class, () -> {
            inscriptionRepository.saveAndFlush(inscription);
        });
    }
}