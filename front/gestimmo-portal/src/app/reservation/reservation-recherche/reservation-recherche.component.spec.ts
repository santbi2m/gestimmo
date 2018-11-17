import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationRechercheComponent } from './reservation-recherche.component';

describe('ReservationRechercheComponent', () => {
  let component: ReservationRechercheComponent;
  let fixture: ComponentFixture<ReservationRechercheComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationRechercheComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationRechercheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
