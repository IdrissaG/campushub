# 📋 CampusHub — Tâches du jour (Jour 8/10)

**Contexte du jour :** authentification côté Angular — le jour le plus dense de la semaine 2. Un visiteur non connecté ne doit plus pouvoir voir les formulaires ni écrire ; un ADMIN doit pouvoir tout faire.

⚠️ **G8 absent pour la 2e journée consécutive** — voir la note de vigilance en bas de document, le pipeline CI n'est toujours pas surveillé activement.

---

## 🔵 G1 – Fondations & Modèle de données

**Mission du jour :** support renforcé, en l'absence prolongée de G8.

### Étapes détaillées

1. **Vérifier manuellement le pipeline CI** ce matin (puisque G8 n'est pas là pour le faire) : ouvrir GitHub Actions, confirmer que les derniers builds backend et frontend sont bien verts, qu'il n'y a pas de régression accumulée depuis 2 jours.

2. **Rester en support prioritaire pour G7** aujourd'hui — c'est la journée la plus critique du parcours frontend, avec le plus de dépendances croisées (G5, G6, G7 tous impliqués).

3. **Vérifier la cohérence de l'affichage conditionnel par rôle** entre G6 (listes/boutons) et G7 (guards de routes) — s'assurer que les deux groupes utilisent la même source de vérité pour connaître le rôle de l'utilisateur connecté.

4. **Documenter dans une note rapide** ce qui devra être vérifié par G8 à son retour (récapitulatif des 2 jours d'absence) pour qu'il puisse rattraper efficacement.

### Livrable attendu ce soir
- Confirmation manuelle que le pipeline CI est toujours vert.
- Note de synthèse pour le retour de G8.

---

## 🟢 G2 – API Étudiants

**Mission du jour :** support — vérifier que les codes 401 sont correctement gérés côté frontend.

### Étapes détaillées

1. **Rester disponible** pour G7, qui va aujourd'hui gérer précisément les réponses 401 de votre API (redirection vers le login).

2. **Confirmer avec G7** le format exact de la réponse 401 (structure JSON définie par G1 au jour 3), pour que l'intercepteur Angular puisse la détecter correctement.

3. **Tester vous-même** : appeler un endpoint protégé sans token depuis Swagger, vérifier que le 401 renvoyé est bien celui que G7 attend.

### Livrable attendu ce soir
- Confirmation que la gestion du 401 fonctionne de bout en bout (API → intercepteur Angular → redirection login).

---

## 🟡 G3 – API Cours & Inscriptions

**Mission du jour :** support, disponibilité si besoin.

### Étapes détaillées

1. **Rester disponible** pour toute question liée à l'affichage conditionnel par rôle sur les endpoints Cours.

2. **Profiter du temps disponible** pour finaliser les tests ou la documentation restants.

### Livrable attendu ce soir
- Aucun blocage signalé.

---

## 🟠 G4 – Persistance

**Mission du jour :** support et consolidation.

### Étapes détaillées

1. **Vérifier que les comptes de test** (ADMIN + ETUDIANT créés par G5 au jour 6) sont toujours valides et utilisables pour les tests intensifs d'aujourd'hui.

2. **Rester disponible** pour toute question de persistance.

### Livrable attendu ce soir
- Comptes de test confirmés fonctionnels.

---

## 🔴 G5 – Sécurité Backend

**Mission du jour :** support direct et prioritaire à G7 — c'est votre contrat JWT qui est implémenté aujourd'hui côté client.

### Étapes détaillées

1. **Être disponible en continu** aujourd'hui, en priorité absolue pour G7. C'est le jour où votre travail du jour 5 est enfin consommé côté frontend.

2. **Clarifier immédiatement** toute question sur :
   - Le format exact du token JWT et de son payload (le rôle est-il dans le claim `role` ?)
   - La durée de validité du token (pour que G7 gère correctement l'expiration)
   - Le comportement attendu en cas de token expiré (401 avec quel message précis ?)

3. **Aider G7 à tester** le parcours complet login → token → accès aux routes protégées, en conditions réelles avec Angular (pas juste Swagger).

4. **Si un ajustement du contrat JWT est nécessaire** suite aux retours de G7, le faire rapidement et le redocumenter dans Swagger.

### Livrable attendu ce soir
- Aucune ambiguïté restante sur le contrat JWT.
- Parcours login → accès protégé validé en conditions réelles avec G7.

---

## 🟣 G6 – Frontend Core

**Mission du jour :** affichage conditionnel des listes et boutons selon le rôle de l'utilisateur connecté.

### Étapes détaillées

1. **Adapter `EtudiantListComponent`** pour masquer les boutons d'action (modifier/supprimer) si l'utilisateur n'est pas ADMIN :
```typescript
@Component({
  selector: 'app-etudiant-list',
  template: `
    @for (etudiant of etudiants(); track etudiant.id) {
      <app-etudiant-card 
        [etudiant]="etudiant" 
        [peutModifier]="authService.estAdmin()"
        (supprimer)="onSupprimer($event)" 
      />
    }
    @if (authService.estAdmin()) {
      <a routerLink="/etudiants/nouveau">Ajouter un étudiant</a>
    }
  `
})
```

2. **Adapter `EtudiantCardComponent`** pour recevoir ce nouvel input :
```typescript
export class EtudiantCardComponent {
  etudiant = input.required<Etudiant>();
  peutModifier = input<boolean>(false);
  supprimer = output<number>();
}
```
```html
<div class="card">
  <h3>{{ etudiant().prenom }} {{ etudiant().nom }}</h3>
  @if (peutModifier()) {
    <button (click)="supprimer.emit(etudiant().id)">Supprimer</button>
    <a [routerLink]="['/etudiants', etudiant().id, 'modifier']">Modifier</a>
  }
</div>
```

3. **Se coordonner avec G7** sur la source de vérité du rôle : utiliser le même `AuthService` (créé par G7 aujourd'hui) plutôt que de dupliquer la logique.

4. **Tester avec les 2 comptes** créés par G5 : un compte ADMIN doit voir tous les boutons, un compte ETUDIANT (ou non connecté) ne doit voir aucun bouton d'action.

### Livrable attendu ce soir
- Affichage conditionnel fonctionnel et testé avec les 2 rôles.
- Coordination confirmée avec G7 sur `AuthService`.

---

## ⚪ G7 – Frontend Formulaires & Auth (le pilote du jour)

**Mission du jour :** implémentation complète de l'authentification côté Angular — la journée la plus dense du parcours.

### Étapes détaillées

**1. Créer la page de login** :
```typescript
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
      <input formControlName="email" placeholder="Email" />
      <input formControlName="motDePasse" type="password" placeholder="Mot de passe" />
      <button type="submit">Se connecter</button>
      @if (erreur()) { <p class="erreur">{{ erreur() }}</p> }
    </form>
  `
})
export class LoginComponent {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', Validators.required],
  });
  erreur = signal<string | null>(null);

  onSubmit() {
    if (this.loginForm.invalid) return;
    this.authService.login(this.loginForm.value).subscribe({
      next: () => this.router.navigate(['/etudiants']),
      error: () => this.erreur.set('Identifiants invalides')
    });
  }
}
```

**2. Créer `AuthService` avec signals** :
```typescript
@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenSignal = signal<string | null>(localStorage.getItem('token'));
  private roleSignal = signal<string | null>(localStorage.getItem('role'));

  estConnecte = computed(() => this.tokenSignal() !== null);
  estAdmin = computed(() => this.roleSignal() === 'ADMIN');

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('http://localhost:8080/api/auth/login', credentials)
      .pipe(tap(response => this.stockerSession(response)));
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.tokenSignal.set(null);
    this.roleSignal.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  private stockerSession(response: AuthResponse) {
    localStorage.setItem('token', response.token);
    localStorage.setItem('role', response.role);
    this.tokenSignal.set(response.token);
    this.roleSignal.set(response.role);
  }
}
```

**3. Créer l'intercepteur HTTP** qui ajoute le token automatiquement :
```typescript
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  const requeteAvecToken = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(requeteAvecToken).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authService.logout();
      }
      return throwError(() => error);
    })
  );
};
```

**4. Enregistrer l'intercepteur** dans `app.config.ts` :
```typescript
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    provideRouter(routes)
  ]
};
```

**5. Créer le guard `canActivate`** pour protéger les routes d'écriture :
```typescript
export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estConnecte()) {
    return true;
  }
  router.navigate(['/login']);
  return false;
};
```

**6. Appliquer le guard sur les routes concernées** :
```typescript
export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiants/nouveau', component: EtudiantFormComponent, canActivate: [authGuard] },
  { path: 'etudiants/:id/modifier', component: EtudiantFormComponent, canActivate: [authGuard] },
];
```

**7. Tester le parcours complet** :
   - Visiteur non connecté → tente d'accéder à `/etudiants/nouveau` → redirigé vers `/login`.
   - Connexion avec le compte ADMIN → accès complet.
   - Connexion avec le compte ETUDIANT → vérifier le comportement attendu (à définir avec G1 : accès en lecture seule uniquement, ou accès complet selon vos règles métier du jour 5).
   - Token expiré ou invalide → déconnexion automatique et redirection.

**Bonus 🔴 si le temps le permet** : recherche d'étudiants avec `debounceTime` + `switchMap` :
```typescript
rechercheControl = new FormControl('');

