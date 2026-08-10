# CampusHub — Plan de rattrapage G8

## Objectif

Organiser de manière optimale les tâches de G8 pour rattraper ce qui a été manqué les jours 4, 5 et 6 sans perdre de temps sur des tâches indépendantes ou mal priorisées.

## Contexte

Les tâches de G8 manquées concernent trois blocs complémentaires :
- la mise en place de l’infrastructure CI pour le backend,
- la sécurisation du pipeline avec les tests de sécurité et la gestion des secrets,
- l’intégration du build frontend et la préparation cloud.

Le meilleur plan consiste à travailler en ordre de dépendance :
1. stabiliser le pipeline backend,
2. ajouter les vérifications de sécurité,
3. intégrer le build frontend,
4. documenter la partie cloud et valider le tout.

---

## 1. Tâches à reprendre depuis les jours 4, 5 et 6

### Jour 4 — CI backend et base de données
- Ajouter un service PostgreSQL au pipeline GitHub Actions.
- Vérifier que les tests `@DataJpaTest` de G2/G3/G4/G5 passent bien en CI avec une base PostgreSQL éphémère.
- Coordonner avec G4 sur la bonne application des migrations Flyway.
- Documenter dans `NOTES-CLOUD.md` la base de données managée ciblée et son intégration avec l’environnement Spring.

### Jour 5 — Sécurité et secrets
- Vérifier les tests MockMvc de sécurité dans le pipeline.
- Ajouter une étape explicite qui échoue si un test de sécurité est absent ou skipped.
- Externaliser `jwt.secret` dans une variable d’environnement.
- Documenter la procédure de gestion des secrets dans `NOTES-CLOUD.md`.

### Jour 6 — Frontend et déploiement
- Ajouter un job de build frontend dans GitHub Actions.
- Vérifier que `npm run build` fonctionne proprement avant automatisation.
- Documenter la stratégie d’hébergement frontend dans `NOTES-CLOUD.md`.

---

## 2. Organisation optimale recommandée

### Bloc A — CI backend et validation de la base (priorité absolue)
C’est le socle de tout le rattrapage. Il faut le faire en premier, car les autres tâches reposent sur un workflow CI stable.

Tâches :
- vérifier le workflow GitHub Actions actuel,
- ajouter le service PostgreSQL,
- exécuter les tests backend ciblés pour valider la base en CI,
- confirmer que les migrations Flyway s’appliquent proprement.


---

### Bloc B — Sécurité et secrets (à faire juste après le Bloc A)
Une fois que le backend tourne correctement dans le pipeline, il faut verrouiller la partie sécurité.

Tâches :
- ajouter la vérification des tests de sécurité,
- s’assurer que les tests 401/403 sont bien pris en compte,
- externaliser `JWT_SECRET` via les variables d’environnement,
- documenter la procédure dans `NOTES-CLOUD.md`.

---

### Bloc C — Build frontend et préparation cloud (à faire en parallèle du Bloc B si possible)
Ces tâches sont indépendantes du travail backend, mais elles doivent être faites après la validation du workflow de base pour éviter de casser l’intégration globale.

Tâches :
- ajouter le job de build frontend,
- vérifier `npm run build` localement puis en CI,
- documenter la stratégie d’hébergement frontend,
- noter la différence entre l’hébergement backend et frontend si nécessaire.

---

## 3. Séquence de travail recommandée

1. Ouvrir et lire le workflow GitHub Actions actuel.
2. Ajouter la configuration PostgreSQL pour le job backend.
3. Valider que les tests `@DataJpaTest` passent bien dans ce contexte.
4. Ajouter la vérification explicite des tests de sécurité.
5. Externaliser le secret JWT et mettre à jour la documentation cloud.
6. Ajouter le job de build frontend.
7. Vérifier le build Angular localement et dans CI.
8. Rédiger la version finale de `NOTES-CLOUD.md` avec les décisions cloud prises.

---


## 5. Livrables attendus

- Pipeline CI backend validé avec PostgreSQL.
- Vérification des tests de sécurité intégrée dans le workflow.
- Build frontend ajouté au pipeline CI.
- `NOTES-CLOUD.md` complété avec :
  - la base de données managée ciblée,
  - la stratégie de gestion des secrets,
  - la stratégie d’hébergement frontend.

---
