package com.fhdw.webproject.facade;

import com.fhdw.webproject.services.SettingsService;
import com.openapi.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFacade {

    @Autowired
    private SettingsService settingsService;
    public Settings getSettings(){
        return settingsService.loadSettings();
    }
}
