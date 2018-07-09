import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ReadCSV {
	
	private static List<List<String>> lines;
	final static JFileChooser fc = new JFileChooser();
	public static int REPLACE_DATA = 1;
	public static int APPEND_DATA = 2;


	public static void main(String[] args){
        readData("dataSet1.csv");
        printData(lines);
    }	
	
	public static List<List<String>> readData(String FileName) {
		Scanner scanner = null;
		lines= new ArrayList<>();
		try {
			scanner = new Scanner(new File(FileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        while(scanner.hasNext()){
        	String line =  scanner.nextLine();

        	if (line != null && (line.trim().equals("") || line.trim().equals("\n"))){
        		//Empty Line of Data
        	}
        	else {
        		String[] val = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        		lines.add(Arrays.asList(val));	
        	}
        }
        
        scanner.close();
        return lines;
	}
	
	public static void printData(List<List<String>> data) {
		
		for(int i = 0; i < data.size();i++ ) {
			for(int j= 0; j < data.get(i).size();j++ ) {
				System.out.print(data.get(i).get(j) + " ");
            }	
			System.out.println( );
		}
		
	}
	
	public static JFileChooser getFC() {
		return fc;
	}
	
	public static void openFile(MainFrame frame, int openFileMode) {
		File file = null;
		int returnVal = fc.showOpenDialog(frame);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(openFileMode == REPLACE_DATA) { //mode replace
				file = fc.getSelectedFile();
				
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
			        
				        MainFrame.setMainFrameData(newFileData);
				        BarPanel.setBarData(newFileData);
				        frame.repaint();
			    }
			} else if (openFileMode == APPEND_DATA) { //mode append
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
		            	List<List<String>> data = MainFrame.getMainFrameData();
		            	String filePath = files[i].getAbsolutePath();
			        	data.addAll(ReadCSV.readData(filePath));
			        	MainFrame.setMainFrameData(data);
			        	frame.repaint();
		            }
	            }
	        }

		} else {
			  System.out.println("Open command cancelled by user." );
		}
	}
}
