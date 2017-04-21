package Tests;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nu.xom.Element;
//import nu.xom.Elements;
import nu.xom.Attribute;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskImpl;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TaskListImpl;
import net.sf.memoranda.date.CalendarDate;

public class TaskTest {
	private static TaskList testList = null;
	private Element testElement = null;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testList = new TaskListImpl(CurrentProject.get());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	} //nada

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * Tests for all get methods:
	 * 	get start date
	 * 	get end date
	 * 	get effort
	 * 	get parent task
	 */
	
	@Test
	public void testGetEffort() {
		Attribute attr = testElement.getAttribute("effort");
		assertNotNull(attr);
	}
}
