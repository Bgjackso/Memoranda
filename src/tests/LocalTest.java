package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.util.Local;

public class LocalTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCurrentLocale() {
		Locale result = Local.getCurrentLocale();
		assertTrue(result.equals(Locale.US));
	}

	@Test
	public void testGetMonthNames() {
		String[] result = Local.getMonthNames();
		String[] tester = {"January",
	            "February",
	            "March",
	            "April",
	            "May",
	            "June",
	            "July",
	            "August",
	            "September",
	            "October",
	            "November",
	            "December" };
		assertTrue(Arrays.equals(result, tester));
	}

	@Test
	public void testGetWeekdayNames() {
		String[] result = Local.getWeekdayNames();
		String[] tester = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		assertTrue(Arrays.equals(result, tester));
	}

	@Test
	public void testGetMonthName() {
		int m = 0;
		String result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("January"));
		m = 1;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("February"));
		m = 2;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("March"));
		m = 3;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("April"));
		m = 4;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("May"));
		m = 5;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("June"));
		m = 6;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("July"));
		m = 7;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("August"));
		m = 8;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("September"));
		m = 9;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("October"));
		m = 10;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("November"));
		m = 11;
		result = Local.getMonthName(m);
		assertTrue(result. equalsIgnoreCase("December"));
	}

	@Test
	public void testGetWeekdayName() {
		int wd = 1;
		String result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Sun"));
		wd = 2;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Mon"));
		wd = 3;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Tue"));
		wd = 4;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Wed"));
		wd = 5;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Thu"));
		wd = 6;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Fri"));
		wd = 7;
		result = Local.getWeekdayName(wd);
		assertTrue(result.equalsIgnoreCase("Sat"));
		
	}

	@Test
	public void testGetYears() {
		Object[] result = Local.getYears();
		ArrayList<Integer> years_tmp = new ArrayList<Integer>();
  	  for(int years = 2000; years<=2999; years++){
  	  years_tmp.add(years);
  	}
  	  Object[] tester = years_tmp.toArray();
		assertTrue(Arrays.equals(result, tester));
	}

}

