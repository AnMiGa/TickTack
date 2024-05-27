import { Injectable } from '@angular/core';
import {Week} from "../api";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private _weeks: Week[];

  constructor() {

    this._weeks =
      [
        {
          "year": 2024,
          "cw": 19,
          "timeEntries": [
            {
              "day": "MONDAY",
              "date": "06.05.24",
              "startTime": "08:12",
              "endTime": "16:50"
            },
            {
              "day": "TUESDAY",
              "date": "07.05.24",
              "startTime": "07:52",
              "endTime": "17:01"
            },
            {
              "day": "WEDNESDAY",
              "date": "08.05.24",
              "startTime": "07:52",
              "endTime": "17:01"
            },
            {
              "day": "THURSDAY",
              "date": "09.05.24",
              "startTime": "07:52",
              "endTime": "17:01"
            },
            {
              "day": "FRIDAY",
              "date": "10.05.24",
              "startTime": "07:52",
              "endTime": "17:01"
            }]
        }]
  }


  get weeks(): Week[] {
    return this._weeks;
  }

  set weeks(value: Week[]) {
    this._weeks = value;
  }
}
