package com.kruczek.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.kruczek.calendar.day.Day;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarUtilsTest {
	private Day day;
	private List<com.kruczek.calendar.month.Month> months;
	private com.kruczek.calendar.month.Month aprilMonth;

	public CalendarUtilsTest() throws ParseException {
	}

	@Before
	public void setup() throws ParseException {
		final com.kruczek.calendar.month.Month month = new com.kruczek.calendar.month.Month();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2019-04-04");
		LocalDate localeDate = CalendarUtils.convertToLocalDate(date);
		day = new Day(date);

		Month mth = localeDate.getMonth();
		month.setName(mth);

		day.setMonth(month);
		month.setDays(Collections.singletonList(day));

		months = CalendarUtils.generateMonths();
		aprilMonth = CalendarUtils.generateMonth().apply(Month.APRIL);
	}


	@Test
	public void shouldReturnListWhenGenerateMonths() {
		//given
		//when
		int monthsSize = months.size();

		//then
		assertThat(monthsSize).isEqualTo(12);
	}

	@Test
	public void shouldReturnDecemberWhenGetLastItem() {
		//given
		//when
		Month monthName = months.get(months.size() - 1).getName();

		//then
		assertThat(monthName).isEqualTo(Month.DECEMBER);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void shouldThrowExceptionWhenGetItem12() {
		//given
		//when
		months.get(100);
	}

	@Test
	public void shouldListBeEmptyWhenMonthNotPopulated() {
		//given
		com.kruczek.calendar.month.Month month = aprilMonth;

		//when
		boolean isEmpty = month.getDays().isEmpty();

		//then
		assertThat(isEmpty).isTrue();
	}

	@Test
	public void shouldReturnDaysSizeWhenMonthPopulated() {
		//given
		com.kruczek.calendar.month.Month april = aprilMonth;

		//when
		CalendarUtils.populateMonth(april);
		int totalDaysInApril2019 = april.getDays().size();

		//then
		assertThat(totalDaysInApril2019).isEqualTo(30);
	}

	@Test
	public void shouldSundayBeHoliday() {
		//given
		DayOfWeek sunday = DayOfWeek.SATURDAY;

		//when
		boolean isHoliday = CalendarUtils.isHoliday(sunday);

		//then
		assertThat(isHoliday).isTrue();
	}

	@Test
	public void shouldNotMondayBeHoliday() {
		//given
		DayOfWeek monday = DayOfWeek.MONDAY;

		//when
		boolean isHoliday = CalendarUtils.isHoliday(monday);

		//then
		assertThat(isHoliday).isFalse();
	}

	@Test
	public void allWeekendDaysShouldBeHoliday() {
		//given
		com.kruczek.calendar.month.Month july = CalendarUtils.generateMonth().apply(Month.JULY);
		CalendarUtils.populateMonth(july);

		//when
		boolean areDaysHolidaysOnly = july.getDays().stream()
				.filter(day -> day.getDayOfWeek() == DayOfWeek.SATURDAY || day.getDayOfWeek() == DayOfWeek.SUNDAY)
				.allMatch(Day::isHoliday);

		//then
		assertThat(areDaysHolidaysOnly).isTrue();
	}



/*	@Test
	public void testGenerateDay() {
		final LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 1);

		int currentYear = localDate.getYear();
		Month currentMonth = localDate.getMonth();
		int monthLength = currentMonth.length(false);

		DayOfWeek from = DayOfWeek.from(localDate);

		System.out.println(currentYear);
		System.out.println(currentMonth);
		System.out.println(monthLength);
		System.out.println(from);

		List<com.kruczek.calendar.month.Month> months = CalendarUtils.generateMonths();
		System.out.println(months.size());
		months.forEach(month -> System.out.println(month.getName()));


		com.kruczek.calendar.month.Month month = months.get(3);

		System.out.println(month.getName());
		CalendarUtils.populateMonth(month);
		int i = Calendar.getInstance().get(Calendar.YEAR);


		assertThat(day.getDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY);
		assertThat(day.getMonth().getName()).isEqualTo(Month.APRIL);

	}*/
}
