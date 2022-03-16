import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionModuleTabComponent } from './gestion-module-tab.component';

describe('GestionModuleTabComponent', () => {
  let component: GestionModuleTabComponent;
  let fixture: ComponentFixture<GestionModuleTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionModuleTabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionModuleTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
