import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filter {

	public static void applyFilterTier2(String text, List<List<String>> data) {
		/*
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
				}static List<List<String>> tier2TableData = new ArrayList<>();
			}catch(NumberFormatException e) {
				
			}
		}
		
		double percentage = (totalTier2Time/totalTime)*100;
		
		MainFrame.setTier2TextArea("Total Tier2 Percentage: "+ percentage);
		*/
		
		List<List<String>> tier2List = MainFrame.getTier2TableData();
		
		for(int i = 0; i < data.size();i++ ) {
			String tier1 = data.get(i).get(4);
	    		//List all with query string
				if(tier1.matches("(.*?)("+text+").*")) {
					String tMin = data.get(i).get(5);
			    	String tMax =data.get(i).get(6);
			    	tier2List.add(Arrays.asList(tier1,tMin,tMax));
				}
			}
		MainFrame.updateTier1Table();
	}
	
	public static void applyTierFilter(String text, List<List<String>> data, int tier) {
		List<List<String>> filterTierList = null;
		
		
		if (tier == 1) filterTierList = MainFrame.getTier1TableData();
		else if (tier == 2) filterTierList = MainFrame.getTier2TableData();
		
		for(int i = 0; i < data.size();i++ ) {
			String tierData = null;
			if (tier == 1) tierData = data.get(i).get(4); // use tier1 column
			else if (tier == 2) tierData = data.get(i).get(2); // user tier 2 column
			
	    		//List all with query string
				if(tierData.matches("(.*?)("+text+").*")) {
					String tMin = data.get(i).get(5);
			    	String tMax = data.get(i).get(6);
			    	filterTierList.add(Arrays.asList(tierData,tMin,tMax));
			    	
				}
			}
		if (tier == 1)  MainFrame.updateTier1Table();
		else if (tier == 2) MainFrame.updateTier2Table();
	}
	
	public static void populateTier2Percen(List<List<String>> data) {
		List<String> tier2List = getUniqueTier2(data);

		List<List<String>> TableData = MainFrame.getTier2PercenTableData();
		
		double totalTime = 0;
		double totalTier2Time = 0;
		double tier2Time = 0;
		double tMin = 0;
		double tMax = 0;
		
		for(int j = 0; j < tier2List.size(); j++) {
			for(int i = 0; i < data.size();i++ ) {
				String tier2 = data.get(i).get(2);
					tMin = Float.parseFloat(data.get(i).get(5));
					tMax = Float.parseFloat(data.get(i).get(6));
					
					totalTime = totalTime + (tMax - tMin);
					
					if(j == 0) {
						if(tier2.matches("(?i)("+tier2List.get(j)+").*")) {
							tier2Time = tMax - tMin;
							totalTier2Time = totalTier2Time + tier2Time;
							System.out.println(tier2 + " equals " + tier2List.get(j));
						}
					}
					else {
						if(tier2.equals(tier2List.get(j))) {
							tier2Time = tMax - tMin;
							totalTier2Time = totalTier2Time + tier2Time;
							System.out.println(tier2 + " equals " + tier2List.get(j));
						}
					}

			}
			String percentage = Double.toString((totalTier2Time/totalTime)*100);
			System.out.println(totalTime);
			totalTier2Time = 0;
			totalTime = 0;
			TableData.add(Arrays.asList(tier2List.get(j),percentage));
		}
		
		MainFrame.updateTier2PercenTable();
	}
	
	private static List<String> getUniqueTier2(List<List<String>> data) {
		List<String> uniqueTier2 = new ArrayList<>();
		uniqueTier2.add("clause");
		
		for(int i = 0; i <data.size(); i++) {
			String tier2 = data.get(i).get(2);
			int j = 0;
			for (j = 0; j <uniqueTier2.size(); j++){
	            if(tier2.matches(uniqueTier2.get(j))) break;
	            else if(tier2.matches("(?i)(clause).*")) break;
	        }
			
			if(j == uniqueTier2.size()) {
				System.out.println("Found Unique: " + tier2);
				uniqueTier2.add(tier2);
			}
			
		}
		
		return uniqueTier2;
	}
		
}
	

