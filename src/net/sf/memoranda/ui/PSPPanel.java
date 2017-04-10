package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

//import TimeFrame.starts;

import javax.swing.JOptionPane;

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
import net.sf.memoranda.PSPTimer;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

public class PSPPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout = new BorderLayout();
  
	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();
	JButton addRowBtn = new JButton("Add Row");
	JButton deleteBtn = new JButton("Delete");

	JScrollPane scrollPane = new JScrollPane();

	DailyItemsPanel parentPanel = null;
	

	//PSP Timer
	JPanel timerPanel = new JPanel();
	JLabel time = new JLabel("Press 'Start' to begin", JLabel.CENTER);
    PSPTimer timer = new PSPTimer();
    JButton pause = new JButton ("Pause");
    JButton start = new JButton ("Start");
    JButton reset = new JButton ("Reset");
	

	/**
	 * Defect Log Table Model
	 */
	public class DefectsTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Table data
		private ArrayList<Object[]> _data;

		// Next defect number
		private int _nextNumber;

		// Column names
		private String[] ColumnNames = {
				"Number",
				"Date Found",
				"Type",
				"Phase Injected",
				"Phase Corrected",
				"Fix Time (mins)",
				"Description"
		};

		// Column types
		private Class[] ColumnClasses = {
				Integer.class,
				CalendarDate.class,
				String.class,
				String.class,
				String.class,
				Integer.class,
				String.class
		};

		public DefectsTableModel() {
			_data = new ArrayList<Object[]>();
			_nextNumber = 1;
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
			if (col == 2)
			{
				attrib = "type";
			}
			else if (col == 3)
			{
				attrib = "injected";
			}
			else if (col == 4)
			{
				attrib = "corrected";
			}
			else if (col == 5)
			{
				attrib = "fix_time";
				value = Integer.toString((Integer)val);
			}
			else if (col == 6)
			{
				attrib = "description";
			}

			if (col != 5)
			{
				value = (String)val;
			}

			CurrentProject.getDefectList().modifyDefect((Integer)_data.get(row)[0], attrib, value);
			fireTableDataChanged();
		}

		/**
		 * Add a new row for a new defect: has default data inputs.
		 */
		public void addNewRow() {
			_data.add(new Object[] {
					_nextNumber,
					new CalendarDate(),
					"",
					"",
					"",
					0,
					""
			});

			Defect temp_defect = new Defect();
			temp_defect.SetDateFound(CalendarDate.today());
			temp_defect.SetDefectNumber(_nextNumber++);
			CurrentProject.getDefectList().addDefect(temp_defect);

			fireTableDataChanged();
		}

		/**
		 * Deletes the specified rows from the table
		 */
		public void deleteRows(int[] rowIndexes) {
			System.out.println(rowIndexes.length);
			for(int i = rowIndexes.length - 1; i >= 0; i--) {
				CurrentProject.getDefectList().removeDefect((Integer)_data.get(rowIndexes[i])[0]);
				_data.remove(rowIndexes[i]);
			}

			fireTableDataChanged();
		}

		/**
		 * Creates table with existing defects
		 */
		protected void populateTable()
		{
			_data.clear();
			Vector<Defect> defects = CurrentProject.getDefectList().getAllDefect();
			for (int i = 0; i < defects.size(); ++i)
			{

				_data.add(new Object[] {
						defects.get(i).GetDefectNumber(),
						defects.get(i).GetDateFound(),
						defects.get(i).GetType(),
						defects.get(i).GetInjected(),
						defects.get(i).GetCorrected(),
						defects.get(i).GetFixTime(),
						defects.get(i).GetDescription()
				});
				if (i == defects.size() - 1)
				{
					_nextNumber = defects.get(i).GetDefectNumber() + 1;
				}
			}
			fireTableDataChanged();
		}
	}

	
	// Things inside the panel that people need to see!
	public PSPPanel(DailyItemsPanel _parentPanel){
		try {
			parentPanel = _parentPanel; // Parent panel is the date/info on top of the panel.			
			jbInit();	
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
  }
	// inside the panel
	@SuppressWarnings("unchecked")
	void jbInit() throws Exception {
		this.setLayout(borderLayout);
    
		scrollPane.getViewport().setBackground(Color.white);
		this.add(scrollPane, BorderLayout.CENTER);
		toolBar.setFloatable(false);
		
		// Back button to go back one day from current date
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
		
		//TIMER WAS HERE

		toolBar.add(addRowBtn);
		toolBar.add(deleteBtn);
		this.add(toolBar, BorderLayout.NORTH);		
	
		DefectsTableModel tableModel = new DefectsTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane2 = new JScrollPane(table);
		
		// Table Size
	
		table.getColumnModel().getColumn(0).setPreferredWidth(20*1000);		
		table.getColumnModel().getColumn(1).setPreferredWidth(50*1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(65*1000);
		table.getColumnModel().getColumn(3).setPreferredWidth(65*1000);
		table.getColumnModel().getColumn(4).setPreferredWidth(65*1000);
		table.getColumnModel().getColumn(5).setPreferredWidth(20*1000);
		table.getColumnModel().getColumn(6).setPreferredWidth(200*1000);

		TableColumn typeCol = table.getColumnModel().getColumn(2); 
		TableColumn injectCol = table.getColumnModel().getColumn(3);
		TableColumn correctCol = table.getColumnModel().getColumn(4);

		// Combo for drop-down list for Type of Defect
		JComboBox typeBox = new JComboBox();
		typeBox.addItem("Documentation");
		typeBox.addItem("Syntax");
		typeBox.addItem("Build, Package");
		typeBox.addItem("Assignment");
		typeBox.addItem("Interface");
		typeBox.addItem("Checking");
		typeBox.addItem("Data");
		typeBox.addItem("Function");
		typeBox.addItem("System");
		typeBox.addItem("Environment");

		// Combo box for drop-list for Phase the Defect was Injected
		JComboBox injectBox = new JComboBox();
		injectBox.addItem("Planning");
		injectBox.addItem("Design");
		injectBox.addItem("Coding");
		injectBox.addItem("Code Review");
		injectBox.addItem("Test");
		injectBox.addItem("Post-Mortem");
		
		// Combo box for drop-list for phase the defected was corrected
		JComboBox correctBox = new JComboBox();
		correctBox.addItem("Planning");
		correctBox.addItem("Design");
		correctBox.addItem("Coding");
		correctBox.addItem("Code Review");
		correctBox.addItem("Test");
		correctBox.addItem("Post-Mortem");

		typeCol.setCellEditor(new DefaultCellEditor(typeBox));
		injectCol.setCellEditor(new DefaultCellEditor(injectBox));
		correctCol.setCellEditor(new DefaultCellEditor(correctBox));

		this.add(scrollPane2, BorderLayout.CENTER);


		table.setFillsViewportHeight(true);
		tableModel.populateTable();

		// The Add Row button adds a new row to the table
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.addNewRow();
			}
		});

		// The Delete button deletes the selected rows
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModel.deleteRows(table.getSelectedRows());
			}
		});
		
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl) {                

			}
			public void projectWasChanged() {
				tableModel.populateTable();
			}
		}); 
		
		//public PSPTimer()
		JScrollPane scrollPane3 = new JScrollPane(timerPanel);
		//timer = new PSPTimer();
		//start.addActionListener(new starts()); //pertain to class below
		//pause.addActionListener(new starts());
		//reset.addActionListener(new starts());
		time.setBackground(Color.WHITE);
		timerPanel.add(time); //new JLabel instead of time? 
		time.setFont(new Font("Consolas", Font.BOLD, 20));
		time.setForeground(Color.BLACK);
		//timerPanel.add(JLabel from PSPTimer)
		timerPanel.add(start);
		timerPanel.add(pause);
		timerPanel.add(reset);
		this.add(scrollPane3, borderLayout.SOUTH); //add scrollPane4 first when complete
		//pull in 'time' lane
		
	}
	
	public class starts implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == start){
            	timer.update(0);
            	timer.startTimer();
            }
            else if (event.getSource() == pause){
                timer.pauseTimer();
            }else{
            	timer.update(0); //updating timer in PSPTimer class?
            	timer.resetTimer(); 
            }
        }
    }
	
	/*public void startTimer() {
	      running = true;
	      paused = false;
	      //start thread
	      runThread = new Thread(this);
	      runThread.start();
	      //update(0);
	  }

	  public void pauseTimer() {
	      //pauses but is still able to restart
	      paused = true;
	  }

	  public void resetTimer() { //can only be reset if timer is paused
	      if (paused){
	      	running = false;
	          paused = false;
	          summedTime = 0;
	          //update(0);
	      }
	  }
	  
	  public void saveTime(){
	  	//TODO
	  }

	  @Override
	  public void run() {
	      long startTime = System.currentTimeMillis();
	      // keep showing the difference in time until we are either paused or not running anymore
	      while(running && !paused) {
	          //update(summedTime + (System.currentTimeMillis() - startTime)); //timer causing issues
	      }
	      if(paused){
	      	summedTime += System.currentTimeMillis() - startTime;
	      }else{
	          summedTime = 0;
	      }
	  } 
	
	public void update(long dTime){
    	time.setText(String.valueOf(String.valueOf((dTime/1000)/60) + ":" 
    			+ String.valueOf((dTime/1000)%60) + ":" + String.valueOf((dTime)%1000)));
	} */
	
}