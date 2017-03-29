package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;

/**
 * Defect Log UI
 * A panel with a table of logged defects
 */
public class DefectPanel extends JPanel {	
	
	/**
	 * Defect Log Table Model
	 */
	public class DefectsTableModel extends AbstractTableModel {
		// Table data
		private ArrayList<Object[]> _data;
		
		// Next defect number
		private int _nextNumber;
		
		// Column names
		private String[] ColumnNames = {
				"Number",
		        "Date Found",
		        "Type",
		        "Injection Phase",
		        "Reason",
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
			// All columns are editable besides Defect Number and Date
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
				attrib = "phase";
			}
			else if (col == 4)
			{
				attrib = "reason";
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
		 * Adds a new row to the table with some defaults filled-in
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
		 * Populates the table if there are already defects in a project
		 */
		private void populateTable()
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
	
	/**
	 * Initializes the UI
	 */
	private void init() throws Exception {
		DefectsTableModel tableModel = new DefectsTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		JButton addRowBtn = new JButton("Add Row");
		JButton deleteBtn = new JButton("Delete");
		JPanel btnPanel = new JPanel();
		
		// Try to set column widths, although this seems to be more of a suggestion than anything
		table.getColumnModel().getColumn(0).setPreferredWidth(52*1000);		
		table.getColumnModel().getColumn(1).setPreferredWidth(77*1000);
		table.getColumnModel().getColumn(2).setPreferredWidth(66*1000);
		table.getColumnModel().getColumn(3).setPreferredWidth(79*1000);
		table.getColumnModel().getColumn(4).setPreferredWidth(106*1000);
		table.getColumnModel().getColumn(5).setPreferredWidth(56*1000);
		table.getColumnModel().getColumn(6).setPreferredWidth(145*1000);
		
		TableColumn typeCol = table.getColumnModel().getColumn(2);
		TableColumn phaseCol = table.getColumnModel().getColumn(3);
		
		// Use a combo box to edit the Type column
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
		
		// Use a combo box to edit the Injection Phase column
		JComboBox phaseBox = new JComboBox();
		phaseBox.addItem("Planning");
		phaseBox.addItem("Design");
		phaseBox.addItem("Coding");
		phaseBox.addItem("Test");
		
		typeCol.setCellEditor(new DefaultCellEditor(typeBox));
		phaseCol.setCellEditor(new DefaultCellEditor(phaseBox));
		
		this.setLayout(new BorderLayout());
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		btnPanel.add(addRowBtn);
		btnPanel.add(deleteBtn);
		
		this.add(btnPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		
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
	}
}
