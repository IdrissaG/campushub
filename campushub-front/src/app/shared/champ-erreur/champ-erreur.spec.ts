import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChampErreur } from './champ-erreur';

describe('ChampErreur', () => {
  let component: ChampErreur;
  let fixture: ComponentFixture<ChampErreur>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChampErreur],
    }).compileComponents();

    fixture = TestBed.createComponent(ChampErreur);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
