# Vérification de l'affichage conditionnel par rôle — G6 / G7

## Objectif
Vérifier si l’affichage conditionnel par rôle est cohérent entre le frontend core (G6) et les formulaires/auth (G7), en s’appuyant sur une vraie source de vérité commune pour le rôle de l’utilisateur connecté.


## Source de vérité du rôle
- Source retenue identique

## Comportements attendus
### Cas 1 — Utilisateur non connecté
- Affichage attendu : pas de formulaire d’écriture visible, pas d’actions sensibles accessibles.
- Comportements observés : protection mise en place sur les routes de création/modification mais les routes restent accessibles sans authentification.

### Cas 2 — Utilisateur ETUDIANT
- Affichage attendu : lecture seule action autorisée, actions de modification/suppression masquées.
- Comportements observés :la logique de rôle est appliquée ; les boutons Modifier/Supprimer rsont masqués pour le rôle etudiant.

### Cas 3 — Utilisateur ADMIN
- Affichage attendu : actions de modification/suppression visibles.
- Comportements observés :l'admin aura en plus des droits d'un utilisateur normal celui de pouvoir modifier le profil d'un étudiant ou d'en supprimer un

## Points de validation
- [ ] Les boutons d’action sont masqués pour un utilisateur non autorisé. 
- [ ] Les formulaires sensibles ne sont pas visibles ou accessibles sans autorisation.
- [ ] L’affichage et les guards utilisent bien la même information de rôle. 
- [ ] Aucun doublon de logique métier n’est observé côté G6/G7


## Notes complémentaires
- Captures d’écran de confirmation 

![alt text](Confirmation_coherence_front.jpeg)
![alt text](Confirmation_coherence2_G7.jpeg)
![alt text](Pertinence_Acces_G6.jpeg)
![alt text](Pertinence_Acces_G6_2.jpeg)