package gestion.campushub.repository;

import gestion.campushub.model.Etudiant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository repository;

    @Test
    void testFindByFiliere() {
        repository.save(new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info"));

        List<Etudiant> resultats = repository.findByFiliere("Info");

        assertEquals(1, resultats.size());
    }

    @Test
    void testFindByEmail() {
        repository.save(new Etudiant("Fall", "Moussa", "moussa@test.com", 24, "Gestion"));

        assertTrue(repository.findByEmail("moussa@test.com").isPresent());
    }
}
