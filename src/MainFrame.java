import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainFrame extends JPanel implements ActionListener {
	
	public static int WIN_WIDTH = 1900;
	public static int WIN_HEIGHT = 420;
	public static int WIN_WIDTH2 = 1200;
	public static int WIN_HEIGHT2 = 240;
	private static List<List<String>> data = null;
	private static List<String> tier2Color = new ArrayList<>();
	private static int mode = 0;
	//MouseData
		private boolean mouseMoved = false;
		private int mx = 0;
		private int my = 0;
	//Global JElements
		private static JTable descTable;
		private static JFrame frame;
		private static JTextField tier2FilterText;
		private static JTextArea tier2TextArea;
		private static JTextField tier1FilterText;
		private static JTable tier1Table;
	//Table Data
		Object[][] tableData = {
			    {"Line", ""},
			    {"Subject", ""},
			    {"Tier1", ""},
			    {"Tier2", ""},
			    {"Tier3", ""},
			    {"T-min", ""},
			    {"T-Max", ""}
			};
		String[] columnNames = {"Data Type", "Value"};
		static String[] tier1ColumnNames = {"Full String", "tmin","tmax"};
		static List<List<String>> tier1TableData = new ArrayList<>();

		
	
	
	public MainFrame(List<List<String>> newData, int mode) {
		frame = new JFrame("Depression Conditions");
		if(mode == 1) frame.getContentPane().setLayout(new GridLayout(2,1));
		else if(mode==2) frame.getContentPane().setLayout(new BorderLayout());
		
		//Create menu bar
		if(mode == 1) {
			JMenuBar menuBar = new JMenuBar();
			JMenu file = new JMenu("File");
			
			JMenuItem openMenuItem = new JMenuItem("Open");
			openMenuItem.setActionCommand("Open Action");
			openMenuItem.addActionListener(this);
			file.add(openMenuItem);
			
			JMenuItem exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setActionCommand("Exit Action");
			exitMenuItem.addActionListener(this);
			file.add(exitMenuItem);
			
			menuBar.add(file);
			frame.setJMenuBar(menuBar);
		}
			
		
		//Create content bar 
			data = newData;
			if(mode == 1) {
				BarPanel barPanel = new BarPanel(data);
				frame.getContentPane().add(barPanel);
			}
			
		//File Menu Buttons
			if(mode == 2) {
				JPanel fileMenuButtonPanel = new JPanel();
				fileMenuButtonPanel.setLayout(new GridLayout(1,3));
				
				JButton addFiles = Aesthetics.createSimpleButton("Add Files");
				addFiles.addActionListener(this);
				addFiles.setActionCommand("Mode 2 Add Files");
				fileMenuButtonPanel.add(addFiles);
				
				JButton resetFiles = Aesthetics.createSimpleButton("Close All Files");
				resetFiles.addActionListener(this);
				resetFiles.setActionCommand("Mode 2 Close Files");
				fileMenuButtonPanel.add(resetFiles);
				
				JButton exitFiles = Aesthetics.createSimpleButton("Exit");
				exitFiles.addActionListener(this);
				exitFiles.setActionCommand("Exit Action");
				fileMenuButtonPanel.add(exitFiles);
				
				frame.add(fileMenuButtonPanel, BorderLayout.SOUTH);
				
			}
		
		//Main Panel
			JPanel mainPanel = new JPanel();
			mainPanel.setBackground(Aesthetics.BGColor);
			frame.getContentPane().add(mainPanel);
			JPanel subPanel1 = new JPanel();
			subPanel1.setBackground(Aesthetics.BGColor);
			mainPanel.add(subPanel1);
			subPanel1.setLayout(new GridLayout(1,6,50,0));
		
		//Details Table
			if(mode == 1) {
			JPanel descPanel = Aesthetics.createBorderedPanel("Details");
			descTable = new JTable(tableData, columnNames);
			descTable.setFont(Aesthetics.robotoFont);
			JScrollPane scrollPane = new JScrollPane(descTable);
			scrollPane.setPreferredSize((new Dimension(300, 135)));
			descPanel.add(scrollPane);
			subPanel1.add(descPanel);
			
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 15, 5, 15);
			Border compound = new CompoundBorder(line, margin);
			descTable.setForeground(Color.BLACK);
			descTable.setBackground(Color.WHITE);
			descTable.setBorder(compound);
			}
		
	
		//Tier2 Filter
			JPanel tier2FilterPanel = Aesthetics.createBorderedPanel("Tier2 Filter");
			tier2FilterPanel.setLayout((new BorderLayout()));
			subPanel1.add(tier2FilterPanel);
			
			JPanel tier2Input = new JPanel();
			tier2Input.setLayout((new FlowLayout()));
			tier2Input.setBackground(Aesthetics.BGColor);
			tier2FilterPanel.add(tier2Input,BorderLayout.NORTH);
			
			tier2FilterText = new JTextField(20);
			tier2Input.add(tier2FilterText);
			tier2FilterText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
			JButton tier2Button = Aesthetics.createSimpleButton("Apply Tier 2");
			tier2Button.addActionListener(this);
			tier2Button.setActionCommand("Tier 2 Apply Filter");
			tier2Input.add(tier2Button);
			tier2TextArea = new JTextArea(7,10);
			tier2FilterPanel.add(tier2TextArea,BorderLayout.SOUTH);
			tier2TextArea.setEditable(false);
			tier2TextArea.setForeground(Aesthetics.textColor);
			tier2TextArea.setFont(Aesthetics.robotoFont);
			tier2TextArea.setBackground(Color.decode("#141d26"));
		
		//Tier1 Filter
			JPanel tier1FilterPanel = Aesthetics.createBorderedPanel("Tier1 Filter");
			tier1FilterPanel.setLayout((new BorderLayout()));
			subPanel1.add(tier1FilterPanel);
			
			JPanel tier1Input = new JPanel();
			tier1Input.setLayout((new FlowLayout()));
			tier1Input.setBackground(Aesthetics.BGColor);
			tier1FilterPanel.add(tier1Input,BorderLayout.NORTH);
			
			tier1FilterText = new JTextField(20);
			tier1Input.add(tier1FilterText);
			tier1FilterText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
			JButton tier1Button = Aesthetics.createSimpleButton("Apply Tier 1");
			tier1Button.addActionListener(this);
			tier1Button.setActionCommand("Tier 1 Apply Filter");
			tier1Input.add(tier1Button);

			tier1Table = new JTable(arrayListToObject(tier1TableData), tier1ColumnNames);
			JScrollPane tier1ScrollPane = new JScrollPane(tier1Table);
			tier1ScrollPane.setPreferredSize((new Dimension(100, 110)));
			tier1FilterPanel.add(tier1ScrollPane,BorderLayout.SOUTH);

		
		//Padding
			if(mode == 1) {
				JPanel padding = new JPanel();
				padding.setBackground(Aesthetics.BGColor);
				subPanel1.add(padding);
			}
		
	
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    if(mode == 1) frame.setSize(WIN_WIDTH, WIN_HEIGHT);
	    else if(mode == 2) frame.setSize(WIN_WIDTH2, WIN_HEIGHT2);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	public static void setDescTable(String line, String subject, String tier1, String tier2, String tier3, String tMin, String tMax) {
		descTable.getModel().setValueAt(line,0,1);
		descTable.getModel().setValueAt(subject,1,1);
		descTable.getModel().setValueAt(tier1,2,1);
		descTable.getModel().setValueAt(tier2,3,1);
		descTable.getModel().setValueAt(tier3,4,1);
		descTable.getModel().setValueAt(tMin,5,1);
		descTable.getModel().setValueAt(tMax,6,1);
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
	    if (actionCommand.equals("Open Action")) {
	    	ReadCSV.openFile(this, ReadCSV.REPLACE_DATA);
	    	clearFields();
	    } else if (actionCommand.equals("Exit Action")){
	    	System.exit(0);
	    } else if (actionCommand.equals("Tier 2 Apply Filter")){
	    	Filter.applyFilterTier2(tier2FilterText.getText(), data);
	    } else if (actionCommand.equals("Tier 1 Apply Filter")){
	    	Filter.applyFilterTier1(tier1FilterText.getText(), data);
	    }else if (actionCommand.equals("Mode 2 Add Files")){
	    	ReadCSV.getFC().setMultiSelectionEnabled(true);
	    	ReadCSV.openFile(this, ReadCSV.APPEND_DATA);
	    	clearFields();
	    }else if (actionCommand.equals("Mode 2 Close Files")){
	    	data.clear();
	    	clearFields();
	    }else {
	       System.out.println("Action not supported");
	    }
	} 
	
	public static void setMainFrameData(List<List<String>> newData) {
		data = newData;
	}
	
	public static List<List<String>> getMainFrameData() {
		return data;
	}
	
	public static void setTier2TextArea(String text) {
		tier2TextArea.setText(text);
	}
	
	public static List<List<String>> getTier1List() {
		return tier1TableData;
	}
	
	
	public static void updateTier1Table() {
		tier1Table.setModel(new DefaultTableModel(arrayListToObject(tier1TableData),tier1ColumnNames));
		tier1TableData.clear();
	}
	
	public static Object[][] arrayListToObject(List<List<String>> tableData) {
		Object[][] tableDataObject = new Object[tableData.size()][];
		
		for (int i = 0; i < tableData.size(); i++)
		{
			tableDataObject[i] = new Object[3];
			tableDataObject[i][0] = tableData.get(i).get(0);
			tableDataObject[i][1] = tableData.get(i).get(1);
			tableDataObject[i][2] = tableData.get(i).get(2);
		}
		return tableDataObject;
	}
	
	private static void clearFields() {
		setTier2TextArea("");
		tier2FilterText.setText("");
		tier1FilterText.setText("");
		tier1TableData.clear();
		updateTier1Table();
		if(mode == 1) setDescTable("","","","","",null,null);
	}
	
}