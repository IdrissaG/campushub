# 📋 CampusHub — Tâches du jour (Jour 9/10)

**⚠️ Changement d'organisation important :** G8 a été redéployé sur un autre projet et ne reviendra pas. Son périmètre (Docker, pipeline CI/CD, déploiement cloud) est **définitivement redistribué** sur les 7 groupes restants à partir d'aujourd'hui. Cette répartition sera aussi celle du jour 10.

**Contexte du jour :** Dockerisation complète de l'application (back + front + base de données), avec `docker compose up` qui doit tout lancer d'une seule commande. C'est normalement le jour le plus dense pour l'ancien G8 — il faut donc répartir intelligemment la charge selon les compétences déjà acquises par chaque groupe.

---

## Nouvelle répartition du périmètre DevOps (jour 9 et jour 10)

| Ancienne tâche G8 | Repreneur | Pourquoi ce choix |
|---|---|---|
| Dockerfile backend | **G2** | Ils connaissent le mieux la structure Maven/Spring Boot à packager |
| Dockerfile frontend | **G6** | Ils maîtrisent le build Angular à containeriser |
| `docker-compose.yml` complet (orchestration) | **G4** | Ils pilotent Postgres/Docker depuis le jour 2, c'est une continuité naturelle |
| Pipeline CI/CD (GitHub Actions) | **G1** | Ils coordonnent déjà l'intégration globale depuis le début |
| Gestion des secrets (JWT_SECRET, DB credentials) | **G5** | Cohérent avec leur rôle sécurité |
| Tests d'intégration Docker (vérifier que tout démarre ensemble) | **G3 + G7** | Le groupe API et le groupe Frontend testent chacun leur bout de la chaîne une fois assemblée |

Chaque groupe garde en tête son objectif habituel du jour 9 (tests manquants) **en plus** de sa nouvelle mission Docker/CI — la charge est volontairement répartie en petits morceaux pour ne pas surcharger un seul groupe.

---

## 🔵 G1 – Fondations & Modèle de données + Pilotage CI/CD *(nouveau)*

**Mission du jour :** finaliser le pipeline GitHub Actions pour qu'il construise et teste l'application dockerisée dans son ensemble.

### Étapes détaillées

1. **Concevoir la structure du pipeline final**, qui devra orchestrer tous les morceaux livrés par les autres groupes aujourd'hui :
```yaml
name: build-and-test
on: [pull_request]

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
        ports: ["5432:5432"]
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '17', distribution: 'temurin' }
      - run: mvn -B clean verify

  build-frontend:
    runs-on: ubuntu-latest
    defaults:
      run:
        working-directory: ./campushub-front
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with: { node-version: '20' }
      - run: npm ci
      - run: npm run build

  build-docker-images:
    needs: [build-backend, build-frontend]
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build image backend
        run: docker build -t campushub-backend:ci -f docker/back/Dockerfile .
      - name: Build image frontend
        run: docker build -t campushub-frontend:ci -f docker/front/Dockerfile .
```

2. **Ajouter un job final** qui lance `docker compose up` en CI et vérifie que les healthchecks passent (à coordonner avec G4 une fois leur `docker-compose.yml` prêt — voir leur section) :
```yaml
  test-docker-compose:
    needs: build-docker-images
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: docker compose up -d
      - name: Attendre les healthchecks
        run: |
          timeout 60 bash -c 'until docker compose ps | grep -q "healthy"; do sleep 2; done'
      - run: docker compose down
```

3. **Récupérer les Dockerfiles de G2 et G6** dès qu'ils sont prêts (coordination active toute la journée, pas d'attente passive).

4. **Faire l'intégration finale** : une fois tous les morceaux livrés (Dockerfiles de G2/G6, compose de G4, secrets de G5), assembler et tester le pipeline complet.

### Livrable attendu ce soir
- Pipeline CI/CD complet incluant build back, build front, build des images Docker, et test du `docker compose up`.
- Documentation courte dans `docs/ci-cd.md` expliquant la structure du pipeline pour que ce soit maintenable après l'immersion.

---

## 🟢 G2 – API Étudiants + Dockerfile Backend *(nouveau)*

**Mission du jour :** tests manquants sur votre module, **et** créer le Dockerfile multi-stage du backend.

### Étapes détaillées

**Partie 1 — Tests (comme prévu initialement)**
1. Compléter les 3 tests de service restants (Mockito) et 2 tests MockMvc supplémentaires si pas déjà faits au jour 5.

