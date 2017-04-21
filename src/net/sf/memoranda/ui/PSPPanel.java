package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.LinesofCode;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.History;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.DefectPanel.DefectsTableModel;
import net.sf.memoranda.util.Local;

public class PSPPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout = new BorderLayout();

	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();
	//Defect Log Buttons
	JButton addRowBtn = new JButton("Add Defect");
	JButton deleteBtn = new JButton("Delete Defect");

	JScrollPane scrollPane = new JScrollPane();
	
	// Lines of Code Panel stuff
			LinesofCode loc_object = null;
			LOCList loc_log = null;
			JTextField locField = new JTextField(8);
			JButton addLOC = new JButton("Add Lines of Code");
			JPanel locPanel = new JPanel();
			JButton deleteLOC =  new JButton("Delete loc");

	DailyItemsPanel parentPanel = null;
	
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
		
		// Adding the buttons to the toolbar and adding the tool bar
		toolBar.add(historyBackB, null);
		toolBar.add(historyForwardB, null);
		
		//TIMER WAS HERE

		toolBar.add(addRowBtn);
		toolBar.add(deleteBtn);
		toolBar.add(addLOC);
		toolBar.add(deleteLOC);
		
		this.add(toolBar, BorderLayout.NORTH);	
		
		
		LocTableModel tableModelloc = new LocTableModel();
		JTable tableloc = new JTable(tableModelloc);
		JScrollPane scrollPaneloc = new JScrollPane(tableloc);
		
		tableloc.getColumnModel().getColumn(0).setPreferredWidth(20*1000);		
		tableloc.getColumnModel().getColumn(1).setPreferredWidth(50*1000);
		tableloc.getColumnModel().getColumn(2).setPreferredWidth(100*1000);
		tableloc.getColumnModel().getColumn(3).setPreferredWidth(200*1000);
		//TableColumn col = table.getColumnModel().getColumn();
		
		this.add(scrollPaneloc, BorderLayout.SOUTH);

		tableloc.setFillsViewportHeight(true);
		tableModelloc.populateTable();

		// The Add LOC button adds a new row to the table
		addLOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModelloc.addNewRow();
			}
		});
		
		deleteLOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableModelloc.deleteRows(tableloc.getSelectedRows());
			}
		});
		
		
			
		
		//Defect Log Table Creator	
		DefectPanel defectLog = new DefectPanel();
		TableModel tableModel = defectLog.new DefectsTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane2 = new JScrollPane(table);
		
		// Defect Log Table Size
	
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
		JComboBox<String> typeBox = new JComboBox<String>();
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
		JComboBox<String> injectBox = new JComboBox<String>();
		injectBox.addItem("Planning");
		injectBox.addItem("Design");
		injectBox.addItem("Coding");
		injectBox.addItem("Code Review");
		injectBox.addItem("Test");
		injectBox.addItem("Post-Mortem");
		
		// Combo box for drop-list for phase the defected was corrected
		JComboBox<String> correctBox = new JComboBox<String>();
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
		((DefectsTableModel) tableModel).populateTable();

		// The Add Row button adds a new row to the table
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefectsTableModel) tableModel).addNewRow();
			}
		});

		// The Delete button deletes the selected rows
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefectsTableModel) tableModel).deleteRows(table.getSelectedRows());
			}
		});
		
		CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl, LOCList ll) {                

			}
			public void projectWasChanged() {
				((DefectsTableModel) tableModel).populateTable();
				tableModelloc.populateTable();
			}
		}); 
		
	
	
	}
	public class LocTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Table data
		private ArrayList<Object[]> _data;

		// Next loc 
		private int _nextNumber;  

		// Column names
		private String[] ColumnNames = {
				"Number",
				"Date Completed",
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
			if (col == 2){
				attrib = "lines_of_code";
				value = Integer.toString((Integer)val);
			}
			if (col == 3)
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
}
