package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.History;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.LOCListImpl;
import net.sf.memoranda.LinesofCode;
import net.sf.memoranda.DefectListImpl;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

@SuppressWarnings("unused")
public class LOCChartPanel extends JPanel implements ExampleChart<CategoryChart>{
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
	public LOCChartPanel(DailyItemsPanel _parentPanel){
		try {
			parentPanel = _parentPanel; // Parent panel is the date/info on top of the panel.			
			jbInit();	
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
  }
	@SuppressWarnings("unchecked")
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

			// Create LOC Chart
			    CategoryChart locChart = new CategoryChartBuilder().width(800).height(400).title("Lines of Code").xAxisTitle("Day").yAxisTitle("Lines of Code").build();
			 
			    // Customize Chart
			    locChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
			    locChart.getStyler().setHasAnnotations(true);
			    
			    // Series
			    locChart.addSeries("Lines of Code", thirtyDaysLOC(), dummyDataLOC());
			    
			    
			// Create Defects Chart
			    CategoryChart defectsChart = new CategoryChartBuilder().width(800).height(400).title("Defects").xAxisTitle("Day").yAxisTitle("Defects").build();
			 
			    // Customize Chart
			    defectsChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
			    defectsChart.getStyler().setHasAnnotations(true);
			 
			    // Series
			    defectsChart.addSeries("Defects", thirtyDaysLOC(), DefectsData());
			    
			    JPanel locChartPanel = new XChartPanel<CategoryChart>(locChart);
		        this.add(locChartPanel, BorderLayout.CENTER);
		        
		        JPanel defectsChartPanel = new XChartPanel<CategoryChart>(defectsChart);
		        this.add(defectsChartPanel, BorderLayout.SOUTH);
	}
	@Override
	public CategoryChart getChart() {
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List LOCData(){
		LOCList getData = CurrentProject.getLOCList();
		Vector<LinesofCode> vectorData = getData.getAllLOC();
		Vector<Integer> locData = new Vector();
		Integer value = 1;
		for(int k = 1; k < vectorData.size(); k++){
			if(vectorData.get(k) != null){
				locData.add(1);
//				if(vectorData.get(k+1).getDateCompleted() == vectorData.get(k).getDateCompleted()){
//					locData.add(value + 1);
//				}
			}else{
				locData.add(0);
			}
		}
		Object[] array = new Integer[30];
		//fill array with zeroes so there will not be any invalid values on pull
		for(int j = 0; j < array.length; j++){
			array[j] = 0;
		}
		ArrayList<Object> data = new ArrayList<Object>();
		array = (locData.toArray());
		for(int i = 0; i < array.length; i++){
			if(locData.elementAt(i) != null){
				data.add(((Integer) array[i]).intValue());
			}else{
				data.add(i, 0);
			}
		}
		return data;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List DefectsData(){
		DefectList getData = CurrentProject.getDefectList();
		Vector<Defect> vectorData = getData.getAllDefect();
		Vector<Integer> defectData = new Vector();
		//represents 30 days in the month to hold data
		Object[] array = new Integer[30];
		//fill array with zeroes so there will not be any invalid values on pull
		for(int j = 0; j < array.length; j++){
			array[j] = 0;
		}
		
		CalendarDate cur_date = vectorData.get(0).GetDateFound();
		int count = 0;
		for(int k = 1; k < vectorData.size(); k++){
			Defect d = vectorData.get(k);
			if (d.GetDateFound().equals(cur_date)){
				count = count + 1; 
				System.out.println(count);
			}
			
			
			/*
			if(vectorData.get(k) != null){
				defectData.add((Integer)vectorData.get(k).GetDefectNumber());
			}else{
				defectData.add(0);
			}*/
		}
		ArrayList<Object> data = new ArrayList<Object>();
		
		array = (defectData.toArray());
		for(int i = 0; i < array.length; i++){
			if(defectData.elementAt(i) != null){
				data.add(((Integer) array[i]).intValue());
			}else{
				data.add(i, 0);
			}
		}
		return data;
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList thirtyDaysLOC(){
//		LOCList getData = CurrentProject.getLOCList();
//		Vector<LinesofCode> vectorData = getData.getAllLOC();
		ArrayList<Integer> data = new ArrayList<Integer>();
		for(int i = 1; i <= 30; i++){
			data.add(i);
		}
		return data;
	}
	
	
	public List dummyDataLOC(){
		List<Integer> list = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < 30; i++){
			list.add(r.nextInt(100));
		}
		return list;
	}
	
	public List dummyDataDefects(){
		List<Integer> list = new ArrayList<Integer>();
		Random r = new Random();
		for(int i = 0; i < 30; i++){
			list.add(r.nextInt(35));
		}
		return list;
	}
//	//chart panel
//    CategoryChart chart = new CategoryChartBuilder().width(400).height(600).title("Lines of Code By Date").xAxisTitle("Date").yAxisTitle("Count").build();
// 
//    // Customize Chart
//    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
//    chart.getStyler().setAvailableSpaceFill(.96);
//    chart.getStyler().setOverlapped(true);
// 
//    // Series
//    Histogram histogram1 = new Histogram(getGaussianData(10000), 20, -20, 20);
//    Histogram histogram2 = new Histogram(getGaussianData(5000), 20, -20, 20);
//    chart.addSeries("Lines Of Code Per Day", histogram1.getxAxisData(), histogram1.getyAxisData());
//    chart.addSeries("Defects Found", histogram2.getxAxisData(), histogram2.getyAxisData());
//	
//	 JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
//        this.add(chartPanel, BorderLayout.SOUTH);
	
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
