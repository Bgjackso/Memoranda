package net.sf.memoranda.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import net.sf.memoranda.util.Local;

//based heavily on sticker expand
public class TaskExpand extends JDialog {
	String tList;  
	Color backGroundColor, foreGroundColor;
	public boolean CANCELLED = true;
	
	JPanel taskPanel = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout(); 
	JPanel bottomPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JLabel header = new JLabel();
	JScrollPane jScrollPanel = new JScrollPane();
	JPanel jPanel1 = new JPanel();
	JLabel taskContents = new JLabel();
	JLabel jLabel1 = new JLabel();
	BorderLayout borderLayout3 = new BorderLayout();
	
	Border border1;
	Border border2;
	
	public TaskExpand(Frame frame, String tasks, String backcolor, String fontcolor){
		super(frame, Local.getString("Tasks"), true);
		setTitle("Tasks");
		this.tList = tasks;
		this.backGroundColor = Color.decode(backcolor);
		this.foreGroundColor = Color.decode(fontcolor);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}
	
	void jbInit() throws Exception {
		border1 = BorderFactory.createCompoundBorder(
						BorderFactory.createEtchedBorder(
							Color.white,
							new Color(156, 156, 158)),
							BorderFactory.createEmptyBorder(5, 5, 5, 5));
		border2 = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		taskPanel.setLayout(borderLayout1);
		this.getContentPane().setLayout(borderLayout2);
		
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(new Insets(0, 5, 0, 5)));
		topPanel.setBackground(Color.WHITE);
		
		jPanel1.setLayout(borderLayout3);
		taskPanel.setBorder(border1);
		jPanel1.setBorder(border2);
		
		getContentPane().add(taskPanel, BorderLayout.CENTER);
		taskPanel.add(jScrollPanel, BorderLayout.CENTER);
		jScrollPanel.setViewportView(taskContents); 
		taskPanel.add(jPanel1, BorderLayout.SOUTH);
		
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		taskContents.setText(tList);
		taskContents.setOpaque(true);
		taskContents.setBackground(backGroundColor);
		taskContents.setForeground(foreGroundColor);
	}
}
