import {Component} from '@angular/core';
import {DefaultService, Week} from "../../api";
import {first} from "rxjs";

@Component({
  selector: 'app-timesheet',
  templateUrl: './timesheet.component.html',
  styleUrl: './timesheet.component.css'
})
export class TimesheetComponent {
  public text: String;
  weeks: Week[];
  curWeek: Week;
  curIndex: number;
  isFirst: boolean = false;
  isLast: boolean = false;


  constructor(private defaultService: DefaultService) {
    this.weeks =
      [
        {
          "cw": "KW 20"
        },
        {
          "cw": "KW 21"

        },
        {
          "cw": "KW 22"
        },]

    this.curIndex = 2;
    this.isLast = true;
    this.curWeek = this.weeks[this.curIndex];

  }


  sendRequest() {
    this.defaultService.helloGet().pipe(first()).subscribe({
      next: (answer: String) => {
        this.text = answer;
      }
    })
  }

  nextWeek() {
    this. curIndex ++;
    this.isLast = this.curIndex == this.weeks.length - 1;
    this.isFirst = false;
    this.curWeek = this.weeks[this.curIndex];
  }

  previousWeek() {
    this. curIndex --;
    this.isFirst = this.curIndex == 0;
    this.isLast = false;
    this.curWeek = this.weeks[this.curIndex];
  }

  addNewWeek() {
    this.weeks.push(
      {
       "cw": "new Week"
      })
    this.curIndex = this.weeks.length - 1;
    this.curWeek = this.weeks[this.curIndex]
  }
}
