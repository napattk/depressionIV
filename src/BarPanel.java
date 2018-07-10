import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
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
	
	Color color[] = {
			Color.decode("#39342f"), 
			Color.decode("#ed1b24"), Color.decode("#f26223"), Color.decode("#faa41b"), 
			Color.decode("#ffd203"), Color.decode("#fff205"), Color.decode("#91c74b"),
			Color.decode("#16a04a"), Color.decode("#01ab86"), Color.decode("#0551a5"), 
			Color.decode("#3e3997"), Color.decode("#5b2f91"), Color.decode("#8a288f"), 
			Color.decode("#8f5d28"), Color.decode("#e2d6c8")
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
		tier2List.add("clause");
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
    	  
    	  double totalTime = Double.parseDouble(data.get(data.size()-1).get(6));
    	  int MAX_BAR_WIDTH = WIN_WIDTH - 60;
    	  
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
	    	  
	    	  //System.out.println("rectX:" +rectX + " rectY:" +rectY+" rectWidth:"+rectWidth+ " rectHeight:"+rectHeight);
	    	  
	    	  Graphics2D g2d = (Graphics2D) g;
	    	  Rectangle2D r2d = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
	    	  if(r2d.contains(mx,my)) {
	    		  r2d.setFrame(rectX, rectY-10, rectWidth, 100);
	    		  MainFrame.setDescTable(line, subject, tier1, tier2, tier3, Double.toString(tMin), Double.toString(tMax));
	    	  }
	    	  g2d.setColor(color[findTier2Color(tier2)]);
	    	  g2d.fill(r2d);
	    	
	    	  prevRectWidth = rectWidth;
	      }
	      
	      /*
	       //////////////////////////////////////////////////
	    	  rectY = 130;
	    	  rectHeight = 20;
	    	  rectWidth = 20;
		      rectX = 20;
		      prevRectWidth = 0;
		      curRectX = 0;
		      for(int j = 0; j <color.length; j++)  {
		    	  Graphics2D g2d = (Graphics2D) g;
		    	  g2d.setColor(color[j]);
		    	  Rectangle2D r2d = new Rectangle2D.Double(rectX, rectY+25*j, rectWidth, rectHeight);
		    	  g2d.fill(r2d);
		      }
	      */
	   }
	
	private static int findTier2Color(String tier2) {
		int i = 0;
		for (i = 0; i <tier2List.size(); i++){
            if(tier2.matches(tier2List.get(i))){
                return i;
            }else if(tier2.matches("(?i)(clause).*")) {
            	return 0;
            }
        }
		
		tier2List.add(tier2);
		System.out.println(tier2List.get(i) + " added");
		System.out.println(tier2List.size());
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