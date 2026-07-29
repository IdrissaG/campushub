# Guide de contribution — CampusHub

Merci de contribuer à CampusHub. Ce projet est une application Spring Boot
écrite en Java 17 et construite avec Maven.

## Pré-requis

- Java 17
- Git
- Aucun Maven local n'est nécessaire : le projet fournit le Maven Wrapper.

## Démarrer le projet

Depuis le dossier `campushub` :

```powershell
.\mvnw.cmd test
```

Sur macOS ou Linux :

```bash
./mvnw test
```

Les tests doivent réussir avant l'envoi d'une pull request.

## Nommer une branche

Utilisez le format :

```text
<type>/<initiales>/<slug>
```

Types de branches disponibles :

- `feature` : nouvelle fonctionnalité ;
- `bugfix` : correction non urgente ;
- `hotfix` : correctif urgent de production ;
- `release` : préparation d'une version.

Exemples :

```text
feature/adama/tri-inscriptions
bugfix/youssouf/calcul-moyenne-age
```

## Qualité du code et tests

- Respectez l'organisation existante des packages `gestion.campushub`.
- Utilisez des `record` pour les modèles métier lorsque cela correspond au
  besoin existant.
- Pour les traitements de collections, privilégiez les Streams Java lorsque
  c'est pertinent et gardez le code lisible.
- Ajoutez des tests JUnit 5 pour tout nouveau comportement ou pour toute
  correction de bug.
- Vérifiez le build avec `./mvnw test` (ou `.\mvnw.cmd test` sous Windows).

### Convention de nommage des packages

Les packages Java sont écrits uniquement en minuscules, sans espaces,
accents, tirets ni caractères spéciaux. Ils commencent par le package racine
du projet :

```text
gestion.campushub
```

Ajoutez ensuite un nom de domaine au pluriel ou selon la couche concernée :

```text
gestion.campushub.model
gestion.campushub.service
gestion.campushub.controller
gestion.campushub.repository
gestion.campushub.config
```

Exemples à éviter : `gestion.CampusHub`, `gestion.campus_hub` et
`gestion.campushub.etudiants-service`.

## Messages de commit

Les messages suivent la convention Conventional Commits :

```text
<type>(<scope>): <message court>

Description (optionnelle)

Closes: #<ticket> (optionnel)
```

Types autorisés : `feat`, `fix`, `docs`, `style`, `refactor`, `test` et
`chore`.

Exemples :

```text
feat(inscription): ajoute le classement des trois meilleures notes
test(service): couvre le regroupement par filière
```

## Pull requests

Avant de créer une PR, assurez-vous que :

- la branche est poussée sur le dépôt distant ;
- le build et les tests sont verts ;
- la documentation est mise à jour si le changement l'exige ;
- la PR ne mélange pas plusieurs sujets indépendants.

Utilisez un titre conforme aux commits, par exemple :

```text
feat(inscription): ajoute le classement par note
```

Dans la description de la PR, indiquez :

- l'objectif du changement ;
- les tests exécutés ;
- les éventuels impacts ou points à vérifier pendant la revue.

Merci de garder les échanges de revue respectueux et constructifs.
