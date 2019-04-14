package com.kruczek.calendar.month;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kruczek.calendar.day.Day;

@Entity
public class Month {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private java.time.Month name;

	@OneToMany(mappedBy = "month", cascade = CascadeType.ALL)
	@JsonBackReference
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
