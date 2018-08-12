import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JPanel implements ActionListener {
	
	public static int WIN_WIDTH = 1900;
	public static int WIN_HEIGHT = 420;
	public static int WIN_WIDTH2 = 1900;
	public static int WIN_HEIGHT2 = 275;
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
		private static JTable tier2Table;
		private static JTable tier2PercenTable;
		private static JTable filesOpenedTable;
		
		private static BarPanel barPanel;
	//Table Data
		Object[][] tableData = {
			    {"Line", ""},
			    {"Subject", ""},
			    {"Transcription (Tier1)", ""},
			    {"Transcription Type (Tier2)", ""},
			    {"Speaker (Tier3)", ""},
			    {"Start Time (T-min)", ""},
			    {"End Time (T-Max)", ""}
			};
		String[] columnNames = {"Data Type", "Value"};
		static String[] tierColumnNames = {"Full String", "Start Time","End Time"};
		static List<List<String>> tier1TableData = new ArrayList<>();
		static List<List<String>> tier2TableData = new ArrayList<>();
		
		static String[] tier2PercenColumnNames = {"Transcription Type", "Percentage"};
		static List<List<String>> tier2PercenTableData = new ArrayList<>();
		
		static String[] filesOpenedColumnNames = {"Files"};
		static List<List<String>>  filesOpenedTableData = new ArrayList<>();

	public MainFrame(List<List<String>> newData, int mode) {
		
		frame = new JFrame("Depression Conditions");
		JPanel contentPane = new JPanel();
		JScrollPane mainScrollPane = new JScrollPane(contentPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(mainScrollPane);
		if(mode == 1) contentPane.setLayout(new GridLayout(2,1));
		else if(mode==2) contentPane.setLayout(new BorderLayout());
		
		//Content of window changes depending on mode selected
		//Create menu bar	
			JMenuBar menuBar = new JMenuBar();
			JMenu file = new JMenu("File");
			
			
			if(mode == ModeSelect.SINGLE_MODE) {
				JMenuItem openMenuItem = new JMenuItem("Open");
				openMenuItem.setActionCommand("Open Action");
				openMenuItem.addActionListener(this);
				file.add(openMenuItem);	
			}
			else if(mode == ModeSelect.MULTI_MODE) {
				JMenuItem addFilesItem = new JMenuItem("Add Files");
				addFilesItem.setActionCommand("Mode 2 Add Files");
				addFilesItem.addActionListener(this);
				file.add(addFilesItem);
				
				JMenuItem closeFilesItem = new JMenuItem("Close All Files");
				closeFilesItem.setActionCommand("Mode 2 Close Files");
				closeFilesItem.addActionListener(this);
				file.add(closeFilesItem);
	
			}
		
			JMenuItem modeSelectItem = new JMenuItem("Mode Select");
			modeSelectItem.setActionCommand("Switch Mode");
			modeSelectItem.addActionListener(this);
			file.add(modeSelectItem);
		
			JMenuItem exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setActionCommand("Exit Action");
			exitMenuItem.addActionListener(this);
			file.add(exitMenuItem);
			
			menuBar.add(file);
			frame.setJMenuBar(menuBar);
		
			
		
		//Create content bar 
			data = newData;
			if(mode == 1) {
				barPanel = new BarPanel(data);
				contentPane.add(barPanel);
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
			contentPane.add(mainPanel);
			JPanel subPanel1 = new JPanel();
			subPanel1.setBackground(Aesthetics.BGColor);
			mainPanel.add(subPanel1);
			subPanel1.setLayout(new GridLayout(1,6,50,0));
		
		
		//Details Table
			if(mode == 1) {
			JPanel descPanel = Aesthetics.createBorderedPanel("Details");
			descTable = new JTable(tableData, columnNames);
			descTable.setFont(Aesthetics.robotoFont);
			JScrollPane detailsScrollPane = new JScrollPane(descTable);
			detailsScrollPane.setPreferredSize((new Dimension(300, 135)));
			descPanel.add(detailsScrollPane);
			subPanel1.add(descPanel);
			
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(5, 15, 5, 15);
			Border compound = new CompoundBorder(line, margin);
			descTable.setForeground(Color.BLACK);
			descTable.setBackground(Color.WHITE);
			descTable.setBorder(compound);
			}
			
		//Files Opened
			if(mode == 2) {
			JPanel filesOpenedPanel = Aesthetics.createBorderedPanel("Files Opened");
			filesOpenedPanel.setLayout((new BorderLayout()));
			subPanel1.add(filesOpenedPanel);
			
			filesOpenedTable = new JTable(arrayListToObject(filesOpenedTableData,1), filesOpenedColumnNames);
			JScrollPane filesOpenedScrollPane = new JScrollPane(filesOpenedTable);
			filesOpenedScrollPane.setPreferredSize((new Dimension(100, 140)));
			filesOpenedPanel.add(filesOpenedScrollPane,BorderLayout.SOUTH);
			}
			
		//Tier1 Filter
			JPanel tier1FilterPanel = Aesthetics.createBorderedPanel("Transcription Filter");
			tier1FilterPanel.setLayout((new BorderLayout()));
			subPanel1.add(tier1FilterPanel);
			
			JPanel tier1Input = new JPanel();
			tier1Input.setLayout((new FlowLayout()));
			tier1Input.setBackground(Aesthetics.BGColor);
			tier1FilterPanel.add(tier1Input,BorderLayout.NORTH);
			
			tier1FilterText = new JTextField(20);
			tier1Input.add(tier1FilterText);
			tier1FilterText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
			JButton tier1Button = Aesthetics.createSimpleButton("Apply Transcription Filter");
			tier1Button.addActionListener(this);
			tier1Button.setActionCommand("Tier 1 Apply Filter");
			tier1Input.add(tier1Button);

			tier1Table = new JTable(arrayListToObject(tier1TableData,3), tierColumnNames);
			JScrollPane tier1ScrollPane = new JScrollPane(tier1Table);
			tier1ScrollPane.setPreferredSize((new Dimension(100, 110)));
			tier1FilterPanel.add(tier1ScrollPane,BorderLayout.SOUTH);
			
		//Tier2 Filter
			JPanel tier2FilterPanel = Aesthetics.createBorderedPanel("Transcription Type Filter");
			tier2FilterPanel.setLayout((new BorderLayout()));
			subPanel1.add(tier2FilterPanel);
			
			JPanel tier2Input = new JPanel();
			tier2Input.setLayout((new FlowLayout()));
			tier2Input.setBackground(Aesthetics.BGColor);
			tier2FilterPanel.add(tier2Input,BorderLayout.NORTH);
			
			tier2FilterText = new JTextField(20);
			tier2Input.add(tier2FilterText);
			tier2FilterText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
			JButton tier2Button = Aesthetics.createSimpleButton("Apply Type Filter");
			tier2Button.addActionListener(this);
			tier2Button.setActionCommand("Tier 2 Apply Filter");
			tier2Input.add(tier2Button);

			tier2Table = new JTable(arrayListToObject(tier2TableData,3), tierColumnNames);
			JScrollPane tier2ScrollPane2 = new JScrollPane(tier2Table);
			tier2ScrollPane2.setPreferredSize((new Dimension(100, 110)));
			tier2FilterPanel.add(tier2ScrollPane2,BorderLayout.SOUTH);
		
		//Tier2 Percentage
			JPanel tier2PercenPanel = Aesthetics.createBorderedPanel("Transcription Type Percentage Breakdown");
			tier2PercenPanel.setLayout((new BorderLayout()));
			subPanel1.add(tier2PercenPanel);
			
			tier2PercenTable = new JTable(arrayListToObject(tier2PercenTableData,2), tier2PercenColumnNames);
			JScrollPane tier2PercenScrollPane2 = new JScrollPane(tier2PercenTable);
			tier2PercenScrollPane2.setPreferredSize((new Dimension(100, 140)));
			tier2PercenPanel.add(tier2PercenScrollPane2,BorderLayout.SOUTH);
			
			Filter.populateTier2Percen(data);
	
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    if(mode == 1) frame.setSize(WIN_WIDTH, WIN_HEIGHT);
	    else if(mode == 2) frame.setSize(WIN_WIDTH2, WIN_HEIGHT2);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
	    if (actionCommand.equals("Open Action")) {
	    	ReadCSV.openFile(this, ReadCSV.REPLACE_DATA);
	    	clearFields();
	    	Filter.populateTier2Percen(data);
	    	barPanel.repaint();
	    } else if (actionCommand.equals("Exit Action")){
	    	System.exit(0);
	    } else if (actionCommand.equals("Tier 2 Apply Filter")){
	    	Filter.applyTierFilter(tier2FilterText.getText(), data, 2);
	    } else if (actionCommand.equals("Tier 1 Apply Filter")){
	    	Filter.applyTierFilter(tier1FilterText.getText(), data, 1);
	    }else if (actionCommand.equals("Mode 2 Add Files")){ //MultiMode Add Files
	    	ReadCSV.getFC().setMultiSelectionEnabled(true);
	    	ReadCSV.openFile(this, ReadCSV.APPEND_DATA);
	    	clearFields();
	    	Filter.populateTier2Percen(data);
	    }else if (actionCommand.equals("Mode 2 Close Files")){ //MultiMode Close Files
	    	data.clear();
	    	filesOpenedTableData.clear();
	    	clearFields();
	    	updateFilesOpenedTable();
	    } else if (actionCommand.equals("Switch Mode")){
	    	frame.dispose();
	    	filesOpenedTableData.clear();
	    	if(mode == 2) updateFilesOpenedTable();
	    	ModeSelect.getModeSelectFrame().setVisible(true);
	    }else { //Case of incorrect action, button not completely implemented, etc. 
	       System.out.println("Action not supported");
	    }
	} 
	
	public static void updateTier1Table() {//updates table with new data 
		tier1Table.setModel(new DefaultTableModel(arrayListToObject(tier1TableData,3),tierColumnNames));
		tier1TableData.clear();
	}
	
	public static void updateTier2Table() {//updates table with new data 
		tier2Table.setModel(new DefaultTableModel(arrayListToObject(tier2TableData,3),tierColumnNames));
		tier2TableData.clear();
	}
	
	public static void updateTier2PercenTable() {//updates table with new data 
		tier2PercenTable.setModel(new DefaultTableModel(arrayListToObject(tier2PercenTableData,2),tier2PercenColumnNames));
		tier2PercenTableData.clear();
	}
	
	public static void updateFilesOpenedTable() {//updates table with new data 
		if(!(filesOpenedTableData == null)) {
			filesOpenedTable.setModel(new DefaultTableModel(arrayListToObject(filesOpenedTableData,1),filesOpenedColumnNames));
		}
	}
	
	public static Object[][] arrayListToObject(List<List<String>> tableData, int cols) {// converts arraylist to array of objects
		Object[][] tableDataObject = new Object[tableData.size()][];
		
		for (int i = 0; i < tableData.size(); i++)
		{
			tableDataObject[i] = new Object[cols];
			for (int j = 0; j < cols; j++) {
				//System.out.println("hi " +tableData.get(i).get(j) + " i: " + i + " j: "+ j);
				//System.out.println("cols: " + cols + " size: " + tableData.size());
				tableDataObject[i][j] = tableData.get(i).get(j);
			}
		}
		return tableDataObject;
	}
	
	private static void clearFields() {//Make all fields blank
		tier2FilterText.setText("");
		tier1FilterText.setText("");
		tier1TableData.clear();
		tier2TableData.clear();
		tier2PercenTableData.clear();
		updateTier1Table();
		updateTier2Table();
		updateTier2PercenTable();
		
		if(mode == 1) setDescTable("","","","","",null,null);
		if(mode == 2) updateFilesOpenedTable();
	}
	
	public static void setMainFrameData(List<List<String>> newData) {
		data = newData;
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
	
	public static void setTier2TextArea(String text) {
		tier2TextArea.setText(text);
	}
	
	public static List<List<String>> getTier1TableData() {
		return tier1TableData;
	}
	
	public static List<List<String>> getMainFrameData() {
		return data;
	}
	
	public static List<List<String>> getTier2TableData() {
		return tier2TableData;
	}
	
	public static List<List<String>> getTier2PercenTableData() {
		return tier2PercenTableData;
	}
	
	public static List<List<String>> getFilesOpenedTableData() {
		return filesOpenedTableData;
	}
	
}