import java.util.List;

public class Filter {

	public static void applyFilter(String text, List<List<String>> data) {
		
		double totalTime = Double.parseDouble(data.get(data.size()-1).get(6));
		double totalTier2Time = 0;
		double tier2Time = 0;
		
		for(int i = 0; i < data.size();i++ ) {
			String tier2 = data.get(i).get(2);
	    	
			if(tier2.matches("(?i)("+text+").*")) {
				try {
					double tMin = Float.parseFloat(data.get(i).get(5));
					double tMax = Float.parseFloat(data.get(i).get(6));
					tier2Time = tMax - tMin;
					totalTier2Time = totalTier2Time + tier2Time;
				} catch(NumberFormatException e) {
					
				}
			}
		}
		
		double percentage = (totalTier2Time/totalTime)*100;
		
		GUITest.setTier2TextArea("Total Tier2 Percentage: "+ percentage);
	}
	
}
