package com.kruczek.calendar.month;

import com.kruczek.calendar.day.Day;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Month {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private java.time.Month name;

    @OneToMany(mappedBy = "month")
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

    public void setName(java.time.Month name) {
        this.name = name;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
