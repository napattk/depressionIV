import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadCSV {
	
	private static List<List<String>> lines;
	

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
	
}
