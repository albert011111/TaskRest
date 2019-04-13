package com.kruczek.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.lang.Nullable;

import com.kruczek.calendar.day.Day;
import com.kruczek.utils.NpeChecker;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

public final class CalendarUtils {

	private CalendarUtils() {
	}

	public static LocalDate convertToLocalDate(final Date date) {
		Objects.requireNonNull(date, getNpeDescription("date"));
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Function<LocalDate, Date> convertToDate() {
		return local -> Date.from(local.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
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

	public static List<com.kruczek.calendar.month.Month> generateMonths() {
		final List<com.kruczek.calendar.month.Month> months = new ArrayList<>();

		Arrays.stream(Month.values())
				.forEachOrdered(month -> {
					com.kruczek.calendar.month.Month monthEntity = generateMonth().apply(month);
					months.add(monthEntity);
				});
		return months;
	}

	public static Function<Month, com.kruczek.calendar.month.Month> generateMonth() {
		return mth -> {
			com.kruczek.calendar.month.Month monthEntity = new com.kruczek.calendar.month.Month();
			monthEntity.setDays(new ArrayList<>());
			monthEntity.setName(mth);
			return monthEntity;
		};
	}

	public static void populateMonth(com.kruczek.calendar.month.Month month) {
		populateMonth(null, month);
	}

	public static void populateMonth(@Nullable Integer year, com.kruczek.calendar.month.Month month) {
		Objects.requireNonNull(month, getNpeDescription("month"));
		final List<Day> days = new ArrayList<>();

		for (int dayIdx = 1; dayIdx <= month.getName().length(false); dayIdx++) {
			final LocalDate localDate = LocalDate.of(year == null
					? Calendar.getInstance().get(Calendar.YEAR) : year, month.getName(), dayIdx);
			days.add(retrieveNewDay(month, localDate).get());
		}

		month.setDays(days);
	}

	public static Supplier<Day> retrieveNewDay(com.kruczek.calendar.month.Month month, LocalDate date) {
		Objects.requireNonNull(date, NpeChecker.getNpeDescription("date"));
		return () -> {
			Day newDay = new Day();
			newDay.setDate(convertToDate().apply(date));
			newDay.setMonth(month);
			newDay.setDayOfWeek(DayOfWeek.from(date));
			newDay.setHoliday(isHoliday(DayOfWeek.from(date)));
			return newDay;
		};
	}

	public static boolean isHoliday(DayOfWeek day) {
		Objects.requireNonNull(day, getNpeDescription("day"));
		final Function<DayOfWeek, Boolean> isHoliday = dayOfWeek ->
				dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY;

		return isHoliday.apply(day);
	}
}
