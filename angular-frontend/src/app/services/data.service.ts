import {Injectable} from '@angular/core';
import {TimesheetService, Week} from "../api";
import {first, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private timesheetService: TimesheetService) {
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
        });
    });
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
        });
    });
  }
}
