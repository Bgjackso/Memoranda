package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import net.sf.memoranda.LOCList;
import net.sf.memoranda.LinesofCode;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class ChartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout = new BorderLayout();
  
	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();

	JScrollPane scrollPane = new JScrollPane();
	
	// Lines of Code Panel stuff
		LinesofCode loc_object = null;
		LOCList loc_log = null;
		JTextField locField = new JTextField(8);
		JButton addLOC = new JButton("Add Lines of Code");
		JPanel locPanel = new JPanel();
		JButton deleteLOC =  new JButton("Delete");
	  

	DailyItemsPanel parentPanel = null;
	
	public class LocTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Table data
		private ArrayList<Object[]> _data;

		// Next loc 
		private int _nextNumber;  // needs to be loc

		// Column names
		private String[] ColumnNames = {
				"Number",
				"Date Finished",
				"Lines of Code",
				"Description"
		};

		// Column types
		private Class[] ColumnClasses = {
				Integer.class,
				CalendarDate.class,
				Integer.class,
				String.class
		};

		public LocTableModel() {
			_data = new ArrayList<Object[]>();
			_nextNumber = 1; // needs to be loc
		}

		@Override
		public int getRowCount() {
			return _data.size();
		}

		@Override
		public int getColumnCount() {
			return ColumnNames.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			return _data.get(row)[column];
		}

		@Override
		public Class getColumnClass(int c) {
			return ColumnClasses[c];
		}

		@Override
		public String getColumnName(int c) {
			return ColumnNames[c];
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return (col != 0 && col != 1);
		}

		@Override
		public void setValueAt(Object val, int row, int col) {
			_data.get(row)[col] = val;

			String attrib = "";
			String value = "";
			if (col == 3){
				attrib = "Lines of Code";
				value = Integer.toString((Integer)val);
			}
			if (col == 4)
			{
				attrib = "description";
				value = (String)val;
			}

			CurrentProject.getLOCList().modifyLoc((Integer)_data.get(row)[0], attrib, value);;
			fireTableDataChanged();
		}
		
		/**
		 * Add a new row for a new loc: has default data inputs.
		 */
		public void addNewRow() {
			_data.add(new Object[] {
					_nextNumber, 
					new CalendarDate(),
					0,
					""
			});

			LinesofCode temp_loc = new LinesofCode();
			temp_loc.setDateCompleted(CalendarDate.today());
			temp_loc.setLocNumber(_nextNumber++); 
			CurrentProject.getLOCList().addLOC(temp_loc);

			fireTableDataChanged();
		}

		
		public void deleteRows(int[] rowIndexes) {
			System.out.println(rowIndexes.length);
			for(int i = rowIndexes.length - 1; i >= 0; i--) {
				CurrentProject.getLOCList().removeLOC((Integer)_data.get(rowIndexes[i])[0]);
				_data.remove(rowIndexes[i]);
			}

			fireTableDataChanged();
		}
		
		protected void populateTable() {
			_data.clear();
			Vector<LinesofCode> loc = CurrentProject.getLOCList().getAllLOC();
			for (int i = 0; i < loc.size(); ++i)
			{

				_data.add(new Object[] {
						loc.get(i).getLocNumber(),
						loc.get(i).getDateCompleted(),
						loc.get(i).getLOCIntValue(),
						loc.get(i).getDescription()
				});
				if (i == loc.size() - 1)
				{
					_nextNumber = loc.get(i).getLocNumber() + 1;
				}
			}
			fireTableDataChanged();
		}
	}
	
	
	//checking to see if the text field is valid to be logged and returns value if valid.
			public void checkLOCValue(JTextField jtext){
				String str = jtext.getText();
				int result;
				if (!str.matches(".*[1234567890].*")){
					String text = "Invalid Value(s)";
					jtext.setText(text);
				} else{
					result = Integer.parseInt(str);
					loc_object.setLocNumber(result);
					loc_object.setLOCStrValue(str);
					jtext.setText(" ");
				}
			}
		
	  private List<Double> getGaussianData(int count) {
		   	 
		    List<Double> data = new ArrayList<Double>(count);
		    Random r = new Random();
		    for (int i = 0; i < count; i++) {
		      data.add(r.nextGaussian() * 10);
		    }
		    return data;
	}
	  
	
	//public class Chart{
	
	// Things inside the panel that people need to see!
	public ChartPanel(DailyItemsPanel _parentPanel){
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
		
		// Adding the buttons to the toolbar then adding toolbar.
		toolBar.add(historyBackB, null);
		toolBar.add(historyForwardB, null);
		toolBar.add(addLOC);
		toolBar.add(deleteLOC);
		this.add(toolBar, BorderLayout.NORTH);		
		
		
		LocTableModel tableModel = new LocTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane2 = new JScrollPane(table);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20*1000);		
		table.getColumnModel().getColumn(1).setPreferredWidth(50*1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(100*1000);
		table.getColumnModel().getColumn(3).setPreferredWidth(200*1000);
		//TableColumn col = table.getColumnModel().getColumn();
		
		this.add(scrollPane2, BorderLayout.CENTER);

		table.setFillsViewportHeight(true);
		tableModel.populateTable();

		// The Add LOC button adds a new row to the table
		addLOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.addNewRow();
			}
		});
		
		deleteLOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.deleteRows(table.getSelectedRows());
			}
		});
		
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl, LOCList ll) {                

			}
			public void projectWasChanged() {
				tableModel.populateTable();
			}
		}); 
		//chart panel
	    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Lines of Code and Defects/Day").xAxisTitle("Date").yAxisTitle("Count").build();
	 
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setAvailableSpaceFill(.96);
	    chart.getStyler().setOverlapped(true);
	 
	    // Series
	    Histogram histogram1 = new Histogram(getGaussianData(10000), 20, -20, 20);
	    Histogram histogram2 = new Histogram(getGaussianData(5000), 20, -20, 20);
	    chart.addSeries("Lines Of Code Per Day", histogram1.getxAxisData(), histogram1.getyAxisData());
	    chart.addSeries("Defects Found", histogram2.getxAxisData(), histogram2.getyAxisData());
		
		 JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
	        this.add(chartPanel, BorderLayout.SOUTH);
	}
}