resultats$ = this.rechercheControl.valueChanges.pipe(
  debounceTime(300),
  switchMap(terme => this.etudiantService.rechercher(terme ?? ''))
);
```

### Livrable attendu ce soir
- Un visiteur non connecté ne peut ni voir les formulaires ni écrire ; un ADMIN peut tout faire.
- Token visible dans les DevTools, rôle de l'intercepteur explicable clairement.
- PR mergée.

---

## ⚫ G8 – DevOps & Cloud

**Absent pour la 2e journée consécutive.**

**Impact à surveiller de près :**
- Le pipeline CI n'a pas été vérifié depuis 2 jours — G1 a pris le relais aujourd'hui pour une vérification manuelle (voir sa section).
- Les changements importants de G6/G7 aujourd'hui (intercepteur, guards, nouvelles dépendances RxJS si le bonus recherche est fait) doivent être buildables en CI — **à vérifier en priorité dès le retour de G8**, sans quoi le risque de régression silencieuse augmente.
- Le volet cloud (stratégie d'hébergement frontend, gestion des secrets) reste en pause depuis 2 jours — sans impact bloquant immédiat, mais à rattraper avant le jour 9 (Docker) qui dépend de ces décisions.

**Recommandation pour la suite :** si l'absence de G8 devait se prolonger jusqu'au jour 9, il faudra redistribuer une partie de sa charge (Dockerfiles, docker-compose) entre les groupes backend et frontend pour ne pas mettre en péril le livrable du jour 9 — à anticiper dès maintenant avec le formateur.

---

## 🔗 Dépendances à surveiller au stand-up de ce soir

- G7 dépend du contrat JWT de G5 → suivi prioritaire toute la journée.
- G6 dépend de `AuthService` de G7 pour son affichage conditionnel → coordination directe nécessaire dès le matin.
- Sans G8, personne ne valide que les nouveaux packages/dépendances Angular d'aujourd'hui ne cassent pas le build CI — vérification manuelle recommandée en fin de journée par un relecteur croisé.

## 📌 Rappel à tous

Minimum 4 commits significatifs, PR + revue croisée obligatoire par un membre d'un autre groupe, `JOURNAL.md` à jour ce soir.
