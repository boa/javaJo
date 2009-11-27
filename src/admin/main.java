package admin;

/* * - BEWARE! FILE ENCODED BY UTF-8! *
 *   - OH NO! WHY, CAP?
 *   - JUST.
 *   */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import GUI.database_unavailable;

import components.querys;

public class main extends JApplet implements
	ActionListener, 
	ListSelectionListener, 
	TableModelListener	
	 {	
	
	//TABS
	JTabbedPane tabbedPane;
	JComponent panel1, panel2;
	
	JPanel TablePanel;
	//MENU
	/*JMenuBar menuBar;
	  JMenu mode;
	    JMenuItem expertMode, normalMode;*/
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
	//TABLE
	JPanel expertTablePanel;
	Dimension expertTableWidth = new Dimension(480,380);
	JTable expertTable;
	JTableHeader expertTableHeader;
	JScrollPane expertScrollTable;
	DefaultTableModel dtm;
	String value1 = "", value2 = "";
	static ArrayList<Object> popUps = new ArrayList<Object>();	
	//FIELDS
	JPanel expertFieldPanel;
	Dimension expertFieldWidth = new Dimension(
			toolBarWidth.width - expertTableWidth.width - 5, 
			expertTableWidth.height
			);
	JPanel inlayGrid; // For generated content
	Box expertFieldBox; // For vertical alligment
	JButton expertAdd, expertFieldClear;	
	//.. other generates in table request
	//QUWERY
	querys q,q1;
	final public static String cgipath = "http://localhost/cgi-bin/cgi_job_list_project.exe?";
	final public static String expChdd = "action=query&t=0";
	Boolean god = true;
	poper pop;
	
	
	JPanel treePanel, addPanel;	
	JTree tree, childTree; 
	JScrollPane jst; 	
	ArrayList<Request> Requests = new ArrayList<Request>();
	DefaultMutableTreeNode rootTreeModel = new DefaultMutableTreeNode("Requests");
	JPopupMenu popupTreeActionMenu;
	JMenuItem popupTreeAdd, popupTreeRemove, popupTreeCheck, popupTreeunCheck;	
	JList treeAddDataList;
	DefaultListModel dlm;
	JButton addOK, addNO;
	DefaultMutableTreeNode popapedObj, globePopapedObj;
	ArrayList<String> excludeIDs;
	Boolean isListShowing = false;
	final static String pathToIcons = "../images/";
	
	database_unavailable dbu;
	
	ActionListener aa = new ActionListener() {		
		public void actionPerformed(ActionEvent e) {						
			try {
				if  (components.database.isDatabaseOK(cgipath.concat(expChdd))) {
									
					getContentPane().remove(dbu);					
					initTabs();	
				    initTreePanel();
				    initTablePanel();	
				    initnShowList(1,null);  
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
	};;;
	
	
	public void init() {	    	 
		//Execute a job on the event-dispatching thread; creating this applet's GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() { 					
					
					try {
				        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				    } catch (Exception evt) {}				    
				   
				    try {
						if (components.database.isDatabaseOK(cgipath.concat(expChdd))) {
											    	
								initTabs();	
							    initTreePanel();
							    initTablePanel();	
							    initnShowList(1,null);	
							
						}
						 else {
							
							dbu = new database_unavailable(aa);//, main.this);
							getContentPane().add(dbu);	
													
							
						}
					} catch (IOException e) {
						dbu = new database_unavailable(aa);//, main.this);
						getContentPane().add(dbu);	
						e.printStackTrace();
					}
				    
				    			    					
				}
			});
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	public void initTabs() {
	/* Инициализация...
	 * 
	 */		
		tabbedPane = new JTabbedPane();
		add(tabbedPane);		
	}	
	
	
	
	public void setPopupWhat(Boolean a, Boolean b, Boolean c, Boolean d) {		
		popupTreeAdd.setVisible(a);		
		popupTreeRemove.setVisible(b);
		popupTreeCheck.setVisible(c);
		popupTreeunCheck.setVisible(d);
	}
	
	
	public void initTreePanel() {
		
		treePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		
		
		if (Request.GrowForestIn(rootTreeModel)) {
			
			popupTreeActionMenu = new JPopupMenu();
			
			
			popupTreeActionMenu.setPreferredSize(new Dimension(100,50));
			
						
			popupTreeAdd = new JMenuItem("Добавить");
			popupTreeAdd.setVisible(false);
			popupTreeActionMenu.add(popupTreeAdd);
			popupTreeAdd.addActionListener(this);
			popupTreeAdd.setIcon(createImageIcon(pathToIcons+"03.png", ""));
			
			popupTreeRemove = new JMenuItem("Удалить");
			popupTreeRemove.setVisible(false);
			popupTreeActionMenu.add(popupTreeRemove);
			popupTreeRemove.addActionListener(this);
			popupTreeRemove.setIcon(createImageIcon(pathToIcons+"04.png", ""));
			
			popupTreeCheck = new JMenuItem("Завершить");
			popupTreeCheck.setVisible(false);
			popupTreeActionMenu.add(popupTreeCheck);
			popupTreeCheck.addActionListener(this);
			popupTreeCheck.setIcon(createImageIcon(pathToIcons+"rDone.png", ""));
			
			popupTreeunCheck = new JMenuItem("Продолжить");
			popupTreeunCheck.setVisible(false);
			popupTreeActionMenu.add(popupTreeunCheck);
			popupTreeunCheck.addActionListener(this);
			popupTreeunCheck.setIcon(createImageIcon(pathToIcons+"runDone.png", ""));
			
			
			tree = new JTree(rootTreeModel);
			
			 tree.setCellRenderer(new myTreeRenderer());
			 tree.setRootVisible(false);
			 
			 // Установка режима выбора 1 узла дерева
			 TreeSelectionModel tsm = tree.getSelectionModel();
			 tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			 
						
			 jst = new JScrollPane(tree);
			 
			 jst.setPreferredSize(new Dimension(toolBarWidth.width-50, 300));
			 
			 Border aquaBorder = UIManager.getBorder( "TitledBorder.aquaVariant" );
			 jst.setBorder( new TitledBorder( aquaBorder, "Список заявок" ) );
			 
			 treePanel.add(jst);
				
			 ImageIcon icon = createImageIcon("../images/p1.png", "");

			 tabbedPane.addTab("Учет заявок", icon, treePanel,
	        "Does nothing");
			 
			 
			// tree.add(popupTreeActionMenu);
			 
			 tree.addMouseListener(new TreeMouseListener());
			 
		} else JOptionPane.showMessageDialog(null, "Something wrong with the tree!");
		
		
		
		
		
	}
	
	
	public void rechargeTree() {
		
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		
				
		Request.GrowForestIn(rootTreeModel);
		
				
		
		model.reload();
		
		
		
	}
	
	
	public void initnShowList(int what, ArrayList<Integer> excludeWhat){
		/*
		 * Функция отображает лист элементов для выбора
		 * Получает что, получает список исключений
		 * 
		 */
		
		//JOBS, Please
		if (what==0) {			
			//TODO HELLO!			
		}
		else
		//WoRKERS
		if(what==1) {			
			addPanel = new JPanel();		
			
			
			dlm = new DefaultListModel();
			
			
			Object[] wha = db_selectAllDataFrom(5, true);
			
			for (int i=0; i < wha.length; i++) {
				
				dlm.addElement(wha[i]);
				
			}
			
			//dlm.addElement("Debbie Scott");			

			treeAddDataList = new JList(dlm);	
			treeAddDataList.setCellRenderer(new IconListRenderer());
			treeAddDataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			treeAddDataList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			treeAddDataList.setVisibleRowCount(-1);
						
			JScrollPane jsa = new JScrollPane(treeAddDataList);			 
			jsa.setPreferredSize(new Dimension(toolBarWidth.width-200, 100));
			 Border aquaBorder = UIManager.getBorder( "TitledBorder.aquaVariant" );
			 jsa.setBorder( new TitledBorder( aquaBorder, "Выбор" ) );
			addPanel.add(jsa);
			
			//treePanel.add(addPanel);
			
			JPanel jpadds = new JPanel();
			jpadds.setPreferredSize(new Dimension(100,60));
			// Border aquaBorder1 = UIManager.getBorder( "TitledBorder.aquaVariant" );
			// jpadds.setBorder( new TitledBorder( aquaBorder1, " " ) );
			addPanel.add(jpadds);
			 
			
			
			addOK = new JButton("Добавить");
			addOK.setPreferredSize(new Dimension(90,25));
			addOK.addActionListener(this);
			jpadds.add(addOK);
			
			addNO = new JButton("Отмена");
			addNO.setPreferredSize(new Dimension(90,25));
			addNO.addActionListener(this);
			jpadds.add(addNO);
			
			
			
			
		}
	}
	
	
	
	
		
	public void initTablePanel()
	{
		TablePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		//initMenu(); 	
		initToolbar();					
		initTable();
		initFieldPanel();
		
		ImageIcon icon = createImageIcon("../images/p2.png", "");

		 tabbedPane.addTab("Расширенные возможности работы с базой", icon, TablePanel,
        "Does nothing");
		
	}

	/*public void initMenu() {
	 Инициализация...
	 * Меню апплета. Содержит главное меню с одним элементом,
	 * включающим в себя еще два элемента. Предполагается
	 * больше элементов
	 
		menuBar = new JMenuBar();		
		setJMenuBar(menuBar);		
		mode = new JMenu("Режим");			
		menuBar.add(mode);		
		normalMode = new JMenuItem("Обычный");
		//uncomment below if you need it (;
		//normalMode.addActionListener(this);		
		expertMode = new JMenuItem("Эксперт");
		expertMode.setEnabled(false);
		expertMode.addActionListener(this);
		mode.add(normalMode);
		mode.add(expertMode);		
	}*/
	
	public void initToolbar() {
	/* Инициализация...
	 * Под меню апплета расположена панель инструментов.
	 * Включает выпадающий список табличек и управляторы
	 * основными действиями с табличками
	 * 
	 */	//TOOLBAR_MAIN
		expertToolbar = new JToolBar(); 
		TablePanel.add(expertToolbar);
		  //SIZE
		  expertToolbar.setPreferredSize(new Dimension(toolBarWidth));
		  //FLOAT?OH_NO!
		  expertToolbar.setFloatable(false);
		  //expertToolbar.setMargin(new Insets(-10, -10, 0, 0));
		  //OPAQUE?F**K_IT!
		  expertToolbar.setOpaque(false);
		  //FLOAT_CAP!FLOAT_TO_LEFT!
		  expertToolbar.setAlignmentX(LEFT_ALIGNMENT);
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
		  	  expertCombo.addActionListener(this);
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
	
	public void initTable() {
	/* Инициализация...
	 * Все, что связано с панелью с таблицей
	 * 
	 */	//TABLE_PANEL (MAIN)		
		expertTablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));		
		expertTablePanel.setPreferredSize(new Dimension(expertTableWidth));
		//+Border
		Border empty = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		expertTablePanel.setBorder(empty);		
		TablePanel.add(expertTablePanel);
		  //TABLE		
		  expertTable = new JTable();
		  //TABLE_MOUSE
		  expertTable.addMouseListener(new TableMouseListener());
		  //DRAG'N'DROP?
		  //expertTable.setDragEnabled(true);		  
		  //TABLE_SCROLL
		  expertScrollTable = new JScrollPane(expertTable);
		  expertTablePanel.add(expertScrollTable);		  
		 
		  expertScrollTable.setPreferredSize(new Dimension(expertTableWidth.width-10,expertTableWidth.height-10));
		 // expertTable.setPreferredScrollableViewportSize(new Dimension(expertTableWidth));
		 // expertTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		  //TABLE_OPTIONS
		  // TABLE_ROWSORTER
		  expertTable.setAutoCreateRowSorter(true);
		  // FOCUS
		  expertTable.setFocusable(false);
		  // TABLE_SELECTIONS
		  expertTable.setColumnSelectionAllowed(false);
		  expertTable.setCellSelectionEnabled(false);
		  expertTable.setRowSelectionAllowed(true);
		  expertTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  //TABLE_ACTION_SELECT_LISTENER
		  ListSelectionModel rowSM = expertTable.getSelectionModel();
		  rowSM.addListSelectionListener(this);	
		  
		  //TABLE_COLSELECT_LISTENER (need in cell recognition as y)			
		  TableColumnModel tcol2 = expertTable.getColumnModel();
		  ListSelectionModel lsm = tcol2.getSelectionModel();
		  lsm.addListSelectionListener(this);
		
	}
	
	public void initFieldPanel(){		
		expertFieldPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		TablePanel.add(expertFieldPanel);
		expertFieldPanel.setPreferredSize(new Dimension(expertFieldWidth));
		Border empty = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		expertFieldPanel.setBorder(empty);		
		/*
		 * TODO BEWARE, %Username%, 
		 * expertFieldBox.add(something);
		 * ...
		 * expertFieldBox.add(something);		
		 * remember yo!
		 */		
	}
	
				
	//*** ACTIONS N LISTENERS ***
	
	//PROCESS_CLICK_ACTIONS
	public void actionPerformed(ActionEvent e) {
	/*
	 * В событийной модели данный метод Слушателя 
	 * обрабатывает события ActionEvent e. 
	 * В программе сделан один слушатель - сам апплет,
	 * и этот его метод (actionPerformed) обрабатывает все события,
	 * требуется только определить кто породил и какое событие,
	 * что делается ниже if'ами и case'ами
	 * 
	 */
		
		Object firedObject = e.getSource();
		
		/*if (e.getSource() == expertMode)  {		
		} else 				
		if (e.getSource() == normalMode)  {			
		}else 
		*/	
			
		//IF_CLICK_COMBO	
		if (firedObject == expertCombo)  {			
			switch (expertCombo.getSelectedIndex()) {
			//CLIENT
			case 0: {
				Object[][] chd = {{"№", "Фамилия", "Имя", "Отчество", "Адрес", "Телефон"},
						           {20,        90,    70,         80,     120,        60}};
				setTable(0, chd);
				generateFields(chd[0]);				
				break;
			}
			//REQUEST
			case 1: {
				Object[][] rhd = {{"№", "Приход", "Сдача", "Готово ли?", "Клиент"},
		  				           {20,      70,      70,           70,       50}};
				setTable(1, rhd);
				generateFields(rhd[0]);
				break;				
			}
			//JOB_LIST
			case 2: {
				Object[][] jlhd = {{"№", "№Заявки", "№Работы"},
		  				            {20,        70,      70}};					
				setTable(2, jlhd);	
				generateFields(jlhd[0]);				
				break;
			}
			//WORKER_LIST
			case 3: {
				Object[][] wlhd = {{"№", "№Заявки", "№Работника"},
	 				                {20,        80,          90}};
				setTable(3, wlhd);
				generateFields(wlhd[0]);
				break;
			}
			//JOB
			case 4: {
				Object[][] jjhd = {{"№", "Описание", "Стоимость"},
						            {20,        400,          70}};
				setTable(4, jjhd);
				generateFields(jjhd[0]);
				break;
			}
			//WORKER
			case 5: {
				Object[][] wwhd = {{"№", "Фамилия", "Имя", "Отчество", "Стаж"},
						            {50,       90,    70,         80,     50}};
				setTable(5, wwhd);
				generateFields(wwhd[0]);				
				break;
			}
			default: break;	
			}//END_SWITCH
		} else
		if (firedObject == expertFieldClear)  {
			
		clearFieldData();
		//	expertTable.getSelectionModel().clearSelection();
			
			
		} else
		if (firedObject == expertAdd) {			
			if (checkFields()) {				
				expertStatusArea.append("\n" + now("HH:mm:ss - ") + "Статус добавленной записи:" + processInsertTableRecord());				
				rechargeTree();
				expertCombo.setSelectedIndex(expertCombo.getSelectedIndex());				
			} else {
				/*
				 * TODO popup
				 * 
				 */
				expertStatusArea.append("\n" + now("HH:mm:ss - ") + "Ошибка в заполнении формы!");
			}
			
			//THERE_FIELD_FILLER
			/*TODO Сделать филлер драгндропный*/
			/* int selectedRow = lsm.getMinSelectionIndex();
            
            //GET_WHOLE_EDITS_AS_COMPONENTSofTHATPANEL
            int j=0; //Out counter
            JTextField jf;
            Object[] values = getSelectedRowDatums(selectedRow);
            for (int i = 0; i < inlayGrid.getComponentCount();i++) {
            	
            	Component heyhey = inlayGrid.getComponent(i);
            	
            	if (heyhey.getClass().getSimpleName().equals("JTextField")) {
            		jf = (JTextField) heyhey;	            		
            		jf.setText((String)values[j]);	            		
            		j++;
            	}
            }*/
			
		} 
		else 
		//Если нажали в дереве добавить
		if (firedObject == addOK) {
			//Если что-то выбрано
			if (!treeAddDataList.isSelectionEmpty()) {
				//GET PARENT NEED REQUEST ID
				//Если выбрали заявку
				DefaultMutableTreeNode dmtaa = (DefaultMutableTreeNode) globePopapedObj.getParent();
				
				if (dmtaa.getUserObject().getClass().equals(Request.class)) {
					
					
					
					// Выбираем ид заявки
					String id = ((Request) dmtaa.getUserObject()).getId();
						
					System.out.println(id);
					
					//Вставляем выбранных работников в дерево
					addNewItems(globePopapedObj, treeAddDataList.getSelectedValues());
					
					// Вставляем работников в базу
					db_insertRecords(3,id, treeAddDataList.getSelectedValues());
					
					treeAddDataList.clearSelection();
					
					treePanel.remove(addPanel);
					isListShowing = false;
					
					treePanel.validate();
					repaint();
					
				}
				
				
			}
			
			
			
			
			
		}
		else
		if (firedObject == addNO) {				
			treePanel.remove(addPanel);
			isListShowing = false;
			
			treePanel.validate();
			repaint();
		}
		else
		
		// Popup actions
		// Добавить
		if (firedObject == popupTreeAdd) {			
			globePopapedObj = 
				(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();			
			//globePopapedObj = 
				//(DefaultMutableTreeNode) globePopapedObj.getParent();			
			treeAddDataList.clearSelection();
			isListShowing = true;			
			treePanel.add(addPanel);					
			treePanel.validate();
			repaint();
		}
		else
		// Удалить
		if (firedObject == popupTreeRemove) {
			DefaultMutableTreeNode sel = 
				(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			db_RemoveRecords(3,((Request.Worker) sel.getUserObject()).getId());
			removeItem();
			}
		else
		// Завершить
		if (firedObject == popupTreeCheck) {			
			DefaultMutableTreeNode sel = 
		   (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();			
			((Request) sel.getUserObject()).setIsDone("1");			
			db_setRequestState(true, true, sel.getUserObject());	
			tree.invalidate();
			tree.repaint();
			}
		else
		// Продолжить
		if (firedObject == popupTreeunCheck) {			
			DefaultMutableTreeNode sel = 
				   (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					((Request) sel.getUserObject()).setIsDone("0");
					db_setRequestState(false, false, sel.getUserObject());					
					tree.invalidate();
					tree.repaint();
			}
	}
	
	
	public void addNewItems(DefaultMutableTreeNode where, Object[] what)
    {
        // ВАЖНО - работа с уже готовым деревом может производится только через модель дерева.
        // Только в этом случае гарантируется правильная работа и вызов событий
        // В противном случае новые узлы могут быть не прорисованы
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        Object obj = tree.getLastSelectedPathComponent();
        if(obj!=null)
        {
        	//DefaultMutableTreeNode sel = (DefaultMutableTreeNode)obj;
            // Смотрим уровень вложенности и работаем в соответствии с ним
           // if(sel.getLevel()==1) {
                for (int i=0; i< what.length; i++) {
                	
                	DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(what[i]);
                    model.insertNodeInto(tmp, where, where.getChildCount());
               
             
                tree.expandPath(new TreePath(where.getPath()));
                }
        	
        	
            
            //System.out.println(sel.getLevel());
            
        }
    }
	
	public void removeItem()
    {
        // Смотри замечание в addNewItem()
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        Object obj = tree.getLastSelectedPathComponent();
        if(obj!=null)
        {
            DefaultMutableTreeNode sel = (DefaultMutableTreeNode)obj;
            // Корень удалять нельзя
            if(!sel.isRoot())
            {
                if(sel.getChildCount()==0)
                    model.removeNodeFromParent(sel);
                else
                    // Если есть "детишки" выведем сообщение
                    JOptionPane.showMessageDialog(null, "Remove all subnodes");
            }
        }
    }
	
	
	public void valueChanged(ListSelectionEvent le) {
		//Ignore extra messages.
		if (le.getValueIsAdjusting()) return;
		ListSelectionModel lsm = (ListSelectionModel)le.getSource();
		if (lsm.isSelectionEmpty()) {
			// HELLO!
	        } else {
	        	if ((expertTable.getSelectedRow()!=-1) && (expertTable.getSelectedColumn()!=-1)) {
	        		value1 = (String) dtm.getValueAt(expertTable.getSelectedRow(), 
	        									 	 expertTable.getSelectedColumn());
	        	/*
	        	 * BUG Почему-то тут берет значение пустой ячейки как null
	        	 * а в tableChanged(TableModelEvent tme) его как пусту строку
	        	 * 
	        	 */
	        		if (value1 == null) {
	        			value1 = "";
	        			//expertStatusArea.append("\n"+"emptied");
	        		}
	        	
	        	
	        	}
	        	//System.out.println(value);
	        	
	        	//expertTable.getSelectedColumn();
	        	
	        	//dtm.getValueAt(row, column)
	           /* int selectedRow = lsm.getMinSelectionIndex();
	            
	            //GET_WHOLE_EDITS_AS_COMPONENTSofTHATPANEL
	            int j=0; //Out counter
	            JTextField jf;
	            Object[] values = getSelectedRowDatums(selectedRow);
	            for (int i = 0; i < inlayGrid.getComponentCount();i++) {
	            	
	            	Component heyhey = inlayGrid.getComponent(i);
	            	
	            	if (heyhey.getClass().getSimpleName().equals("JTextField")) {
	            		jf = (JTextField) heyhey;	            		
	            		jf.setText((String)values[j]);	            		
	            		j++;
	            	}
	            }*/
	           // System.out.print(selectedRow + " | " +getSelectedRowDatums(selectedRow));
	        }
	}
	
	public void tableChanged(TableModelEvent tme) {		
		if (tme.getType() == TableModelEvent.UPDATE) {						
			value2 = (String) dtm.getValueAt(expertTable.getSelectedRow(), 
										   	 expertTable.getSelectedColumn());
			if (!value1.equals(value2)) {							
				processUpdateTableRecord(tme.getLastRow());				
				if (processUpdateTableRecord(tme.getLastRow()).equals(
						"OK (executed)")) {					
					pop = new poper("OK",expertTableWidth.width-20, 
							(expertTable.getSelectedRow()*20) - 21);					
				} else {					
					pop = new poper("BAD",expertTableWidth.width-20, 
							(expertTable.getSelectedRow()*20) - 21);					
					dtm.setValueAt(value1, expertTable.getSelectedRow(), 
							expertTable.getSelectedColumn());
				}	
				
				rechargeTree();
				
			}
		}
	}
	
	public class TableMouseListener extends MouseAdapter {
		/*
		 * Мышиный адаптер к таблицке
		 * Он нужен для выделения строк таблицы
		 * правой кнопкой мыши и генерации popUP'ов
		 * TODO много всяких скобок + куери переделать
		 */
			public void mouseClicked( MouseEvent e )
			{
				//LEFT_MOUSE
				if ( SwingUtilities.isLeftMouseButton( e ) )
				{
					//HELLO!
				}
				//RIGHT_MOUSE
				else if ( SwingUtilities.isRightMouseButton(e)) {
					// get the coordinates of the mouse click
					Point p = e.getPoint();	 
					// get the row index that contains that coordinate
					int rowNumber = expertTable.rowAtPoint(p) ;	 
					// Get the ListSelectionModel of the JTable
					ListSelectionModel model = expertTable.getSelectionModel();	 
					// set the selected interval of rows. Using the "rowNumber"
					// variable for the beginning and end selects only that one row.
					model.setSelectionInterval( rowNumber, rowNumber );
					//DIALOG_CONFIRM
					Object[] options = {"Подтверждаю", "Я передумал!"};
					int rez=JOptionPane.showOptionDialog(expertTablePanel,
						    "Вы действительно хотите удалить запись: " + 
						    (rowNumber+1),
						    "Удаление",
						    JOptionPane.YES_NO_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[1]);
					//CLICKED_YO! DELETE RECORD!
					if (rez == JOptionPane.YES_OPTION) {
						//DELETE-QUERY
						String recordID = (String) dtm.getValueAt(rowNumber, 0);									
						String qDelRequest  = cgipath + "action=del";
							   qDelRequest += "&id=" + recordID;
						       qDelRequest += "&t="+expertCombo.getSelectedIndex();	
						q = new querys(qDelRequest);							
						if (q!= null) {
							String nextLine = "";
							nextLine = q.getNextQueryLine();
									
							nextLine = q.getNextQueryLine();// Берем 2-ю строку ответа
							if (nextLine.equals("OK")) {							
								pop = new poper("OK",expertTableWidth.width-20, p.y - 21);
								dtm.removeRow(rowNumber);
								rechargeTree();
							} else {
								pop = new poper("BAD",expertTableWidth.width-20, p.y - 21);
							}
						}
					}
					}
				}
		}
	
	public class TreeMouseListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("mc");
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("me");
		}


		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("me");
		}


		@Override
		public void mousePressed(MouseEvent mpr) {			
			
			//DefaultMutableTreeNode what = getSelectedNodePlease() ;						
			
			if ( mpr.isPopupTrigger() ) {				
				formatePopupOfNode(mpr);
			}
			
		}


		@Override
		public void mouseReleased(MouseEvent mre) {			
			
			int selRow = tree.getRowForLocation( mre.getX(), 
					mre.getY());
			if ( selRow<0 ) return;
			
			TreePath selPath = tree.getPathForLocation(mre.getX(), mre.getY());
			tree.setSelectionPath(selPath);	
		
	        
			//DefaultMutableTreeNode what = getSelectedNodePlease();				
			
			if ( mre.isPopupTrigger() ) {				
				formatePopupOfNode(mre);
				
				
				popapedObj = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();			        	        
		        
		        
		        //Формируем лист исключения	рабочих
		        
		        if (popapedObj.getUserObject().equals("Работники")) {
		        	
		        	 excludeIDs = new ArrayList<String>();
				        for (int i=0; i< popapedObj.getChildCount(); i++) {
				        	
				        	DefaultMutableTreeNode dn = (DefaultMutableTreeNode) popapedObj.getChildAt(i);
				        	
				        	excludeIDs.add(((Request.Worker) dn.getUserObject()).getId());
		        }
		        
				        System.out.println(Arrays.toString(excludeIDs.toArray()));
		        	
		        	
		        }
		        
		       
		        
		        //Return Parent of
		       // popapedObj = (DefaultMutableTreeNode) popapedObj.getParent();
		        
				//System.out.println(popapedObj.toString());
				}
				
				
				
				
				
						

				// TODO: do task with the selected path		
	}
		
		
		
		
	}
	
	public void formatePopupOfNode(	MouseEvent me ) {
		
		
		DefaultMutableTreeNode what = null;		
        Object obj = tree.getLastSelectedPathComponent();
        if(obj!=null)
        {
        	what = (DefaultMutableTreeNode)obj;		
        }        
       
        
        if ( what.getUserObject().getClass().equals(Request.Worker.class) ) {					
			setPopupWhat(false, true, false, false);					
			popupTreeActionMenu.setPreferredSize( new Dimension(110,40));
			popupTreeActionMenu.show(me.getComponent(), me.getX(), me.getY());					
		} else
		if (what.getUserObject().equals("Работники") ) {
			setPopupWhat(true, false, false, false);	
			popupTreeActionMenu.setPreferredSize( new Dimension(110,21));
					
			popupTreeActionMenu.show(me.getComponent(), me.getX(), me.getY());					
		} else
		if	( what.getUserObject().getClass().equals(Request.class) ) {	
			if (((Request)what.getUserObject()).getIsDone().equals("0")) {
				setPopupWhat(false, false, true, false);					
				popupTreeActionMenu.setPreferredSize( new Dimension(120,21));
				popupTreeActionMenu.show(me.getComponent(), me.getX(), me.getY());	
			} else				
			if (((Request)what.getUserObject()).getIsDone().equals("1")) {
				setPopupWhat(false, false, false, true);					
				popupTreeActionMenu.setPreferredSize( new Dimension(130,21));
				popupTreeActionMenu.show(me.getComponent(), me.getX(), me.getY());	
			}
			
				 
        
		}
	}
		
	
	// *** GENERIC FUNCTIONS ***
	 
	public String now(String dateFormat) {
	/* Функция формирует текущее время
	 * по шаблону
	 */
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());
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
		
	public class poper implements ActionListener {
	/*
	 * Всплывалка с информацией.
	 * Используется для отражения
	 * результатов запроса
	 * 
	 */
		Timer timer = new Timer(3000, this);
		Popup popup;		
		
			
		public poper(String message, int x, int y) {			
			createPopUpElement(message);
			PopupFactory factory = PopupFactory.getSharedInstance();		     
		    popup = factory.getPopup(main.this,
		    						 (JButton)popUps.get(popUps.size()-1), 
		    						 expertTable.getLocationOnScreen().x+x,
		    						 expertTable.getLocationOnScreen().y+y);
		    popup.show();			
		    timer.start();			
		}
			
			public void actionPerformed(ActionEvent e) {
			  // Hide popup in n seconds				
				if (e.getSource() == timer) {				
					popup.hide();
					popUps.remove(popUps.size()-1);
					//System.out.println(popUps.size());
					timer.stop();
				}
			}
			
			public void createPopUpElement(String w) {
				Dimension dSize = new Dimension(49,21);			
				
				JButton bb = new JButton(w);
				Font bf = new Font("sansserif",Font.BOLD,11);
					bb.setFont(bf);			
				
				if (w.equals("OK")) {
				bb.setBackground(new Color(154,200,153));			
				} else
					bb.setBackground(new Color(238,126,7));
				bb.setEnabled(false);
				bb.setPreferredSize(new Dimension(dSize.width+10,dSize.height));			
				
				popUps.add(bb);					
				
			}
			
		}
	
	
	// *** SPECIFIC FUNCTIONS ***
	
	//DATABASEeee
	//|
	//1 SELECT_ALL
	public Object[] db_selectAllDataFrom(int t, Boolean forceClass) {		
	/** Возвращает Обжект обжектов! 
	  * Мобжкект джобжектов и
	  * бобжект вобжектов
	  * */	
			
		ArrayList<Object> YARRRR = new ArrayList<Object>();		
		
		Object[] aRow = null;							
		String nextLine = "";
		q = new querys(cgipath + "action=query&t=" + t);
		//IFEXISTS
		if ( q != null ) {				
			//READ_QUERY	
			//SKIP_FIRST
			nextLine = q.getNextQueryLine();
			//READ_EM_ALL
			while (nextLine!=null) {					
				nextLine = q.getNextQueryLine();  
				if (nextLine!=null) {
					//F**K_PATTERN - "something;something;something;...;something"
					aRow = nextLine.split(";");
					//TABLE_ADD_F****D_ROW
					//FORCE CLASS FOR TREE
					if ( (forceClass) && (t==5/**WORKER*/) ) {
						
						Request.Worker wo = new Request.Worker(
								(String)aRow[0], 
								(String)aRow[1], 
								(String)aRow[2],
								(String)aRow[3], 
								(String)aRow[4]);
						
						YARRRR.add(wo);
					  //System.out.println(wo);						
					} 
					else					
						YARRRR.add(aRow);					
					  //System.out.print(Arrays.asList(aRow)+"\n");					
				}
			}
		}
				
		return YARRRR.toArray();
						
	}
	//|
	//2 ADD RECORDS
	public void db_insertRecords(int t, String id, Object[] what) {
		/*
		 * TODO Ркализовать для осталных таблиц
		 * или абстракция или зафорсить		 * 
		 * 
		 * !!! Предполагается что все записи успешно добавятся
		 * 
		 */
		
		//ArrayList<Object> YARRRR = new ArrayList<Object>();
			
	    for (int i = 0; i<what.length;i++) {	
	    	
	    	//IF WORKER
	    	//System.out.println(what.getClass().getCanonicalName());
	    	if (what[i].getClass().equals(Request.Worker.class)) {
	    		
	    		String insertQ = cgipath + "action=add";
	    		
	    		Request.Worker heyhey = ((Request.Worker)what[i]);
	    		
	    		insertQ += "&j=NULL&a="+id;	    			
	    		insertQ += "&"+i+"=" + heyhey.getId().trim();
	    		insertQ += "&t=" + t;
		        
		        q = new querys(insertQ);
				/*  if (q!= null) {
					  String nextLine = "";		
					  nextLine = q.getNextQueryLine();
					  // Берем 2-ю строку ответа
					  nextLine = q.getNextQueryLine();	
					  
					 System.out.println(insertQ + " " + nextLine);
	    		
				  }*/
	    	} 		
	    }		
	}
	//|
	//3 DELETE RECORD
	public void db_RemoveRecords(int t, String id) {
	   
		String insertQ = cgipath + "action=del";
		insertQ += "&0="+id;
		insertQ += "&t="+t;
        
        q = new querys(insertQ);
		  if (q!= null) {
			  String nextLine = "";		
			  nextLine = q.getNextQueryLine();
			  // Берем 2-ю строку ответа
			  nextLine = q.getNextQueryLine();	
			  
			 System.out.println(insertQ + " " + nextLine);
		
		  }
		
	}
	//|
	//4 UPDATE RECORD
	public void db_updateRecord() {
		/** TODO */
		
	}
	
	public void db_setRequestState(Boolean state, Boolean d, Object what) {
		
		String stateQ = cgipath + "action=update";
		
		Request r = (Request) what;
				
		if (d) {
			stateQ += r.prepareToState(true).replace("$ouch!", state?"1":"0").replace("$datuuum!", now("yyyy-MM-dd"));	
		} else 
			stateQ += r.prepareToState(false).replace("$ouch!", state?"1":"0");	
		
				
        
        q = new querys(stateQ);
		  if (q!= null) {
			  String nextLine = "";		
			  nextLine = q.getNextQueryLine();
			  // Берем 2-ю строку ответа
			  nextLine = q.getNextQueryLine();	
			  
			 System.out.println(stateQ + " " + nextLine);
		
		  }
		  
		 stateQ = null;
		
		
	}
	
	public String setTable(int t, Object[][] header) {
		/*
		 * Функция получает номер таблички и двумерный
		 * массив (имена столбиков/их ширина) и 
		 * выводит содержимое сущности из базы в
		 * табличку 
		 * 
		 */
			Object[] aRow = null;							
			String result = "BAD", nextLine = "";
			q = new querys(cgipath + "action=query&t=" + t);
			//IFEXISTS
			if ( q != null ) {		
				result = "OK";
				//TABLE(MODEL)_SET_HEADER
				dtm = new DefaultTableModel(header[0], 0);
				//TABLE_ROWSELECT_LISTENER (need in cell recognition as x)
				dtm.addTableModelListener(this);			
				expertTable.setModel(dtm);	
				
				
				
				//READ_QUERY	
				//SKIP_FIRST
				nextLine = q.getNextQueryLine();
				//READ_EM_ALL
				while (nextLine!=null) {					
					nextLine = q.getNextQueryLine();  
					if (nextLine!=null) {
						//F**K_PATTERN - "something;something;something;...;something"
						aRow = nextLine.split(";");
						//TABLE_ADD_F****D_ROW
						dtm.addRow(aRow);					
						//System.out.print(Arrays.asList(aRow)+"\n");					
					}
				}
				//TABLE_SET_COLUMN_SIZES	
				for (int i=0; i<expertTable.getColumnCount();i++){
					TableColumn tcol = expertTable.getColumnModel().getColumn(i);											
				    tcol.setCellRenderer(new CustomTableCellRenderer());
				    Integer siz = new Integer(header[1][i].toString());
					tcol.setPreferredWidth(siz.intValue());		
				}
			//OH_NO!SOme thing went!
			} else result = "BAD";		
			//aRow = null;
			return result;		
		}
	
	public Object[] getSelectedRowDatums(int cRow) {
		/* → valueChanged
		 * Данная функция получает на вход номер (строки таблички),
		 * а на выходе возвращает массив значений всех элементов (строки)
		 * (возможно, каким-то методом можно возвращать всю строку сразу,
		 * но в данный момент он мне не известен (:
		 * 
		 */
			ArrayList<String> result = new ArrayList<String>();	
			TableModel tm = expertTable.getModel();
			int end = tm.getColumnCount();
			for (int i=0; i < end; i++) {			
				result.add((String)tm.getValueAt(cRow,i));			
			}
			return result.toArray();
		}
	
	public String processInsertTableRecord() {
	/*
	 * Die Funktion ist ähnlich zu aktualisieren und 
	 * erhält eine Liste der Elemente hinzufügen (;
	 * 
	 */		
		String insertQ = cgipath + "action=add";
		String result = "";
			
	    for (int i = 0; i<inlayGrid.getComponentCount();i++) {	        	
	    	Component heyhey = inlayGrid.getComponent(i);
	    	if (heyhey.getClass().getSimpleName().equals("JTextField")) {
	    		insertQ += "&"+i+"=" + ((JTextField) heyhey).getText().trim();
				}	
	    }
	    insertQ += "&t=" + expertCombo.getSelectedIndex();
	        
	        q = new querys(insertQ);
			  if (q!= null) {
				  String nextLine = "";		
				  nextLine = q.getNextQueryLine();
				  // Берем 2-ю строку ответа
				  nextLine = q.getNextQueryLine();			
				  result =  nextLine;			
			  }		
			
			  return result; 
		}
	
	public String processUpdateTableRecord(int cRow) {
		/*
		 * Функция получает номер строки для обновления
		 * аналогичной записи в базе
		 * 
		 */
			String updateQ = cgipath + "action=update";
			String result = "";
			//1.PREPARE_UPDATE_QUERY
			  //1.1_WALK_THE_CHANGED_TABLE_ROW
			  Object[] rowDatum = getSelectedRowDatums(cRow);
			  for (int i=0; i < rowDatum.length; i++) {
				  //LET_WITHOUT_UTF
				  /*
				   * TODO trim it!
				   */
				  updateQ += "&"+i+"=" + rowDatum[i];
				}		  
			  updateQ += "&t=" + expertCombo.getSelectedIndex();
			  updateQ += "&where=" + rowDatum[0];
			  /**/
			  q = new querys(updateQ);
			  if (q!= null) {
				  String nextLine = "";		
				  nextLine = q.getNextQueryLine();
				  // Берем 2-ю строку ответа
				  nextLine = q.getNextQueryLine();			
				  result =  nextLine;			
			  }		
			  return result; 
		}
	
	public void generateFields(Object[] names) {
		/*
		 * Данный функций генерирует форму полей 
		 * с лейблами для табличек. Получает массив, предположительно,
		 * с названиями полей таблицки	
		 * 	
		 */ //REMOVE_ALL_COMPONENTS
			expertFieldPanel.removeAll();
			expertFieldPanel.repaint();				
			//CENTRED_FORMATTING_INLAY_ELEMENTS
			inlayGrid = new JPanel();	
			expertFieldPanel.add(inlayGrid);		
			inlayGrid.setLayout(new GridLayout(names.length*2+1,1));		
			//GENERATE_ALL_LABLESandFIELDS
			for (int i=0; i<names.length; i++) {
				JLabel jl = new JLabel((String) names[i]);
				//jl.setName(names.);
				inlayGrid.add(jl);							
				JTextField jf = new JTextField();
				inlayGrid.add(jf);
				jf.setPreferredSize(new Dimension(expertFieldWidth.width-14,24));			
			}
			//ACTION_BUTTONS
			  //ADD
			  expertAdd = new JButton("[+] Добавить"); 
			  expertAdd.addActionListener(this);
			  expertFieldPanel.add(expertAdd);	
			  expertAdd.setPreferredSize(new Dimension(110,25));
			  //CLEAR
			  expertFieldClear = new JButton("X");
			  expertFieldPanel.add(expertFieldClear);
			  expertFieldClear.addActionListener(this);
			  expertFieldClear.setPreferredSize(new Dimension(50,25));		
			//WTF?WITHouT_IT_and_SCROLLPANE_IF_USE_CONTENT_HIDING
			validate();
		}
	
	//CLEAR_FIELDS
	public void clearFieldData() {		
		 //GET_WHOLE_EDITS_AS_COMPONENTSofTHATPANEL
		JTextField jf;
		for (int i = 0; i < inlayGrid.getComponentCount();i++) {
			Component heyhey = inlayGrid.getComponent(i);
			if (heyhey.getClass().getSimpleName().equals("JTextField")) {
				jf = (JTextField) heyhey;
				//SET_NULL
				jf.setText("");
			}
		}
	}
	
	public Boolean checkFields() {		
		Boolean rr = true;
		//GET_WHOLE_EDITS_AS_COMPONENTSofTHATPANEL        
        JTextField jf;        
        for (int i = 0; i < inlayGrid.getComponentCount();i++) {        	
        	Component heyhey = inlayGrid.getComponent(i);        	
        	if (heyhey.getClass().getSimpleName().equals("JTextField")) {
        		jf = (JTextField) heyhey;        		
        		rr = rr && !jf.getText().equals("");        		      		
        	}
        }
		return rr;	
	}
	
	
	// *** RENDERERS
	
	class myTreeRenderer extends DefaultTreeCellRenderer {		
		//icons		
		String r1 = "rDone.png", 
			  r2 = "runDone.png",
			   c  = "client.png",
			   j  = "jobs.png",
			   w  = "workers.gif";
		
	    public myTreeRenderer() {
	       //nope
	    }

	    public Component getTreeCellRendererComponent(
	                        JTree tree,
	                        Object value,
	                        boolean sel,
	                        boolean expanded,
	                        boolean leaf,
	                        int row,
	                        boolean hasFocus) {

	    	super.getTreeCellRendererComponent(
	                        tree, value, sel,
	                        expanded, leaf, row,
	                        hasFocus);
	        
	        DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)value;	        
	    	
	    	Object obj = node.getUserObject();	
	    	
	    	/*if (sel) {	    	
	    	setBackgroundNonSelectionColor(null);
	    	setBackgroundSelectionColor(new Color(232,242,252));
	    	}*/	    	
	    	if ( (obj.getClass().equals(Request.class) 
	    			//&& (((Request)obj).getIsDone().equals("0")) 
	    	)){	    		
	    		setFont(new Font("sansserif",Font.BOLD,12));
	    	} else 
	    		setFont(new Font("sansserif",Font.PLAIN,12));
	         	setIcon(setMyIcon(obj));	        

	        return this;
	    }
	    	    
	    protected ImageIcon setMyIcon(Object obj) {
	    	
	    	if (obj.getClass().equals(Request.class)) {
	    		if (((Request)obj).getIsDone().equals("1")) {	    			
		        	ImageIcon icon = createImageIcon(pathToIcons+r1,"");
		        	return icon;
	    		} 
	    		else 
	    		if (((Request)obj).getIsDone().equals("0")) {	    			
			        ImageIcon icon = createImageIcon(pathToIcons+r2,""); 
			        return icon;
		    	}
	    	}
	    	else 
	    	if (obj.getClass().equals(Request.Client.class)) {	    		
	    		ImageIcon icon = createImageIcon(pathToIcons+c,""); 
	    		return icon;
	    	}
	    	else 
	    	if (obj.equals("Работы")) {
	    		ImageIcon icon = createImageIcon(pathToIcons+j,"");
	    		return icon;
	    	}
	    	else 
	    	if (obj.equals("Работники")) {
	    		ImageIcon icon = createImageIcon(pathToIcons+w,"");	
	    		return icon;
	    	}	    	
			return null;	    	
	    }	    	    
	}
	
	public class IconListRenderer extends DefaultListCellRenderer {
	
		
		@Override	
		public Component getListCellRendererComponent(
				JList list, Object value, int index,	
				boolean isSelected, boolean cellHasFocus) {
	
			// Get the renderer component from parent class
	
			JLabel label = null;
			
			
			if (isListShowing) {				
					String rw = ((Request.Worker) value).getId();					
					if	( excludeIDs.contains(rw) ) {
						label =	
							(JLabel) super.getListCellRendererComponent(list,	
									value, index, false, false);
					
						label.setEnabled(false);
						label.setFocusable(false);
						label.setVisible(false);
					} else 
					{
						label =	
							(JLabel) super.getListCellRendererComponent(list,	
									value, index, isSelected, cellHasFocus);
						
					}
			} else {
				
				label =	
					(JLabel) super.getListCellRendererComponent(list,	
							value, index, isSelected, cellHasFocus);
			}
			
			// Set icon to display for value
			ImageIcon icon = createImageIcon(pathToIcons+"workers.gif","");
			label.setIcon(icon);
			
	
			return label;
	
		}
		
	}


		
	
	}
