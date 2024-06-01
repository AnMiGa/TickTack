package com.fhdw.webproject.api;

import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
import com.fhdw.webproject.postgresql.repository.TimeEntryRepository;
import com.openapi.api.DefaultApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultApiDelegateImpl implements DefaultApiDelegate {

    public DefaultApiDelegateImpl() {
    }

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Override
    public ResponseEntity<String> helloGet() {
        List<TimeEntryDTO> timeEntries = timeEntryRepository.findAll();
        return new ResponseEntity<>(timeEntries.toString(), HttpStatus.OK);
    }
}
