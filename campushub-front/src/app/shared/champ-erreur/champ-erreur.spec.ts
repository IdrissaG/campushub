import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChampErreurComponent } from './champ-erreur';

describe('ChampErreurComponent', () => {
  let component: ChampErreurComponent;
  let fixture: ComponentFixture<ChampErreurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChampErreurComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ChampErreurComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});