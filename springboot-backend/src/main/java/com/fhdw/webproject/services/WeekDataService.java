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
import java.util.Objects;


@Service
public class WeekDataService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");

    public List<Week> loadWeeksAll() {
        List<Week> result = new ArrayList<>();

        List<TimeEntryDTO> timeEntries = timeEntryRepository.findAll();
//        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");

        for (TimeEntryDTO entry : timeEntries) {
            LocalDate date = LocalDate.parse(entry.getDate(), DATE_FORMAT);
            int curYear = date.getYear();
            int curCW = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            Week week = result
                    .stream()
                    .filter(w -> w.getYear().equals(curYear))
                    .filter(w -> w.getCw().equals(curCW))
                    .findAny()
                    .orElse(null);

            if (week == null) {
                week = new Week();
                week.setYear(curYear);
                week.setCw(curCW);
                result.add(week);
            }
            week.addTimeEntriesItem(convertDTOtoTimeEntry(entry));
            week.getTimeEntries().sort((e1, e2) -> LocalDate.parse(e1.getDate(), DATE_FORMAT).compareTo(LocalDate.parse(e2.getDate(), DATE_FORMAT)));
        }
        return result;
    }

    private void sortTimeEntries(List<TimeEntry> timeEntries) {
       timeEntries.sort((e1, e2) -> LocalDate.parse(e1.getDate(), DATE_FORMAT).compareTo(LocalDate.parse(e2.getDate(), DATE_FORMAT)));
    }

    private TimeEntry convertDTOtoTimeEntry(TimeEntryDTO entry) {
        TimeEntry result = new TimeEntry();
        result.setId(entry.getId());
        result.setDate(entry.getDate());
        result.setDay(entry.getDay());
        result.setStartTime(entry.getStarttime());
        result.setEndTime(entry.getEndtime());
        return result;
    }

    private TimeEntryDTO convertTimeEntryToDTO(TimeEntry entry) {
        TimeEntryDTO result = new TimeEntryDTO();
        result.setId(entry.getId());
        result.setDate(entry.getDate());
        result.setDay(entry.getDay());
        result.setStarttime(entry.getStartTime());
        result.setEndtime(entry.getEndTime());
        return result;
    }

    public void saveWeek(Week week) {
        for (TimeEntry entry : week.getTimeEntries()) {
            TimeEntryDTO dto = convertTimeEntryToDTO(entry);
            timeEntryRepository.save(dto);
        }
    }
}
