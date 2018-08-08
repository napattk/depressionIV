import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class Filter {
	
	
	public static void applyTierFilter(String text, List<List<String>> data, int tier) { //Filters column for string entered
		List<List<String>> filterTierList = null;
		
		 //Use data from correct table
		if (tier == 1) filterTierList = MainFrame.getTier1TableData();
		else if (tier == 2) filterTierList = MainFrame.getTier2TableData();
		
		for(int i = 0; i < data.size();i++ ) {
			String tierData = null;
			if (tier == 1) tierData = data.get(i).get(4); // use tier1 column
			else if (tier == 2) tierData = data.get(i).get(2); // user tier 2 column
			
	    		//List all with query string
				if(tierData.matches("(.*?)("+text+").*")) {
					String tMin = Aesthetics.twoDecimals.format(Double.parseDouble(data.get(i).get(5)));
			    	String tMax = Aesthetics.twoDecimals.format(Double.parseDouble(data.get(i).get(6)));
			    	filterTierList.add(Arrays.asList(tierData,tMin,tMax));
			    	
				}
			}
		if (tier == 1)  MainFrame.updateTier1Table();
		else if (tier == 2) MainFrame.updateTier2Table();
	}
	
	
	public static void populateTier2Percen(List<List<String>> data) { //Calculate percentage of all tier 2 types
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

					if(j == 0) {//clauses
						if(tier2.matches("(?i)(clause).*")){
							String[] clauseSplit = tier2.split("_");
			            	
			            	if(clauseSplit[clauseSplit.length-1].matches("(?i)(exp).*")) { } //dont count if _exp
			            	else { 
			            		tier2Time = tMax - tMin;
								totalTier2Time = totalTier2Time + tier2Time;
			            	}
						}
					}
					else if (j==1) {// clause_exp
						
						if(tier2.matches("(?i)(clause).*")){
							String[] clauseSplit = tier2.split("_");
			            	
			            	if(clauseSplit[clauseSplit.length-1].matches("(?i)(exp).*")) {
			            		tier2Time = tMax - tMin;
								totalTier2Time = totalTier2Time + tier2Time;
			            	}
						}
					}
					else {//all else
						if(tier2.equals(tier2List.get(j))) {
							tier2Time = tMax - tMin;
							totalTier2Time = totalTier2Time + tier2Time;
							//System.out.println(tier2 + " equals " + tier2List.get(j));
						}
					}

			}
			
			String percentage = Aesthetics.twoDecimals.format((totalTier2Time/totalTime)*100);
			totalTier2Time = 0;
			totalTime = 0;
			TableData.add(Arrays.asList(tier2List.get(j),percentage));
		}
		
		MainFrame.updateTier2PercenTable();
	}
	
	private static List<String> getUniqueTier2(List<List<String>> data) { //Add one instance of each type into array and return
		List<String> uniqueTier2 = new ArrayList<>();
		
		//Exists in all data sets
		uniqueTier2.add("clause");
		uniqueTier2.add("clause_exp");
		
		for(int i = 0; i <data.size(); i++) {
			String tier2 = data.get(i).get(2);
			int j = 0;
			for (j = 0; j <uniqueTier2.size(); j++){
				try{
	            if(tier2.matches(uniqueTier2.get(j))) break;
	            else if(tier2.matches("(?i)(clause).*")) break;
				}catch(PatternSyntaxException e) {
					break;
				}
	        }
			
			if(j == uniqueTier2.size()) {
				//System.out.println("Found Unique: " + tier2);
				uniqueTier2.add(tier2);
			}
			
		}
		
		return uniqueTier2;
	}
		
}
	

