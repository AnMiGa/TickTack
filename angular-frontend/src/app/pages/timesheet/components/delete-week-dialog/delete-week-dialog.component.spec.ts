import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteWeekDialogComponent } from './delete-week-dialog.component';

describe('DeleteWeekDialogComponent', () => {
  let component: DeleteWeekDialogComponent;
  let fixture: ComponentFixture<DeleteWeekDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteWeekDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteWeekDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