**Partie 2 — Dockerfile backend (nouvelle mission)**
2. Créer `docker/back/Dockerfile` en multi-stage (build avec Maven, exécution avec un JRE léger) :
```dockerfile
# Étape 1 : build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image finale légère
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

3. **Construire l'image en local** et vérifier qu'elle démarre correctement :
```bash
docker build -t campushub-backend:test -f docker/back/Dockerfile .
docker run -p 8080:8080 campushub-backend:test
```

4. **Vérifier la taille de l'image** (`docker images`) — l'objectif du multi-stage est d'avoir une image finale sans Maven ni le code source, juste le `.jar` et le JRE.

5. **Livrer ce Dockerfile à G1** dès qu'il fonctionne, pour intégration au pipeline.

### Livrable attendu ce soir
- Tests complétés.
- `docker/back/Dockerfile` fonctionnel, image testée en local, livré à G1.

---

## 🟡 G3 – API Cours & Inscriptions + Test d'intégration backend *(nouveau)*

**Mission du jour :** tests manquants, **et** valider que l'API complète fonctionne une fois dockerisée.

### Étapes détaillées

**Partie 1 — Tests (comme prévu initialement)**
1. Compléter les tests manquants sur votre module (service + MockMvc).

**Partie 2 — Validation d'intégration Docker (nouvelle mission)**
2. Une fois que G2 a livré son Dockerfile et que G4 a un premier jet du `docker-compose.yml`, **tester le parcours API complet** dans l'environnement dockerisé (pas juste en local classique) :
   - Lancer `docker compose up`.
   - Depuis Swagger ou Postman, exécuter le scénario complet : créer un étudiant, un cours, une inscription, vérifier la persistance après un `docker compose restart`.
3. **Remonter tout problème** rencontré (variables d'environnement manquantes, port mal exposé, connexion base de données qui échoue) directement à G4 et G1.

### Livrable attendu ce soir
- Tests complétés.
- Rapport de validation : "l'API complète fonctionne correctement une fois dockerisée" (ou liste des problèmes rencontrés et corrigés).

---

## 🟠 G4 – Persistance + Orchestration Docker Compose *(rôle élargi)*

**Mission du jour :** le pilote Docker de la journée — assembler le `docker-compose.yml` complet.

### Étapes détaillées

1. **Créer le `docker-compose.yml` final à la racine du projet**, orchestrant les 3 services :
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
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U campushub"]
      interval: 5s
      timeout: 5s
      retries: 5

  backend:
    build:
      context: .
      dockerfile: docker/back/Dockerfile
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/campushub
      SPRING_DATASOURCE_USERNAME: campushub
      SPRING_DATASOURCE_PASSWORD: campushub
      JWT_SECRET: ${JWT_SECRET}
    depends_on:
      postgres:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 10s
      timeout: 5s
      retries: 5

  frontend:
    build:
      context: ./campushub-front
      dockerfile: ../docker/front/Dockerfile
    ports:
      - "4200:80"
    depends_on:
      - backend

volumes:
  pgdata:
```

2. **Attendre les Dockerfiles de G2 (backend) et G6 (frontend)** — coordination active, pas d'attente passive : demandez-leur un statut d'avancement en milieu de matinée.

3. **Une fois les 3 services assemblés**, tester `docker compose up` de bout en bout sur une machine "vierge" si possible (ou au moins après un `docker system prune` pour éviter les caches trompeurs).

4. **Vérifier les healthchecks** : que se passe-t-il si Postgres met du temps à démarrer ? Le backend doit attendre (`depends_on: condition: service_healthy`), pas planter.

5. **Livrer le fichier final à G1** pour intégration dans le pipeline CI.

### Livrable attendu ce soir
- `docker-compose.yml` complet et fonctionnel, testé de bout en bout.
- `docker compose up` lance l'application complète (Swagger UI inclus) en une seule commande.

---

## 🔴 G5 – Sécurité Backend + Gestion des secrets *(nouveau)*

**Mission du jour :** tests manquants, **et** sécuriser la gestion des secrets dans l'environnement Docker.

### Étapes détaillées

**Partie 1 — Tests (comme prévu initialement)**
1. Compléter les tests de sécurité manquants si nécessaire.

