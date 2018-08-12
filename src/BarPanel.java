import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class BarPanel extends JPanel implements MouseMotionListener{
	
	private static int WIN_WIDTH;
	private static int WIN_HEIGHT;
	private static List<List<String>> data = null;
	private static List<String> tier2List = new ArrayList<>();
	private boolean mouseMoved = false;
	private int mx = 0;
	private int my = 0;
	private JTable descTable;
	
	Color color[] = { //Colors for content bar
			Color.decode("#39342f"), Color.decode("#800000"),
			Color.decode("#ed1b24"), Color.decode("#f26223"), Color.decode("#faa41b"), 
			Color.decode("#ffd203"), Color.decode("#fff205"), Color.decode("#91c74b"),
			Color.decode("#16a04a"), Color.decode("#01ab86"), Color.decode("#1affe5"),
			Color.decode("#00b3ff"),Color.decode("#0551a5"), 
			Color.decode("#3e3997"), Color.decode("#5b2f91"), Color.decode("#8a288f"), 
			Color.decode("#8f5d28"), Color.decode("#5a5a5a"),Color.decode("#c3c3c3"),
			Color.decode("#e2d6c8"), Color.decode("#e2d6c8"), Color.decode("#e2d6c8"), Color.decode("#e2d6c8"), Color.decode("#e2d6c8")
			};
	
	public BarPanel(List<List<String>> newData) {
		this.addMouseMotionListener(this);
		BarPanel contentBar = this;
		contentBar.setBackground(Aesthetics.BGColor);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Aesthetics.BGColor),"Content Bar");
		titledBorder.setTitleColor(Aesthetics.textColor);
		titledBorder.setTitleFont(Aesthetics.robotoFont);
		contentBar.setBorder(titledBorder);
		data = newData;
		WIN_WIDTH = MainFrame.WIN_WIDTH;
		WIN_HEIGHT = MainFrame.WIN_HEIGHT;
		
	}
	
	protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      double rectHeight = 80;
	      double rectY = 50;
	      double rectWidth = 0;
	      double rectX = 20;
	      double prevRectWidth = 0;
	      double curRectX = 0;
	      
	      //Reset Tear2 List when component is painted
	      tier2List.clear();
	      tier2List.add("clause");
		  tier2List.add("clause_exp");

    	  double totalTime = Double.parseDouble(data.get(data.size()-1).get(6));
    	  int MAX_BAR_WIDTH = WIN_WIDTH - 60;
    	  
    	  //Draw Content Bar
	      for(int i = 0; i<data.size(); i++) {
	    	  rectX = rectX + prevRectWidth;
	    	  curRectX = 10;
	    	  
	    	  String line = data.get(i).get(0);
	    	  String subject = data.get(i).get(1);
	    	  String tier2 = data.get(i).get(2);
	    	  String tier3 = data.get(i).get(3);
	    	  String tier1 = data.get(i).get(4);
	    	  double tMin = Float.parseFloat(data.get(i).get(5));
	    	  double tMax = Float.parseFloat(data.get(i).get(6));
	    	  
	    	  rectWidth = (double) ((tMax-tMin)/totalTime)*MAX_BAR_WIDTH;
	    	  
	    	  Graphics2D g2d = (Graphics2D) g;
	    	  Rectangle2D r2d = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
	    	  
	    	  //Highlight bar when mouse over
	    	  if(r2d.contains(mx,my)) {
	    		  r2d.setFrame(rectX, rectY-10, rectWidth, 100);
	    		  MainFrame.setDescTable(line, subject, tier1, tier2, tier3, Aesthetics.twoDecimals.format(tMin), Aesthetics.twoDecimals.format(tMax));
	    	  }
	    	  g2d.setColor(color[findTier2Color(tier2)]);
	    	  g2d.fill(r2d);
	    	
	    	  prevRectWidth = rectWidth;
	      }
	     
	      
	       //Display color palette and legend
	    	  rectY = 150;
	    	  rectHeight = 20;
	    	  rectWidth = 20;
		      rectX = 25;
		      prevRectWidth = 0;
		      curRectX = 0;
		      for(int j = 0; j <tier2List.size(); j++)  {
		    	  Graphics2D g2d = (Graphics2D) g;
		    	  g2d.setColor(color[j]);
		    	  Rectangle2D r2d = new Rectangle2D.Double(rectX+95*j, rectY, rectWidth, rectHeight);
		    	  g2d.fill(r2d);
		    	  
		    	  g2d.setColor(Color.WHITE);
		    	  g.drawString(tier2List.get(j),(int) rectX+25+95*j, (int)rectY+15);
		      }
	      
	   }
	
	private static int findTier2Color(String tier2) {
		int i = 0;
		tier2 = tier2.toLowerCase();
		
			for (i = 0; i <tier2List.size(); i++){
	            if(tier2.matches(tier2List.get(i))){ //Add count
	                return i;
	            }else if(tier2.equals("?")) { //Fix Regular expression errors
	            	return i;
	            }
	            else if(tier2.matches("(?i)(clause).*")) { //Count clauses
	            	String[] clauseSplit = tier2.split("_");
	   
	            	if(clauseSplit[clauseSplit.length-1].matches("(?i)(exp).*")) { //Count clauses by experimenter
	            		return 1;
	            	}else { //Count clauses by participant
	            		return 0;
	            	}
	            }
	        }

		tier2List.add(tier2);
		return i+1;
	}
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("moving");
		mouseMoved = true;
		repaint();
	    mx = e.getX();
	    my = e.getY();

	}
	
	public static void setBarData(List<List<String>> newData) {
		data = newData;
		
	}
	
}