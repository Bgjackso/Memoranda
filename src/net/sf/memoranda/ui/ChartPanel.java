package net.sf.memoranda.ui;

//imports
import org.jfree.chart.JFreeChart;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ChartPanel {
	
	ChartPanel(JFreeChart chart) {
		//Design of chart
		
	}
	
	
	//where the data to populate the chart comes from 
	Date getStartDate(Date today){
		Date start;
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		start = cal.getTime();
				
		return start;
	}
	
	//set date range to display
	
	//pull data for loc during date range
	
	//pull data for bugs during date range
	
	
}