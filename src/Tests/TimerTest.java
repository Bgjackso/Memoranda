package Tests;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.PSPTimer;
//import net.sf.memoranda.ui.PSPPanel.PSPTimer;

public class TimerTest{
	private Thread runThread;
	private boolean running;
	private boolean paused;
    private long summedTime = 0;
	  
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JLabel time = new JLabel("Time");
		PSPTimer timer = new PSPTimer();
		//have not imported PSPTimer from PSPPanel class
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	} //dont touch

	@Before
	public void setUp() throws Exception {
		running = true;
	    paused = false;
	    runThread = new Thread();
	    runThread.start();
	}

	@After
	public void tearDown() throws Exception {
	} //dont touch

	/*public void run() {
		/*long startTime = System.currentTimeMillis();
		      
		while(running && !paused) {
			//update(summedTime + (System.currentTimeMillis() - startTime)); //timer causing issues
		}
		/*if(paused){
			summedTime += System.currentTimeMillis() - startTime;
		}else{
			summedTime = 0;
		} 
	}
	
	/*@Test
	public void update(long dTime){
		  time.setText(String.valueOf(String.valueOf((dTime/1000)/60) + ":" 
				  + String.valueOf((dTime/1000)%60) + ":" + String.valueOf((dTime)%1000)));
	  } */
	
	@Test
	public void test(){
		//assert summedTime not null?
		//assert update value not null?
		assertNotNull(summedTime);
		//assertNotSame(0, summedTime); ??
	}
}
