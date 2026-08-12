# Documentation CI/CD — CampusHub

## Vue d'ensemble

Le pipeline CI/CD est défini dans `.github/workflows/build-and-test.yml` et se déclenche automatiquement sur chaque **pull request**. Il enchaîne 4 jobs séquentiels qui valident l'application à la fois en local (backend/frontend) et dans son environnement dockerisé complet.

## Structure du pipeline

```
build-backend ──┐
                 ├──> build-docker-images ──> test-docker-compose
build-frontend ──┘
```

### 1. `build-backend`

Compile et teste le backend Spring Boot.

- Lance un service **Postgres 16** éphémère (base `campushub_test`) pour les tests d'intégration.
- Exécute `mvn -B clean verify` depuis le dossier `campushub/`.
- Java 17 (Temurin).

### 2. `build-frontend`

Build de l'application Angular.

- Node.js 22 (aligné avec l'image `node:22-alpine` utilisée dans le Dockerfile frontend).
- Exécute `npm ci` puis `npm run build` depuis `campushub-front/`.

### 3. `build-docker-images`

Dépend de `build-backend` et `build-frontend`. Construit les deux images Docker de l'application :

- **Backend** : `docker/backend/Dockerfile` → image `campushub-backend:ci` (multi-stage Maven → JRE Alpine).
- **Frontend** : `docker/frontend/Dockerfile` → image `campushub-frontend:ci` (multi-stage Node → Nginx Alpine).

### 4. `test-docker-compose`

Dépend de `build-docker-images`. Valide que l'application complète démarre correctement via `docker compose up -d`, en attendant que les services passent à l'état `healthy` (timeout 60s), puis nettoie avec `docker compose down`.

## Fichiers associés

| Fichier | Rôle |
|---|---|
| `docker-compose.yml` | Orchestration des 3 services : `postgres`, `backend`, `frontend`. |
| `docker/backend/Dockerfile` | Image backend multi-stage (build Maven, exécution JRE Alpine). |
| `docker/frontend/Dockerfile` | Image frontend multi-stage (build Angular, servi par Nginx). |
| `docker/frontend/nginx.conf` | Config Nginx minimale gérant le routing Angular (`try_files`). |
| `.env.example` | Structure des variables d'environnement requises (`JWT_SECRET`, `POSTGRES_PASSWORD`). |

## Variables d'environnement

Le service `backend` du `docker-compose.yml` attend `JWT_SECRET`, avec une valeur par défaut de secours (`changez-moi-en-production-secret-256-bits`) si la variable n'est pas définie. En local, chaque développeur doit créer un fichier `.env` à partir de `.env.example` (voir `docs/secrets.md`). En CI, la valeur par défaut est utilisée automatiquement.

## Limite connue

Le service `frontend` ne dispose pas encore de healthcheck dans `docker-compose.yml`. La boucle d'attente du job `test-docker-compose` (`grep -q "healthy"`) peut donc théoriquement se terminer dès qu'un seul service (ex. `postgres`) atteint l'état healthy, sans garantir que `backend` et `frontend` le soient aussi. Le job reste utile pour détecter un crash au démarrage, mais un healthcheck sur `frontend` (ex. `curl -f http://localhost/`) et une boucle comptant le nombre exact de services healthy attendus amélioreraient la fiabilité du test. Amélioration prévue pour le jour 10.

## Maintenance

Pour modifier le pipeline :

- Garder les noms de dossiers `docker/backend/` et `docker/frontend/` cohérents entre les Dockerfiles et `build-docker-images` — toute divergence fait échouer le job silencieusement avec une erreur "Dockerfile not found".
- Si la version de Node ou Java change dans les Dockerfiles, répercuter le changement dans `build-backend`/`build-frontend` pour éviter les écarts d'environnement entre build CI et build Docker.
- Le contexte de build Docker est la racine du repo (`context: .`) — les `COPY` dans les Dockerfiles utilisent donc des chemins relatifs à la racine (`campushub/`, `campushub-front/`), pas aux sous-dossiers `docker/`.
