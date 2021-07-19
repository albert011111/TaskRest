package com.kruczek.calendar.day;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kruczek.calendar.CalendarLogger;
import com.kruczek.calendar.month.Month;
import com.kruczek.calendar.month.MonthService;
import com.kruczek.exceptions.DayNotFoundException;
import com.kruczek.task.Task;

import static com.kruczek.utils.Commons.API;
import static com.kruczek.utils.Commons.DAYS;
import static com.kruczek.utils.Commons.ID;
import static com.kruczek.utils.Commons.MONTHS;
import static com.kruczek.utils.Commons.MONTH_NAME;

@RestController
@RequestMapping(API)
@CrossOrigin(value = "*", maxAge = 3600)
public class DayController implements CalendarLogger {
	private final MonthService monthService;
	private final DayService dayService;

	public DayController(MonthService monthService, DayService dayService) {
		this.monthService = monthService;
		this.dayService = dayService;
	}

	@Nullable
	@GetMapping(DAYS + MONTHS + MONTH_NAME)
	@Deprecated
	public List<Day> getDaysByMonth(@PathVariable String monthName) {
		LOGGER.debug("Fetching days process started");
		try {
			final Month month = monthService.getMonthByName(monthName.toUpperCase());
			return dayService.getDaysByMonth(month);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Illegal month name: {}", monthName);
			return Collections.emptyList();
		} finally {
			LOGGER.debug("Fetching days process finished");
		}
	}

	@Nullable
	@GetMapping(DAYS + ID)
	public Day getDayById(@PathVariable Long id) {
		LOGGER.debug("Fetching day with id={} started", id);
		try {
			return dayService.getDay(id).orElseThrow(() -> new DayNotFoundException(id));
		} catch (DayNotFoundException ex) {
			LOGGER.warn("Fetching day with id={} failed", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Day with id:" + id + " not found", ex);
		}
	}

	@PostMapping(DAYS + ID)
	public void addTaskToDay(@PathVariable Long id, @RequestBody Task task) {
		Optional<Day> dayOptional = dayService.getDay(id);
		Day day = dayOptional.orElseThrow(NullPointerException::new);
		day.addNewTask(task);

		dayService.save(day);
	}
}
