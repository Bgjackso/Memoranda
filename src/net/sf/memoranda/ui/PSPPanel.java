package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.History;
import net.sf.memoranda.LOCList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.PSPTimer;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.ui.DefectPanel.DefectsTableModel;
import net.sf.memoranda.util.Local;

public class PSPPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout = new BorderLayout();

	JToolBar toolBar = new JToolBar();
	JButton historyBackB = new JButton();
	JButton historyForwardB = new JButton();
	//Defect Log Buttons
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
		this.add(toolBar, BorderLayout.NORTH);	
		
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
			}
		}); 

		JScrollPane scrollPane3 = new JScrollPane(timerPanel);
		start.addActionListener(new starts());
		pause.addActionListener(new starts());
		reset.addActionListener(new starts());
		time.setBackground(Color.WHITE);
		timerPanel.add(time);
		time.setFont(new Font("Consolas", Font.BOLD, 20));
		time.setForeground(Color.BLACK);
		timerPanel.add(start);
		timerPanel.add(pause);
		timerPanel.add(reset);

		this.add(scrollPane3, BorderLayout.SOUTH); //add scrollPane4 first when complete
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
            	timer.update(0);
            	timer.resetTimer(); 
            }
        }
    }
	
	public class PSPTimer implements Runnable{
		  private Thread runThread;
		  private boolean running = false;
		  private boolean paused = false;
		  private long summedTime = 0;

		  public void startTimer() {
			  if (!running){
				  running = true;
			      paused = false;
			      runThread = new Thread(this);
			      runThread.start();
			  }
		  }

		  public void pauseTimer() {
		      paused = true;
		  }

		  public void resetTimer() {
		      if (paused){
		    	  running = false;
		          paused = false;
		          summedTime = 0;
		          update(0);
		      }
		  }
		  
		  public void saveTime(){
		  	//TODO
		  }
		
		  @Override
		  public void run() {
			  long startTime = System.currentTimeMillis();
		      
		      while(running && !paused) {
		    	  update(summedTime + (System.currentTimeMillis() - startTime)); //timer causing issues
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
		  } 
	}
}
