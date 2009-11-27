package admin;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class CustomTableCellRenderer extends DefaultTableCellRenderer{
	    public Component getTableCellRendererComponent (JTable table, 
	Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
	      Component cell = super.getTableCellRendererComponent(
	                         table, obj, isSelected, hasFocus, row, column);
	      int evenR = 220, evenG = 220, evenB = 220;
	      int oddR = 230, oddG = 230, oddB = 230;
	      int shiftR = 2, shiftG = 12, shiftB = 24; 
	      
	      
	      
	      if (isSelected) {
	    	  cell.setForeground(Color.BLACK);
	    	  if (row % 2 == 0) {
	    		  cell.setBackground(new Color(evenR+shiftR,evenG+shiftG,evenB+shiftB));
	    		  
	    	  }
	    	  else {
	    		  cell.setBackground(new Color(oddR+shiftR,oddG+shiftG,oddB+shiftB));
	    		  
	    	  }	 
	      }else{
	      if (row % 2 == 0) {
	    	  cell.setBackground(new Color(evenR,evenG,evenB));
	    	   
	    	  
	    	  
	      }
	      else {
	    	  cell.setBackground(new Color(oddR,oddG,oddB));
	    	  
	      }
	      }
	     
	      
	      return cell;
	    }
}