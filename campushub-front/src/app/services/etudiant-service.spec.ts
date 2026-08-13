import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { EtudiantService } from './etudiant-service';

describe('EtudiantService', () => {
  let service: EtudiantService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(EtudiantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('devrait créer un étudiant via POST', () => {
    const nouvelEtudiant = { nom: 'Diop', prenom: 'Awa', email: 'awa@test.com', age: 22, filiere: 'Informatique' };
    service.create(nouvelEtudiant).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/etudiants');
    expect(req.request.method).toBe('POST');
    req.flush({ id: 1, ...nouvelEtudiant });
  });
});