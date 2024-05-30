import {Component, OnDestroy, OnInit} from '@angular/core';
import {AppSettingsService} from "../../services/app-settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent{
  // weeklyHours: number;
  // breakDuration: number;

  constructor(public settingsService: AppSettingsService) {
  }


  public saveSettings(){
    this.settingsService.saveSettings();
  }


}

