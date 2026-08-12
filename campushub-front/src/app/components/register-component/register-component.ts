// src/app/components/register-component/register-component.ts

import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../model/auth.interface';

@Component({
  selector: 'app-register-component',
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <div class="max-w-md mx-auto mt-12 bg-white p-8 rounded-xl shadow-sm border border-slate-200">
      <h2 class="text-xl font-bold text-slate-800 mb-6 text-center">Créer un compte</h2>

      <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="space-y-4">

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">Nom</label>
          <input
            formControlName="nom"
            type="text"
            placeholder="Amina Diop"
            class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" />
          @if (registerForm.get('nom')?.invalid && registerForm.get('nom')?.touched) {
            <p class="text-red-500 text-xs mt-1">Le nom est obligatoire</p>
          }
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">Email</label>
          <input
            formControlName="email"
            type="email"
            placeholder="amina.diop@exemple.com"
            class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" />
          @if (registerForm.get('email')?.errors?.['required'] && registerForm.get('email')?.touched) {
            <p class="text-red-500 text-xs mt-1">L'email est obligatoire</p>
          }
          @if (registerForm.get('email')?.errors?.['email'] && registerForm.get('email')?.touched) {
            <p class="text-red-500 text-xs mt-1">Format d'email invalide</p>
          }
        </div>

        <div>
          <label class="block text-sm font-medium text-slate-700 mb-1">Mot de passe</label>
          <input
            formControlName="motDePasse"
            type="password"
            placeholder="Au moins 8 caractères"
            class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all" />
          @if (registerForm.get('motDePasse')?.errors?.['required'] && registerForm.get('motDePasse')?.touched) {
            <p class="text-red-500 text-xs mt-1">Le mot de passe est obligatoire</p>
          }
          @if (registerForm.get('motDePasse')?.errors?.['minlength'] && registerForm.get('motDePasse')?.touched) {
            <p class="text-red-500 text-xs mt-1">Le mot de passe doit contenir au moins 8 caractères</p>
          }
        </div>

        <button
          type="submit"
          [disabled]="registerForm.invalid"
          class="w-full bg-indigo-600 text-white font-medium py-2.5 rounded-lg hover:bg-indigo-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed">
          Créer mon compte
        </button>

        @if (erreur()) {
          <div class="bg-red-50 text-red-600 text-sm p-3 rounded-lg border border-red-200 text-center">
            {{ erreur() }}
          </div>
        }

        <p class="text-center text-sm text-slate-500">
          Déjà un compte ?
          <a routerLink="/login" class="text-indigo-600 font-medium hover:underline">Se connecter</a>
        </p>
      </form>
    </div>
  `
})
export class RegisterComponent {

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  registerForm = this.fb.nonNullable.group({
    nom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', [Validators.required, Validators.minLength(8)]],
  });

  erreur = signal<string | null>(null);

  onSubmit() {
    if (this.registerForm.invalid) {
      return;
    }

    const data: RegisterRequest = this.registerForm.getRawValue();

    this.authService.register(data).subscribe({
      next: () => {
        this.router.navigate(['/etudiants']);
      },
      error: (err: HttpErrorResponse) => {
        this.erreur.set(
          err.error?.erreurs?.[0] ?? 'Une erreur est survenue lors de la création du compte.'
        );
      }
    });
  }
}