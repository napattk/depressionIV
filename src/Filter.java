import java.util.Arrays;
import java.util.List;

public class Filter {

	public static void applyFilterTier2(String text, List<List<String>> data) {
		
		double totalTime = 0;
		double totalTier2Time = 0;
		double tier2Time = 0;
		double tMin = 0;
		double tMax = 0;
		
		for(int i = 0; i < data.size();i++ ) {
			String tier2 = data.get(i).get(2);
			try {
				tMin = Float.parseFloat(data.get(i).get(5));
				tMax = Float.parseFloat(data.get(i).get(6));
				
				totalTime = totalTime + (tMax - tMin);
		    	
				if(tier2.matches("(?i)("+text+").*")) {
					try {
						tier2Time = tMax - tMin;
						totalTier2Time = totalTier2Time + tier2Time;
					} catch(NumberFormatException e) {
						
					}
				}
			}catch(NumberFormatException e) {
				
			}
		}
		
		double percentage = (totalTier2Time/totalTime)*100;
		
		MainFrame.setTier2TextArea("Total Tier2 Percentage: "+ percentage);
	}
	
	public static void applyFilterTier1(String text, List<List<String>> data) {
		List<List<String>> tier1List = MainFrame.getTier1List();
		
		for(int i = 0; i < data.size();i++ ) {
			String tier1 = data.get(i).get(4);
	    		//List all with query string
				if(tier1.matches("(?i)("+text+").*")) {
					String tMin = data.get(i).get(5);
			    	String tMax =data.get(i).get(6);
			    	tier1List.add(Arrays.asList(tier1,tMin,tMax));
				}
			}
		MainFrame.updateTier1Table();
	}

		
}
	

