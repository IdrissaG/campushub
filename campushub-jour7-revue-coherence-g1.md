# Revue de cohérence G1 — Jour 7

## Contexte

Objectif : valider la cohérence entre le travail de G6 (composants d'affichage) et G7 (formulaires) pour le jour 7, en s'assurant qu'ils respectent une architecture frontend partagée et qu'il n'existe pas de duplication de composants.

## Synthèse

- Groupe : G1
- Date : jour7
- Branche : feature/nft-pm/revue-coherence-g1 
- Livrable : note de revue de cohérence G6/G7

## Vérifications réalisées

### 1. Architecture frontend
- [ ] Structure des dossiers cohérente (`components/`, `services/`, `models/`, `pages/`, etc.)
- [ ] Organisation conforme aux conventions Angular standard
- [ ] Services partagés clairement identifiés et réutilisés
- [ ] Modèles / interfaces centralisés dans `models/`

### 2. Conventions de nommage
- [ ] Nom des composants Angular cohérent (`EtudiantListComponent`, `EtudiantFormComponent`, etc.)
- [ ] Classes CSS et sélecteurs alignés sur un style commun
- [ ] Conventions TypeScript respectées (camelCase, PascalCase pour classes)
- [ ] Fichiers nommés de manière homogène (`*.component.ts`, `*.service.ts`)

### 3. Réutilisation des composants
- [ ] G7 réutilise les composants de G6 lorsqu'ils existent déjà
- [ ] Aucun composant d’affichage dupliqué inutilement
- [ ] Composants de base (cartes, boutons, erreurs) sont partagés
- [ ] Si des composants similaires sont créés, ils sont factorisés

### 4. Formulaires et UX
- [ ] Formulaire réactif G7 conforme à la structure Angular Forms
- [ ] Validation client affichée clairement
- [ ] Gestion des erreurs serveur implémentée via `RestControllerAdvice`
- [ ] Routing et page 404 en place pour le flux CRUD étudiant

## Points forts

- - Architecture frontend claire et structurée : dossier components/, services/, model/ bien séparés dans campushub-front/src/app.
- Nommage cohérent des composants et services : EtudiantListComponent, EtudiantFormComponent, EtudiantCardComponent, EtudiantService.
- Utilisation des composants standalone Angular pour EtudiantListComponent, EtudiantCardComponent et NotFoundComponent, ce qui facilite la réutilisation.
- G6 a déjà fourni un composant de présentation (EtudiantCardComponent) réutilisable dans la liste des étudiants.
- G7 a mis en place un formulaire réactif avec validation client (Validators.required, Validators.email, Validators.min) et gestion des erreurs serveur via un signal d’erreur.
- Routing de base en place avec redirection par défaut vers /etudiants et page 404 implémentée.

## Points à corriger

- - Le formulaire EtudiantFormComponent implémente aujourd’hui uniquement la création, il ne gère pas encore l’édition d’un étudiant existant.
- La route d’édition attendue /etudiants/:id/modifier est absente du routeur actuel.
- Le signalement des erreurs serveur est présent, mais il faudra vérifier que les messages remontés par G2 sont bien affichés tels qu’ils sont envoyés.
- Il reste à vérifier que le flux complet CRUD se fait sans duplication de logique entre EtudiantDetailComponent et EtudiantFormComponent.

## Actions recommandées

- - Architecture frontend claire et structurée : dossier components/, services/, model/ bien séparés dans campushub-front/src/app.
- Nommage cohérent des composants et services : EtudiantListComponent, EtudiantFormComponent, EtudiantCardComponent, EtudiantService.
- Utilisation des composants standalone Angular pour EtudiantListComponent, EtudiantCardComponent et NotFoundComponent, ce qui facilite la réutilisation.
- G6 a déjà fourni un composant de présentation (EtudiantCardComponent) réutilisable dans la liste des étudiants.
- G7 a mis en place un formulaire réactif avec validation client (Validators.required, Validators.email, Validators.min) et gestion des erreurs serveur via un signal d’erreur.
- Routing de base en place avec redirection par défaut vers /etudiants et page 404 implémentée.

## Disponibilité / support

- G1 reste disponible pour soutenir G6/G7 sur les points suivants :
- relecture de la structure Angular


