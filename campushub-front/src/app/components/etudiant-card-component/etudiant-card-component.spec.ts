import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EtudiantCardComponent } from './etudiant-card-component';
import { provideRouter } from '@angular/router';
import { By } from '@angular/platform-browser';

describe('EtudiantCardComponent', () => {
  let component: EtudiantCardComponent;
  let fixture: ComponentFixture<EtudiantCardComponent>;

  const mockEtudiant = {
    id: 1,
    prenom: 'Ibrahima',
    nom: 'Sow',
    age: 21,
    filiere: 'Génie Informatique',
    email: 'ibrahima@example.com'
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtudiantCardComponent],
      providers: [provideRouter([])]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantCardComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('etudiant', mockEtudiant);
  });

  it('should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should display etudiant details correctly', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    expect(compiled.textContent).toContain('Ibrahima');
    expect(compiled.textContent).toContain('Sow');
    expect(compiled.textContent).toContain('21 ans');
    expect(compiled.textContent).toContain('Génie Informatique');
    expect(compiled.textContent).toContain('ibrahima@example.com');
  });

  it('should emit supprimer event when delete button is clicked', () => {
    vi.spyOn(component, 'peutModifier').mockReturnValue(true);

    fixture.detectChanges();

    const emitSpy = vi.spyOn(component.supprimer, 'emit');

    const deleteButton = fixture.debugElement.query(By.css('button.text-red-600'));
    expect(deleteButton).toBeTruthy();

    deleteButton.triggerEventHandler('click', null);

    expect(emitSpy).toHaveBeenCalledWith(mockEtudiant.id);
  });
});
