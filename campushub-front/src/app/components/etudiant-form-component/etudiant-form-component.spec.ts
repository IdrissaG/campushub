import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantFormComponent } from './etudiant-form-component';
import { EtudiantService } from '../../services/etudiant-service';
import { provideRouter, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('EtudiantFormComponent', () => {
  let component: EtudiantFormComponent;
  let fixture: ComponentFixture<EtudiantFormComponent>;
  let etudiantServiceSpy: any;
  let router: Router;

  const mockEtudiant = {
    id: 1,
    nom: 'Sow',
    prenom: 'Ibrahima',
    email: 'ibrahima@example.com',
    age: 21,
    filiere: 'Génie Informatique'
  };

  beforeEach(async () => {
    etudiantServiceSpy = {
      create: vi.fn().mockReturnValue(of(mockEtudiant)),
      update: vi.fn().mockReturnValue(of(mockEtudiant)),
      getById: vi.fn().mockReturnValue(of(mockEtudiant))
    };

    await TestBed.configureTestingModule({
      imports: [EtudiantFormComponent],
      providers: [
        provideRouter([]),
        { provide: EtudiantService, useValue: etudiantServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantFormComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values in create mode', () => {
    expect(component.etudiantId).toBeNull();
    expect(component.etudiantForm.get('nom')?.value).toBe('');
    expect(component.etudiantForm.get('age')?.value).toBe(0);
  });

  it('should mark form as invalid when required fields are empty', () => {
    const nomControl = component.etudiantForm.get('nom');
    nomControl?.setValue('');
    expect(nomControl?.invalid).toBe(true);
    expect(component.etudiantForm.invalid).toBe(true);
  });

  it('should validate email format correctly', () => {
    const emailControl = component.etudiantForm.get('email');

    emailControl?.setValue('invalid-email');
    expect(emailControl?.errors?.['email']).toBeTruthy();

    emailControl?.setValue('ibrahima@example.com');
    expect(emailControl?.errors).toBeNull();
  });

  it('should validate minimum age restriction (min 15)', () => {
    const ageControl = component.etudiantForm.get('age');

    ageControl?.setValue(12);
    expect(ageControl?.errors?.['min']).toBeTruthy();

    ageControl?.setValue(18);
    expect(ageControl?.errors).toBeNull();
  });

  it('should call create service and navigate on valid form submission', () => {
    const navigateSpy = vi.spyOn(router, 'navigate');

    component.etudiantForm.setValue({
      nom: 'Sow',
      prenom: 'Ibrahima',
      email: 'ibrahima@example.com',
      age: 21,
      filiere: 'Génie Informatique'
    });

    expect(component.etudiantForm.valid).toBe(true);

    component.onSubmit();

    expect(etudiantServiceSpy.create).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['/etudiants']);
  });

  it('should handle server error on submission failure', () => {
    etudiantServiceSpy.create.mockReturnValue(throwError(() => ({
      error: { erreurs: ['Erreur serveur personnalisée'] }
    })));

    component.etudiantForm.setValue({
      nom: 'Sow',
      prenom: 'Ibrahima',
      email: 'ibrahima@example.com',
      age: 21,
      filiere: 'Génie Informatique'
    });

    component.onSubmit();

    expect(component.erreurServeur()).toContain('Erreur serveur personnalisée');
  });
});
