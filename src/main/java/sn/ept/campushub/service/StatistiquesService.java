package sn.ept.campushub.service;

import org.springframework.stereotype.Service;
import sn.ept.campushub.model.Etudiant;
import sn.ept.campushub.model.Inscription;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Traitements statistiques via l'API Stream.
 */
@Service
public class StatistiquesService {

    /**
     * Moyenne d'age de la liste d'etudiants.
     * Renvoie 0 si la liste est vide.
     */
    public double moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0.0);
    }

    /**
     * Groupe les etudiants par filiere.
     */
    public Map<String, List<Etudiant>> grouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    /**
     * Les trois meilleures notes, ordre decroissant.
     */
    public List<Inscription> top3ParNote(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .sorted(Comparator.comparingDouble(Inscription::note).reversed())
                .limit(3)
                .toList();
    }

    /**
     * Bonus : moyenne des notes par filiere.
     */
    public Map<String, Double> moyenneNoteParFiliere(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .collect(Collectors.groupingBy(
                        i -> i.etudiant().filiere(),
                        Collectors.averagingDouble(Inscription::note)
                ));
    }
}