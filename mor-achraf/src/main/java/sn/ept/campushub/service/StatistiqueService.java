package sn.ept.campushub.service;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sn.ept.campushub.domain.Etudiant;
import sn.ept.campushub.domain.Filiere;
import sn.ept.campushub.domain.Inscription;

@Service
public class StatistiqueService {

    public OptionalDouble moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average();
    }

    public Map<Filiere, List<Etudiant>> grouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }
    

public List<MoyenneEtudiant> top3ParNote(List<Etudiant> etudiants, List<Inscription> inscriptions) {

        Map<Long, Double> moyenneParEtudiantId = inscriptions.stream()
                .filter(Inscription::estNotee)
                .collect(Collectors.groupingBy(
                        Inscription::etudiantId,
                        Collectors.averagingDouble(Inscription::note)));

        Comparator<MoyenneEtudiant> parMoyenneDecroissante =
                Comparator.comparingDouble(MoyenneEtudiant::moyenne).reversed();
        Comparator<MoyenneEtudiant> parNom =
                Comparator.comparing(m -> m.etudiant().nom());

        return etudiants.stream()
                .filter(etudiant -> moyenneParEtudiantId.containsKey(etudiant.id()))
                .map(etudiant -> new MoyenneEtudiant(etudiant, moyenneParEtudiantId.get(etudiant.id())))
                .sorted(parMoyenneDecroissante.thenComparing(parNom))
                .limit(3)
                .toList();
    }
    }