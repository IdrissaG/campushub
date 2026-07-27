```markdown
# CampusHub

CampusHub est un projet Spring Boot permettant de modéliser un système simple de gestion universitaire.

## Technologies utilisées

- Java 21
- Spring Boot 3.5.4
- Maven
- JUnit 5

## Fonctionnalités

Le projet contient les modèles suivants :

- Etudiant
- Cours
- Inscription

Les objets métier sont représentés avec des records Java afin de simplifier la création de modèles immuables.

Le service étudiant utilise l'API Java Streams pour réaliser :

- le calcul de la moyenne d'âge ;
- le regroupement des étudiants par filière ;
- la récupération des trois meilleurs étudiants selon leur note.

## Developpement
Les fonctionnalités sont développées sur des branches dédiées avant d'être proposées via Pull Request vers la branche main.

## Tests

Les tests unitaires sont réalisés avec JUnit 5.

Ils permettent de vérifier :

- le calcul de la moyenne d'âge ;
- le regroupement par filière ;
- le classement des étudiants.

Pour exécuter les tests :

```bash
.\mvnw.cmd clean test

