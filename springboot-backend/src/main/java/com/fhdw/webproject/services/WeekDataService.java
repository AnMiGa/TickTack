package com.fhdw.webproject.services;

import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
import com.fhdw.webproject.postgresql.repository.TimeEntryRepository;
import com.openapi.model.TimeEntry;
import com.openapi.model.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;


@Service
public class WeekDataService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;
    public List<Week> loadWeeksAll() {
        List<Week> result = new ArrayList<>();

        List<TimeEntryDTO> timeEntries = timeEntryRepository.findAll();
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");

        for(TimeEntryDTO entry : timeEntries){
            LocalDate date = LocalDate.parse(entry.getDate(), DATE_FORMAT);
            int curYear = date.getYear();
            int curCW = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            Week week = result
                    .stream()
                    .filter(w -> w.getYear().equals(curYear))
                    .filter(w -> w.getCw().equals(curCW))
                    .findAny()
                    .orElse(null);
            
            if(week == null){
                week = new Week();
                week.setYear(curYear);
                week.setCw(curCW);
                result.add(week);
            }
            week.addTimeEntriesItem(convertTImeEntry(entry));
        }
        return result;
    }

    private TimeEntry convertTImeEntry(TimeEntryDTO entry) {
        TimeEntry result = new TimeEntry();
        result.setId(entry.getId());
        result.setDate(entry.getDate());
        result.setDay(entry.getDay());
        result.setStartTime(entry.getStarttime());
        result.setEndTime(entry.getEndtime());
        return result;
    }
}
