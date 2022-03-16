import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionFiliereTabComponent } from './gestion-filiere-tab.component';

describe('GestionFiliereTabComponent', () => {
  let component: GestionFiliereTabComponent;
  let fixture: ComponentFixture<GestionFiliereTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionFiliereTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionFiliereTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
