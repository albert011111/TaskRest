package com.kruczek.calendar.day;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kruczek.calendar.CalendarUtils;
import com.kruczek.calendar.month.Month;
import com.kruczek.task.Task;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

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
	private List<Task> tasks = new ArrayList<>();

	@Column(columnDefinition = "boolean default false")
	private boolean isHoliday;

	public Day() {
	}

	public Day(final Date date) {
		this.date = Objects.requireNonNull(date, getNpeDescription("date"));
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
