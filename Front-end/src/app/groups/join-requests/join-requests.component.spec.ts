import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinRequestsComponent } from './join-requests.component';

describe('JoinRequestsComponent', () => {
  let component: JoinRequestsComponent;
  let fixture: ComponentFixture<JoinRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoinRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JoinRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
