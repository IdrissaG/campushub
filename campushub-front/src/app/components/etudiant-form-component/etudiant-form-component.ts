import { Component, inject, OnInit, signal } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { EtudiantService } from '../../services/etudiant-service';
import { EtudiantRequest } from '../../model/etudiant-api.interface';

@Component({
  selector: 'app-etudiant-form-component',
  imports: [ReactiveFormsModule],
  templateUrl: './etudiant-form-component.html',
  styleUrl: './etudiant-form-component.scss',
})
export class EtudiantFormComponent implements OnInit {

  private fb = inject(NonNullableFormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private etudiantService = inject(EtudiantService);

  erreurServeur = signal<string[]>([]);
  etudiantId: number | null = null;

  etudiantForm = this.fb.group({
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    age: [0, [Validators.required, Validators.min(15)]],
    filiere: ['']
  });

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.etudiantId = +id;
      this.etudiantService.getById(this.etudiantId).subscribe(etudiant => {
        this.etudiantForm.patchValue(etudiant);
      });
    }
  }

  onSubmit(): void {
    if (this.etudiantForm.invalid) return;

    const request: EtudiantRequest = this.etudiantForm.getRawValue();

    const operation = this.etudiantId
      ? this.etudiantService.update(this.etudiantId, request)
      : this.etudiantService.create(request);

    operation.subscribe({
      next: () => this.router.navigate(['/etudiants']),
      error: (err: HttpErrorResponse) => {
        this.erreurServeur.set(
          err.error?.erreurs ?? [
            'Une erreur est survenue lors de la création de l\'étudiant.'
          ]
        );
      }
    });
  }
}