package com.kruczek.calendar;

import com.kruczek.calendar.day.Day;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarUtilsTest {
    private LocalDate localeDate;
    private Date date;

    public CalendarUtilsTest() throws ParseException {
    }

    @Before
    public void setup() throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.parse("2019-04-04");
        localeDate = CalendarUtils.convertToLocalDate(date);
    }

    @Test
    public void testGenerateDay() {
        Month month = localeDate.getMonth();
        DayOfWeek dayOfWeek = localeDate.getDayOfWeek();
        int year = localeDate.getYear();

        final Day day = new Day(date);

        System.out.println(month);
        System.out.println(dayOfWeek);
        System.out.println(year);

        assertThat(day.getDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY);
        assertThat(day.getMonth()).isEqualTo(Month.DECEMBER);

    }
}
