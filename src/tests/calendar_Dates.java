package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import net.sf.memoranda.date.CalendarDate;

public class calendar_Dates {
		private CalendarDate date1;
		private CalendarDate date2;
		private CalendarDate date3;
		private CalendarDate date4;

		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
		}

		@AfterClass
		public static void tearDownAfterClass() throws Exception {
		}

		@Before
		public void setUp() throws Exception {
			date1 = new CalendarDate(2,2,2017);
			date2 = new CalendarDate(3,2,2017);
			date3 = new CalendarDate(2,2,2017);
			date4 = null;
		}

		@After
		public void tearDown() throws Exception {
		}

		@Test
		public void equalsTest() {
			assertTrue(date1.equals(date3));
			assertFalse(date1.equals(date2));
			assertNotNull(date1.equals(date1));
		}
		
		@Test
		public void beforeTest() {
			assertTrue(date1.before(date2));
			assertFalse(date2.before(date1)); //Pass with assertFalse
		}
		
		@Test
		public void afterTest() {
			assertFalse(date1.after(date2));
			assertTrue(date2.after(date1));
		}
		@Test
		public void inPeriodTest() {
			
			assertTrue(date1.inPeriod(date1, date2));
			assertNotNull(date1.inPeriod(date1, date2));
			assertNotNull(date2.inPeriod(date1, date2));
			assertTrue(date1.inPeriod(date4, date4));
			assertFalse(date2.inPeriod(date4, date1));
		}

	}
