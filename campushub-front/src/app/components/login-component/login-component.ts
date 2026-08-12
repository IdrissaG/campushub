import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../model/auth.interface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <div class="max-w-md mx-auto mt-12 bg-white p-8 rounded-xl shadow-sm border border-slate-200">
      <h2 class="text-xl font-bold text-slate-800 mb-6 text-center">Connexion</h2>

      <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="space-y-4">

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">Email</label>
          <input
            formControlName="email"
            type="email"
            placeholder="admin@campushub.com"
            class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" />
          @if (loginForm.get('email')?.errors?.['required'] && loginForm.get('email')?.touched) {
            <p class="text-red-500 text-xs mt-1">L'email est obligatoire</p>
          }
          @if (loginForm.get('email')?.errors?.['email'] && loginForm.get('email')?.touched) {
            <p class="text-red-500 text-xs mt-1">Format d'email invalide</p>
          }
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">Mot de passe</label>
          <input
            formControlName="motDePasse"
            type="password"
            placeholder="••••••••"
            class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" />
          @if (loginForm.get('motDePasse')?.invalid && loginForm.get('motDePasse')?.touched) {
            <p class="text-red-500 text-xs mt-1">Le mot de passe est obligatoire</p>
          }
        </div>

        <button
          type="submit"
          [disabled]="loginForm.invalid"
          class="w-full bg-indigo-600 text-white font-medium py-2.5 rounded-lg hover:bg-indigo-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
          Se connecter
        </button>

        @if (erreur()) {
          <div class="bg-red-50 text-red-600 text-sm p-3 rounded-lg border border-red-200 text-center">
            {{ erreur() }}
          </div>
        }
      </form>
      <p class="text-center text-sm text-slate-500 mt-4">
        Pas encore de compte ?
        <a routerLink="/register" class="text-indigo-600 font-medium hover:underline">S'inscrire</a>
      </p>
    </div>
  `
})
export class LoginComponent {

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loginForm = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', Validators.required],
  });

  erreur = signal<string | null>(null);
  
  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    const credentials: LoginRequest = this.loginForm.getRawValue();

    this.authService.login(credentials).subscribe({
      next: () => {
        this.router.navigate(['/etudiants']);
      },
      error: () => {
        this.erreur.set('Identifiants invalides');
      }
    });
  }
}