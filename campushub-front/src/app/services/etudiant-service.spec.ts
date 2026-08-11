import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { EtudiantService } from './etudiant-service';

describe('EtudiantService', () => {
  let service: EtudiantService;
  let httpMock: HttpTestingController; // Permet d'intercepter les requêtes HTTP au lieu de les envoyer réellement

  const apiUrl = 'http://localhost:8080/api/etudiants';

  beforeEach(() => {
    TestBed.configureTestingModule({
      // provideHttpClient() : fournit un HttpClient au service (nécessaire, EtudiantService en dépend)
      // provideHttpClientTesting() : remplace le backend HTTP réel par un mock contrôlable en test
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(EtudiantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Vérifie qu'aucune requête HTTP n'est restée sans réponse simulée (flush)
    // Si un test déclenche un appel HTTP oublié, ça le fait échouer plutôt que de le laisser passer silencieusement
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('devrait créer un étudiant via POST', () => {
    const nouvelEtudiant = { nom: 'Diop', prenom: 'Awa', email: 'awa@test.com', age: 22, filiere: 'Informatique' };

    // On s'abonne à l'appel, sans encore avoir de réponse : la requête part immédiatement (Angular HttpClient) mais reste "en attente" côté mock
    service.create(nouvelEtudiant).subscribe();

    // On récupère la requête interceptée et on vérifie qu'elle correspond à ce qu'on attend
    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');

    // On simule la réponse du serveur : ça débloque le .subscribe() ci-dessus
    req.flush({ id: 1, ...nouvelEtudiant });
  });
});