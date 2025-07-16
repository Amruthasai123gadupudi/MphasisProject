import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyCategoriesComponent } from './property-categories.component';

describe('PropertyCategoriesComponent', () => {
  let component: PropertyCategoriesComponent;
  let fixture: ComponentFixture<PropertyCategoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyCategoriesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
