package net.sf.memoranda.ui;

//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
////imports
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberTickUnit;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.category.StackedBarRenderer;
//import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
//import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.time.DateRange;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;
//import org.jfree.data.Range;

//import javafx.scene.chart.NumberAxis;

//import java.awt.BasicStroke;
//import java.awt.BorderLayout;
//import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

//import javax.swing.BorderFactory;
//import javax.swing.JPanel;

//import net.sf.memoranda.*;
//
//public class ChartPanelUI extends JPanel{
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	XYSeriesCollection defects;
//	XYSeriesCollection linesOfCode;
//	CategoryDataset dataset;
//	Date dayStart = getStartDate(Calendar.getInstance().getTime());
//	Date today = Calendar.getInstance().getTime();
//	SimpleDateFormat sdf = new SimpleDateFormat("mmDDyyyy");
//	
//	public ChartPanelUI(){
//		super(new BorderLayout());
		
		//Design of chart
//		JFreeChart chart = ChartFactory.createStackedBarChart("Code Defects and Lines Of Code by Date", "Date", 
//				"Lines of Code/Defects", dataset, 
//				PlotOrientation.HORIZONTAL, true, false, false);
//		chart.setBackgroundPaint(Color.white);
//		ChartPanel chartPanel = new ChartPanel(chart);
//		chartPanel.setBorder(BorderFactory.createCompoundBorder
//				(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(Color.black)));
//	      
//	      XYPlot plot = chart.getXYPlot();
//	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//	      renderer.setSeriesPaint(0, Color.red);
//	      renderer.setSeriesStroke(0, new BasicStroke(1.0f));
//	      
//	      StackedBarRenderer barChart = new StackedBarRenderer();
//	      
//	      NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
//	      yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//	      yAxis.setFixedAutoRange(5000);
//	      
//	      NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
//	      xAxis.setTickUnit(new NumberTickUnit(1000));
//	      xAxis.setFixedAutoRange(10000);
	      
//	      DateRange xRange = new DateRange((dayStart),(today));
	      
//	      plot.setRenderer(renderer);
//	      add(chartPanel);
		//Draw the loc bars
		
		//Draw the defects bars
		
	
//	}
	
	
	//where the data to populate the chart comes from 
//	Date getStartDate(Date today){
//		Date start;
//		Calendar cal = new GregorianCalendar();
//		cal.setTime(today);
//		cal.add(Calendar.DAY_OF_MONTH, -30);
//		start = cal.getTime();
//				
//		return start;
//	}
	
	//set date range to display
	
	//pull data for loc during date range
//	private static XYDataset createLOCbars(){
//		XYSeries locByDate = new XYSeries(dayStart);//not sure if right
//		return null;
//	}
	
	//pull data for bugs during date range
	
	 /**
	    * Adds an value with time stamp to the data set to draw the graph.
	    * 
	    * @param millis   the current time stamp.
	    * @param value      the value matching the time stamp
	    */
//	   public void addValuesObservation(double millis, double value){
//	      this.analogue1.add(millis, value);
//	   }
//}
/**
 * Histogram Overlapped
 * 
 * Demonstrates the following:
 * <ul>
 * <li>Histogram
 * <li>Bar Chart styles - overlapped, bar width
 */
public class ChartPanelUI implements ExampleChart<CategoryChart> {
 
  public static void main(String[] args) {
 
    ExampleChart<CategoryChart> exampleChart = new ChartPanelUI();
    CategoryChart chart = exampleChart.getChart();
    new SwingWrapper<CategoryChart>(chart).displayChart();
  }
	  @Override
	  public CategoryChart getChart() {
	 
	    // Create Chart
	    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram").xAxisTitle("Mean").yAxisTitle("Count").build();
	 
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setAvailableSpaceFill(.96);
	    chart.getStyler().setOverlapped(true);
	 
	    // Series
	    Histogram histogram1 = new Histogram(getGaussianData(10000), 20, -20, 20);
	    Histogram histogram2 = new Histogram(getGaussianData(5000), 20, -20, 20);
	    chart.addSeries("histogram 1", histogram1.getxAxisData(), histogram1.getyAxisData());
	    chart.addSeries("histogram 2", histogram2.getxAxisData(), histogram2.getyAxisData());
	 
	    return chart;
	  }
	 
	  private List<Double> getGaussianData(int count) {
	 
	    List<Double> data = new ArrayList<Double>(count);
	    Random r = new Random();
	    for (int i = 0; i < count; i++) {
	      data.add(r.nextGaussian() * 10);
	    }
	    return data;
	  }
	 
	}
