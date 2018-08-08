import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Aesthetics {
		public static Color BGColor = Color.decode("#334c66");
		public static Color borderColor = Color.decode("#fab81e");
		public static Color textColor = Color.decode("#ffffff");
		static DecimalFormat twoDecimals = new DecimalFormat("#.##");
		static Font robotoFont;
		
		Aesthetics(){
			//set font
			try {
			    robotoFont = Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf")).deriveFont(12f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("roboto.ttf")));
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		
		public static JButton createSimpleButton(String text) {
			  JButton button = new JButton(text);
			  button.setForeground(Color.BLACK);
			  button.setBackground(Color.WHITE);
			  Border line = new LineBorder(Color.BLACK);
			  Border margin = new EmptyBorder(5, 15, 5, 15);
			  Border compound = new CompoundBorder(line, margin);
			  button.setBorder(compound);
			  button.setFont(Aesthetics.robotoFont);
			  return button;
		}
		
		public static JPanel createBorderedPanel(String text ) {
			JPanel JPanel = new JPanel();
			TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Aesthetics.borderColor),text);
			titledBorder.setTitleColor(Aesthetics.textColor);
			titledBorder.setTitleFont(Aesthetics.robotoFont);
			
			JPanel.setBackground(Aesthetics.BGColor);
			JPanel.setBorder(titledBorder);
			return JPanel;
		}
}
