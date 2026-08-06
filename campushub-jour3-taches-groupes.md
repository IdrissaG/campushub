# 📋 CampusHub — Tâches du jour (Jour 3/10)

**Contexte du jour :** Swagger entre en jeu aujourd'hui. **Règle d'or activée dès maintenant : toute route backend non documentée dans Swagger sera refusée en revue croisée**, et ce jusqu'à la fin de l'immersion.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** garantir que G2 et G3 utilisent exactement le même format de réponse d'erreur JSON, pour que le frontend n'ait qu'un seul format à gérer.

### Étapes détaillées

1. **Définir la structure d'erreur commune** avec G2 et G3 (réunion rapide de 15 min en début de matinée). Exemple de format à valider ensemble :
```json
{
  "timestamp": "2026-07-29T10:15:30",
  "status": 400,
  "erreurs": [
    "le champ email est invalide",
    "l'âge doit être supérieur à 15"
  ]
}
```

2. **Créer un module partagé** `campushub-common` (ou un package `com.campushub.common` si vous êtes en mono-module) contenant :
```java
package com.campushub.common;

import java.time.LocalDateTime;
import java.util.List;

public record ErreurResponse(
    LocalDateTime timestamp,
    int status,
    List<String> erreurs
) {}
```

3. **Livrer ce module à G2 et G3 avant midi** — ils en ont besoin pour construire leur `@RestControllerAdvice` cet après-midi. Ne les laissez pas attendre.

4. **En fin de journée**, faites une revue croisée : ouvrez le Swagger UI de G2 et celui de G3, provoquez volontairement une erreur de validation sur chacun (ex. email invalide), et vérifiez que le JSON renvoyé est structurellement identique des deux côtés.

### Livrable attendu ce soir
- PR mergée avec la classe `ErreurResponse` partagée.
- Preuve (capture d'écran ou note dans la PR) que G2 et G3 renvoient le même format d'erreur.

---

## 🟢 G2 – API Étudiants

**Mission du jour :** transformer le CRUD en mémoire d'hier en une API documentée, validée et avec une gestion d'erreurs propre.

### Étapes détaillées

**1. Créer les DTO** (ne plus exposer l'entité `Etudiant` directement dans l'API) :
```java
public record EtudiantRequest(
    @NotBlank String nom,
    @NotBlank String prenom,
    @Email @NotBlank String email,
    @Min(15) int age,
    String filiere
) {}

public record EtudiantResponse(
    Long id,
    String nom,
    String prenom,
    String email,
    int age,
    String filiere
) {}
```

**2. Créer un mapper** entre l'entité et les DTO (une classe simple ou MapStruct si vous voulez aller plus vite) :
```java
public class EtudiantMapper {
    public static EtudiantResponse toResponse(Etudiant e) {
        return new EtudiantResponse(e.id(), e.nom(), e.prenom(), e.email(), e.age(), e.filiere());
    }
}
```

**3. Adapter le contrôleur** pour utiliser `EtudiantRequest` en entrée et `EtudiantResponse` en sortie plutôt que l'entité brute.

**4. Ajouter la validation** avec `@Valid` sur les paramètres du contrôleur :
```java
@PostMapping
public ResponseEntity<EtudiantResponse> create(@Valid @RequestBody EtudiantRequest request) {
    ...
}
```

**5. Créer le `@RestControllerAdvice`** en réutilisant la classe `ErreurResponse` livrée par G1 :
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> erreurs = ex.getBindingResult().getFieldErrors().stream()
            .map(f -> f.getField() + " : " + f.getDefaultMessage())
            .toList();
        return ResponseEntity.badRequest()
            .body(new ErreurResponse(LocalDateTime.now(), 400, erreurs));
    }
}
```

**6. Ajouter springdoc-openapi** dans le `pom.xml` :
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

**7. Annoter les endpoints** :
```java
@Operation(summary = "Créer un étudiant")
@ApiResponse(responseCode = "201", description = "Étudiant créé")
@ApiResponse(responseCode = "400", description = "Données invalides")
@PostMapping
public ResponseEntity<EtudiantResponse> create(@Valid @RequestBody EtudiantRequest request) { ... }
```

**8. Annoter les DTO** avec `@Schema` pour que Swagger affiche des descriptions claires :
```java
public record EtudiantRequest(
    @Schema(description = "Nom de famille", example = "Diop") @NotBlank String nom,
    ...
) {}
```

**9. Vérifier** que `/swagger-ui.html` liste bien les 5 endpoints, avec les codes 200/201/204/400/404 documentés.

### Livrable attendu ce soir
- Swagger UI complet et accessible.
- Un camarade d'un autre groupe doit pouvoir tester tout le CRUD **uniquement depuis Swagger UI**, sans Postman.
- PR mergée.

---

## 🟡 G3 – API Cours

**Mission du jour :** exactement le même travail que G2, appliqué à l'entité `Cours`.

### Étapes détaillées

1. Créer `CoursRequest` / `CoursResponse` avec validation (`@NotBlank` sur l'intitulé, `@Min(1)` sur les crédits, etc.).
2. Créer le mapper `CoursMapper`.
3. Adapter le contrôleur pour utiliser les DTO.
4. **Réutiliser le `@RestControllerAdvice` et la classe `ErreurResponse` de G1** — ne pas en recréer une différente. Si vous êtes en multi-module, importez le module commun de G1.
5. Brancher springdoc-openapi (même dépendance que G2).
6. Annoter tous les endpoints Cours (`@Operation`, `@ApiResponse`, `@Schema`).

### Point de coordination obligatoire avec G2
Avant la fin de journée, comparez vos deux Swagger UI côte à côte avec G2 :
- Est-ce que le format des erreurs est identique ?
- Est-ce que le niveau de détail des descriptions (`@Schema`) est cohérent ?
- Est-ce que la structure des noms de champs suit la même convention ?

### Livrable attendu ce soir
- Swagger UI Cours complet.
- Preuve de la synchronisation avec G2 (note dans la PR ou capture d'écran des deux Swagger côte à côte).

---

## 🟠 G4 – Persistance

**Mission du jour :** écrire le vrai schéma SQL de la base de données, à la main, avant de le connecter à JPA demain.

### Étapes détaillées

1. **Écrire la migration Flyway** `src/main/resources/db/migration/V1__init.sql` :
```sql
CREATE TABLE etudiant (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    age INT NOT NULL,
    filiere VARCHAR(100)
);

