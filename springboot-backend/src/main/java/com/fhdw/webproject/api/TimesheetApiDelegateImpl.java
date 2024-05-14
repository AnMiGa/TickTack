package com.fhdw.webproject.api;

import com.openapi.api.TimesheetApiDelegate;
import com.openapi.model.TimeEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TimesheetApiDelegateImpl implements TimesheetApiDelegate {
    @Override
    public ResponseEntity<Void> postEntry(TimeEntry timeEntry) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
