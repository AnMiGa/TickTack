import { Injectable } from '@angular/core';
import {Settings, SettingsService} from "../api";
import {first} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppSettingsService {

  private settings: Settings;

  private _user_name:string;
  private _weeklyHours: number;
  private _breakDurationMinutes: number;

  constructor(private settingsService: SettingsService) {
    this.settingsService.getSettings()
      .pipe(first())
      .subscribe(
        {next: (settings: Settings) => {
          this._user_name= settings.user_name;
          this._weeklyHours = settings.weekly_hours;
          this._breakDurationMinutes = settings.break_duration;
    } })
  }


  get user_name(): string {
    return this._user_name;
  }

  set user_name(value: string) {
    this._user_name = value;
  }

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

  public saveSettings(): void {
    const curSettings: Settings =
      {
        "id": 1,
        "user_name": this.user_name,
        "weekly_hours": this.weeklyHours,
        "break_duration": this.breakDurationMinutes
      }
    this.settingsService.saveSettings(curSettings).pipe(first()).subscribe();
  }
}
