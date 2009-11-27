
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class general extends JApplet {
	
	
	
	
	
	
	public void init() {	    	 
		//Execute a job on the event-dispatching thread; creating this applet's GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() { 				
						    
				   		    					
				}
			});
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	

	
	
}
