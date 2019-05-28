package com.kruczek.calendar.month;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.kruczek.calendar.CalendarUtils;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Service
public class MonthService {

	private final MonthRepository monthRepository;

	@Autowired
	public MonthService(MonthRepository monthRepository) {
		this.monthRepository = monthRepository;
	}

	public void addMonth(final Month month) {
		Objects.requireNonNull(month, getNpeDescription("month"));
		month.setDays(CalendarUtils.generateDaysForMonth(month));

		monthRepository.save(month);
	}

	@Nullable
	public Month getMonthByName(final String monthName) {
		Objects.requireNonNull(monthName, getNpeDescription("month"));
		return monthRepository.findByName(java.time.Month.valueOf(monthName));
	}

/*	public Month getMonthByNameAndUsername(final String monthName, final String username){
		getMonthByName(monthName).getDays()
				.stream()
				.flatMap(day -> day.getTasks().stream())
				.filter(task -> task.getUser().getUsername().equals(username))
		.collect(Collectors.groupingBy());
	}*/
}
