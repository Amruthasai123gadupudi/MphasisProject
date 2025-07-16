import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Inquiry1Component } from './inquiry1.component';

describe('Inquiry1Component', () => {
  let component: Inquiry1Component;
  let fixture: ComponentFixture<Inquiry1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Inquiry1Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Inquiry1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
