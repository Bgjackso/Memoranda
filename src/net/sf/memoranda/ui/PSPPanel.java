package net.sf.memoranda.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JOptionPane;

import net.sf.memoranda.LinesofCode;
import net.sf.memoranda.LOCLog;
import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.date.DateListener;
import net.sf.memoranda.util.AgendaGenerator;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Element;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PSPPanel extends JPanel {
	BorderLayout borderLayout = new BorderLayout();
	
	//toolbar stuff
	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();
	
	// Lines of Code Panel stuff
	LinesofCode loc_object = null;
	LOCLog loc_log = null;
	JTextField locField = new JTextField(8);
	JButton saveLOC = new JButton("Save");
	JPanel locPanel = new JPanel();
	//@SuppressWarnings("unchecked")
	JComboBox locSaves = new JComboBox(/*loc_log.getAllLOC()*/);
	JButton deleteLOC =  new JButton("Delete");
	private final JPanel panel = new JPanel();
	private final JLabel lblLinesOfCode = new JLabel("Lines of Code: ");
	
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
		this.add(toolBar, BorderLayout.NORTH);
		
		// Adding Lines of Code text and field
		locPanel.add(lblLinesOfCode);
		locPanel.add(locField);
		// Adding the save button and action listener
		saveLOC.addActionListener(e -> checkLOCValue(locField));
		locPanel.add(saveLOC);
		// Adding loc to loc list and to drop down
		//loc_log.addLOC(loc_object);
		locPanel.add(locSaves);
		// Adding the delete button
		locPanel.add(deleteLOC);
		// Adding Entire loc panel
		this.add(locPanel, BorderLayout.CENTER);
		
		add(panel, BorderLayout.SOUTH);
		
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
					loc_object.setLOCIntValue(result);
					loc_object.setLOCStrValue(str);
					jtext.setText(" ");
				}
		}
		
}