CREATE TABLE cours (
    id BIGSERIAL PRIMARY KEY,
    intitule VARCHAR(150) NOT NULL,
    filiere VARCHAR(100),
    credits INT NOT NULL
);

CREATE TABLE inscription (
    id BIGSERIAL PRIMARY KEY,
    etudiant_id BIGINT NOT NULL REFERENCES etudiant(id),
    cours_id BIGINT NOT NULL REFERENCES cours(id),
    note DOUBLE PRECISION,
    date_inscription DATE NOT NULL,
    UNIQUE (etudiant_id, cours_id)
);
```

2. **Respecter exactement le modèle validé au jour 1** par tous les groupes (mêmes noms de champs que dans les records `Etudiant`/`Cours`/`Inscription`) — en cas de doute, demandez à G1.

3. **Ajouter la dépendance Flyway** au `pom.xml` du projet où la migration doit s'exécuter (probablement un module commun ou celui de G2/G3, à définir avec G1) :
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-database-postgresql</artifactId>
</dependency>
```

4. **Configurer la connexion** dans `application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/campushub
spring.datasource.username=campushub
spring.datasource.password=campushub
spring.flyway.enabled=true
```

5. **Lancer l'application** et vérifier dans les logs que Flyway applique bien la migration `V1__init.sql` sans erreur.

6. **Vérifier visuellement** (DBeaver, psql, ou extension VS Code) que les 3 tables sont bien créées avec les bonnes colonnes et contraintes.

⚠️ Ne touchez pas encore aux entités JPA (`@Entity`) — ce sera le jour 4, une fois le schéma SQL validé aujourd'hui.

### Livrable attendu ce soir
- Migration `V1__init.sql` versionnée.
- Preuve que la migration s'applique sans erreur sur la base Docker (capture des logs Flyway ou des tables créées).

---

