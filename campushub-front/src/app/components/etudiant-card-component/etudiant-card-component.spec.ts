import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtudiantCardComponent } from './etudiant-card-component';

describe('EtudiantCardComponent', () => {
  let component: EtudiantCardComponent;
  let fixture: ComponentFixture<EtudiantCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtudiantCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtudiantCardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
