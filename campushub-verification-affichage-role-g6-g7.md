# Vérification de l'affichage conditionnel par rôle — G6 / G7

## Objectif
Vérifier que l'affichage conditionnel par rôle est cohérent entre le frontend core (G6) et les guards/formulaires/auth (G7), en utilisant la même source de vérité pour le rôle de l'utilisateur connecté.

## Statut
- [ ] En attente du travail de G6
- [ ] En attente du travail de G7
- [ ] Prêt à être rempli une fois les implémentations disponibles

## Source de vérité du rôle
- Source retenue :
- Éléments à vérifier :

## Comportements attendus
### Cas 1 — Utilisateur non connecté
- Affichage attendu :
- Comportements à vérifier :

### Cas 2 — Utilisateur ETUDIANT
- Affichage attendu :
- Comportements à vérifier :

### Cas 3 — Utilisateur ADMIN
- Affichage attendu :
- Comportements à vérifier :

## Points de validation
- [ ] Les boutons d'action sont masqués pour un utilisateur non autorisé.
- [ ] Les formulaires sensibles ne sont pas visibles ou accessibles sans autorisation.
- [ ] L'affichage et les guards utilisent bien la même information de rôle.
- [ ] Aucun doublon de logique métier n'est observé côté G6/G7.

## Résultats à renseigner
- Date de vérification :
- Branche / commit concerné :
- Résultat global :
- Problèmes observés :
- Actions correctives à prévoir :

## Notes complémentaires
- 
