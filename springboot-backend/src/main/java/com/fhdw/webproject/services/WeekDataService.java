package com.fhdw.webproject.services;

import com.fhdw.webproject.postgresql.model.SettingsDTO;
import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
import com.fhdw.webproject.postgresql.repository.SettingsRepository;
import com.fhdw.webproject.postgresql.repository.TimeEntryRepository;
import com.openapi.model.TimeEntry;
import com.openapi.model.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class WeekDataService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private SettingsRepository settingsRepository;

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
        result.sort((w1, w2) -> {
            if (w1.getYear().compareTo(w2.getYear()) == 0) {
                return w1.getCw().compareTo(w2.getCw());
            } else {
                return w1.getYear().compareTo(w2.getYear());
            }
        });

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

    public Double getWorkedHoursCurrWeek() {
        LocalDate curDate = LocalDate.now();

        int curYear = curDate.getYear();
        int curCW = curDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        List<Week> weeks = this.loadWeeksAll();

        Optional<Week> curWeek = weeks.stream().filter(w -> w.getYear() == curYear && w.getCw() == curCW).findFirst();

        if(curWeek.isPresent()){
            List<TimeEntry> entries = new ArrayList<>();
            for(TimeEntry e : curWeek.get().getTimeEntries()){
                LocalDate entryDate = LocalDate.parse(e.getDate(), DATE_FORMAT);
                if(entryDate.isBefore(curDate) || entryDate.isEqual(curDate)){
                    entries.add(e);
                }
            }
            return calcTotalHours(entries);
        } else {
            return 0.0;
        }


//        return curWeek.isPresent() ? calcTotalHours(curWeek.get()): 0.0;
    }

    private Double calcTotalHours(List<TimeEntry> entries) {
        Optional<SettingsDTO> settings = settingsRepository.findById(1L);
        int break_duration = settings.isPresent() ? settings.get().getBreak_duration() : 0;

        int hours_without_break = 6;

        Duration duration = Duration.ZERO;

        for(TimeEntry entry: entries){
            Duration diff = calcTimeDifference(entry);
            duration = diff.toHours() > hours_without_break ? duration.plus(diff.minusMinutes(break_duration)): duration.plus(diff);
        }

        return (double)duration.toHours() + (double)duration.toMinutesPart()* (1.0/60.0);

//        return String.format("%02d:%02d",duration.toHours(),duration.toMinutesPart());

    }

    private Duration calcTimeDifference(TimeEntry entry) {
        LocalTime start = entry.getStartTime().isEmpty() ? null: LocalTime.parse(entry.getStartTime());
        LocalTime end = entry.getEndTime().isEmpty() ? null : LocalTime.parse(entry.getEndTime());

        return start == null || end == null ? Duration.ZERO: Duration.between(start, end);
    }
}
