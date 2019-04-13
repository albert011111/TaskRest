package com.kruczek.calendar.month;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MonthRepository extends JpaRepository<Month, Integer> {
	Month findByName(java.time.Month month);
}
