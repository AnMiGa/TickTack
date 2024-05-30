import {Component, ElementRef, OnDestroy, QueryList, ViewChildren} from '@angular/core';
import {DefaultService, TimeEntry, Week} from "../../api";
import {delay, first} from "rxjs";
import {DataService} from "../../services/data.service";
import {AppSettingsService} from "../../services/app-settings.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-timesheet',
  templateUrl: './timesheet.component.html',
  styleUrl: './timesheet.component.css'
})
export class TimesheetComponent{
  public text: String;
  weeks: Week[];
  curWeek: Week;
  curIndex: number;
  isFirst: boolean = false;
  isLast: boolean = false;


  constructor(
    private defaultService: DefaultService,
    private dataService: DataService,
    public settingsService: AppSettingsService,
    private snackBar: MatSnackBar,
  ) {
    dataService.getWeeksAll().pipe(first()).subscribe({
      next: (value: Week[]) => {
        this.weeks = value;
        this.curIndex = this.weeks.length - 1;
        this.isLast = this.curIndex <= 0;
        this.isFirst = this.curIndex >= this.weeks.length - 1;
        this.curWeek = this.weeks[this.curIndex];
      }
    });




    // this.curIndex = this.weeks.length - 1;
    // this.isLast = this.curIndex <= 0;
    // this.isFirst = this.curIndex >= this.weeks.length - 1;
    // this.curWeek = this.weeks[this.curIndex];

  }

  sendRequest() {
    this.defaultService.helloGet().pipe(first()).subscribe({
      next: (answer: String) => {
        this.text = answer;
      }
    })
  }

  nextWeek() {
    this.curIndex++;
    this.isFirst = this.curIndex == this.weeks.length - 1;
    this.isLast = false;
    this.curWeek = this.weeks[this.curIndex];
  }

  previousWeek() {
    this.curIndex--;
    this.isLast = this.curIndex == 0;
    this.isFirst = false;
    this.curWeek = this.weeks[this.curIndex];
  }

  addNewWeek() {
    const curr: Date = new Date;

    const currYear: number = curr.getFullYear();
    const currCW: number = this.getDateWeek(curr);

    const weekExists = this.weeks.find((week: Week) => week.cw == currCW && week.year == currYear);

    if (!weekExists) {
      let firstDay: number = curr.getDate() - curr.getDay() + 1; // First day is the day of the month - the day of the week +1 because starts with sunday
      let newWeek: Week = {};
      newWeek.timeEntries = [];

      newWeek.year = currYear;
      newWeek.cw = currCW;

      for (let i = firstDay; i < firstDay + 5; i++) {
        let entry: TimeEntry = {};

        let date: Date = new Date(curr.setDate(i));

        entry.day = date.toLocaleString('en-us', {weekday: 'long'}).toUpperCase();

        let dayString = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        let monthString = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        let yearString = date.getFullYear() % 100;


        entry.date = dayString + '.' + monthString + '.' + yearString;

        newWeek.timeEntries.push(entry);
      }

      this.dataService.saveWeek(newWeek)
        .pipe(first())
        .subscribe({
          next: () => {
            this.dataService.getWeeksAll().pipe(first()).subscribe({
              next: (value: Week[]) => {
                this.weeks = value;
                this.curIndex = this.weeks.length - 1;
                this.curWeek = this.weeks[this.curIndex];

                this.isLast = false;
              }
            });
          }
        })
    } else {
      this.snackBar.open('Week already exists!', 'close', {
        duration: 5000
      });
    }


  }


  getDateWeek(date) {
    const currentDate =
      (typeof date === 'object') ? date : new Date();
    const januaryFirst =
      new Date(currentDate.getFullYear(), 0, 1);
    const daysToNextMonday =
      (januaryFirst.getDay() === 1) ? 0 :
        (7 - januaryFirst.getDay()) % 7;
    const nextMonday =
      new Date(currentDate.getFullYear(), 0,
        januaryFirst.getDate() + daysToNextMonday);

    return (currentDate < nextMonday) ? 52 :
      (currentDate > nextMonday ? Math.ceil(
        (currentDate.getTime() - nextMonday.getTime()) / (24 * 3600 * 1000) / 7) : 1);
  }

  calcTimeDifference(start, end): string {
    if (start && end) {
      const [h1, m1] = start.split(':');
      const [h2, m2] = end.split(':');
      let diff = (h2 - h1) * 60 + (m2 - m1);
      if (diff >= 360) diff -= this.settingsService.breakDurationMinutes;
      if (diff < 0) diff += 24 * 60;
      const hours = Math.floor(diff / 60);
      const minutes = diff - hours * 60;
      const hh = hours.toString().padStart(2, '0');
      const mm = minutes.toString().padStart(2, '0');
      return `${hh}:${mm}`;

    }
    return null;
  }

  calcTotalTime(): string {
    let result ="00:00"
    this.curWeek.timeEntries.forEach(e => {
      let diff = this.calcTimeDifference(e.startTime, e.endTime);
      result = this.addDifference(result, diff);
    })
    return result;
  }

  private addDifference(v1, v2): string {
    const [h1, m1]: number[] = v1.split(':');
    const [h2, m2]: number[] = v2.split(':');

    let sum: number = ((h1 *1)+ (h2 *1)) * 60 + ((m2 *1) + (m1 *1));
    console.log(sum);

    // if (sum < 0) sum += 24 * 60;
    const hours:number = Math.floor(sum / 60);
    const minutes:number = sum - hours * 60;
    const hh = hours.toString().padStart(2, '0');
    const mm = minutes.toString().padStart(2, '0');
    return `${hh}:${mm}`;
  }

  saveData() {
    this.dataService.saveWeek(this.curWeek).pipe(first()).subscribe();
  }
}
