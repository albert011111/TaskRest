package com.kruczek.calendar;

import com.kruczek.calendar.day.Day;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

public final class CalendarUtils {


    private CalendarUtils() {
    }

    public static Day generateDay(Date date) {

        new Day(date);

        return new Day();
    }

    public static LocalDate convertToLocalDate(final Date date) {
        Objects.requireNonNull(date, getNpeDescription("date"));
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static DayOfWeek retrieveDayOfWeek(final Date date) {
        return convertToLocalDate(date).getDayOfWeek();
    }

    public static java.time.Month retrieveMonth(final Date date) {
        return convertToLocalDate(date).getMonth();
    }

    public static Function<Date, java.time.Month> retrieveMonth() {
        return date -> convertToLocalDate(date).getMonth();
    }

    public static Function<Date, DayOfWeek> retrieveDayOfWeek() {
        return CalendarUtils::retrieveDayOfWeek;
    }

    public static <T, R> R setupValue(Function<T, R> converter, T data) {
        Objects.requireNonNull(converter, getNpeDescription("converter"));
        Objects.requireNonNull(data, getNpeDescription("data"));
        return converter.apply(data);
    }


}
