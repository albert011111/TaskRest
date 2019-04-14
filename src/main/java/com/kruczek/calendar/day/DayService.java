package com.kruczek.calendar.day;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kruczek.calendar.month.Month;
import com.kruczek.utils.NpeChecker;

@Service
public class DayService {
	private final DayRepository dayRepository;

	@Autowired
	public DayService(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}

	public void save(final Day day) {
		dayRepository.save(day);
	}

	public List<Day> getDaysByMonth(final Month month) {
		Objects.requireNonNull(month, NpeChecker.getNpeDescription("month"));

		return dayRepository.getDaysByMonth(month);
	}

	public Optional<Day> getDay(Long dayId) {
		return dayRepository.findById(dayId);
	}

}
