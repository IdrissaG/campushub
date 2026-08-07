# 📋 CampusHub — Tâches du jour (Jour 7/10)

**Contexte du jour :** routing complet et formulaires réactifs côté Angular. Le CRUD doit devenir démontrable entièrement depuis le navigateur, sans passer par Postman.

⚠️ **G8 absent aujourd'hui** — ses tâches prévues (tests e2e légers) sont reportées. Voir la note en bas de document pour la répartition de la charge.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** revue de cohérence UX/architecture entre G6 et G7.

### Étapes détaillées

1. **Observer le travail de G6 (composants d'affichage) et G7 (formulaires)** en fin de journée, et vérifier que les deux respectent un style cohérent : nommage des classes CSS, structure des dossiers de composants, conventions de nommage TypeScript.

2. **Vérifier qu'il n'y a pas de duplication** : si G7 a besoin d'un composant déjà créé par G6 (ex. un composant de carte, de bouton), s'assurer qu'il est réutilisé plutôt que recréé.

3. **Faire un point rapide sur l'architecture globale du frontend** : structure des dossiers (`components/`, `services/`, `models/`), cohérence avec les conventions Angular standards.

4. **Comme G8 est absent aujourd'hui**, se rendre disponible en support supplémentaire pour G6/G7 si des questions techniques urgentes se posent — ce n'est pas votre rôle habituel, mais la charge doit être répartie.

### Livrable attendu ce soir
- Note de revue de cohérence entre G6 et G7 (points forts, points à corriger).
- Aucune duplication de composants signalée.

---

## 🟢 G2 – API Étudiants

**Mission du jour :** mode support — vérifier que le contrat POST/PUT correspond exactement à ce que G7 implémente aujourd'hui.

### Étapes détaillées

1. **Rester disponible en continu** pour G7, qui va brancher son formulaire de création/édition sur votre API aujourd'hui.

2. **Revalider le contrat `EtudiantRequest`** avec G7 en tout début de journée — noms de champs, types attendus, contraintes de validation (`@NotBlank`, `@Email`, `@Min`).

3. **Si G7 remonte un écart**, corriger rapidement côté API et republier la documentation Swagger à jour.

4. **Tester vous-même le cycle complet** une fois que G7 a une première version fonctionnelle : créer un étudiant depuis le formulaire Angular et vérifier qu'il apparaît bien correctement en base et dans Swagger.

### Livrable attendu ce soir
- Contrat API stable, aucun écart persistant avec ce que G7 a implémenté.

---

## 🟡 G3 – API Cours & Inscriptions

**Mission du jour :** mode support, disponibilité si G7 étend son travail aux formulaires Cours.

### Étapes détaillées

1. **Rester disponible** si G7 souhaite aussi implémenter un formulaire pour Cours en plus d'Étudiant aujourd'hui (selon l'avancement, cela peut être reporté).

2. **Profiter du temps disponible** pour renforcer la couverture de tests ou améliorer la documentation Swagger existante.

### Livrable attendu ce soir
- Aucun blocage signalé côté Cours.

---

## 🟠 G4 – Persistance

**Mission du jour :** support et consolidation.

### Étapes détaillées

1. **Vérifier que les données de démonstration** créées hier restent cohérentes après les tests répétés de création/modification/suppression que G6 et G7 vont effectuer aujourd'hui via le frontend.

2. **Si la base devient "sale"** après de nombreux tests manuels, proposer un script de réinitialisation simple pour repartir sur une base propre :
```sql
-- reset-demo.sql (à exécuter manuellement si besoin, jamais en CI/prod)
TRUNCATE TABLE inscription, etudiant, cours RESTART IDENTITY CASCADE;
```

3. **Rester disponible** pour toute question de persistance.

### Livrable attendu ce soir
- Base de données stable, script de réinitialisation disponible si besoin.

---

## 🔴 G5 – Sécurité Backend

**Mission du jour :** support et préparation finale avant l'implémentation Angular du jour 8.

### Étapes détaillées

1. **Vérifier que les comptes de test** créés hier (ADMIN + ETUDIANT) fonctionnent toujours correctement pour les tests manuels de G7.

2. **Répondre aux questions anticipées** de G7 sur le fonctionnement du token JWT, en prévision de l'implémentation de demain (jour 8).

3. **Optionnel** : préparer un exemple concret de requête `curl` ou Postman montrant le cycle login → récupération du token → appel protégé, pour servir de référence claire à G7 demain.

### Livrable attendu ce soir
- Comptes de test validés et fonctionnels.
- G7 prêt à attaquer l'implémentation JWT demain sans zone d'ombre.

---

## 🟣 G6 – Frontend Core

**Mission du jour :** support sur les composants partagés réutilisés dans les formulaires de G7.

### Étapes détaillées

1. **Identifier avec G7** les composants d'affichage qui peuvent être réutilisés dans les formulaires (ex. un composant de carte pour prévisualiser un étudiant avant validation, un composant de message d'erreur générique).

2. **Finaliser et documenter** les composants déjà créés (`EtudiantListComponent`, `EtudiantCardComponent`) pour qu'ils soient facilement réutilisables — props claires, pas de logique métier cachée à l'intérieur.

3. **Corriger les éventuels bugs d'affichage** remontés lors des tests intensifs de G7 sur le CRUD complet aujourd'hui.

4. **Optionnel si en avance** : commencer à réfléchir à l'affichage conditionnel selon le rôle (boutons visibles/masqués), qui sera implémenté au jour 8, pour préparer le terrain.

### Livrable attendu ce soir
- Composants stables et bien documentés, prêts à être réutilisés par G7.
- Aucun bug d'affichage bloquant signalé.

---

## ⚪ G7 – Frontend Formulaires & Auth (le pilote du jour)

**Mission du jour :** routing complet + CRUD entièrement fonctionnel depuis le navigateur.

### Étapes détaillées

**1. Finaliser le routing complet** :
```typescript
export const routes: Routes = [
  { path: '', redirectTo: 'etudiants', pathMatch: 'full' },
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiants/:id', component: EtudiantDetailComponent },
  { path: 'etudiants/:id/modifier', component: EtudiantFormComponent },
  { path: 'etudiants/nouveau', component: EtudiantFormComponent },
  { path: '**', component: NotFoundComponent },
];
```

**2. Créer la page 404** :
```typescript
@Component({
  selector: 'app-not-found',
  standalone: true,
  template: `
    <div class="not-found">
      <h2>404 — Page introuvable</h2>
      <a routerLink="/etudiants">Retour à la liste</a>
    </div>
  `
})
export class NotFoundComponent {}
```

**3. Finaliser le formulaire réactif** (création ET édition dans le même composant) :
```typescript
export class EtudiantFormComponent implements OnInit {
  etudiantForm = this.fb.group({
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    age: ['', [Validators.required, Validators.min(15)]],
    filiere: [''],
  });

  etudiantId: number | null = null;

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.etudiantId = +id;
      this.etudiantService.getById(this.etudiantId).subscribe(etudiant => {
        this.etudiantForm.patchValue(etudiant);
      });
    }
  }

  onSubmit() {
    if (this.etudiantForm.invalid) return;

    const operation = this.etudiantId
      ? this.etudiantService.update(this.etudiantId, this.etudiantForm.value)
      : this.etudiantService.create(this.etudiantForm.value);

    operation.subscribe({
      next: () => this.router.navigate(['/etudiants']),
      error: (err) => this.erreurServeur.set(err.error.erreurs)
    });
  }
}
```

**4. Implémenter la suppression** depuis la liste (en coordination avec G6) :
```typescript
onSupprimer(id: number) {
  if (confirm('Confirmer la suppression ?')) {
    this.etudiantService.delete(id).subscribe(() => this.chargerEtudiants());
  }
}
```

**5. Ajouter les méthodes manquantes au service** (`getById`, `update`, `delete`) :
```typescript
getById(id: number): Observable<Etudiant> {
  return this.http.get<Etudiant>(`${this.apiUrl}/${id}`);
}
update(id: number, etudiant: EtudiantRequest): Observable<EtudiantResponse> {
  return this.http.put<EtudiantResponse>(`${this.apiUrl}/${id}`, etudiant);
}
delete(id: number): Observable<void> {
  return this.http.delete<void>(`${this.apiUrl}/${id}`);
}
```

**6. Tester le cycle complet démontrable** : créer un étudiant → le voir dans la liste → cliquer pour l'éditer → modifier → sauvegarder → le supprimer. Tout ceci **sans jamais ouvrir Postman**.

**7. Vérifier la gestion des erreurs serveur** dans le formulaire (email déjà utilisé, âge invalide, etc.) — les messages du `@RestControllerAdvice` de G2 doivent s'afficher clairement.

### Livrable attendu ce soir
- CRUD complet fonctionnel depuis le navigateur : créer, modifier, supprimer un étudiant sans toucher à Postman.
- Routing complet avec page 404.
- PR mergée.

---

## ⚫ G8 – DevOps & Cloud

**Absent aujourd'hui.** Ses tâches prévues (premiers tests e2e légers si le temps le permet) sont reportées — ce n'était qu'un objectif "bonus" du jour 7, pas un livrable bloquant pour la suite du planning.

**Répartition de la charge en son absence :**
- G1 reste disponible en support supplémentaire pour G6/G7 (voir ci-dessus).
- Le pipeline CI reste tel qu'il était hier (build backend + build frontend) — aucune action requise aujourd'hui de la part des autres groupes sur ce point.
- **À rattraper demain (jour 8)** : G8 devra vérifier que le pipeline CI n'a pas de régression suite aux ajouts de routing/formulaires de G7 (le build Angular doit toujours passer).

---

## 🔗 Dépendances à surveiller au stand-up de ce soir

- G7 dépend du contrat API stable de G2 (POST/PUT) → vérifier en priorité au matin.
- G7 dépend des composants partagés de G6 → coordination directe recommandée dès le début de journée.
- Sans G8 aujourd'hui, personne ne surveille activement le pipeline CI — s'assurer qu'aucune PR n'est mergée avec un build cassé (vérification manuelle par le relecteur croisé).

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire par un membre d'un autre groupe, `JOURNAL.md` à jour ce soir.
