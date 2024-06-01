package com.fhdw.webproject.postgresql.model;

import jakarta.persistence.*;

@Entity
@Table(name = "time_entries")
public class TimeEntryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "day")
    private String day;
    @Column(name = "date")
    private String date;
    @Column(name = "starttime")
    private String startTime;
    @Column(name = "endtime")
    private String endTime;

    @Column(name = "absent")
    private boolean absent;

    public TimeEntryDTO() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDay() {
        return day;
    }


    public void setDay(String day) {
        this.day = day;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}