## 🔴 G5 – Sécurité Backend

**Mission du jour :** documenter le contrat d'authentification dans Swagger, sans encore l'implémenter.

### Étapes détaillées

1. **Décrire les futurs endpoints** dans Swagger, même sans implémentation réelle. Deux options :
   - Créer les méthodes de contrôleur avec juste une réponse factice (`return ResponseEntity.ok("TODO")`), annotées avec `@Operation`.
   - Ou documenter directement dans un fichier `openapi-auth.yaml` séparé si vous préférez ne pas toucher au code Java encore.

2. **Endpoint register** :
```java
@Operation(summary = "Créer un compte utilisateur")
@ApiResponse(responseCode = "201", description = "Compte créé")
@ApiResponse(responseCode = "400", description = "Email déjà utilisé ou données invalides")
@PostMapping("/api/auth/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.status(501).body("Non implémenté — arrive demain (J5)");
}
```

3. **Endpoint login** :
```java
@Operation(summary = "Se connecter")
@ApiResponse(responseCode = "200", description = "Connexion réussie, retourne un token JWT")
@ApiResponse(responseCode = "401", description = "Identifiants invalides")
@PostMapping("/api/auth/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    return ResponseEntity.status(501).body("Non implémenté — arrive demain (J5)");
}
```

4. **Définir les DTO du contrat** dès maintenant (ils ne changeront pas demain) :
```java
public record RegisterRequest(@NotBlank String email, @NotBlank String motDePasse, String nom) {}
public record LoginRequest(@NotBlank String email, @NotBlank String motDePasse) {}
public record AuthResponse(String token, String role) {}
```

5. **Partager ce contrat** avec G6 et G7 dès aujourd'hui, même si non fonctionnel — ils en auront besoin pour anticiper leur travail des jours 7-8.

### Livrable attendu ce soir
- Contrat d'authentification visible et documenté dans Swagger (endpoints + DTO), même non fonctionnel.
- Message envoyé à G6/G7 avec le lien Swagger pour qu'ils en prennent connaissance.

---

## 🟣 G6 – Frontend Core

**Mission du jour :** premier vrai appel HTTP vers l'API réelle de G2 (fini les données mockées).

### Étapes détaillées

1. **Créer le service HTTP** :
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EtudiantService {
  private apiUrl = 'http://localhost:8080/api/etudiants';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(this.apiUrl);
  }
}
```

2. **Remplacer les données mockées** dans `EtudiantListComponent` par un appel réel :
```typescript
etudiants: Etudiant[] = [];

ngOnInit() {
  this.etudiantService.getAll().subscribe({
    next: (data) => this.etudiants = data,
    error: (err) => console.error('Erreur API', err)
  });
}
```
+
3. **Vérifier que `provideHttpClient()`** est bien configuré dans `app.config.ts` :
```typescript
export const appConfig: ApplicationConfig = {
  providers: [provideHttpClient(), provideRouter(routes)]
};
```

4. **Si erreur CORS** : contactez immédiatement G2 aujourd'hui (ne pas attendre demain). G2 doit ajouter dans son contrôleur ou sa config :
```java
@CrossOrigin(origins = "http://localhost:4200")
```

5. Vérifiez dans les DevTools (onglet Network) que la requête part bien vers `localhost:8080/api/etudiants` et reçoit une réponse 200 avec les vraies données de G2.

### Livrable attendu ce soir
- Liste d'étudiants réels (ceux créés par G2 via Swagger/Postman) affichée dans le navigateur.
- Preuve que le CORS fonctionne (capture DevTools sans erreur CORS).

---

## ⚪ G7 – Frontend Formulaires & Auth

**Mission du jour :** construire le formulaire réactif de création/édition, en respectant exactement le contrat Swagger de G2.

### Étapes détaillées

1. **Ouvrir le Swagger UI de G2** et noter précisément les noms de champs et contraintes de `EtudiantRequest` (nom, prenom, email, age, filiere).

2. **Créer le `FormGroup` réactif** avec les mêmes noms de champs et les mêmes règles de validation :
```typescript
import { FormBuilder, Validators } from '@angular/forms';

