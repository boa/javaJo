package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class database_unavailable extends JPanel {

	JPanel outer;
	JLabel message;
	JButton refresh_button;
	Box container;
	
	Image image;
	
	Dimension infoPanelSize;
	
	public database_unavailable(ActionListener a) {//, JApplet aaa) {
		
		this.setLayout(new BorderLayout());
		
		
		container = Box.createHorizontalBox();
		
		container.setBorder(new RoundEdgedBorder());
		//container.setBorder(new LineBorder(Color.black, 1, true));
		
		infoPanelSize = new Dimension(600,140);
		container.setPreferredSize(infoPanelSize);
		container.setMaximumSize(infoPanelSize);
		container.setMinimumSize(infoPanelSize);
		
		outer = new JPanel();		
		outer.setLayout(
		         new BoxLayout( outer, BoxLayout.X_AXIS ) );
		
						
		outer.add( Box.createGlue() );
		
		outer.add(container);
		outer.add( Box.createVerticalGlue()  );
		outer.add( Box.createGlue() );
		
		/** container
		 *   ____________________________________
		 *  |
		 *  |  
		 *  | >
		 *  | >
		 *  | >
		 *  |
		 *  |
		 *   _____________________________________
		 *  
		 *  
		 *  
		 *  */
		try {
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {// if the look and feel is not
			// supported, don't worry about it.
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		
		container.add( Box.createHorizontalGlue() );
		String htmlLabel = "<html>B настоящий момент база данных <br>недоступна, " 
			+ "пожалуйста, <br>повторите свою попытку позже</html>";
		
		//java.net.URL imgURL = aaa.getClass().getResource("/ouch.png");
		//ImageIcon icon = new ImageIcon( imgURL );
		
		
		message = new JLabel(htmlLabel, SwingConstants.CENTER);
		//message.setIcon(icon.getImage());
		message.setMaximumSize(new Dimension(50,130));		
		
		
		
		message.setFont(new Font("Verdana", Font.PLAIN, 22));
		message.setIconTextGap(20);
		container.add(message);		
		container.add( Box.createHorizontalGlue() );
		refresh_button = new JButton("Обновить");			
		refresh_button.addActionListener(a);
		refresh_button.setOpaque(false);
		container.add(refresh_button);
		container.add( Box.createHorizontalGlue() );
				
		
		this.add(outer);
		
		
		
		
	}
	
	public ImageIcon createImageIcon(String path,
	        String description) {
	/** Returns an ImageIcon, or null if the path was invalid. */
	java.net.URL imgURL = getClass().getResource(path);
	if (imgURL != null) {
	return new ImageIcon(imgURL, description);
	} else {
	System.err.println("Couldn't find file: " + path);
	return null;
	}
	}
	
		
	public class RoundEdgedBorder extends LineBorder
	{
	int arcWidth=85,arcHeight=85;
	Color fillColor=new Color(250,243,215);

	public RoundEdgedBorder() {
	super(Color.RED);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{ 
	g.setColor(fillColor); 
	//g.drawRoundRect(x, y, width-2, height-2, arcWidth, arcHeight);
	g.fillRoundRect(x, y, width-3, height-3, arcWidth, arcHeight); 
	
	
	}
	}

	
}
