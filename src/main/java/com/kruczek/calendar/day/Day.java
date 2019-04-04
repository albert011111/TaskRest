package com.kruczek.calendar.day;

import com.kruczek.calendar.CalendarUtils;
import com.kruczek.calendar.month.Month;
import com.kruczek.task.Task;
import com.kruczek.utils.NpeChecker;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Day implements Serializable {
    private static final long serialVersionUID = -3989654440890545362L;

    @Id
    @GeneratedValue
    private long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "month_id")
    private Month month;

    @OneToMany
    private List<Task> tasks;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isHoliday;

    public Day() {
    }

    public Day(Date date) {
        this.date = Objects.requireNonNull(date, NpeChecker.getNpeDescription("date"));
        this.dayOfWeek = CalendarUtils.setupValue(CalendarUtils.retrieveDayOfWeek(), date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