this.etudiantForm = this.fb.group({
  nom: ['', Validators.required],
  prenom: ['', Validators.required],
  email: ['', [Validators.required, Validators.email]],
  age: ['', [Validators.required, Validators.min(15)]],
  filiere: [''],
});
```

3. **Afficher les messages d'erreur** sous chaque champ :
```html
<input formControlName="email" />
@if (etudiantForm.get('email')?.invalid && etudiantForm.get('email')?.touched) {
  <span class="erreur">Email invalide</span>
}
```

4. **Vérifier avec G2** (synchronisation directe aujourd'hui) que le JSON que votre formulaire produira correspondra exactement à ce que `EtudiantRequest` attend — même casse, mêmes noms de champs.

⚠️ Ne branchez pas encore le vrai POST vers l'API — ce sera le jour 7. Aujourd'hui, concentrez-vous sur la structure et la validation du formulaire.

### Livrable attendu ce soir
- Formulaire réactif avec validation fonctionnelle (testable en saisissant des données invalides).
- Confirmation écrite (dans la PR) que les noms de champs correspondent au contrat Swagger de G2.

---

## ⚫ G8 – DevOps & Cloud

**Mission du jour :** compléter le pipeline CI avec le vrai build backend, et démarrer la préparation de l'environnement cloud cible.

### Étapes détaillées

**1. Remplacer le job "hello build" d'hier par un vrai job de build backend :**
```yaml
name: build
on: [pull_request]

jobs:
  build-backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build et tests backend
        run: mvn -B clean verify
```

**2. Vérifier que ce job échoue bien si un test casse** — testez volontairement en cassant un test dans une branche de test, confirmez que la PR est bloquée, puis annulez.

**3. Nouveau volet Cloud — choisir la plateforme cible :**
   - Comparer rapidement Render, Railway, Fly.io (gratuit/facile à démarrer) vs un VPS (plus de contrôle mais plus de configuration).
   - Créer un compte sur la plateforme choisie.
   - Ne pas encore déployer quoi que ce soit aujourd'hui — juste préparer le terrain.

**4. Documenter les futurs secrets/variables d'environnement** dans un fichier `NOTES-CLOUD.md` (sans jamais committer de vraies valeurs) :
```markdown
# Variables d'environnement prévues pour la prod

- DB_URL — url de connexion Postgres managée
- DB_USERNAME / DB_PASSWORD
- JWT_SECRET — clé de signature des tokens (à générer, ne jamais commit)
- SPRING_PROFILES_ACTIVE=prod
```

**5. Se synchroniser avec G4** : quel type de base Postgres managée sera utilisée en production (souvent fournie directement par la plateforme choisie) ? Notez la réponse dans `NOTES-CLOUD.md`.

**6. Si le temps le permet**, commencer à explorer la doc de déploiement de la plateforme choisie (comment connecter un repo GitHub, comment déclencher un déploiement automatique) — sans encore rien déployer.

### Livrable attendu ce soir
- Pipeline CI avec job de build backend fonctionnel et vert.
- Compte créé sur la plateforme cloud choisie.
- `NOTES-CLOUD.md` avec la liste des secrets/variables prévus et le choix de plateforme justifié en 2-3 lignes.

---

## ⚠️ Règle Swagger — active à partir d'aujourd'hui et jusqu'à la fin

Toute Pull Request contenant une nouvelle route backend **sans documentation Swagger correspondante** doit être refusée par le relecteur, même si le code fonctionne. C'est non négociable à partir de maintenant.

## 🔗 Dépendances à surveiller au stand-up de ce soir

- G6 dépend du CORS de G2 → à vérifier en priorité s'il y a blocage.
- G7 dépend du contrat Swagger de G2 (noms de champs) → confirmer la synchro avant la fin de journée.
- G3 doit rester aligné sur le format d'erreur de G1/G2 → vérifier ensemble.
- G8 doit se synchroniser avec G4 sur le choix de base managée en prod.

## 📌 Rappel à tous

Minimum **4 commits significatifs**, travail par branche + PR, **revue croisée obligatoire par un membre d'un AUTRE groupe**, 5 lignes dans `JOURNAL.md` ce soir (appris / bloqué / question).
