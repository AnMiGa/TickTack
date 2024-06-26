package com.fhdw.webproject.facade;

import com.fhdw.webproject.services.SettingsService;
import com.fhdw.webproject.services.WeekDataService;
import com.openapi.model.Settings;
import com.openapi.model.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFacade {

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private WeekDataService weekDataService;

    public Settings getSettings() {
        return settingsService.loadSettings();
    }

    public List<Week> getWeeksAll() {
        return weekDataService.loadWeeksAll();
    }

    public void saveWeek(Week week) {
        weekDataService.saveWeek(week);
    }

    public void saveSettings(Settings settings) {
        settingsService.saveSettings(settings);
    }

    public Double getWorkedHoursCurrWeek() {
        return weekDataService.getWorkedHoursCurrWeek();
    }

    public Double getCurBalance() {
        return weekDataService.getCurBalance();
    }

    public void deleteWeek(Week week) {
        weekDataService.deleteWeek(week);
    }
}
