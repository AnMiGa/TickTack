import { Component } from '@angular/core';
import {AppSettingsService} from "../../services/app-settings.service";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent {

  constructor(public settingsService: AppSettingsService) {

  }

}
