import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {MainNavComponent} from './main-nav/main-nav.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {OverviewComponent} from './pages/overview/overview.component';
import {TimesheetComponent} from './pages/timesheet/timesheet.component';
import {SettingsComponent} from './pages/settings/settings.component';
import {MatCard, MatCardContent} from "@angular/material/card";
import {ApiModule, Configuration, ConfigurationParameters} from "./api";
import {HttpClientModule} from "@angular/common/http";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatTooltip} from "@angular/material/tooltip";
import {DeleteWeekDialogComponent} from './pages/timesheet/components/delete-week-dialog/delete-week-dialog.component';
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";

export function apiConfigFactory(): Configuration {
  const params: ConfigurationParameters = {
    basePath: 'http://localhost:8080',
  };
  return new Configuration(params);
}

@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    OverviewComponent,
    TimesheetComponent,
    SettingsComponent,
    DeleteWeekDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCard,
    MatCardContent,
    ApiModule.forRoot(apiConfigFactory),
    HttpClientModule,
    MatGridList,
    MatGridTile,
    MatFormField,
    MatLabel,
    MatInput,
    MatSuffix,
    FormsModule,
    MatTooltip,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
