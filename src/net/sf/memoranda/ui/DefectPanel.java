package net.sf.memoranda.ui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.date.CalendarDate;

public class DefectPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public class DefectsTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		// Table data
		protected ArrayList<Object[]> _data;

		// Next defect number
		protected int _nextNumber;

		// Column names
		protected String[] ColumnNames = {
				"Number",
				"Date Found",
				"Type",
				"Phase Injected",
				"Phase Corrected",
				"Fix Time (mins)",
				"Description"
		};

		// Column types
		@SuppressWarnings("rawtypes")
		protected Class[] ColumnClasses = {
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
		public Class<?> getColumnClass(int c) {
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

}
