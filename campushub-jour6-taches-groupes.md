# 📋 CampusHub — Tâches du jour (Jour 6/10)

**Contexte du jour :** début de la semaine 2 — le focus passe au frontend Angular. L'API backend étant complète et sécurisée depuis hier, G6 attaque l'affichage des données réelles pendant que les groupes backend passent en mode support.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** support et veille — pas de livrable technique nouveau, mais un rôle de coordination important au démarrage de la semaine 2.

### Étapes détaillées

1. **Faire un point rapide en début de matinée** avec G6 et G7 : s'assurer qu'ils ont bien accès à l'API complète (URL, ports, comment lancer le backend localement).

2. **Vérifier que la documentation Swagger** est à jour et accessible facilement pour G6/G7 tout au long de la semaine (partager le lien, vérifier qu'il n'y a pas de régression depuis le merge d'hier).

3. **Rester disponible** pour arbitrer d'éventuels écarts entre ce que G6/G7 attendent de l'API et ce que G2/G3 ont réellement livré.

4. **Optionnel** : commencer à esquisser la structure du futur README global (jour 10), en listant dès maintenant les sections nécessaires (installation, architecture, lien Swagger).

### Livrable attendu ce soir
- Confirmation que G6/G7 ont un accès fluide à l'API et à Swagger.
- Aucun blocage d'intégration signalé.

---

## 🟢 G2 – API Étudiants

**Mission du jour :** mode support — corriger les éventuels écarts de contrat révélés par le frontend.

### Étapes détaillées

1. **Rester disponible en continu** aujourd'hui pour G6, qui va consommer votre API pour la première fois en conditions réelles (affichage de vraies listes, avec pagination).

2. **Vérifier la configuration CORS** une dernière fois — c'est le point de friction le plus probable :
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*");
    }
}
```

3. **Si G6 remonte un écart** entre ce que Swagger documente et ce que l'API renvoie réellement (ex. un champ manquant dans la réponse paginée), corriger rapidement et redocumenter.

4. **Profiter du temps disponible** pour compléter d'éventuels tests manquants ou améliorer la couverture de tests du module.

### Livrable attendu ce soir
- Aucune régression signalée par G6.
- CORS validé en conditions réelles avec le vrai frontend Angular.

---

## 🟡 G3 – API Cours & Inscriptions

**Mission du jour :** mode support, identique à G2.

### Étapes détaillées

1. **Rester disponible** si G6 a besoin de consommer aussi l'API Cours en parallèle (selon l'organisation retenue, G6 peut se concentrer uniquement sur Étudiant aujourd'hui et Cours viendra plus tard).

2. **Vérifier le CORS** sur vos endpoints également.

3. **Compléter les tests** ou la documentation Swagger si des lacunes ont été identifiées lors du merge collectif d'hier.

### Livrable attendu ce soir
- API Cours stable et prête à être consommée sans blocage.

---

## 🟠 G4 – Persistance

**Mission du jour :** journée calme — consolidation et disponibilité en support.

### Étapes détaillées

1. **Vérifier la stabilité** de la base de données après le merge collectif d'hier — relancer les migrations sur une base fraîche pour confirmer qu'il n'y a pas de régression.

2. **Alimenter la base avec des données de démonstration réalistes**, utiles pour que G6/G7 travaillent avec des données qui ressemblent à la réalité plutôt que 2-3 lignes de test :
```sql
-- V5__donnees_demo.sql (optionnel, à ne pas utiliser en prod)
INSERT INTO etudiant (nom, prenom, email, age, filiere) VALUES
('Diop', 'Awa', 'awa.diop@campushub.sn', 22, 'Informatique'),
('Fall', 'Moussa', 'moussa.fall@campushub.sn', 24, 'Gestion'),
('Ba', 'Fatou', 'fatou.ba@campushub.sn', 20, 'Informatique');
```
⚠️ Cette migration de données de démo doit être clairement identifiée comme non destinée à la production (à exclure du profil `prod` plus tard).

3. **Support disponible** pour toute question de persistance venant des autres groupes.

### Livrable attendu ce soir
- Base de données stable avec des données de démonstration réalistes pour faciliter les tests visuels de G6/G7.

---

## 🔴 G5 – Sécurité Backend

**Mission du jour :** support et documentation — préparer le terrain pour l'implémentation Angular du jour 8.

### Étapes détaillées

1. **Rédiger une documentation claire du contrat JWT** à destination de G7 (qui l'implémentera au jour 8), avec des exemples concrets de requêtes/réponses :
```markdown
## Contrat d'authentification

### POST /api/auth/login
Requête : { "email": "...", "motDePasse": "..." }
Réponse (200) : { "token": "eyJhbGc...", "role": "ADMIN" }
Réponse (401) : { "timestamp": "...", "status": 401, "erreurs": ["Identifiants invalides"] }

