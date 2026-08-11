# Gestion des secrets — CampusHub

## Pourquoi

Les secrets (clé JWT, mot de passe de la base de données) ne doivent jamais être commités dans le dépôt Git. Ils sont fournis à l'application via des variables d'environnement, lues à partir d'un fichier `.env` local à chaque développeur (jamais versionné).

## Créer son `.env` local

1. Copier le fichier d'exemple à la racine du projet :
```bash
   cp .env.example .env
```
2. Générer une clé JWT locale :
```bash
   openssl rand -base64 32
```
3. Remplacer les valeurs `change-me-in-production` dans `.env` par :
   - la clé générée pour `JWT_SECRET`
   - un mot de passe de ton choix pour `POSTGRES_PASSWORD` (ou `campushub` en local pour rester cohérent avec les autres membres de l'équipe)

## Variables obligatoires pour `docker compose up`

| Variable | Description | Utilisée par |
|---|---|---|
| `JWT_SECRET` | Clé de signature des tokens JWT | Service `backend` |
| `POSTGRES_PASSWORD` | Mot de passe de la base Postgres | Services `postgres` et `backend` |

Sans ces deux variables définies dans `.env`, `docker compose up` échouera au démarrage du service `backend` (connexion à la base et génération de tokens impossibles).

## Rappel de sécurité

- `.env` est dans `.gitignore` : ne jamais forcer son ajout avec `git add -f`.
- `.env.example` ne doit contenir que des placeholders, jamais de vraies valeurs.
- Si un secret a été commité par erreur, il doit être considéré comme compromis et régénéré immédiatement (pas seulement supprimé du dernier commit — l'historique Git le garde).
