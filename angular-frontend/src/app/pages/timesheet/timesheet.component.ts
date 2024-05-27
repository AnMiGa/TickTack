import {Component} from '@angular/core';
import {DefaultService, TimeEntry, Week} from "../../api";
import {first} from "rxjs";
import {DataService} from "../../services/data.service";
import {AppSettingsService} from "../../services/app-settings.service";

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


  constructor(
    private defaultService: DefaultService,
    private dataService: DataService,
    private settingsService: AppSettingsService) {
    this.weeks = dataService.weeks;


    this.curIndex = this.weeks.length - 1;
    this.isLast = this.curIndex <= 0;
    this.isFirst= this.curIndex >= this.weeks.length -1;
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
    this.curIndex++;
    this.isFirst = this.curIndex == this.weeks.length - 1;
    this.isLast = false;
    this.curWeek = this.weeks[this.curIndex];
    console.log(this.curWeek)
  }

  previousWeek() {
    this.curIndex--;
    this.isLast = this.curIndex == 0;
    this.isFirst = false;
    this.curWeek = this.weeks[this.curIndex];
    console.log(this.curWeek)
  }

  addNewWeek() {

    let newWeek:Week={};
    newWeek.timeEntries =[];

    const curr: Date = new Date;
    let first: number = curr.getDate() - curr.getDay() + 1; // First day is the day of the month - the day of the week +1 because starts with sunday

    newWeek.year=curr.getFullYear();
   newWeek.cw=this.getDateWeek(curr);

    for(let i = first; i < first + 5; i++){
      let entry: TimeEntry = {};

      let date: Date = new Date(curr.setDate(i));

      entry.day = date.toLocaleString('en-us', {weekday:'long'}).toUpperCase();

      let dayString = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let monthString = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
      let yearString = date.getFullYear() % 100;


      entry.date=dayString + '.' + monthString + '.' + yearString;

      newWeek.timeEntries.push(entry);
    }

    this.weeks.push(newWeek);

    this.curIndex = this.weeks.length - 1;
    this.curWeek = this.weeks[this.curIndex];

    this.isLast=false;

    this.dataService.weeks = this.weeks;
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

  calcTimeDifference(start, end): string{
    if(start && end){
      const [h1, m1] = start.split(':');
      const [h2, m2] = end.split(':');
      let diff = (h2 - h1) * 60 + (m2 - m1 - this.settingsService.breakDurationMinutes);
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


    return
  }
}