### Utilisation du token
Header à ajouter sur les requêtes protégées :
Authorization: Bearer eyJhbGc...
```

2. **Créer quelques comptes de test** (via Swagger ou un script SQL) avec les deux rôles, pour que G7 puisse tester le login sans repartir de zéro au jour 8 :
   - Un compte `ADMIN`
   - Un compte `ETUDIANT`

3. **Rester disponible** pour répondre aux questions anticipées de G6/G7 sur le fonctionnement du token.

### Livrable attendu ce soir
- Documentation du contrat JWT partagée avec G7.
- Comptes de test créés (ADMIN + ETUDIANT), identifiants communiqués à G7.

---

## 🟣 G6 – Frontend Core (le pilote du jour)

**Mission du jour :** finaliser les composants d'affichage contre l'API réelle et complète.

### Étapes détaillées

**1. Finaliser `EtudiantListComponent`** avec la nouvelle syntaxe de contrôle de flux :
```typescript
@Component({
  selector: 'app-etudiant-list',
  standalone: true,
  imports: [EtudiantCardComponent],
  template: `
    @if (loading()) {
      <p>Chargement des étudiants...</p>
    } @else if (erreur()) {
      <p class="erreur">{{ erreur() }}</p>
    } @else if (etudiants().length === 0) {
      <p>Aucun étudiant trouvé.</p>
    } @else {
      @for (etudiant of etudiants(); track etudiant.id) {
        <app-etudiant-card [etudiant]="etudiant" (supprimer)="onSupprimer($event)" />
      }
    }
  `
})
export class EtudiantListComponent {
  etudiants = signal<Etudiant[]>([]);
  loading = signal(true);
  erreur = signal<string | null>(null);
}
```

**2. Finaliser `EtudiantCardComponent`** avec `@Input`/`@Output` :
```typescript
@Component({
  selector: 'app-etudiant-card',
  standalone: true,
  template: `
    <div class="card">
      <h3>{{ etudiant().prenom }} {{ etudiant().nom }}</h3>
      <p>{{ etudiant().filiere }} — {{ etudiant().age }} ans</p>
      <button (click)="supprimer.emit(etudiant().id)">Supprimer</button>
    </div>
  `
})
export class EtudiantCardComponent {
  etudiant = input.required<Etudiant>();
  supprimer = output<number>();
}
```

**3. Vérifier que l'affichage fonctionne** avec les données réelles et paginées créées par G4 aujourd'hui — tester avec plusieurs pages si le volume de données le permet.

**4. Ajouter la pagination côté Angular** (boutons précédent/suivant simples pour l'instant) :
```typescript
pageActuelle = signal(0);

pagePrecedente() {
  if (this.pageActuelle() > 0) {
    this.pageActuelle.update(p => p - 1);
    this.chargerEtudiants();
  }
}

pageSuivante() {
  this.pageActuelle.update(p => p + 1);
  this.chargerEtudiants();
}
```

**5. Tester rigoureusement les 3 états** : chargement, erreur (couper temporairement le backend), succès avec données.

### Livrable attendu ce soir
- Liste d'étudiants réels, paginée, affichée proprement dans le navigateur.
- Les 3 états (chargement/erreur/succès) gérés et testés.
- PR mergée.

---

## ⚪ G7 – Frontend Formulaires & Auth

**Mission du jour :** support à G6 sur les composants partagés, préparation pour la suite.

### Étapes détaillées

1. **Identifier avec G6** les composants qui seront réutilisés dans vos futurs formulaires (jour 7) — par exemple un composant de champ de saisie générique, un composant de message d'erreur.

2. **Créer ces composants partagés** dès aujourd'hui si l'occasion se présente, pour éviter que chaque groupe réinvente sa propre version :
```typescript
@Component({
  selector: 'app-champ-erreur',
  standalone: true,
  template: `
    @if (message()) {
      <span class="erreur-champ">{{ message() }}</span>
    }
  `
})
export class ChampErreurComponent {
  message = input<string | null>(null);
}
```

3. **Lire la documentation du contrat JWT** livrée par G5 aujourd'hui, pour bien préparer le travail du jour 8.

4. **Optionnel si en avance** : commencer à esquisser la structure des routes avec leurs futurs guards (sans implémenter la logique de protection encore) :
```typescript
export const routes: Routes = [
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiants/nouveau', component: EtudiantFormComponent /* canActivate: [authGuard] à venir J8 */ },
];
```

### Livrable attendu ce soir
- Composants partagés créés en coordination avec G6 (si applicable).
- Compréhension claire du contrat JWT, prêt pour le jour 8.

---

## ⚫ G8 – DevOps & Cloud

**Mission du jour :** ajouter le build frontend au pipeline CI.

### Étapes détaillées

1. **Ajouter un job de build frontend** au pipeline GitHub Actions, en parallèle du job backend existant :
```yaml
jobs:
  build-backend:
    # ... (inchangé)

  build-frontend:
    runs-on: ubuntu-latest
    defaults:
      run:
        working-directory: ./campushub-front
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '20'
      - run: npm ci
      - run: npm run build
```

2. **Vérifier que le job échoue bien** si le build Angular casse (tester volontairement en introduisant une erreur TypeScript sur une branche de test).

3. **Avancer sur le volet cloud** : commencer à réfléchir à la stratégie d'hébergement du frontend (souvent différente du backend — ex. hébergement statique sur Netlify/Vercel, ou dans le même conteneur Nginx que prévu au jour 9). Noter la décision dans `NOTES-CLOUD.md`.

4. **Vérifier avec G6** que le build Angular fonctionne bien en local avant de l'automatiser en CI (`npm run build` doit réussir sans erreur).

### Livrable attendu ce soir
- Pipeline CI avec build backend **et** frontend, tous deux fonctionnels et vérifiés.
- Stratégie d'hébergement frontend documentée dans `NOTES-CLOUD.md`.

---

## 🔗 Dépendances à surveiller au stand-up de ce soir

- G6 dépend de la stabilité de l'API de G2 (CORS, format de pagination) → priorité absolue en cas de blocage.
- G7 dépend de la documentation JWT de G5, à valider aujourd'hui pour ne pas être bloqué au jour 8.
- G8 dépend du bon fonctionnement du build Angular local de G6 avant de l'intégrer en CI.

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire par un membre d'un autre groupe, `JOURNAL.md` à jour ce soir.
