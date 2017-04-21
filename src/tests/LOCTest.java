package tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.LinesofCode;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.LOCListImpl;

public class LOCTest {
	private static LOCList p_loclist = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		p_loclist = new LOCListImpl(CurrentProject.get());
	}
	
	@Before
	public void setUp() throws Exception {
		LinesofCode temp_loc = new LinesofCode();
		temp_loc.setLocNumber(0);
		temp_loc.setDateCompleted(CalendarDate.today());
		temp_loc.setLOCIntValue(2583);
		temp_loc.setDescription("I Wrote Something");
		
		p_loclist.addLOC(temp_loc);
	}
	
	@Test
	public void testGetLOC() {
		System.out.println("Testing GetLOC");
		
		LinesofCode temp = p_loclist.getLOC(0);
		assertEquals("Lines of Code Number", 0, temp.getLocNumber());
		assertEquals("Date Day", CalendarDate.today().getDay(), temp.getDateCompleted().getDay());
		assertEquals("Date Month", CalendarDate.today().getMonth(), temp.getDateCompleted().getMonth());
		assertEquals("Date Year", CalendarDate.today().getYear(), temp.getDateCompleted().getYear());
		assertEquals("Lines of Code", 2583, temp.getLOCIntValue());
		assertEquals("Description", "I Wrote Something", temp.getDescription());
	}

	@Test
	public void testRemoveLoc(){
		LinesofCode temp = new LinesofCode();
		temp.setLocNumber(1);
		temp.setDateCompleted(CalendarDate.today());
		temp.setLOCIntValue(200);
		temp.setDescription("This");
		
		p_loclist.addLOC(temp);
		p_loclist.removeLOC(1);
		
		assertNull(p_loclist.getLOC(1));
	}
}
