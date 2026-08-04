\# Notes de conception — Module Auth (Jour 2)



\## Rôles identifiés

\- `ETUDIANT` : accès en lecture à ses propres données

\- `ADMIN`    : accès complet à toutes les ressources



\## Endpoints qui seront protégés

| Endpoint                  | Méthode | Rôle requis       |

|---------------------------|---------|-------------------|

| /api/etudiants            | GET     | ETUDIANT, ADMIN   |

| /api/etudiants/{id}       | GET     | ETUDIANT, ADMIN   |

| /api/etudiants            | POST    | ADMIN             |

| /api/etudiants/{id}       | PUT     | ADMIN             |

| /api/etudiants/{id}       | DELETE  | ADMIN             |

| /api/cours                | GET     | ETUDIANT, ADMIN   |

| /api/cours/{id}           | GET     | ETUDIANT, ADMIN   |

| /api/cours                | POST    | ADMIN             |

| /api/cours/{id}           | PUT     | ADMIN             |

| /api/cours/{id}           | DELETE  | ADMIN             |

| /api/auth/login           | POST    | PUBLIC            |

| /api/auth/register        | POST    | PUBLIC            |



\## Décisions à prendre demain (Jour 3)

\- \[ ] Durée de validité du token JWT

\- \[ ] Stratégie de refresh token (oui/non ?)

\- \[ ] Stocker les utilisateurs en mémoire (jour 3) ou en base (jour 4) ?

\- \[ ] Quel champ sert d'identifiant : email ou matricule ?



\## Structure des packages prévue

gestion.campushub.auth.config   → SecurityConfig, CorsConfig

gestion.campushub.auth.filter   → JwtAuthenticationFilter

gestion.campushub.auth.service  → JwtService, UserDetailsService



