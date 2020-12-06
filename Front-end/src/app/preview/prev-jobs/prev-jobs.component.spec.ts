import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrevJobsComponent } from './prev-jobs.component';

describe('PrevJobsComponent', () => {
  let component: PrevJobsComponent;
  let fixture: ComponentFixture<PrevJobsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrevJobsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrevJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
