package com.fhdw.webproject.api;

import com.fhdw.webproject.facade.ServiceFacade;
import com.fhdw.webproject.services.SettingsService;
import com.openapi.api.SettingsApiDelegate;
import com.openapi.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SettingsApiDelegateImpl implements SettingsApiDelegate {


    @Autowired
    ServiceFacade serviceFacade;

    @Override
    public ResponseEntity<Settings> getSettings() {
        return new ResponseEntity<>(serviceFacade.getSettings(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> saveSettings(Settings settings) {
        this.serviceFacade.saveSettings(settings);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
