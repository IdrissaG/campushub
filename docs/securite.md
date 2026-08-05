#  Matrice des Droits et Sécurité (CampusHub)

Ce document décrit la configuration des accès et des permissions pour l'ensemble des endpoints de l'API CampusHub. 
L'implémentation repose sur **Spring Security** et des tokens **JWT**.

##  Rôles disponibles
- **Public** : Accès sans authentification (pas de token requis).
- **ETUDIANT** : Utilisateur authentifié avec le rôle étudiant.
- **ADMIN** : Administrateur du système.

---

##  Module Étudiants (G2)

| Endpoint | Méthode | Niveau d'accès | Description |
|---|---|---|---|
| `/api/etudiants` | `GET` | **Public** | Consulter la liste des étudiants |
| `/api/etudiants/{id}` | `GET` | **Public** | Consulter le profil d'un étudiant |
| `/api/etudiants` | `POST` | **ADMIN** | Ajouter un nouvel étudiant |
| `/api/etudiants/{id}` | `PUT` | **ADMIN** | Modifier un étudiant existant |
| `/api/etudiants/{id}` | `DELETE` | **ADMIN** | Supprimer un étudiant |

---

##  Module Cours & Inscriptions (G3)

###  Cours
| Endpoint | Méthode | Niveau d'accès | Description |
|---|---|---|---|
| `/api/cours` | `GET` | **Public** | Consulter la liste des cours |
| `/api/cours/{id}` | `GET` | **Public** | Consulter les détails d'un cours |
| `/api/cours` | `POST` | **ADMIN** | Créer un nouveau cours |
| `/api/cours/{code}` | `PUT` | **ADMIN** | Modifier un cours existant |
| `/api/cours/{code}` | `DELETE` | **ADMIN** | Supprimer un cours |

###  Inscriptions
*Note : Conformément aux discussions G1/G3, la création d'inscription est réservée aux administrateurs pour assurer la cohérence des données, à moins qu'une auto-inscription ne soit explicitement demandée.*

| Endpoint | Méthode | Niveau d'accès | Description |
|---|---|---|---|
| `/api/inscriptions` | `GET` | **ADMIN** | Voir toutes les inscriptions |
| `/api/inscriptions` | `POST` | **ADMIN** | Inscrire un étudiant à un cours |
| `/api/inscriptions/{id}` | `DELETE` | **ADMIN** | Annuler une inscription |

---

##  Module Authentification (G5)

| Endpoint | Méthode | Niveau d'accès | Description |
|---|---|---|---|
| `/api/auth/register` | `POST` | **Public** | Créer un compte |
| `/api/auth/login` | `POST` | **Public** | Se connecter et récupérer un token JWT |

---

##  Implémentation technique
- Les endpoints en écriture (`POST`, `PUT`, `DELETE`) sont protégés avec l'annotation `@PreAuthorize("hasRole('ADMIN')")`.
- Les endpoints publics sont configurés dans la méthode `filterChain` de `SecurityConfig` avec `.permitAll()`.
