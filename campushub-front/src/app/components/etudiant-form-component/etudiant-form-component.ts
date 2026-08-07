import { Component, inject, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { EtudiantService } from '../../services/etudiant-service';
import { EtudiantRequest } from '../../model/etudiant-api.interface';

@Component({
  selector: 'app-etudiant-form-component',
  imports: [ReactiveFormsModule],
  templateUrl: './etudiant-form-component.html',
  styleUrl: './etudiant-form-component.scss',
})
export class EtudiantFormComponent {

  private fb = inject(NonNullableFormBuilder);
  private router = inject(Router);
  private etudiantService = inject(EtudiantService);

  erreurServeur = signal<string[]>([]);

  etudiantForm = this.fb.group({
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    age: [0, [Validators.required, Validators.min(15)]],
    filiere: ['']
  });

  onSubmit(): void {
    if (this.etudiantForm.valid) {

      const request: EtudiantRequest =
        this.etudiantForm.getRawValue();

      this.etudiantService.create(request).subscribe({
        next: () => {
          this.router.navigate(['/etudiants']);
        },

        error: (err: HttpErrorResponse) => {
          this.erreurServeur.set(
            err.error?.erreurs ?? [
              'Une erreur est survenue lors de la création de l’étudiant.'
            ]
          );
        }
      });
    }
  }
}



