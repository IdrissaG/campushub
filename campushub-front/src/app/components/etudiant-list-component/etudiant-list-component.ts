import { Component, inject, OnInit } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';
import { EtudiantCardComponent } from '../etudiant-card-component/etudiant-card-component';
import { EtudiantService } from '../../services/etudiant-service';

@Component({
  selector: 'app-etudiant-list-component',
  imports: [EtudiantCardComponent],
  templateUrl: './etudiant-list-component.html',
  styleUrl: './etudiant-list-component.scss',
})

export class EtudiantListComponent implements OnInit {

  etudiants: Etudiant[] = [];

  private etudiantService = inject(EtudiantService);

  ngOnInit() {
    this.etudiantService.getAll().subscribe({
      next: (data) => {
        console.log('Étudiants reçus :', data);
        this.etudiants = data;
      },
      error: (err) => {
        console.error('Erreur API', err);
      }
    });
  }
}

