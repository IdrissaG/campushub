# 📋 CampusHub — Tâches du jour (Jour 4/10)

**Contexte du jour :** on passe du stockage en mémoire à une vraie persistance PostgreSQL avec JPA. C'est aussi le jour où les records `Etudiant`/`Cours`/`Inscription` deviennent des entités JPA (`@Entity`) — la transition expliquée dès le jour 1.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** piloter la transition record → entité JPA, et arbitrer les conflits de mapping entre G2, G3 et G4.

### Étapes détaillées

1. **Réunion de 20 min en début de matinée** avec G2, G3 et G4 pour valider ensemble la transition : les records deviennent des classes JPA classiques.

2. **Documenter la règle de transition** dans `CONTRIBUTING.md` pour que tout le monde applique le même modèle :
```java
// AVANT (jour 1, record)
public record Etudiant(Long id, String nom, String prenom, String email, int age, String filiere) {}

// APRÈS (jour 4, entité JPA)
@Entity
@Table(name = "etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private int age;
    private String filiere;

    protected Etudiant() {} // constructeur vide requis par JPA

    public Etudiant(String nom, String prenom, String email, int age, String filiere) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.filiere = filiere;
    }

    // getters (pas de setters si on veut rester proche de l'immutabilité, sauf si Hibernate en a besoin)
}
```

3. **Arbitrer les conflits de mapping** entre G2/G3/G4 pendant la journée : cohérence des noms de colonnes, des types, des relations (`@ManyToOne`, `@OneToMany`), des stratégies de cascade.

