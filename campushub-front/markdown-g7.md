# G7 – Analyse du contrat JWT (Backend G5)

## Objectif

L'objectif est de comprendre le fonctionnement de l'authentification JWT du backend afin de préparer son intégration dans l'application Angular (jour 8).


---

# Endpoints d'authentification

Le backend expose deux endpoints publics :

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Création d'un compte utilisateur |
| POST | `/api/auth/login` | Connexion d'un utilisateur |

Ces routes sont accessibles sans authentification grâce à la configuration suivante :

```java
.requestMatchers(
        "/api/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**"
).permitAll()
```

---

# Contrat RegisterRequest

Le endpoint :

```
POST /api/auth/register
```

attend un objet JSON ayant la structure suivante :

```json
{
  "email": "amina.diop@exemple.com",
  "motDePasse": "MotDePasse123!",
  "nom": "Amina Diop"
}
```

## Contraintes de validation

| Champ | Contraintes |
|--------|-------------|
| email | obligatoire, format email valide |
| motDePasse | obligatoire, minimum 8 caractères |
| nom | chaîne de caractères |

En cas d'utilisation d'un email déjà existant, le backend renvoie une erreur :

```
Cet email est deja utilise
```

---

# Contrat LoginRequest

Le endpoint :

```
POST /api/auth/login
```

attend un objet JSON :

```json
{
  "email": "amina.diop@exemple.com",
  "motDePasse": "MotDePasse123!"
}
```

## Contraintes

| Champ | Contraintes |
|--------|-------------|
| email | obligatoire, email valide |
| motDePasse | obligatoire |

---

# Contrat AuthResponse

Les endpoints **register** et **login** renvoient tous les deux le même objet :

```json
{
  "token": "...",
  "role": "ETUDIANT"
}
```

## Description des champs

| Champ | Description |
|--------|-------------|
| token | JWT à utiliser pour accéder aux routes protégées |
| role | rôle de l'utilisateur connecté |

---

# Génération du JWT

Le token est généré par `JwtService`.

Les informations actuellement placées dans le JWT sont :

- le rôle (`role`)
- le nom (`nom`)
- le sujet (`subject`) qui correspond à l'email de l'utilisateur.

Code observé :

```java
claims.put("role", utilisateur.getRole().name());
claims.put("nom", utilisateur.getNom());

return buildToken(claims, utilisateur.getEmail());
```

Le sujet du token est donc l'adresse email.

---

# Durée de validité

Le JWT est valide pendant :

```java
1000 * 60 * 60 * 24
```

soit **24 heures**.

---

# Vérification du JWT

À chaque requête protégée :

1. le backend lit l'en-tête Authorization ;
2. vérifie qu'il commence par `Bearer `;
3. extrait le JWT ;
4. récupère l'email contenu dans le token ;
5. charge l'utilisateur ;
6. vérifie que le token est valide ;
7. authentifie l'utilisateur dans le contexte Spring Security.

Code principal :

```java
final String authHeader = request.getHeader("Authorization");

if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    filterChain.doFilter(request, response);
    return;
}

jwt = authHeader.substring(7);
```

Puis :

```java
userEmail = jwtService.extractUsername(jwt);
```

Puis :

```java
jwtService.isTokenValid(jwt, userDetails);
```

---

# Transmission du token

Toutes les routes protégées devront recevoir le JWT dans l'en-tête HTTP :

```http
Authorization: Bearer <token>
```

Le backend recherche précisément cet en-tête.

---

# Routes publiques

Les routes suivantes ne nécessitent aucun JWT :

- `/api/auth/**`
- `/swagger-ui/**`
- `/swagger-ui.html`
- `/v3/api-docs/**`
- `GET /api/etudiants/**`
- `GET /api/cours/**`

---

# Routes protégées

Toutes les autres routes nécessitent une authentification :

```java
.anyRequest().authenticated()
```

---

# Fonctionnement du parcours d'authentification

## Inscription

```
Angular
        │
        ▼
POST /api/auth/register
        │
        ▼
création de l'utilisateur
        │
        ▼
génération du JWT
        │
        ▼
AuthResponse
(token + role)
```

---

## Connexion

```
Angular
        │
        ▼
POST /api/auth/login
        │
        ▼
vérification des identifiants
        │
        ▼
génération du JWT
        │
        ▼
AuthResponse
(token + role)
```

---

# Conséquences pour le frontend (G7)

À partir du backend actuel, le frontend devra :

- envoyer les données de connexion conformément au `LoginRequest` ;
- envoyer les données d'inscription conformément au `RegisterRequest` ;
- récupérer le `token` et le `role` renvoyés par `AuthResponse` ;
- conserver le JWT (la stratégie de stockage reste à définir par l'équipe) ;
- ajouter automatiquement l'en-tête :

```http
Authorization: Bearer <token>
```

sur toutes les requêtes vers les routes protégées.

---

# Interfaces TypeScript à préparer (éventuellement, d'après la tâche)

```ts
export interface RegisterRequest {
  email: string;
  motDePasse: string;
  nom: string;
}
```

```ts
export interface LoginRequest {
  email: string;
  motDePasse: string;
}
```

```ts
export interface AuthResponse {
  token: string;
  role: string;
}
```

---

# Points qu'on devra implémenter lors du jour 8

- Création d'un `AuthService`.
- Appel de `/api/auth/register`.
- Appel de `/api/auth/login`.
- Stockage du JWT.
- Ajout automatique du header `Authorization: Bearer <token>`.
- Mise en place d'un `AuthGuard (canActivate)` pour protéger les routes Angular.
- Gestion des erreurs d'authentification renvoyées par le backend.