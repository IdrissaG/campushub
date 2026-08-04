
import { inject, Injectable } from '@angular/core';
import { Etudiant } from '../model/etudiant.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EtudiantRequest, EtudiantResponse } from '../model/etudiant-api.interface';

@Injectable({ providedIn: 'root' }) // @Service aussi marche
export class EtudiantService {
  private apiUrl = 'http://localhost:8080/api/etudiants';
  private http = inject(HttpClient);

  getAll(): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(this.apiUrl);
  }

  create(etudiant: EtudiantRequest): Observable<EtudiantResponse> {
      return this.http.post<EtudiantResponse>(this.apiUrl, etudiant);


  }
}

