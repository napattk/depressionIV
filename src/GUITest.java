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

public class GUITest extends JPanel implements ActionListener {
	
	public static int WIN_WIDTH = 1900;
	public static int WIN_HEIGHT = 420;
	private static List<List<String>> data = null;
	private static List<String> tier2Color = new ArrayList<>();
	private boolean mouseMoved = false;
	private int mx = 0;
	private int my = 0;
	private static JTable descTable;
	private static JFrame frame;
	final JFileChooser fc = new JFileChooser();
	private static JTextField tier2FilterText;
	private static JTextArea tier2TextArea;
	public static Color BGColor = Color.decode("#334c66");
	public static Color borderColor = Color.decode("#fab81e");
	public static Color textColor = Color.decode("#ffffff");
	static Font robotoFont;
	
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
	
	
	public GUITest(List<List<String>> newData) {
		
		try {
		    robotoFont = Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf")).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf")));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		frame = new JFrame("Depression Conditions");
		frame.getContentPane().setLayout(new GridLayout(2,1));
		
		//Create menu bar
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
		
		//Create content bar 
		data = newData;
		BarPanel barPanel = new BarPanel(data);
		frame.getContentPane().add(barPanel);
		
		//Main Panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(BGColor);
		frame.getContentPane().add(mainPanel);
		JPanel subPanel1 = new JPanel();
		subPanel1.setBackground(BGColor);
		mainPanel.add(subPanel1);
		subPanel1.setLayout(new GridLayout(1,6,50,0));
		
		//Description Table
		JPanel descPanel = createBorderedPanel("Details");
		descTable = new JTable(tableData, columnNames);
		descTable.setFont(robotoFont);
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
		
	
		//Tier2 Filter
		JPanel tier2FilterPanel = createBorderedPanel("Tier2 Filter");
		tier2FilterPanel.setLayout((new BorderLayout()));
		subPanel1.add(tier2FilterPanel);
		
		JPanel tier2Input = new JPanel();
		tier2Input.setLayout((new FlowLayout()));
		tier2Input.setBackground(BGColor);
		tier2FilterPanel.add(tier2Input,BorderLayout.NORTH);
		
		tier2FilterText = new JTextField(20);
		tier2Input.add(tier2FilterText);
		tier2FilterText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	
		JButton tier2Button = createSimpleButton("Apply");
		tier2Button.setFont(robotoFont);
		tier2Button.addActionListener(this);
		tier2Button.setActionCommand("Tier 2 Apply Filter");
		tier2Input.add(tier2Button);
		tier2TextArea = new JTextArea(7,10);
		tier2FilterPanel.add(tier2TextArea,BorderLayout.SOUTH);
		tier2TextArea.setEditable(false);
		tier2TextArea.setForeground(textColor);
		tier2TextArea.setFont(robotoFont);
		tier2TextArea.setBackground(Color.decode("#141d26"));

		
		//Padding
		JPanel padding = new JPanel();
		padding.setBackground(BGColor);
		subPanel1.add(padding);
		
		JPanel padding2 = new JPanel();
		padding2.setBackground(BGColor);
		subPanel1.add(padding2);
		
		JPanel padding3 = new JPanel();
		padding3.setBackground(BGColor);
		subPanel1.add(padding3);
	
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setSize(WIN_WIDTH, WIN_HEIGHT);
	    frame.setResizable(false);
	    frame.setVisible(true);
	}
	
	public static void setDescTable(String line, String subject, String tier1, String tier2, String tier3, double tMin, double tMax) {
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
	    	int returnVal = fc.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	File file = fc.getSelectedFile();
	            openFile(file);
	        } else {
	        	  System.out.println("Open command cancelled by user." );
	        }
	    } else if (actionCommand.equals("Exit Action")){
	    	System.exit(0);
	    } else if (actionCommand.equals("Tier 2 Apply Filter")){
	    	System.out.println("Aasfasf");
	    	Filter.applyFilter(tier2FilterText.getText(), data);
	    } else {
	       
	    }
	} 
	
	private void openFile(File file) {
		
        String filename = file.getName();
        System.out.println("Opening: " + file.getAbsolutePath() + "." );
        
        String extension =  filename.substring(filename.indexOf("."));
        System.out.println(extension);
        
        if(!extension.equals(".csv")) {
        	System.out.println("Unsupported file type selected.");
        }
        else {
	        String filePath = file.getAbsolutePath();
	        List<List<String>> newFileData = ReadCSV.readData(filePath);
	        data = newFileData;
	        BarPanel.setBarData(newFileData);
	        frame.repaint();
        }
	}
	
	public static void setTier2TextArea(String text) {
		tier2TextArea.setText(text);
	}
	
	private static JButton createSimpleButton(String text) {
		  JButton button = new JButton(text);
		  button.setForeground(Color.BLACK);
		  button.setBackground(Color.WHITE);
		  Border line = new LineBorder(Color.BLACK);
		  Border margin = new EmptyBorder(5, 15, 5, 15);
		  Border compound = new CompoundBorder(line, margin);
		  button.setBorder(compound);
		  return button;
	}
	
	private static JPanel createBorderedPanel(String text ) {
		JPanel JPanel = new JPanel();
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor),text);
		titledBorder.setTitleColor(textColor);
		titledBorder.setTitleFont(robotoFont);
		
		JPanel.setBackground(BGColor);
		JPanel.setBorder(titledBorder);
		return JPanel;
	}
	
}