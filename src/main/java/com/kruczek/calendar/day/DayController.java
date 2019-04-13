package com.kruczek.calendar.day;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.calendar.CalendarLogger;
import com.kruczek.calendar.month.Month;
import com.kruczek.calendar.month.MonthService;
import com.kruczek.utils.Commons;

import static com.kruczek.utils.Commons.API;

@RestController
@RequestMapping(API)
public class DayController implements CalendarLogger {
	private final MonthService monthService;
	private final DayService dayService;

	@Autowired
	public DayController(MonthService monthService, DayService dayService) {
		this.monthService = monthService;
		this.dayService = dayService;
	}

	@GetMapping
	@RequestMapping(Commons.MONTH_NAME)
	public List<Day> getDaysByMonth(@PathVariable String name) {
//		LOGGER.info("");
		try {
			final Month month = monthService.getMonthByName(name);
			return dayService.getDaysByMonth(month);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Illegal month name: {}", name);
			return new ArrayList<>();
		}
	}
}
