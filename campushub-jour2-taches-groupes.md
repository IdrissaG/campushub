# 📋 CampusHub — Tâches du jour (Jour 2/10)

---

## 🔵 G1 – Fondations & Modèle de données

**Mission :** livrer la base technique commune que G2 à G5 vont utiliser toute la semaine.

1. Créer/finaliser l'arborescence Maven multi-module (ou multi-dossier si un seul repo) :
```
campushub/
├── pom.xml (parent)
├── campushub-model/      (records validés hier)
├── campushub-api-etudiants/   (pour G2)
├── campushub-api-cours/       (pour G3)
```
2. Le `pom.xml` parent doit fixer : version de Java (17), version de Spring Boot, gestion centralisée des dépendances communes (Lombok, Validation).
3. Rédiger `CONTRIBUTING.md` : convention de nommage des packages (`com.campushub.<module>`), format des messages de commit, règle "1 PR = 1 fonctionnalité".
4. Livrer ce `pom.xml` parent à G2 et G3 **avant midi** pour ne pas les bloquer.

**Livrable attendu ce soir :** PR mergée avec la structure + `CONTRIBUTING.md`, annoncée aux autres groupes au stand-up de fin de journée.

---

## 🟢 G2 – API Étudiants

**Mission :** premier CRUD REST fonctionnel, en mémoire (pas de base de données aujourd'hui).

1. Générer le projet via [Spring Initializr](https://start.spring.io) avec les dépendances : Web, DevTools, Lombok, Validation.
2. Architecture en 3 couches obligatoire :
   - `EtudiantController` (endpoints REST)
   - `EtudiantService` (logique métier)
   - `EtudiantRepository` (stockage en mémoire, ex. `Map<Long, Etudiant>`)
3. Implémenter les 5 endpoints avec les bons codes HTTP :
   - `GET /api/etudiants` → 200
   - `GET /api/etudiants/{id}` → 200 ou 404
   - `POST /api/etudiants` → 201
   - `PUT /api/etudiants/{id}` → 200 ou 404
   - `DELETE /api/etudiants/{id}` → 204
4. **Injection par constructeur uniquement** (pas de `@Autowired` sur les champs) :
```java
public EtudiantController(EtudiantService service) {
    this.service = service;
}
```
5. Créer une collection Postman et tester chaque endpoint manuellement.

**Livrable attendu ce soir :** collection Postman versionnée dans `campushub-api-etudiants/postman/`, PR mergée.
**Attention :** ne pas encore introduire de DTO — ce sera le sujet de demain (jour 3).

---

## 🟡 G3 – API Cours

**Mission :** identique à G2, mais sur l'entité `Cours`.

1. Même architecture 3 couches : `CoursController` → `CoursService` → `CoursRepository` (en mémoire).
2. Mêmes 5 endpoints (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`) avec les mêmes codes HTTP.
3. **Important :** synchronisez-vous avec G2 aujourd'hui sur le nommage des packages et la structure des classes — vos deux modules doivent être quasi-identiques en style, car ils fusionneront dans le même projet vendredi.
4. Collection Postman pour Cours.

**Livrable attendu ce soir :** collection Postman + PR mergée. Vérifiez avec G1 que votre structure respecte bien les conventions du `CONTRIBUTING.md`.

---

## 🟠 G4 – Persistance (préparation)

**Mission :** préparer l'environnement Postgres, sans encore le connecter au code.

1. Écrire un premier jet de `docker-compose.yml` :
```yaml
services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_DB: campushub
      POSTGRES_USER: campushub
      POSTGRES_PASSWORD: campushub
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
volumes:
  pgdata:
```
2. Tester que `docker compose up` lance bien Postgres et qu'on peut s'y connecter (via DBeaver, psql, ou une extension VS Code).
3. Ne rien brancher côté Spring Boot aujourd'hui — c'est pour demain/jour 4.

**Livrable attendu ce soir :** `docker-compose.yml` versionné + capture d'écran ou preuve de connexion réussie à la base.

---

## 🔴 G5 – Sécurité Backend (préparation)

**Mission :** poser les fondations du futur module de sécurité, sans implémentation fonctionnelle.

1. Lire la documentation officielle Spring Security (section JWT) : https://docs.spring.io/spring-security/reference/
2. Créer le module `campushub-auth` avec les dépendances Maven nécessaires (`spring-boot-starter-security`, une lib JWT comme `jjwt`).
3. Créer la structure de packages vide : `auth/config`, `auth/filter`, `auth/service` — sans code fonctionnel encore.
4. Lister par écrit (dans un `NOTES.md`) : quels rôles seront nécessaires (`ETUDIANT`, `ADMIN`), quels endpoints seront protégés.

**Livrable attendu ce soir :** squelette du module + `NOTES.md` avec la liste des décisions à prendre demain.

---

## 🟣 G6 – Frontend Core

**Mission :** démarrer le projet Angular avec des données factices, sans connexion à l'API pour l'instant.

1. Générer le projet :
```bash
ng new campushub-front --routing --style=scss
```
2. Créer `EtudiantListComponent` et `EtudiantCardComponent`.
3. Utiliser des données mockées en dur dans le composant, par exemple :
```typescript
etudiants = [
  { id: 1, nom: 'Diop', prenom: 'Awa', filiere: 'Info' },
  { id: 2, nom: 'Fall', prenom: 'Moussa', filiere: 'Gestion' }
];
```
4. Afficher la liste avec la nouvelle syntaxe `@for` (pas `*ngFor`) :
```html
@for (etudiant of etudiants; track etudiant.id) {
  <app-etudiant-card [etudiant]="etudiant" />
}
```

**Livrable attendu ce soir :** liste d'étudiants mockés affichée dans le navigateur, PR mergée.

---

## ⚪ G7 – Frontend Formulaires & Auth

**Mission :** poser le squelette de navigation, sans logique métier.

1. Définir les routes dans `app.routes.ts` :
```typescript
export const routes: Routes = [
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiants/:id', component: EtudiantDetailComponent },
  { path: 'etudiants/nouveau', component: EtudiantFormComponent },
  { path: '**', component: NotFoundComponent },
];
```
2. Créer les composants vides (juste un titre dans le template) pour vérifier que la navigation fonctionne.
3. Vérifier que cliquer sur les liens change bien l'URL et affiche le bon composant.

**Livrable attendu ce soir :** navigation fonctionnelle entre les 3 routes + page 404, PR mergée.

---

## ⚫ G8 – Qualité, Docker & CI/CD

**Mission :** premier pipeline CI, même minimal.

1. Créer `.github/workflows/build.yml` :
```yaml
name: build
on: [pull_request]
jobs:
  hello-build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: echo "build placeholder — sera remplacé au jour 3"
```
2. Vérifier que ce job s'exécute bien et passe au vert sur une PR de test.
3. Préparer la structure des dossiers pour les futurs Dockerfiles (`docker/back/`, `docker/front/`) — vides pour l'instant.

**Livrable attendu ce soir :** pipeline visible et vert sur GitHub Actions, structure de dossiers Docker créée.

---

## ⚠️ Dépendance à surveiller au stand-up de ce soir

G6 et G7 travaillent encore sur des données mockées/maquettes — c'est normal, ils passeront au contrat réel (Swagger) dès demain (jour 3). Profitez du stand-up pour qu'ils lisent déjà le modèle `Etudiant`/`Cours` validé hier par G1.

## 📌 Rappel à tous

Minimum **4 commits significatifs**, travail par branche + PR, **revue croisée par un membre d'un AUTRE groupe obligatoire**, 5 lignes dans `JOURNAL.md` ce soir.
