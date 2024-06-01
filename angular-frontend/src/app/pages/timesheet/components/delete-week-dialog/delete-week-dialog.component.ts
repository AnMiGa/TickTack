import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";

export interface DialogData {
  week: string;
}

@Component({
  selector: 'app-delete-week-dialog',
  templateUrl: './delete-week-dialog.component.html',
  styleUrl: './delete-week-dialog.component.css'
})
export class DeleteWeekDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

}
