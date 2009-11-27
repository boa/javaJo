package admin.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class Toolbar {
	
	//TOOLBARS
	  //EXPERT
	  JToolBar expertToolbar; 	    
	  Dimension toolBarWidth = new Dimension(700,40);
	  	//Combo!
	  	JComboBox expertCombo;
	  	//BEWARE! LOoK at folowing, in cgi and there
	  	String[] comboWhat = { "Клиенты", "Заявки", "Работы заявки", 
			  			       "Работники в заявке", "Работы", "Работники" };	  	
	  	//ALIGNMENT (VERTICAL)
	  	Box expertTableBox;
	  	//STATUS
	  	//JLabel expertStatus;
	  	JTextArea expertStatusArea;
	  //NoRMAL
	
	public Toolbar() {
		/* Инициализация...
		 * Под меню апплета расположена панель инструментов.
		 * Включает выпадающий список табличек и управляторы
		 * основными действиями с табличками
		 * 
		 */	//TOOLBAR_MAIN
			expertToolbar = new JToolBar(); 
			//TablePanel.add(expertToolbar);
			  //SIZE
			  expertToolbar.setPreferredSize(new Dimension(toolBarWidth));
			  //FLOAT?OH_NO!
			  expertToolbar.setFloatable(false);
			  //expertToolbar.setMargin(new Insets(-10, -10, 0, 0));
			  //OPAQUE?F**K_IT!
			  expertToolbar.setOpaque(false);
			  //FLOAT_CAP!FLOAT_TO_LEFT!
			 // expertToolbar.setAlignmentX(LEFT_ALIGNMENT);
			  //SEXY-BORDER.DO_YA?
			  Border empty = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
			  expertToolbar.setBorder(empty);
			    //TOOLBAR_ELEMENTS
			    //ЫУЗфКФещК!
			    expertToolbar.addSeparator();		  	
			    //COMBO_КОРОБКА ;)
			  	expertCombo = new JComboBox(comboWhat);		  	
			  	expertToolbar.add(expertCombo);
			  	  //SETTINGS_OF_КОМБО-КОРОБКА!
			  	  //DID_U_SEEN_FOCUS?
			  	  expertCombo.setFocusable(false);
			  	  //APPLET_HEARING_COMBO!
			  	//  expertCombo.addActionListener(this);
			  	  //COMBO-SIZE!
			  	  expertCombo.setMaximumSize(new Dimension(150,23));	  	
			  	//ЫУЗфКФещК!
			  	expertToolbar.addSeparator();
			  	//STATUS_LABEL
			  	//expertStatus = new JLabel("..:: Прикладное направление");
			  	//expertToolbar.add(expertStatus);
			  	expertStatusArea = new JTextArea();
			  	//expertStatusArea.setPreferredSize(new Dimension(150, 10));
			  	expertStatusArea.setFont(new Font("sansserif", Font.PLAIN, 12));
			  	expertStatusArea.setLineWrap(true);
			  	expertStatusArea.setWrapStyleWord(true);
			  	expertStatusArea.setEditable(false);
			  	expertStatusArea.insert("Журнал событий" , expertStatusArea.getLineCount()-1);
			  	JScrollPane jsca = new JScrollPane(expertStatusArea);
			  	jsca.setVerticalScrollBarPolicy(
			  			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			  	jsca.setMaximumSize(new Dimension((toolBarWidth.width-180), 25));
			  	expertToolbar.add(jsca);
		}

}
