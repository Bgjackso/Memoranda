package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class DefectsChartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout = new BorderLayout();
  
	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();

	JScrollPane scrollPane = new JScrollPane();

	DailyItemsPanel parentPanel = null;
	
	//public class Chart{
	
	// Things inside the panel that people need to see!
	public DefectsChartPanel(DailyItemsPanel _parentPanel){
		try {
			parentPanel = _parentPanel; // Parent panel is the date/info on top of the panel.			
			jbInit();	
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
  }
	// inside the panel
	void jbInit() throws Exception {
		toolBar.setFloatable(false);
		this.setLayout(borderLayout);
    
		scrollPane.getViewport().setBackground(Color.white);
		this.add(scrollPane, BorderLayout.CENTER);
		
		// Back button to go back one day previous to current date
		historyBackB.setAction(History.historyBackAction);
		historyBackB.setFocusable(false);
		historyBackB.setBorderPainted(false);
		historyBackB.setToolTipText(Local.getString("History back"));
		historyBackB.setRequestFocusEnabled(false);
		historyBackB.setPreferredSize(new Dimension(24, 24));
		historyBackB.setMinimumSize(new Dimension(24, 24));
		historyBackB.setMaximumSize(new Dimension(24, 24));
		historyBackB.setText("");

		// Forward button to go forward one day from the current date. 
		historyForwardB.setAction(History.historyForwardAction);
		historyForwardB.setBorderPainted(false);
		historyForwardB.setFocusable(false);
		historyForwardB.setPreferredSize(new Dimension(24, 24));
		historyForwardB.setRequestFocusEnabled(false);
		historyForwardB.setToolTipText(Local.getString("History forward"));
		historyForwardB.setMinimumSize(new Dimension(24, 24));
		historyForwardB.setMaximumSize(new Dimension(24, 24));
		historyForwardB.setText("");
		
		// Adding the buttons to the toolbar and adding the tools bar
		toolBar.add(historyBackB, null);
		toolBar.add(historyForwardB, null);
		this.add(toolBar, BorderLayout.NORTH);	
//
//			    ExampleChart<CategoryChart> exampleChart = new BarChart01();
//			    CategoryChart chart = exampleChart.getChart();
//			    new SwingWrapper<CategoryChart>(chart).displayChart();

			 
			    // Create Chart
			    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();
			 
			    // Customize Chart
			    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
			    chart.getStyler().setHasAnnotations(true);
			 
			    // Series
			    chart.addSeries("test 1", Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }), Arrays.asList(new Integer[] { 4, 5, 9, 6, 5 }));

		
//		//chart panel
//	    CategoryChart chart = new CategoryChartBuilder().width(400).height(600).title("Lines of Code By Date").xAxisTitle("Date").yAxisTitle("Count").build();
//	 
//	    // Customize Chart
//	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
//	    chart.getStyler().setAvailableSpaceFill(.96);
//	    chart.getStyler().setOverlapped(true);
//	 
//	    // Series
//	    Histogram histogram1 = new Histogram(getGaussianData(10000), 20, -20, 20);
//	    Histogram histogram2 = new Histogram(getGaussianData(5000), 20, -20, 20);
//	    chart.addSeries("Lines Of Code Per Day", histogram1.getxAxisData(), histogram1.getyAxisData());
//	    chart.addSeries("Defects Found", histogram2.getxAxisData(), histogram2.getyAxisData());
//		
//		 JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
//	        this.add(chartPanel, BorderLayout.SOUTH);
	}
	
//  private List<Double> getGaussianData(int count) {
//	   	 
//	    List<Double> data = new ArrayList<Double>(count);
//	    Random r = new Random();
//	    for (int i = 0; i < count; i++) {
//	      data.add(r.nextGaussian() * 10);
//	    }
//	    return data;
//	  }
	}

