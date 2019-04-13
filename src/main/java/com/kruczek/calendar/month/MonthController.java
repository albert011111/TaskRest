package com.kruczek.calendar.month;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruczek.calendar.CalendarLogger;
import com.kruczek.calendar.CalendarUtils;

import static com.kruczek.utils.Commons.API;
import static com.kruczek.utils.Commons.MONTHS;
import static com.kruczek.utils.Commons.MONTH_NAME;

@RestController
@RequestMapping(API)
public class MonthController implements CalendarLogger {
	private final MonthService monthService;

	@Autowired
	public MonthController(MonthService monthService) {
		this.monthService = monthService;
	}

	@PutMapping(MONTHS)
	public List<Month> addMonthsByYear() {
		LOGGER.info("Months generating process started");
		try {
			List<Month> months = CalendarUtils.generateMonths();
			months.forEach(monthService::addMonth);
			LOGGER.info("Months generating process succesfully finished");

			return months;
		} catch (Exception e) {
			LOGGER.error("Adding months list to DB failed.", e);
		}
		return new ArrayList<>();
	}

	@GetMapping(MONTHS + MONTH_NAME)
	public Optional<Month> getMonthByName(@PathVariable String name) {
		return Optional.of(monthService.getMonthByName(name.toUpperCase()));
	}
}