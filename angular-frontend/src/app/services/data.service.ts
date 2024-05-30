import {Injectable} from '@angular/core';
import {TimesheetService, Week} from "../api";
import {first, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  // private _weeks: Week[];

  constructor(private timesheetService: TimesheetService) {

    // this._weeks =
    //   [
    //     {
    //       "year": 2024,
    //       "cw": 19,
    //       "timeEntries": [
    //         {
    //           "day": "MONDAY",
    //           "date": "06.05.24",
    //           "startTime": "08:12",
    //           "endTime": "16:50"
    //         },
    //         {
    //           "day": "TUESDAY",
    //           "date": "07.05.24",
    //           "startTime": "07:52",
    //           "endTime": "17:01"
    //         },
    //         {
    //           "day": "WEDNESDAY",
    //           "date": "08.05.24",
    //           "startTime": "07:52",
    //           "endTime": "17:01"
    //         },
    //         {
    //           "day": "THURSDAY",
    //           "date": "09.05.24",
    //           "startTime": "07:52",
    //           "endTime": "17:01"
    //         },
    //         {
    //           "day": "FRIDAY",
    //           "date": "10.05.24",
    //           "startTime": "07:52",
    //           "endTime": "17:01"
    //         }]
    //     }]
  }


  public getWeeksAll(): Observable<Week[]> {
    return new Observable<Week[]>(o => {
      this.timesheetService.getWeeksAll()
        .pipe(first())
        .subscribe({
          next: (value: Week[]) => {
            o.next(value);
          },
          error: () => {
            o.error();
          }
        })

    })
    // return this._weeks;
  }

  public saveWeek(week: Week): Observable<any> {
    return new Observable(o => {
      this.timesheetService.saveWeek(week)
        .pipe(first())
        .subscribe({
          next: () => {
            o.next();
          },
          error: () => {
            o.error();
          }
        })

    })
  }

  public getWorkedHoursCurrWeek(): Observable<number> {
    return new Observable(o => {
      this.timesheetService.getWorkedHoursCurrWeek()
        .pipe(first())
        .subscribe({
          next: (value: number) => {
            o.next(value);
          }
        });

    });
  }

  public getCurBalance(): Observable<number> {
    return new Observable(o => {
      this.timesheetService.getCurBalance()
        .pipe(first())
        .subscribe({
          next: value => {
            o.next(value);
          }
        });
    });
  }

  // set weeks(value: Week[]) {
  //   this._weeks = value;
  // }
  deleteWeek(week: Week): Observable<any> {
    return new Observable(o => {
      this.timesheetService.deleteWeek(week)
        .pipe(first())
        .subscribe({
          next: () => {
            o.next();
          }, error: () => {
            o.error();
          }
        })

    })

  }
}
