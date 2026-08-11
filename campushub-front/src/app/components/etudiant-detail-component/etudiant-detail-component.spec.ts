import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantDetailComponent } from './etudiant-detail-component';
import { EtudiantService } from '../../services/etudiant-service';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('EtudiantDetailComponent', () => {
  let component: EtudiantDetailComponent;
  let fixture: ComponentFixture<EtudiantDetailComponent>;
  let etudiantServiceSpy: any;

  const mockEtudiant = {
    id: 3,
    prenom: 'Ibrahima',
    nom: 'Sow',
    age: 21,
    filiere: 'Génie Informatique',
    email: 'ibrahima@example.com'
  };

  beforeEach(async () => {
    etudiantServiceSpy = {
      getById: vi.fn().mockReturnValue(of(mockEtudiant))
    };

    await TestBed.configureTestingModule({
      imports: [EtudiantDetailComponent],
      providers: [
        { provide: EtudiantService, useValue: etudiantServiceSpy },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => '3'
              }
            }
          }
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch and display etudiant details on init', () => {
    fixture.detectChanges();

    expect(etudiantServiceSpy.getById).toHaveBeenCalledWith(3);
    expect(component.etudiant()).toEqual(mockEtudiant);

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Ibrahima');
    expect(compiled.textContent).toContain('Sow');
    expect(compiled.textContent).toContain('Génie Informatique');
  });

  it('should handle error when student is not found', () => {
    etudiantServiceSpy.getById.mockReturnValue(throwError(() => new Error('Not found')));

    fixture.detectChanges();

    expect(component.erreur()).toBe('Étudiant introuvable.');

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Étudiant introuvable.');
  });
});
