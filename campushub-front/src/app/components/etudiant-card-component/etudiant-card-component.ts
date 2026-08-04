import { Component, input } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-etudiant-card-component',
  imports: [RouterLink],

  templateUrl: './etudiant-card-component.html',
  styleUrl: './etudiant-card-component.scss',
})
export class EtudiantCardComponent {
  etudiant = input.required<Etudiant>();
}
// card attend obligatoirement un étudiant venant de l'extérieur.