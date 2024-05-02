import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OverviewComponent} from "./pages/overview/overview.component";
import {TimesheetComponent} from "./pages/timesheet/timesheet.component";
import {SettingsComponent} from "./pages/settings/settings.component";

const routes: Routes = [
  {
    path: '',
    component: OverviewComponent
  },
  {
    path: 'timesheet',
    component: TimesheetComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
