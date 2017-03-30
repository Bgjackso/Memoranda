package net.sf.memoranda.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

//import TimeFrame.starts;

import javax.swing.JOptionPane;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.History;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.PSPTimer;
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

public class PSPPanel extends JPanel {
	BorderLayout borderLayout = new BorderLayout();
	
	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();
	
	JScrollPane scrollPane = new JScrollPane();
	
	DailyItemsPanel parentPanel = null;
	
	//PSP Timer
	JPanel timerPanel = new JPanel();
	JLabel time = new JLabel("Press 'Start' to begin", JLabel.CENTER);
    PSPTimer timer;
    JButton pause = new JButton ("Pause");
    JButton start = new JButton ("Start");
    JButton reset = new JButton ("Reset");
	
	
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
		this.setLayout(borderLayout);
		
		scrollPane.getViewport().setBackground(Color.white);
		//scrollPane.getViewport().add(viewer, null);
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
		
		//PSP Timer
		timer = new PSPTimer();
        //start.addActionListener(new starts());
        //pause.addActionListener(new starts());
        //reset.addActionListener(new starts());
        time.setBackground(Color.WHITE);
        timerPanel.add(time);
        time.setFont(new Font("Consolas", Font.BOLD, 20));
        time.setForeground(Color.BLACK);
        timerPanel.add(start);
        timerPanel.add(pause);
        timerPanel.add(reset);
        this.add(timerPanel);
	}
	
}



