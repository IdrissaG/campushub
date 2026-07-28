# API Etudiants (G2)

CRUD en memoire pour l'entite `Etudiant`. Aucune base de donnees a ce stade
(voir G4 pour la preparation Postgres) : les donnees sont stockees dans un
`Map<Long, Etudiant>` et sont perdues a chaque redemarrage de l'application.

## Modele

```java
public record Etudiant(Long id, String nom, int age, String filiere) {}
```

Le champ `id` est attribue automatiquement a la creation (non fourni par le
client). Aucun DTO n'est utilise a ce jour (prevu jour 3).

## Endpoints

| Methode | URL | Code succes | Code erreur |
|---|---|---|---|
| GET | `/api/etudiants` | 200 | - |
| GET | `/api/etudiants/{id}` | 200 | 404 si id inconnu |
| POST | `/api/etudiants` | 201 | - |
| PUT | `/api/etudiants/{id}` | 200 | 404 si id inconnu |
| DELETE | `/api/etudiants/{id}` | 204 | 404 si id inconnu |

## Exemple de corps de requete (POST / PUT)

```json
{
  "nom": "Awa Diop",
  "age": 21,
  "filiere": "Informatique"
}
```

## Tester

- Collection Postman : `postman/CampusHub-Etudiants.postman_collection.json`
  (variable `baseUrl` = `http://localhost:8080`)
- Tests automatises : `EtudiantServiceTest` (couche service) et
  `EtudiantControllerTest` (couche HTTP, via `@WebMvcTest` + MockMvc)

## A savoir pour les autres groupes

Le modele `Etudiant` (dans `campushub-model`) a recu un champ `id`
aujourd'hui, necessaire pour les endpoints `/{id}`. L'ancien constructeur a
3 arguments (`nom`, `age`, `filiere`) reste disponible pour compatibilite.
