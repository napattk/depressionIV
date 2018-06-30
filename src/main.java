import java.util.List;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] arg) {
		
		List<List<String>> dataSet = null;
		dataSet = ReadCSV.readData("dataSet1.csv");
		//readCSV.printData(dataSet);
		//GUI.setData(dataSet);
		GUITest gui = new GUITest(dataSet);

		
		
	}
}