**Partie 2 — Secrets Docker (nouvelle mission, reprise du volet cloud de l'ex-G8)**
2. **Créer un fichier `.env.example`** à la racine (jamais de vraies valeurs dedans, juste la structure) :
```
JWT_SECRET=change-me-in-production
POSTGRES_PASSWORD=change-me-in-production
```
3. **Vérifier que `.env` est bien dans `.gitignore`** — le vrai fichier `.env` avec les valeurs réelles ne doit jamais être commité.

4. **Générer une vraie clé secrète** pour les tests locaux de l'équipe :
```bash
openssl rand -base64 32
```

5. **Documenter la procédure** dans `docs/secrets.md` : comment créer son `.env` local à partir de `.env.example`, quelles variables sont obligatoires pour que `docker compose up` fonctionne.

6. **Coordonner avec G4** pour que le `docker-compose.yml` lise bien ces variables depuis un fichier `.env` (`${JWT_SECRET}` comme montré dans la section G4).

### Livrable attendu ce soir
- Tests complétés.
- `.env.example` et documentation des secrets livrés, `.env` réel exclu du versioning.

---

## 🟣 G6 – Frontend Core + Dockerfile Frontend *(nouveau)*

**Mission du jour :** tests de composants, **et** créer le Dockerfile multi-stage du frontend.

### Étapes détaillées

**Partie 1 — Tests (comme prévu initialement)**
1. Compléter les tests de composants manquants (en plus du test de service avec `HttpTestingController` que G7 gère de son côté).

**Partie 2 — Dockerfile frontend (nouvelle mission)**
2. Créer `docker/front/Dockerfile` en multi-stage (build Angular, puis servi par Nginx) :
```dockerfile
# Étape 1 : build Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Étape 2 : image Nginx légère
FROM nginx:alpine
COPY --from=build /app/dist/campushub-front/browser /usr/share/nginx/html
COPY docker/front/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
```

3. **Créer la config Nginx minimale** nécessaire pour une app Angular (gestion du routing côté client) :
```nginx
# docker/front/nginx.conf
server {
    listen 80;
    location / {
        root /usr/share/nginx/html;
        try_files $uri $uri/ /index.html;
    }
}
```

4. **Construire et tester l'image en local** :
```bash
docker build -t campushub-frontend:test -f docker/front/Dockerfile .
docker run -p 4200:80 campushub-frontend:test
```

5. **Vérifier que le routing Angular fonctionne** une fois servi par Nginx (naviguer directement vers une URL type `/etudiants/3` sans passer par la page d'accueil, pour tester la règle `try_files`).

6. **Livrer ce Dockerfile à G4** pour intégration dans le `docker-compose.yml`.

### Livrable attendu ce soir
- Tests complétés.
- `docker/front/Dockerfile` fonctionnel, image testée en local, livré à G4.

---

## ⚪ G7 – Frontend Formulaires & Auth + Test d'intégration frontend *(nouveau)*

**Mission du jour :** tests avec `HttpTestingController`, **et** valider le parcours utilisateur complet une fois l'application dockerisée.

### Étapes détaillées

**Partie 1 — Tests (comme prévu initialement)**
1. Écrire le test de service avec `HttpTestingController` :
```typescript
it('devrait créer un étudiant via POST', () => {
  const nouvelEtudiant = { nom: 'Diop', prenom: 'Awa', email: 'awa@test.com', age: 22 };
  service.create(nouvelEtudiant).subscribe();

  const req = httpMock.expectOne('http://localhost:8080/api/etudiants');
  expect(req.request.method).toBe('POST');
  req.flush({ id: 1, ...nouvelEtudiant });
});
```

**Partie 2 — Validation d'intégration Docker (nouvelle mission)**
2. Une fois que G4 a un `docker-compose.yml` fonctionnel avec les 3 services, **tester le parcours utilisateur complet** dans l'environnement dockerisé (frontend servi par Nginx sur le port 4200, communiquant avec le backend sur le port 8080) :
   - Login avec le compte ADMIN.
   - Créer, modifier, supprimer un étudiant.
   - Vérifier que le routing fonctionne bien après un rafraîchissement de page (test spécifique à l'environnement Nginx, différent du serveur de dev Angular).
3. **Remonter tout problème** (CORS différent en environnement dockerisé, URL d'API à ajuster) à G4 et G2.

### Livrable attendu ce soir
- Tests avec `HttpTestingController` complétés.
- Rapport de validation du parcours utilisateur complet en environnement dockerisé.

---

## 🔗 Séquencement de la journée (pour éviter les blocages)

Comme plusieurs groupes dépendent maintenant les uns des autres pour cette mission Docker répartie, voici l'ordre logique à respecter :

1. **Matin** : G2 et G6 travaillent en parallèle sur leurs Dockerfiles respectifs (aucune dépendance entre eux).
2. **Début d'après-midi** : dès que G2 et G6 ont un Dockerfile fonctionnel, G4 les intègre dans le `docker-compose.yml`.
3. **Fin d'après-midi** : une fois le compose assemblé par G4, G3 et G7 testent chacun leur périmètre (API et parcours utilisateur) en conditions dockerisées.
4. **Parallèlement toute la journée** : G5 prépare les secrets, G1 construit le pipeline CI en intégrant les morceaux au fur et à mesure qu'ils arrivent.

**Stand-up renforcé recommandé** : vu la nouvelle répartition, faites un point de mi-journée (pas seulement le stand-up du matin) pour vérifier que G2/G6 ont bien livré à temps pour que G4 puisse assembler avant la fin de journée.

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire par un membre d'un autre groupe, `JOURNAL.md` à jour ce soir. Notez aussi dans le journal comment s'est passée cette redistribution de charge — utile pour le bilan du jour 10.
