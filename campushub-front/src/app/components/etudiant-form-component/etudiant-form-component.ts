import { Component, inject } from '@angular/core'; // inject : nouvelle syntaxe Angular pour l'injection de dépendances
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-etudiant-form-component',
  imports: [ReactiveFormsModule], // rend les directives [formGroup] et formControlName disponibles dans le template
  templateUrl: './etudiant-form-component.html',
  styleUrl: './etudiant-form-component.scss',
})
export class EtudiantFormComponent {
  etudiantForm: FormGroup;
  private router = inject(Router); // inject() remplace l'injection par constructeur pour les versions modernes d'Angular

  constructor(private fb: FormBuilder) {
    this.etudiantForm = this.fb.group({
      // ['valeur_initiale', validator(s)]
      nom:     ['', Validators.required],
      prenom:  ['', Validators.required],
      email:   ['', [Validators.required, Validators.email]],  // tableau quand plusieurs validators
      age:     ['', [Validators.required, Validators.min(15)]],
      filiere: [''], // pas de validation obligatoire
    });
  }

  onSubmit(): void {
    // on ne traite les données que si tout le formulaire est valide
    if (this.etudiantForm.valid) {
      console.log(this.etudiantForm.value); // { nom: '...', prenom: '...', email: '...', ... }
      this.router.navigate(['/etudiants']); // redirection vers la liste après soumission
    }
  }
}