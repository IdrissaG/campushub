# 📋 CampusHub — Tâches du jour (Jour 5/10)

**Contexte du jour :** implémentation complète de la sécurité JWT. C'est aussi la **fin de la semaine 1** — l'objectif du soir est d'avoir une API complète (CRUD + persistance + sécurité), 100% documentée dans Swagger, prête à être consommée par le frontend la semaine prochaine.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** vérifier la cohérence des rôles et permissions sur l'ensemble du backend.

### Étapes détaillées

1. **Faire l'inventaire des routes protégées** : demander à G2 et G3 la liste de leurs endpoints et le niveau d'accès attendu pour chacun (public, ETUDIANT, ADMIN). Exemple de tableau à construire ensemble :

| Endpoint | Méthode | Accès |
|---|---|---|
| `/api/etudiants` | GET | Public |
| `/api/etudiants` | POST | ADMIN |
| `/api/etudiants/{id}` | PUT | ADMIN |
| `/api/etudiants/{id}` | DELETE | ADMIN |
| `/api/cours` | GET | Public |
| `/api/cours` | POST | ADMIN |

2. **Vérifier qu'aucune route sensible n'a été oubliée** (ex. DELETE non protégé par erreur).

3. **S'assurer que G2 et G3 appliquent la règle de façon identique** (même annotation, même style — pas l'un avec `@PreAuthorize` et l'autre avec une config différente).

4. **Documenter la matrice des droits** dans `docs/securite.md`, pour que ce soit visible par tous (utile aussi pour G6/G7 la semaine prochaine).

### Livrable attendu ce soir
- Matrice des droits validée et documentée dans `docs/securite.md`.
- Confirmation que G2 et G3 appliquent les règles de façon cohérente.

---

## 🟢 G2 – API Étudiants

**Mission du jour :** appliquer les règles d'accès livrées par G5 sur ses endpoints.

### Étapes détaillées

1. **Attendre la livraison du filtre JWT et de la configuration de sécurité par G5** (normalement disponible en début/milieu de matinée).

2. **Protéger les endpoints d'écriture** avec `@PreAuthorize` :
```java
@PostMapping
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<EtudiantResponse> create(@Valid @RequestBody EtudiantRequest request) { ... }

@PutMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<EtudiantResponse> update(@PathVariable Long id, @Valid @RequestBody EtudiantRequest request) { ... }

@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> delete(@PathVariable Long id) { ... }
```

3. **Laisser les endpoints de lecture publics** (`GET /api/etudiants`, `GET /api/etudiants/{id}`) — pas d'annotation `@PreAuthorize` dessus.

4. **Tester avec Swagger** : utiliser le bouton "Authorize" (mis en place par G5) avec un token ADMIN, puis avec un token ETUDIANT, pour vérifier que les bonnes routes sont bloquées ou autorisées.

5. **Ajouter un test MockMvc** vérifiant qu'un appel non authentifié sur `POST /api/etudiants` renvoie bien 401 :
```java
@Test
void testCreateSansAuth_retourne401() throws Exception {
    mockMvc.perform(post("/api/etudiants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{...}"))
        .andExpect(status().isUnauthorized());
}
```

### Livrable attendu ce soir
- Endpoints d'écriture protégés, lecture publique conservée.
- Test 401 vert.
- Vérification manuelle via Swagger "Authorize" avec un token ADMIN et un token ETUDIANT.

---

## 🟡 G3 – API Cours & Inscriptions

**Mission du jour :** exactement le même travail que G2, sur les endpoints Cours et Inscriptions.

### Étapes détaillées

1. **Protéger les endpoints d'écriture** de la même façon que G2 (`@PreAuthorize("hasRole('ADMIN')")` sur POST/PUT/DELETE).

2. **Réfléchir au cas des inscriptions** : qui a le droit de créer une inscription ? Décision à valider avec G1 — probablement ADMIN pour rester cohérent, sauf si vous décidez qu'un étudiant peut s'auto-inscrire (à documenter clairement si c'est le choix retenu).

3. **Tester avec Swagger "Authorize"**, comme G2.

4. **Ajouter les tests MockMvc 401/403** correspondants.

5. **Synchronisation avec G2** : vérifier que le style d'annotation et la logique de permission sont identiques entre les deux modules.

### Livrable attendu ce soir
- Endpoints Cours/Inscriptions protégés de façon cohérente avec G2.
- Tests 401/403 verts.
- Décision documentée sur les droits d'inscription.

---

## 🟠 G4 – Persistance

**Mission du jour :** ajuster les migrations si G5 a besoin de modifications sur la table `utilisateur`.

### Étapes détaillées

1. **Vérifier avec G5** si la structure de la table `utilisateur` créée hier (`V3__utilisateur.sql`) est suffisante pour l'implémentation JWT complète d'aujourd'hui, ou si des ajustements sont nécessaires (ex. ajout d'un champ `date_creation`, `actif`).

