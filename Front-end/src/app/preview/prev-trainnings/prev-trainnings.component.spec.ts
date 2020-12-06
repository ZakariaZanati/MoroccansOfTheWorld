import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrevTrainningsComponent } from './prev-trainnings.component';

describe('PrevTrainningsComponent', () => {
  let component: PrevTrainningsComponent;
  let fixture: ComponentFixture<PrevTrainningsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrevTrainningsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrevTrainningsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
