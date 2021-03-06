package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();
	CardLayout cardLayout1 = new CardLayout();

	public JButton notesB = new JButton();
	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
	public ResourcesPanel filesPanel = new ResourcesPanel();
	public JButton agendaB = new JButton();
	public JButton tasksB = new JButton();
	public JButton eventsB = new JButton();
	public JButton filesB = new JButton();
	public JButton pspB = new JButton();
	public JButton chartB = new JButton();
	JButton currentB = null;
	Border border1;

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	
	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES")) {
				notesB_actionPerformed(null);
			}
			else if (pan.equals("TASKS")) {
				tasksB_actionPerformed(null);
			}
			else if (pan.equals("EVENTS")) {
				eventsB_actionPerformed(null);
			}
			else if (pan.equals("FILES")) {
				filesB_actionPerformed(null);
			}
			else if (pan.equals("PSP")) {
				pspB_actionPerformed(null);
			}
			else if (pan.equals("CHART")) {
				chartB_actionPerformed(null);
			}
		}
	}

	public void agendaB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(agendaB);
		Context.put("CURRENT_PANEL", "AGENDA");
	}

	public void notesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		Context.put("CURRENT_PANEL", "NOTES");
	}

	public void tasksB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(tasksB);
		Context.put("CURRENT_PANEL", "TASKS");
	}

	public void eventsB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(eventsB);
		Context.put("CURRENT_PANEL", "EVENTS");
	}

	public void filesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "FILES");
		setCurrentButton(filesB);
		Context.put("CURRENT_PANEL", "FILES");
	}
	
	public void pspB_actionPerformed(ActionEvent e){
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("PSP");
		setCurrentButton(pspB);
		Context.put("CURRENT_PANEL", "PSP");
	}
	
	public void chartB_actionPerformed(ActionEvent e){
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("CHART");
		setCurrentButton(chartB);
		Context.put("CURRENT_PANEL", "CHART");
	}
	 

	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}

	void jbInit() throws Exception {
		border1 = BorderFactory.createCompoundBorder(
			BorderFactory.createBevelBorder(
			BevelBorder.LOWERED,
			Color.white,
			Color.white, 
			new Color(124, 124, 124),
			new Color(178, 178, 178)),
			BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		this.setPreferredSize(new Dimension(1073, 300));
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(new Color(215, 225, 250));
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		toolBar.add(agendaB, null);
		toolBar.add(eventsB, null);
		toolBar.add(tasksB, null);
		toolBar.add(notesB, null);
		toolBar.add(filesB, null);
		toolBar.add(pspB, null);
		toolBar.add(chartB, null);
		toolBar.setBorder(null);
		
		panel.setLayout(cardLayout1);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES");
		panel.setBorder(null);

		currentB = agendaB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);

		//Agenda Panel
		agendaB.setBackground(Color.white);
		agendaB.setMaximumSize(new Dimension(60, 80));
		agendaB.setMinimumSize(new Dimension(30, 30));
		agendaB.setFont(new java.awt.Font("Dialog", 1, 10));
		agendaB.setPreferredSize(new Dimension(50, 50));
		agendaB.setBorderPainted(false);
		agendaB.setContentAreaFilled(false);
		agendaB.setFocusPainted(false);
		agendaB.setHorizontalTextPosition(SwingConstants.CENTER);
		agendaB.setText(Local.getString("Agenda"));
		agendaB.setVerticalAlignment(SwingConstants.TOP);
		agendaB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		agendaB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendaB_actionPerformed(e);
			}
		});
		
		agendaB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/agenda.png")));
		agendaB.setOpaque(false);
		agendaB.setMargin(new Insets(0, 0, 0, 0));
		agendaB.setSelected(true);

		// Event Panel
		eventsB.setBackground(Color.white);
		eventsB.setMaximumSize(new Dimension(60, 80));
		eventsB.setMinimumSize(new Dimension(30, 30));
		eventsB.setFont(new java.awt.Font("Dialog", 1, 10));
		eventsB.setPreferredSize(new Dimension(50, 50));
		eventsB.setBorderPainted(false);
		eventsB.setContentAreaFilled(false);
		eventsB.setFocusPainted(false);
		eventsB.setHorizontalTextPosition(SwingConstants.CENTER);
		eventsB.setText(Local.getString("Events"));
		eventsB.setVerticalAlignment(SwingConstants.TOP);
		eventsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		eventsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventsB_actionPerformed(e);
			}
		});
		
		eventsB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/events.png")));
		eventsB.setOpaque(false);
		eventsB.setMargin(new Insets(0, 0, 0, 0));
		//eventsB.setSelected(true);

		// Task Panel
		tasksB.setSelected(true);
		tasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		tasksB.setMargin(new Insets(0, 0, 0, 0));
		tasksB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/tasks.png")));
		tasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		tasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tasksB_actionPerformed(e);
			}
		});
		
		tasksB.setVerticalAlignment(SwingConstants.TOP);
		tasksB.setText(Local.getString("Tasks"));
		tasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tasksB.setFocusPainted(false);
		tasksB.setBorderPainted(false);
		tasksB.setContentAreaFilled(false);
		tasksB.setPreferredSize(new Dimension(50, 50));
		tasksB.setMinimumSize(new Dimension(30, 30));
		tasksB.setOpaque(false);
		tasksB.setMaximumSize(new Dimension(60, 80));
		tasksB.setBackground(Color.white);

		// Notes Panel
		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		
		notesB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		

		// Resources Panel
		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesB_actionPerformed(e);
			}
		});
		
		filesB.setFont(new java.awt.Font("Dialog", 1, 10));
		filesB.setVerticalAlignment(SwingConstants.TOP);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(50, 50));
		filesB.setMinimumSize(new Dimension(30, 30));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(60, 80));
		filesB.setBackground(Color.white);
		
		
		// Psp Panel
		pspB.setBackground(Color.white);
		pspB.setMaximumSize(new Dimension(60, 80));
		pspB.setMinimumSize(new Dimension(30, 30));
		pspB.setFont(new java.awt.Font("Dialog", 1, 10));
		pspB.setPreferredSize(new Dimension(50, 50));
		pspB.setBorderPainted(false);
		pspB.setContentAreaFilled(false);
		pspB.setFocusPainted(false);
		pspB.setHorizontalTextPosition(SwingConstants.CENTER);
		pspB.setText(Local.getString("PSP"));
		pspB.setVerticalAlignment(SwingConstants.TOP);
		pspB.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		pspB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pspB_actionPerformed(e);
			}
		});
		
		pspB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/PSPpsd.png")));
		pspB.setOpaque(false);
		pspB.setMargin(new Insets(0, 0, 0, 0));
		pspB.setSelected(true);
		
		
		//chart panel

		chartB.setBackground(Color.white);
		chartB.setMaximumSize(new Dimension(60, 80));
		chartB.setMinimumSize(new Dimension(30, 30));
		chartB.setFont(new java.awt.Font("Dialog", 1, 10));
		chartB.setPreferredSize(new Dimension(50, 50));
		chartB.setBorderPainted(false);
		chartB.setContentAreaFilled(false);
		chartB.setFocusPainted(false);
		chartB.setHorizontalTextPosition(SwingConstants.CENTER);
		chartB.setText(Local.getString("Chart"));
		chartB.setVerticalAlignment(SwingConstants.TOP);
		chartB.setVerticalTextPosition(SwingConstants.BOTTOM);
				
		chartB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartB_actionPerformed(e);
			}
		});
				
		chartB.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/chartbtn.png")));
		chartB.setOpaque(false);
		chartB.setMargin(new Insets(0, 0, 0, 0));
		chartB.setSelected(true);
		
	}

}