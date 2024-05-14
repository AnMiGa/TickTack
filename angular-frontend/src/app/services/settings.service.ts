import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  private _weeklyHours: number = 38.5;
  private _breakDurationMinutes: number = 45;

  constructor() { }


  get weeklyHours(): number {
    return this._weeklyHours;
  }

  set weeklyHours(value: number) {
    this._weeklyHours = value;
  }

  get breakDurationMinutes(): number {
    return this._breakDurationMinutes;
  }

  set breakDurationMinutes(value: number) {
    this._breakDurationMinutes = value;
  }
}
