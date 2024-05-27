package com.fhdw.webproject.postgresql.model;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class SettingsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="user_name")
    private String user_name;
    @Column(name = "weekly_hours")
    private float weekly_hours;
    @Column(name ="break_duration")
    private Integer break_duration;

    public String getUser_name() {
        return user_name;
    }

    public Integer getId() {
        return id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public float getWeekly_hours() {
        return weekly_hours;
    }

    public void setWeekly_hours(float weekly_hours) {
        this.weekly_hours = weekly_hours;
    }

    public Integer getBreak_duration() {
        return break_duration;
    }

    public void setBreak_duration(Integer break_duration) {
        this.break_duration = break_duration;
    }
}
