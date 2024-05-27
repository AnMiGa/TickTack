package com.fhdw.webproject.postgresql.controller;

import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fhdw.webproject.postgresql.repository.TimeEntryRepository;

@Controller
@RequestMapping(path = "/db")
public class TimeEntryController {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @PostMapping(path="/addTimeEntry")
    public @ResponseBody String addTimeEntry (@RequestParam String day
            , @RequestParam String date , @RequestParam String starttime , @RequestParam String endtime) {

        // @ResponseBody means the returned String
        // is the response, not a view name
        // @RequestParam means it is a parameter
        // from the GET or POST request
        TimeEntryDTO timeEntry = new TimeEntryDTO();

        timeEntry.setDay(day);
        timeEntry.setDate(date);
        timeEntry.setStarttime(starttime);
        timeEntry.setEndtime(endtime);

        timeEntryRepository.save(timeEntry);
        return "TimeEntry got Saved";
    }

    @GetMapping(path="/getTimeEntries")
    public @ResponseBody Iterable<TimeEntryDTO> getAllTimeEntries() {
        // This returns a JSON or
        // XML with the Book
        return timeEntryRepository.findAll();
    }


}