4. **Vérifier la cohérence des annotations** entre les 3 entités en fin de journée (mêmes conventions de nommage, même style d'écriture des relations).

### Livrable attendu ce soir
- Note de transition validée et partagée dans `CONTRIBUTING.md`.
- Confirmation que G2, G3 et G4 ont un mapping JPA cohérent entre eux (revue rapide des 3 PR).

---

## 🟢 G2 – API Étudiants

**Mission du jour :** remplacer le repository en mémoire par `JpaRepository`, ajouter pagination et tri.

### Étapes détaillées

1. **Transformer `Etudiant`** en entité JPA (voir modèle fourni par G1 ci-dessus).

2. **Créer le repository JPA** :
```java
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByFiliere(String filiere);
    Optional<Etudiant> findByEmail(String email);
}
```

3. **Adapter le service** pour utiliser ce repository à la place du `Map` en mémoire d'hier.

4. **Ajouter la pagination et le tri** sur `GET /api/etudiants` :
```java
@GetMapping
public Page<EtudiantResponse> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "nom") String sortBy
) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    return etudiantRepository.findAll(pageable).map(EtudiantMapper::toResponse);
}
```

5. **Documenter les nouveaux paramètres** dans Swagger (`@Parameter` sur `page`, `size`, `sortBy`).

6. **Ajouter 2 tests `@DataJpaTest`** :
```java
@DataJpaTest
class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository repository;

    @Test
    void testFindByFiliere() {
        repository.save(new Etudiant("Diop", "Awa", "awa@test.com", 22, "Info"));
        List<Etudiant> resultats = repository.findByFiliere("Info");
        assertEquals(1, resultats.size());
    }

    @Test
    void testFindByEmail() {
        repository.save(new Etudiant("Fall", "Moussa", "moussa@test.com", 24, "Gestion"));
        assertTrue(repository.findByEmail("moussa@test.com").isPresent());
    }
}
```

7. **Vérifier que les données persistent** : créer un étudiant via Swagger, redémarrer l'application, vérifier qu'il est toujours là.

### Livrable attendu ce soir
- Étudiants persistés en base, survivant à un redémarrage.
- Pagination et tri visibles et fonctionnels dans Swagger.
- 2 tests `@DataJpaTest` verts.

---

## 🟡 G3 – API Cours & Inscriptions

**Mission du jour :** le jour le plus technique — mapper la relation many-to-many via l'entité `Inscription`.

### Étapes détaillées

1. **Transformer `Cours`** en entité JPA (même principe que G2).

2. **Créer l'entité `Inscription`**, qui porte la relation many-to-many enrichie (note + date) :
```java
@Entity
@Table(name = "inscription")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    private Double note;
    private LocalDate dateInscription;

    protected Inscription() {}

    public Inscription(Etudiant etudiant, Cours cours, Double note, LocalDate dateInscription) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.note = note;
        this.dateInscription = dateInscription;
    }
    // getters
}
```

3. **Créer le repository** :
```java
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findByEtudiantId(Long etudiantId);

    List<Inscription> findByCoursId(Long coursId);

    @Query("SELECT i FROM Inscription i WHERE i.note >= :seuil ORDER BY i.note DESC")
    List<Inscription> findAvecNoteMinimum(@Param("seuil") double seuil);
}
```
Les 2 premières sont des requêtes dérivées, la 3e est votre requête JPQL personnalisée demandée dans l'exercice.

4. **Vérifier que le mapping many-to-many fonctionne** : créer un étudiant, un cours, une inscription les reliant, puis vérifier dans la base (DBeaver/psql) que la ligne `inscription` contient bien les bonnes clés étrangères.

5. **Ajouter les endpoints** pour consulter les inscriptions d'un étudiant ou d'un cours, documentés dans Swagger.

6. **Tests `@DataJpaTest`** sur ces requêtes.

### Livrable attendu ce soir
- Relation many-to-many fonctionnelle et persistée.
- Requête JPQL personnalisée testée.
- Endpoints de consultation documentés dans Swagger.

---

## 🟠 G4 – Persistance (finalisation)

**Mission du jour :** livrer les entités JPA finalisées à G2/G3, garantir la robustesse de la persistance.

### Étapes détaillées

1. **Revoir la migration Flyway** d'hier (`V1__init.sql`) à la lumière des entités que G2/G3 sont en train de créer — ajuster si des écarts apparaissent (types de colonnes, contraintes manquantes).

2. **Si un ajustement est nécessaire**, créer une **nouvelle migration** plutôt que de modifier `V1` (règle Flyway : les migrations appliquées ne se modifient jamais) :
```sql
-- V2__ajout_contraintes.sql
ALTER TABLE etudiant ADD CONSTRAINT chk_age CHECK (age >= 15);
```

3. **Vérifier le redémarrage propre** : arrêter le conteneur Postgres, le relancer, vérifier que Flyway réapplique les migrations dans le bon ordre et que rien n'est cassé.

4. **Écrire des tests `@DataJpaTest`** transverses pour vérifier l'intégrité référentielle (ex. : une inscription ne peut pas être créée avec un `etudiant_id` inexistant).

5. **Documenter le schéma final** (diagramme simple, à la main ou avec un outil comme dbdiagram.io) dans `docs/schema.md`, pour que ce soit visuel pour tout le monde.

6. **Support actif** à G2 et G3 toute la journée sur leurs questions de mapping.

### Livrable attendu ce soir
- Migrations Flyway finalisées et versionnées.
- Diagramme du schéma relationnel dans `docs/schema.md`.
- Tests d'intégrité référentielle verts.

---

## 🔴 G5 – Sécurité Backend

**Mission du jour :** préparer les entités `Utilisateur`/`Role` en coordination avec G4, avant l'implémentation complète de demain.

### Étapes détaillées

1. **Créer l'entité `Utilisateur`** :
```java
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasseHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected Utilisateur() {}

    public Utilisateur(String email, String motDePasseHash, Role role) {
        this.email = email;
        this.motDePasseHash = motDePasseHash;
        this.role = role;
    }
    // getters
}
```

2. **Créer l'énumération `Role`** :
```java
public enum Role {
    ETUDIANT, ADMIN
}
```

3. **Coordonner avec G4** l'ajout de la migration Flyway correspondante :
```sql
-- V3__utilisateur.sql
CREATE TABLE utilisateur (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

4. **Créer le repository** :
```java
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
}
```

5. **Ne pas encore implémenter** le login/register fonctionnel avec JWT — ça reste pour demain (jour 5). Aujourd'hui, seulement la structure de données.

### Livrable attendu ce soir
- Entité `Utilisateur` + enum `Role` créées.
- Migration Flyway correspondante, coordonnée avec G4.
- Repository fonctionnel, testé avec un `@DataJpaTest` simple (sauvegarde + recherche par email).

---

## 🟣 G6 – Frontend Core

**Mission du jour :** finaliser la gestion des états de chargement et d'erreur, maintenant que l'API réelle avec pagination arrive.

### Étapes détaillées

1. **Adapter le service** à la nouvelle réponse paginée de G2 :
```typescript
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
}

getAll(page: number = 0, size: number = 10): Observable<PageResponse<Etudiant>> {
  return this.http.get<PageResponse<Etudiant>>(`${this.apiUrl}?page=${page}&size=${size}`);
}
```

2. **Ajouter un état de chargement** dans le composant :
```typescript
loading = signal(true);
erreur = signal<string | null>(null);

