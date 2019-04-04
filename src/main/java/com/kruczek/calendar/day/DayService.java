package com.kruczek.calendar.day;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    public void save(final Day day) {
        dayRepository.save(day);
    }

}
