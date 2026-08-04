import { inject, Service } from '@angular/core';
import { Etudiant } from '../model/etudiant.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse } from '../model/page-response.model';

@Service()
export class EtudiantService {
    private apiUrl = 'http://localhost:8080/api/etudiants';

    private http = inject(HttpClient);

    getAll(page: number = 0, size: number = 10): Observable<PageResponse<Etudiant>> {
      return this.http.get<PageResponse<Etudiant>>(`${this.apiUrl}?page=${page}&size=${size}`);
    }
}