2. **Si besoin**, créer une nouvelle migration (jamais modifier `V3` déjà appliquée) :
```sql
-- V4__utilisateur_ajustements.sql
ALTER TABLE utilisateur ADD COLUMN date_creation TIMESTAMP DEFAULT NOW();
```

3. **Vérifier que l'ensemble des migrations** (`V1` à `V4`) s'appliquent proprement sur une base fraîche (utile pour la démo de vendredi).

4. **Support disponible** pour G5 toute la journée sur les questions de persistance liées aux utilisateurs.

5. **Profiter du calme relatif d'aujourd'hui** pour finaliser la documentation du schéma (`docs/schema.md`) avec le diagramme incluant maintenant `utilisateur`.

### Livrable attendu ce soir
- Migrations à jour et vérifiées de bout en bout.
- Schéma documenté incluant la table `utilisateur`.

---

## 🔴 G5 – Sécurité Backend (le pilote du jour)

**Mission du jour :** implémentation complète de Spring Security avec JWT — le cœur de la journée.

### Étapes détaillées

**1. Ajouter les dépendances nécessaires** au `pom.xml` (si pas déjà fait au jour 2) :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
```

**2. Créer le service JWT** :
```java
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 1000 * 60 * 60 * 10; // 10h

    public String genererToken(Utilisateur utilisateur) {
        return Jwts.builder()
            .subject(utilisateur.getEmail())
            .claim("role", utilisateur.getRole().name())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigningKey())
            .compact();
    }

    public String extraireEmail(String token) {
        return extraireClaims(token).getSubject();
    }

    public boolean estValide(String token) {
        try {
            extraireClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims extraireClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build()
            .parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
```

**3. Créer le filtre JWT** :
```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (jwtService.estValide(token)) {
            String email = jwtService.extraireEmail(token);
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow();

            var auth = new UsernamePasswordAuthenticationToken(
                email, null,
                List.of(new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
```

**4. Configurer Spring Security** :
```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/etudiants/**", "/api/cours/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**5. Implémenter register et login** (remplacer les `501 Non implémenté` d'hier) :
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (utilisateurRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailDejaUtiliseException();
        }
        Utilisateur utilisateur = new Utilisateur(
            request.email(),
            passwordEncoder.encode(request.motDePasse()),
            Role.ETUDIANT
        );
        utilisateurRepository.save(utilisateur);
        String token = jwtService.genererToken(utilisateur);
        return ResponseEntity.status(201).body(new AuthResponse(token, utilisateur.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.email())
            .orElseThrow(() -> new IdentifiantsInvalidesException());

        if (!passwordEncoder.matches(request.motDePasse(), utilisateur.getMotDePasseHash())) {
            throw new IdentifiantsInvalidesException();
        }
        String token = jwtService.genererToken(utilisateur);
        return ResponseEntity.ok(new AuthResponse(token, utilisateur.getRole().name()));
    }
}
```

**6. Configurer le bouton "Authorize" dans Swagger** :
```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
```

**7. Ajouter les tests MockMvc** :
```java
@Test
void testAccesRouteProtegeeSansToken_retourne401() throws Exception {
    mockMvc.perform(post("/api/etudiants"))
        .andExpect(status().isUnauthorized());
}

@Test
void testAccesAdminAvecTokenEtudiant_retourne403() throws Exception {
    String tokenEtudiant = genererTokenTest(Role.ETUDIANT);
    mockMvc.perform(post("/api/etudiants")
            .header("Authorization", "Bearer " + tokenEtudiant)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{...}"))
        .andExpect(status().isForbidden());
}
```

**8. Livrer le contrat final à G2 et G3** dès que le filtre et la config sont prêts (priorité absolue ce matin, ils en ont besoin toute la journée).

### Livrable attendu ce soir
- Parcours complet register → login → action protégée, entièrement testable dans Swagger UI (bouton Authorize).
- 2 tests MockMvc (401 et 403) verts.
- Filtre et config livrés à G2/G3 avant la mi-journée.

---

## 🟣 G6 – Frontend Core

**Mission du jour :** pas de tâche technique nouvelle aujourd'hui — journée de consolidation et de préparation.

### Étapes détaillées

1. **Prendre connaissance du contrat JWT finalisé** par G5 aujourd'hui (format de `AuthResponse`, header `Authorization: Bearer`) — vous en aurez besoin au jour 8.

2. **Consolider l'affichage existant** : revue de code interne au groupe, nettoyage, amélioration de la gestion d'erreur mise en place au jour 4.

3. **Optionnel si en avance** : commencer à réfléchir à la structure de `AuthService` (sans l'implémenter) pour être prêt au jour 8.

### Livrable attendu ce soir
- Code existant consolidé et nettoyé.
- Compréhension claire du contrat JWT de G5 (notes internes si besoin).

---

## ⚪ G7 – Frontend Formulaires & Auth

**Mission du jour :** prendre connaissance du contrat JWT — préparation avant l'implémentation du jour 8.

### Étapes détaillées

1. **Étudier en détail le contrat d'authentification** livré par G5 aujourd'hui :
   - Format de `RegisterRequest` / `LoginRequest`
   - Format de la réponse `AuthResponse` (token + rôle)
   - Comment le token doit être transmis (`Authorization: Bearer <token>`)

2. **Tester manuellement le parcours** register → login directement dans Swagger UI, pour bien comprendre le flux avant de le coder en Angular.

3. **Consolider le CRUD existant** (formulaire du jour 7 à venir n'est pas encore commencé, mais vous pouvez déjà réfléchir à la structure des routes protégées pour le guard `canActivate` du jour 8).

4. **Optionnel si en avance** : esquisser la structure de `AuthService` (interfaces TypeScript pour `LoginRequest`, `AuthResponse`) sans implémenter la logique complète.

### Livrable attendu ce soir
- Notes claires sur le contrat JWT (à partager en équipe).
- Éventuellement, les interfaces TypeScript du contrat auth déjà posées.

---

## ⚫ G8 – DevOps & Cloud

**Mission du jour :** ajouter les tests de sécurité au pipeline CI, et avancer sur la préparation cloud des secrets.

### Étapes détaillées

1. **Vérifier que les nouveaux tests MockMvc de sécurité** (401/403 de G2, G3, G5) passent bien dans le pipeline CI existant — pas de configuration supplémentaire nécessaire normalement, juste une vérification.

2. **Ajouter une étape de vérification** dans le pipeline qui échoue explicitement si un test de sécurité est absent ou skippé (bonne pratique pour éviter les régressions de sécurité) :
```yaml
      - name: Vérifier les tests de sécurité
        run: mvn -B test -Dtest=*SecurityTest,*AuthTest
```

3. **Avancer sur le volet cloud** : le `JWT_SECRET` doit maintenant être un vrai secret à gérer proprement.
   - Générer une clé secrète robuste (ex. `openssl rand -base64 32`).
   - **Ne jamais la committer.**
   - Configurer ce secret dans les paramètres de la plateforme cloud choisie (variables d'environnement chiffrées) — la plupart des plateformes (Render, Railway, Fly.io) ont une section "Environment Variables" dédiée.

4. **Documenter la procédure** de gestion des secrets dans `NOTES-CLOUD.md`, pour que ce soit reproductible au jour 10.

5. **Vérifier avec G5** que la config `application.properties` lit bien le secret depuis une variable d'environnement et non une valeur en dur :
```properties
jwt.secret=${JWT_SECRET}
```

### Livrable attendu ce soir
- Tests de sécurité intégrés et vérifiés dans le pipeline CI.
- Procédure de gestion du secret JWT documentée dans `NOTES-CLOUD.md`.
- Confirmation que `jwt.secret` est bien externalisé en variable d'environnement, jamais en dur dans le code.

---

## 🎉 Fin de la semaine 1 — Merge collectif

En fin de journée, organisez un **merge collectif** de tous les modules backend (G1 à G5) sur `main`. Vérifiez ensemble que :

- [ ] L'API complète tourne sans erreur au démarrage.
- [ ] Toutes les routes sont documentées dans Swagger.
- [ ] Le parcours register → login → CRUD protégé fonctionne de bout en bout depuis Swagger UI.
- [ ] Les migrations Flyway s'appliquent proprement sur une base fraîche.
- [ ] Tous les tests (unitaires, `@DataJpaTest`, MockMvc) sont verts en CI.

**Faites une démo rapide entre groupes back et front** (30 min) pour que G6/G7 voient l'API finale tourner avant d'attaquer la semaine 2.

## ⚠️ Dépendance critique du jour

G2 et G3 sont **bloqués** tant que G5 n'a pas livré le filtre JWT et la config de sécurité — G5 doit prioriser cette livraison en tout début de matinée, pas en fin de journée.

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire, `JOURNAL.md` à jour ce soir. C'est aussi le moment de faire un petit bilan de semaine dans le journal : ce qui a bien fonctionné dans la collaboration, ce qui pourrait s'améliorer semaine 2.
