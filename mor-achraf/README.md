# campushub — binôme Mor / Achraf

Exercice Jour 1 : modélisation en `record`, statistiques avec l'API Stream, tests JUnit 5.

## Stack

- Java 21 · Spring Boot 4.1.0 · Maven
- JUnit 5 + AssertJ (`spring-boot-starter-test`)

## Lancer les tests

```bash
cd mor-achraf
./mvnw test        # mvnw.cmd test sous Windows
```

## Structure

## Statistiques implémentées

| Méthode | Retour | Opérateurs Stream |
|---|---|---|
| `moyenneAge` | `OptionalDouble` | `mapToInt` + `average` |
| `grouperParFiliere` | `Map<Filiere, List<Etudiant>>` | `groupingBy` |
| `top3ParNote` | `List<MoyenneEtudiant>` | `filter`, `groupingBy` + `averagingDouble`, `map`, `sorted`, `limit` |

## Choix de conception

- **Records plutôt que classes** : immuabilité garantie, `equals`/`hashCode` générés (indispensables aux assertions de test), validation centralisée dans le constructeur compact — une instance qui existe est forcément valide.
- **`OptionalDouble` pour la moyenne d'âge** : sur une liste vide la moyenne n'existe pas ; renvoyer `0.0` serait une valeur fausse.
- **`Double note` et non `double`** : `null` distingue « pas encore évalué » de « a eu 0/20 ».
- **Un étudiant sans aucune note est exclu du top 3**, pas classé à 0.
- **Tri secondaire par nom** dans le top 3 : rend le classement déterministe en cas d'égalité de moyennes, condition nécessaire pour que le test soit fiable.
- **Tests unitaires purs, sans `@SpringBootTest`** : le service n'a aucune dépendance à injecter, charger le contexte Spring coûterait ~2,5 s par exécution sans rien vérifier de plus.