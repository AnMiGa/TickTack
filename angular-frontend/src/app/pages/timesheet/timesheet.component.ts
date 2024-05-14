import { Component } from '@angular/core';
import {DefaultService} from "../../api";
import {first} from "rxjs";

@Component({
  selector: 'app-timesheet',
  templateUrl: './timesheet.component.html',
  styleUrl: './timesheet.component.css'
})
export class TimesheetComponent {
  public text: String;


  constructor(private defaultService: DefaultService ) {

  }


  sendRequest() {
    this.defaultService.helloGet().pipe(first()).subscribe({
      next: (answer: String) => {
        this.text= answer;
      }
    })
  }
}
