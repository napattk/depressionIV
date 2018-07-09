import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ModeSelect extends JPanel implements ActionListener{
	
	static JFrame frame = new JFrame("Mode Selection");
	JFileChooser fc = new JFileChooser();
	private static int SINGLE_MODE = 1;
	private static int MULTI_MODE = 2;

	public ModeSelect() {
		frame.getContentPane().setLayout(new GridLayout(3,1));
		
		JButton singleModeButton = Aesthetics.createSimpleButton("Open Single File");
		singleModeButton.addActionListener(this);
		singleModeButton.setSize(20, 100);
		singleModeButton.setActionCommand("Single Mode");
		frame.add(singleModeButton);
		
		JButton multiModeButton = Aesthetics.createSimpleButton("Open Multiple Files");
		multiModeButton.addActionListener(this);
		multiModeButton.setActionCommand("Multi Mode");
		frame.add(multiModeButton);
		
		JButton exitButton = Aesthetics.createSimpleButton("Exit");
		exitButton.addActionListener(this);
		exitButton.setActionCommand("Exit");
		frame.add(exitButton);
		
		
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.pack();
		 frame.setSize(300, 300);
		 frame.setLocationRelativeTo(null);
		 frame.setResizable(false);
		 frame.setVisible(true);
		 
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
	    if (actionCommand.equals("Single Mode")) {
	    	frame.dispose();
	    	List<List<String>> dataSet = getFileData(SINGLE_MODE);
	    	if(dataSet == null) {
	    		System.out.print("Error: Data set is null, or user canceled operation.");
	    	}else {
	    		MainFrame mainPanel = new MainFrame(dataSet,1);
	    	}
	    	
	    } else if (actionCommand.equals("Multi Mode")){
	    	frame.dispose();
	    	fc.setMultiSelectionEnabled(true);
	    	List<List<String>> dataSet = getFileData(MULTI_MODE);
	        MainFrame mainPanel = new MainFrame(dataSet,2);
	    } else if (actionCommand.equals("Exit")){
	    	System.exit(0);
	    }else {
	       
	    }
		
	}
	
	private  List<List<String>> getFileData(int mode) {
		
		int returnVal = fc.showOpenDialog(this);
		List<List<String>> dataSet = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	if (mode == MULTI_MODE) {
	        	File[] files = fc.getSelectedFiles();
	        		
	        	for(int i = 0; i < files.length; i++) {
		        	String filename = files[i].getName();
		            System.out.println("Opening: " + files[i].getAbsolutePath() + "." );
		    	
		            String extension =  filename.substring(filename.indexOf("."));
		            System.out.println(extension);
		        	
		            
		            if(!extension.equals(".csv")) {
		            	System.out.println("Unsupported file type selected.");
		            }
		            else {
		    	        String filePath = files[i].getAbsolutePath();
		    	        if (i == 0) dataSet = ReadCSV.readData(filePath);
		    	        else {
		    	        	dataSet.addAll(ReadCSV.readData(filePath));
		    	        }
		    	        	
		    	        if(i == files.length-1) return dataSet;
		            }
	            }
        	}
        	else if (mode==SINGLE_MODE) {
        		File files = fc.getSelectedFile();
	        		
		        	String filename = files.getName();
		            System.out.println("Opening: " + files.getAbsolutePath() + "." );
		    	
		            String extension =  filename.substring(filename.indexOf("."));
		            System.out.println(extension);
		        	
		            
		            if(!extension.equals(".csv")) {
		            	System.out.println("Unsupported file type selected.");
		            }
		            else {
		    	        String filePath = files.getAbsolutePath();
		    	        dataSet = ReadCSV.readData(filePath);
		    	        return dataSet;
		            }
	            
        	}
        } else {
        	  System.out.println("Open command cancelled by user." );
        	  return null;
        }
		return null;
	}
	


}
