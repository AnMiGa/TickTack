import {Component, OnInit} from '@angular/core';
import {AppSettingsService} from "../../services/app-settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit{
  // weeklyHours: number;
  // breakDuration: number;

  constructor(public settingsService: AppSettingsService) {
  }

  ngOnInit(){
      // this.weeklyHours = this.settingsService.weeklyHours;
      // this.breakDuration = this.settingsService.breakDurationMinutes;
  }

  saveSettings(){
      // this.settingsService.weeklyHours = this.weeklyHours;
      // this.settingsService.breakDurationMinutes = this.breakDuration;
  }


}
