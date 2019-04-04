package com.kruczek.calendar.day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DayRepository extends JpaRepository<Day, Long> {
}
