package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.DefectListImpl;
import net.sf.memoranda.date.CalendarDate;

public class DefectListImplTest {
	private static DefectList p_defectList = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p_defectList = new DefectListImpl(CurrentProject.get());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Defect temp_defect = new Defect();
		temp_defect.SetDateFound(CalendarDate.today());
		temp_defect.SetDefectNumber(0);
		temp_defect.SetDescription("a description");
		temp_defect.SetInjected("phase injected");
		temp_defect.SetCorrected("phase corrected");
		temp_defect.SetType("a type");
		temp_defect.SetFixTime(20);
		p_defectList.addDefect(temp_defect);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDefect() {
		System.out.println("Testing GetDefect(p_defect_number) in DefectListImpl class");

		Defect temp = p_defectList.getDefect(0);
		assertEquals("Checking defect num", 0, temp.GetDefectNumber());
		assertEquals("Checking date day", CalendarDate.today().getDay(), temp.GetDateFound().getDay());
		assertEquals("Checking date month", CalendarDate.today().getMonth(), temp.GetDateFound().getMonth());
		assertEquals("Checking date year", CalendarDate.today().getYear(), temp.GetDateFound().getYear());
		assertEquals("Checking description", "a description", temp.GetDescription());
		assertEquals("Checking injected", "phase injected", temp.GetInjected());
		assertEquals("Checking corrected", "phase corrected", temp.GetCorrected());
		assertEquals("Checking type", "a type", temp.GetType());
		assertEquals("Checking time", 20, temp.GetFixTime());
	}

	@Test
	public void testAddDefect() {
		System.out.println("Testing AddDefect(p_defect) in DefectListImpl class");

		Defect temp_defect = new Defect();
		temp_defect.SetDateFound(CalendarDate.today());
		temp_defect.SetDefectNumber(1);
		temp_defect.SetDescription("a description 1");
		temp_defect.SetInjected("phase injected 1");
		temp_defect.SetCorrected("phase corrected 1");
		temp_defect.SetType("a type 1");
		temp_defect.SetFixTime(25);

		p_defectList.addDefect(temp_defect);
		Defect temp = p_defectList.getDefect(1);
		assertEquals("Checking defect num", 1, temp.GetDefectNumber());
		assertEquals("Checking date day", CalendarDate.today().getDay(), temp.GetDateFound().getDay());
		assertEquals("Checking date month", CalendarDate.today().getMonth(), temp.GetDateFound().getMonth());
		assertEquals("Checking date year", CalendarDate.today().getYear(), temp.GetDateFound().getYear());
		assertEquals("Checking description", "a description 1", temp.GetDescription());
		assertEquals("Checking injected", "phase injected 1", temp.GetInjected());
		assertEquals("Checking corrected", "phase corrected 1", temp.GetCorrected());
		assertEquals("Checking type", "a type 1", temp.GetType());
		assertEquals("Checking time", 25, temp.GetFixTime());
	}

	@Test
	public void testRemoveDefect() {
		Defect temp_defect = new Defect();
		temp_defect.SetDateFound(CalendarDate.today());
		temp_defect.SetDefectNumber(2);
		temp_defect.SetDescription("a description 2");
		temp_defect.SetInjected("phase injected 2");
		temp_defect.SetCorrected("phase corrected 2");
		temp_defect.SetType("a type 2");
		temp_defect.SetFixTime(30);

		p_defectList.addDefect(temp_defect);
		p_defectList.removeDefect(2);

		assertNull(p_defectList.getDefect(2));
	}

	@Test
	public void testGetAllDefectsCount() {
		p_defectList.removeDefect(0);
		p_defectList.removeDefect(1);
		p_defectList.removeDefect(2);

		Defect temp_defect = new Defect();
		temp_defect.SetDateFound(CalendarDate.today());
		temp_defect.SetDefectNumber(0);
		temp_defect.SetDescription("a description");
		temp_defect.SetInjected("phase injected");
		temp_defect.SetCorrected("phase corrected");
		temp_defect.SetType("a type");
		temp_defect.SetFixTime(20);
		p_defectList.addDefect(temp_defect);

		assertEquals("Checking number of defects", 1, p_defectList.getAllDefectsCount());
	}
}
