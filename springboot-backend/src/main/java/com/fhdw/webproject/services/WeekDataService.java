package com.fhdw.webproject.services;

import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
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
import java.util.Optional;


@Service
public class WeekDataService {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private SettingsService settingsService;

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");

    public List<Week> loadWeeksAll() {
        List<Week> result = new ArrayList<>();

        List<TimeEntryDTO> timeEntries = timeEntryRepository.findAll();

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

    private TimeEntry convertDTOtoTimeEntry(TimeEntryDTO entry) {
        TimeEntry result = new TimeEntry();
        result.setId(entry.getId());
        result.setDate(entry.getDate());
        result.setDay(entry.getDay());
        result.setStartTime(entry.getStartTime());
        result.setEndTime(entry.getEndTime());
        result.setAbsent(entry.isAbsent());
        return result;
    }

    private TimeEntryDTO convertTimeEntryToDTO(TimeEntry entry) {
        TimeEntryDTO result = new TimeEntryDTO();
        result.setId(entry.getId());
        result.setDate(entry.getDate());
        result.setDay(entry.getDay());
        result.setStartTime(entry.getStartTime());
        result.setEndTime(entry.getEndTime());
        result.setAbsent(entry.getAbsent());
        return result;
    }

    public void saveWeek(Week week) {
        for (TimeEntry entry : week.getTimeEntries()) {
            TimeEntryDTO dto = convertTimeEntryToDTO(entry);
            timeEntryRepository.save(dto);
        }
    }

    public Double getWorkedHoursCurrWeek() {
        return getWorkedHoursByWeek(LocalDate.now(), false);
    }

    public Double getWorkedHoursByWeek(LocalDate date, boolean fullWeek) {


        int curYear = date.getYear();
        int curCW = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        List<Week> weeks = this.loadWeeksAll();

        Optional<Week> curWeek = weeks.stream().filter(w -> w.getYear() == curYear && w.getCw() == curCW).findFirst();

        if (curWeek.isPresent()) {
            List<TimeEntry> entries = new ArrayList<>();
            for (TimeEntry e : curWeek.get().getTimeEntries()) {
                if (!e.getAbsent()) {
                    LocalDate entryDate = LocalDate.parse(e.getDate(), DATE_FORMAT);
                    if (fullWeek) {
                        entries.add(e);
                    } else if (entryDate.isBefore(date) || entryDate.isEqual(date)) {
                        entries.add(e);
                    }
                }
            }
            return calcTotalHours(entries);
        } else {
            return 0.0;
        }
    }

    private Double calcTotalHours(List<TimeEntry> entries) {

        Duration duration = Duration.ZERO;

        for (TimeEntry entry : entries) {
            Duration diff = calcTimeDifference(entry);
            diff = subtractBreakTimeIfNeccessary(diff);
            duration = duration.plus(diff);
        }

        return durationToDouble(duration);

    }

    private Duration calcTimeDifference(TimeEntry entry) {
        LocalTime start = entry.getStartTime() == null || entry.getStartTime().isEmpty() ? null : LocalTime.parse(entry.getStartTime());
        LocalTime end = entry.getEndTime() == null || entry.getEndTime().isEmpty() ? null : LocalTime.parse(entry.getEndTime());

        return start == null || end == null ? Duration.ZERO : Duration.between(start, end);
    }

    private double durationToDouble(Duration duration) {
        return (double) duration.toHours() + (double) duration.toMinutesPart() * (1.0 / 60.0);
    }

    private Duration subtractBreakTimeIfNeccessary(Duration duration) {
        int break_duration = settingsService.loadSettings().getBreakDuration();
        int hours_without_break = 6;

        return duration.toHours() > hours_without_break ? duration.minusMinutes(break_duration) : duration;

    }

    public Double getCurBalance() {
        float weeklyHours = settingsService.loadSettings().getWeeklyHours();

        List<TimeEntryDTO> entriesDTO = timeEntryRepository.findAll();
        List<TimeEntry> entries = new ArrayList<>();

        for (TimeEntryDTO e : entriesDTO) {
            entries.add(convertDTOtoTimeEntry(e));
        }

        LocalDate refDate = LocalDate.now().plusDays(1);

        List<TimeEntry> filteredEntries = entries
                .stream().filter(e -> LocalDate.parse(e.getDate(), DATE_FORMAT).isBefore(refDate))
                .filter(e -> !e.getAbsent())
                .toList();

        double hoursPerDay = weeklyHours / 5;
        double requiredHours = filteredEntries.size() * hoursPerDay;

        double sum = 0;

        for (TimeEntry e : filteredEntries) {
            Duration diff = this.calcTimeDifference(e);
            diff = subtractBreakTimeIfNeccessary(diff);
            sum += durationToDouble(diff);
        }

        return sum - requiredHours;

    }

    public void deleteWeek(Week week) {
        for (TimeEntry e : week.getTimeEntries()) {
            timeEntryRepository.deleteById(Long.valueOf(e.getId()));
        }
    }
}