ngOnInit() {
  this.etudiantService.getAll().subscribe({
    next: (data) => {
      this.etudiants = data.content;
      this.loading.set(false);
    },
    error: (err) => {
      this.erreur.set('Impossible de charger les étudiants');
      this.loading.set(false);
    }
  });
}
```

3. **Afficher un indicateur visuel** pendant le chargement et un message clair en cas d'erreur :
```html
@if (loading()) {
  <p>Chargement...</p>
} @else if (erreur()) {
  <p class="erreur">{{ erreur() }}</p>
} @else {
  @for (etudiant of etudiants; track etudiant.id) {
    <app-etudiant-card [etudiant]="etudiant" />
  }
}
```

4. **Tester le cas d'erreur volontairement** : arrêtez temporairement le backend de G2 et vérifiez que le message d'erreur s'affiche correctement plutôt qu'un écran blanc.

### Livrable attendu ce soir
- Gestion propre des 3 états (chargement / erreur / succès).
- Compatible avec la réponse paginée de G2.

---

## ⚪ G7 – Frontend Formulaires & Auth

**Mission du jour :** vérifier que le POST/PUT du formulaire correspond exactement au contrat Swagger, maintenant que l'API est persistée.

### Étapes détaillées

1. **Brancher réellement le formulaire** sur l'API (pour la première fois) :
```typescript
onSubmit() {
  if (this.etudiantForm.valid) {
    this.etudiantService.create(this.etudiantForm.value).subscribe({
      next: () => this.router.navigate(['/etudiants']),
      error: (err) => this.erreurServeur.set(err.error.erreurs)
    });
  }
}
```

2. **Créer la méthode `create` dans le service** :
```typescript
create(etudiant: EtudiantRequest): Observable<EtudiantResponse> {
  return this.http.post<EtudiantResponse>(this.apiUrl, etudiant);
}
```

3. **Tester le cycle complet** : remplir le formulaire → soumettre → vérifier dans Swagger ou en base que l'étudiant a bien été créé et persisté.

4. **Afficher les erreurs serveur** renvoyées par le `@RestControllerAdvice` de G2 (le format `ErreurResponse` défini par G1 au jour 3) directement dans le formulaire.

5. **Croisement obligatoire avec G2** : testez un cas d'erreur métier (ex. email déjà utilisé) et vérifiez que le message s'affiche correctement côté Angular.

### Livrable attendu ce soir
- Création d'étudiant fonctionnelle de bout en bout (formulaire → API → base de données).
- Gestion des erreurs serveur affichées proprement dans le formulaire.

---

## ⚫ G8 – DevOps & Cloud

**Mission du jour :** intégrer Postgres dans l'environnement CI, et avancer la préparation cloud.

### Étapes détaillées

1. **Ajouter un service Postgres au pipeline CI**, pour que les tests `@DataJpaTest` de G2/G3/G4/G5 puissent tourner en CI :
```yaml
jobs:
  build-backend:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:16
        env:
          POSTGRES_DB: campushub_test
          POSTGRES_USER: campushub
          POSTGRES_PASSWORD: campushub
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - run: mvn -B clean verify
```

2. **Vérifier que les tests `@DataJpaTest`** de G2/G3/G4 passent bien en CI avec ce Postgres éphémère (pas seulement en local).

3. **Avancer sur le volet cloud** : sur la plateforme choisie hier, identifier concrètement l'offre de base de données managée (ex. Render Postgres, Railway Postgres) et noter dans `NOTES-CLOUD.md` :
   - Le type d'offre (gratuite/payante, limites).
   - Comment elle s'intègre avec les variables d'environnement Spring (`SPRING_DATASOURCE_URL`, etc.).

4. **Se coordonner avec G4** : le schéma de migration Flyway sera-t-il appliqué automatiquement au démarrage en production, ou faut-il une étape manuelle ? Documenter la réponse.

5. **Si le temps le permet**, faire un premier test de connexion depuis votre machine locale vers une base Postgres managée créée sur la plateforme cible (juste pour valider que ça fonctionne, sans encore y connecter l'application).

### Livrable attendu ce soir
- Pipeline CI avec Postgres intégré, tests `@DataJpaTest` de tous les groupes backend passant en CI.
- `NOTES-CLOUD.md` mis à jour avec les détails de la base managée cible.

---

## ⚠️ Dépendance critique à traiter en priorité aujourd'hui

**G6 ne peut pas avancer correctement si le CORS ou le format de pagination de G2 change en cours de journée.** Toute modification de contrat de la part de G2 doit être annoncée immédiatement au stand-up ou en direct à G6/G7, pas découverte a posteriori.

## 🔗 Autres dépendances à surveiller

- G2/G3 dépendent des entités JPA finalisées par G4 → vérifier au stand-up du matin que G4 n'a pas de blocage.
- G5 dépend de la migration Flyway coordonnée avec G4 pour la table `utilisateur`.
- G8 dépend des entités de G2/G3/G4/G5 pour valider que les tests passent bien en CI.

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire par un membre d'un autre groupe, `JOURNAL.md` à jour ce soir (appris / bloqué / question).
