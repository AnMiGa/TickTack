package com.fhdw.webproject.api;

import com.fhdw.webproject.facade.ServiceFacade;
import com.openapi.api.TimesheetApiDelegate;
import com.openapi.model.Week;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesheetApiDelegateImpl implements TimesheetApiDelegate {

    @Autowired
    ServiceFacade serviceFacade;

    @Override
    public ResponseEntity<List<Week>> getWeeksAll() {
        return new ResponseEntity<>(serviceFacade.getWeeksAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> saveWeek(Week week) {
        this.serviceFacade.saveWeek(week);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getWorkedHoursCurrWeek() {
        return new ResponseEntity<>(serviceFacade.getWorkedHoursCurrWeek(), HttpStatus.OK);
    }
}
