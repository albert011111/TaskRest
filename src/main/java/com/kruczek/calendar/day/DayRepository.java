package com.kruczek.calendar.day;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kruczek.calendar.month.Month;

@Repository
interface DayRepository extends JpaRepository<Day, Long> {
	List<Day> getDaysByMonth(Month month);
}
