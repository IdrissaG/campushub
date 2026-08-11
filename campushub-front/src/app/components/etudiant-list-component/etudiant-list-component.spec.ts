import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantListComponent } from './etudiant-list-component';
import { EtudiantService } from '../../services/etudiant-service';
import { AuthService } from '../../services/auth.service';
import { provideRouter } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('EtudiantListComponent', () => {
  let component: EtudiantListComponent;
  let fixture: ComponentFixture<EtudiantListComponent>;
  let etudiantServiceSpy: any;
  let authServiceSpy: any;

  const mockPageResponse = {
    content: [
      { id: 1, prenom: 'Ibrahima', nom: 'Sow', age: 21, filiere: 'Génie Info', email: 'ib@example.com' },
      { id: 2, prenom: 'Fatou', nom: 'Diop', age: 22, filiere: 'Réseaux', email: 'fatou@example.com' }
    ],
    totalElements: 2
  };

  beforeEach(async () => {
    etudiantServiceSpy = {
      getAll: vi.fn().mockReturnValue(of(mockPageResponse)),
      delete: vi.fn().mockReturnValue(of(null))
    };

    authServiceSpy = {
      estAdmin: vi.fn().mockReturnValue(true)
    };

    await TestBed.configureTestingModule({
      imports: [EtudiantListComponent],
      providers: [
        provideRouter([]),
        { provide: EtudiantService, useValue: etudiantServiceSpy },
        { provide: AuthService, useValue: authServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load and display students on init', () => {
    fixture.detectChanges();

    expect(etudiantServiceSpy.getAll).toHaveBeenCalledWith(0, 4);
    expect(component.etudiants().length).toBe(2);
    expect(component.loading()).toBe(false);

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Ibrahima');
    expect(compiled.textContent).toContain('Fatou');
  });

  it('should handle error when loading students fails', () => {
    etudiantServiceSpy.getAll.mockReturnValue(throwError(() => new Error('API Error')));

    fixture.detectChanges();

    expect(component.erreur()).toBe('Impossible de charger les étudiants');
    expect(component.loading()).toBe(false);

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Impossible de charger les étudiants');
  });

  it('should delete student when confirmed', () => {
    vi.spyOn(window, 'confirm').mockReturnValue(true);

    fixture.detectChanges();

    component.onSupprimer(1);

    expect(etudiantServiceSpy.delete).toHaveBeenCalledWith(1);
    expect(etudiantServiceSpy.getAll).toHaveBeenCalledTimes(2);
  });
});
