# Contrat d'authentification JWT — CampusHub

Documentation du contrat d'API pour le module `campushub-auth`, à destination de G7 (implémentation Angular prévue Jour 8).

## POST /api/auth/register

Crée un compte utilisateur.

**Requête**
```json
{
  "email": "awa.diop@campushub.sn",
  "motDePasse": "MotDePasse123!",
  "nom": "Awa Diop"
}
```

| Champ | Type | Obligatoire | Remarque |
|---|---|---|---|
| `email` | string | oui | doit être unique |
| `motDePasse` | string | oui | |
| `nom` | string | non | |

**Réponse succès (201)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "ETUDIANT"
}
```

**Réponse erreur (400)** — email déjà utilisé ou données invalides (champs `@NotBlank` manquants)
```json
{
  "timestamp": "2026-08-06T10:15:00",
  "status": 400,
  "erreurs": ["Email deja utilise"]
}
```

## POST /api/auth/login

Authentifie un utilisateur existant.

**Requête**
```json
{
  "email": "awa.diop@campushub.sn",
  "motDePasse": "MotDePasse123!"
}
```

**Réponse succès (200)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "ETUDIANT"
}
```

**Réponse erreur (401)** — identifiants invalides
```json
{
  "timestamp": "2026-08-06T10:15:00",
  "status": 401,
  "erreurs": ["Identifiants invalides"]
}
```

## Utilisation du token

Sur toute requête vers un endpoint protégé, ajouter le header :

Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.....

## Rôles disponibles

- `ADMIN`
- `ETUDIANT`

Le rôle est renvoyé dans `AuthResponse.role` et détermine les endpoints accessibles côté frontend (guards Angular, Jour 8).

> ⚠️ Note pour G7 : ces endpoints renvoient actuellement `501 Not Implemented` (implémentation fonctionnelle prévue plus tard). Ce contrat décrit le format attendu une fois l'implémentation terminée — utile dès maintenant pour préparer les services Angular et les guards sans attendre l'implémentation réelle.
 