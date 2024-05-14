export * from './default.service';
import { DefaultService } from './default.service';
export * from './timesheet.service';
import { TimesheetService } from './timesheet.service';
export const APIS = [DefaultService, TimesheetService];
