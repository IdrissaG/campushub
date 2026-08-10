import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../model/auth.interface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  template: `
    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">

      <input
        formControlName="email"
        type="email"
        placeholder="Email"
      />

      <input
        formControlName="motDePasse"
        type="password"
        placeholder="Mot de passe"
      />

      <button type="submit">
        Se connecter
      </button>

      @if (erreur()) {
        <p class="erreur">{{ erreur() }}</p>
      }

    </form>
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