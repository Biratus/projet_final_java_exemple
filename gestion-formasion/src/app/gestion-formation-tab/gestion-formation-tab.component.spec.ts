import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionFormationTabComponent } from './gestion-formation-tab.component';

describe('GestionFormationTabComponent', () => {
  let component: GestionFormationTabComponent;
  let fixture: ComponentFixture<GestionFormationTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionFormationTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionFormationTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
