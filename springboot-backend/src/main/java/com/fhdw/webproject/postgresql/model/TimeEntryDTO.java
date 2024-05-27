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
    @Column(name ="date")
    private String date;
    @Column(name ="starttime")
    private String starttime;
    @Column(name = "endtime")
    private String endtime;

    public TimeEntryDTO() {
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }


}
