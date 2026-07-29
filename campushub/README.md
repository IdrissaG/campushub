# CampusHub - API Étudiants (G2)

API REST CRUD pour la gestion des étudiants de CampusHub.

## Architecture

L'application suit une architecture en 3 couches :

- **Controller** (`EtudiantController`) : endpoints REST
- **Service** (`EtudiantsService`) : logique métier
- **Repository** (`EtudiantRepository`) : stockage en mémoire

## Endpoints

### Base URL
```
http://localhost:8080/api/etudiants
```

### 1. Récupérer tous les étudiants
```
GET /api/etudiants
```
**Response :** 200 OK
```json
[
  {
    "id": 1,
    "nom": "Diop",
    "prenom": "Awa",
    "filiere": "Informatique",
    "age": 21
  },
  {
    "id": 2,
    "nom": "Fall",
    "prenom": "Moussa",
    "filiere": "Gestion",
    "age": 23
  }
]
```

### 2. Récupérer un étudiant par ID
```
GET /api/etudiants/{id}
```
**Response :** 200 OK ou 404 Not Found

### 3. Créer un nouvel étudiant
```
POST /api/etudiants
Content-Type: application/json

{
  "nom": "Gueye",
  "prenom": "Fatima",
  "filiere": "Économie",
  "age": 22
}
```
**Response :** 201 Created
```json
{
  "id": 3,
  "nom": "Gueye",
  "prenom": "Fatima",
  "filiere": "Économie",
  "age": 22
}
```

### 4. Modifier un étudiant
```
PUT /api/etudiants/{id}
Content-Type: application/json

{
  "nom": "Diop",
  "prenom": "Awa",
  "filiere": "Informatique Appliquée",
  "age": 22
}
```
**Response :** 200 OK ou 404 Not Found

### 5. Supprimer un étudiant
```
DELETE /api/etudiants/{id}
```
**Response :** 204 No Content ou 404 Not Found

## Démarrage

### Prérequis
- Java 17+
- Maven 3.8+

### Lancer l'application
```bash
cd campushub
mvn spring-boot:run
```

L'application sera disponible sur `http://localhost:8080`

## Tests avec Postman

Une collection Postman complète est disponible :
```
campushub/postman/CampusHub_API_Etudiants.postman_collection.json
```

Importez ce fichier dans Postman et testez tous les endpoints.

## Modèle de données

```java
public record Etudiant(
    Long id,
    String nom,
    String prenom,
    String filiere,
    int age
)
```

## Points importants

✅ Injection par constructeur uniquement (pas de `@Autowired`)  
✅ Codes HTTP corrects pour chaque endpoint  
✅ Validation des données avec `@Valid`  
✅ Stockage en mémoire (pas de DB pour le moment)  
✅ Données par défaut à la création du repository
