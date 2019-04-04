package com.kruczek.calendar.day;

import com.kruczek.calendar.month.MonthDTO;
import com.kruczek.task.TaskDTO;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;


public class DayDTO {
    private long id;
    private Date date;
    private DayOfWeek dayOfWeek;
    private MonthDTO monthDTO;
    private List<TaskDTO> tasks;

    private boolean isHoliday;

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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public MonthDTO getMonthDTO() {
        return monthDTO;
    }

    public void setMonthDTO(MonthDTO monthDTO) {
        this.monthDTO = monthDTO;
    }


}
