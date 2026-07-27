package com.campushub.service;

import com.campushub.model.ClassementEtudiant;
import com.campushub.model.Etudiant;
import com.campushub.model.Inscription;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EtudiantStatsService {

    /**
     * Moyenne d'age de la liste d'etudiants.
     */
    public double moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0.0);
    }

    /**
     * Regroupe les etudiants par filiere.
     */
    public Map<String, List<Etudiant>> groupParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    /**
     * Top 3 des etudiants classes par moyenne de notes (calculee a partir de leurs inscriptions),
     * du meilleur au moins bon.
     */
    public List<ClassementEtudiant> top3ParNote(List<Etudiant> etudiants, List<Inscription> inscriptions) {
        Map<Long, Double> moyenneParEtudiant = inscriptions.stream()
                .collect(Collectors.groupingBy(Inscription::etudiantId, Collectors.averagingDouble(Inscription::note)));

        return etudiants.stream()
                .filter(e -> moyenneParEtudiant.containsKey(e.id()))
                .map(e -> new ClassementEtudiant(e, moyenneParEtudiant.get(e.id())))
                .sorted(Comparator.comparingDouble(ClassementEtudiant::moyenneNote).reversed())
                .limit(3)
                .toList();
    }
}
