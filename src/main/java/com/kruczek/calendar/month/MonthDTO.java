package com.kruczek.calendar.month;

import com.kruczek.calendar.day.Day;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthDTO {
    private long id;
    private java.time.Month name;
    private List<Day> days = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public java.time.Month getName() {
        return name;
    }

    public void setName(Month name) {
        this.name = name;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
