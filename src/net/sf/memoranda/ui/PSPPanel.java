package net.sf.memoranda.ui;

import java.awt.BorderLayout;
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

public class PSPPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*BorderLayout borderLayout = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JScrollPane scrollPane = new JScrollPane();
	DailyItemsPanel parentPanel = null;
	DefectTable table1 = null;


	public PSPPanel(DailyItemsPanel _parentPanel){
		try {
			parentPanel = _parentPanel;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public PSPPanel(DefectTable _table1){
		try {
			table1 = _table1;
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		toolBar.setFloatable(false);
		this.setLayout(borderLayout);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.NORTH);
		scrollPane.getViewport().setBackground(Color.white);

	}


}*/
	/**
	 * Defect Log Table Model
	 */
	private class DefectsTableModel extends AbstractTableModel {
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

	public PSPPanel(DailyItemsPanel _parentPanel) {
		try {
			init();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
			ex.printStackTrace();
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