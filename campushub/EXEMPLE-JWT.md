# Exemple d'utilisation du JWT — cycle complet

Référence pratique pour G7 (implémentation Jour 8).

## 1. Login — récupérer le token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "etudiant.test@campushub.sn",
    "motDePasse": "Test1234!"
  }'
```

Réponse (200) :
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "ETUDIANT"
}
```

## 2. Appel protégé avec le token

Exemple : création d'un étudiant (POST, nécessite authentification).

```bash
curl -X POST http://localhost:8080/api/etudiants \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -d '{
    "nom": "Diallo",
    "prenom": "Fatou",
    "email": "fatou.diallo@campushub.sn",
    "age": 21,
    "filiere": "Informatique"
  }'
```

## 3. Sans token

Le même appel sans le header `Authorization` échoue :

```bash
curl -X POST http://localhost:8080/api/etudiants \
  -H "Content-Type: application/json" \
  -d '{ "nom": "Test" }'
```

→ `401 Unauthorized` ou `403 Forbidden` selon la configuration.

## Comptes de test disponibles

| Rôle | Email | Mot de passe |
|---|---|---|
| ETUDIANT | etudiant.test@campushub.sn | Test1234! |
| ADMIN | admin.test@campushub.sn | Test1234! |

> ⚠️ Ces comptes vivent dans la base locale de dev — ils ne persistent pas si la base est réinitialisée (`docker compose down -v`).