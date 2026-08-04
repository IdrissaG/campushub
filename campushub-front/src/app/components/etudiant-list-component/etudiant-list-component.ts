import { Component, inject, OnInit, signal } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';
import { EtudiantCardComponent } from '../etudiant-card-component/etudiant-card-component';
import { EtudiantService } from '../../services/etudiant-service';
import { PageResponse } from '../../model/page-response.model';

@Component({
  selector: 'app-etudiant-list-component',
  imports: [EtudiantCardComponent],
  templateUrl: './etudiant-list-component.html',
  styleUrl: './etudiant-list-component.scss',
})

export class EtudiantListComponent implements OnInit{
  etudiants: Etudiant[] = [];
  private etudiantService = inject(EtudiantService);

  loading = signal(true);
  erreur = signal<string | null>(null);

  ngOnInit() {
    this.etudiantService.getAll().subscribe({
      next: (data) => {
        this.etudiants = data.content;
        this.loading.set(false);
      },
      error: (err) => {
        this.erreur.set('Impossible de charger les étudiants');
        this.loading.set(false);
      }
    });
}
}
