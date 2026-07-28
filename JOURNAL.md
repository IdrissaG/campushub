# Journal

## Jour 2 - 2026-07-28 - G2 (API Etudiants)

- Mis en place l'architecture 3 couches (`EtudiantController` / `EtudiantService` / `EtudiantRepository`) avec stockage en memoire.
- Implemente et teste les 5 endpoints CRUD (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`) avec les codes HTTP attendus (200/201/204/404).
- Ajoute un champ `id` au modele `Etudiant` partage, necessaire pour les operations par identifiant (a signaler a G1/G3/G6/G7).
- Ecrit les tests JUnit 5 (service + controller via MockMvc) et une collection Postman versionnee.
- Teste manuellement via Postman en conditions reelles avant ouverture de la PR.
