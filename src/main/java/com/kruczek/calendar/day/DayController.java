package com.kruczek.calendar.day;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.calendar.CalendarLogger;
import com.kruczek.calendar.month.Month;
import com.kruczek.calendar.month.MonthService;
import com.kruczek.task.Task;

import static com.kruczek.utils.Commons.API;
import static com.kruczek.utils.Commons.DAYS;
import static com.kruczek.utils.Commons.ID;
import static com.kruczek.utils.Commons.MONTH_NAME;

@RestController
@RequestMapping(API)
@CrossOrigin(value = "*", maxAge = 3600)
public class DayController implements CalendarLogger {
	private final MonthService monthService;
	private final DayService dayService;

	@Autowired
	public DayController(MonthService monthService, DayService dayService) {
		this.monthService = monthService;
		this.dayService = dayService;
	}

	//TODO replace with getDaysByMonthId(monthId)
	@Nullable
	@GetMapping(DAYS + MONTH_NAME)
	@Deprecated
	public List<Day> getDaysByMonth(@PathVariable String monthName) {
		LOGGER.info("Fetching days process started");
		try {
			final Month month = monthService.getMonthByName(monthName.toUpperCase());
			return dayService.getDaysByMonth(month);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Illegal month name: {}", monthName);
			return null;
		} finally {
			LOGGER.info("Fetching days process finished");
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
