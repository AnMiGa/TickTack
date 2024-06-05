import {Component, OnInit} from '@angular/core';
import {AppSettingsService} from "../../services/app-settings.service";
import {DataService} from "../../services/data.service";
import {first} from "rxjs";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent implements OnInit {

  hoursCurrWeek: number;
  curBalance: number;

  constructor(public settingsService: AppSettingsService,
              private dataService: DataService) {

  }

  ngOnInit() {
    this.dataService.getWorkedHoursCurrWeek()
      .pipe(first())
      .subscribe({
        next: value => {
          this.hoursCurrWeek = value;
        }
      });

    this.dataService.getCurBalance()
      .pipe(first())
      .subscribe({
        next: value => {
          this.curBalance = value;
        }
      });
  }

}
