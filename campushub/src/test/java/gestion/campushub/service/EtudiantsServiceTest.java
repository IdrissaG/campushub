package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtudiantsServiceTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantsService service;

    @Test
    void testGetAllEtudiants_retourneLaPageDuRepository() {
        Pageable pageable = PageRequest.of(0, 10);
        Etudiant etudiant = new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info");
        Page<Etudiant> page = new PageImpl<>(List.of(etudiant));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Etudiant> resultat = service.getAllEtudiants(pageable);

        assertEquals(1, resultat.getTotalElements());
        verify(repository).findAll(pageable);
    }

    @Test
    void testGetEtudiantById_trouve() {
        Etudiant etudiant = new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info");
        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));

        Optional<Etudiant> resultat = service.getEtudiantById(1L);

        assertTrue(resultat.isPresent());
        assertEquals("Awa", resultat.get().getPrenom());
    }

    @Test
    void testGetEtudiantById_absent() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Etudiant> resultat = service.getEtudiantById(99L);

        assertTrue(resultat.isEmpty());
    }

    @Test
    void testCreateEtudiant_sauvegardeViaRepository() {
        Etudiant etudiant = new Etudiant("Fall", "Moussa", "moussa@test.com", 24, "Gestion");
        when(repository.save(etudiant)).thenReturn(etudiant);

        Etudiant resultat = service.createEtudiant(etudiant);

        assertEquals("Fall", resultat.getNom());
        verify(repository).save(etudiant);
    }

    @Test
    void testUpdateEtudiant_trouve_metAJourLesChamps() {
        Etudiant existant = new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info");
        Etudiant nouvellesDonnees = new Etudiant("Diop", "Awa", "awa.nouveau@test.com", 23, "Gestion");
        when(repository.findById(1L)).thenReturn(Optional.of(existant));
        when(repository.save(existant)).thenReturn(existant);

        Optional<Etudiant> resultat = service.updateEtudiant(1L, nouvellesDonnees);

        assertTrue(resultat.isPresent());
        assertEquals("awa.nouveau@test.com", resultat.get().getEmail());
        assertEquals(23, resultat.get().getAge());
        assertEquals("Gestion", resultat.get().getFiliere());
    }

    @Test
    void testUpdateEtudiant_absent_neSauvegardeRien() {
        Etudiant nouvellesDonnees = new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info");
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Etudiant> resultat = service.updateEtudiant(99L, nouvellesDonnees);

        assertTrue(resultat.isEmpty());
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteEtudiant_existant_supprimeEtRetourneTrue() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean resultat = service.deleteEtudiant(1L);

        assertTrue(resultat);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEtudiant_absent_retourneFalseSansSupprimer() {
        when(repository.existsById(99L)).thenReturn(false);

        boolean resultat = service.deleteEtudiant(99L);

        assertFalse(resultat);
        verify(repository, never()).deleteById(anyLong());
    }
}
