package client;

/* * - BEWARE! FILE ENCODED BY UTF-8! *
 *   - OH NO! WHY, CAP?
 *   - JUST because F**K YOU!
 *   */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import GUI.database_unavailable;

import components.querys;

public class main extends JApplet { 
	
	JPanel main_panel = new JPanel();
	JobPanel jobListPanel;
	
	String cgipath = "http://localhost/cgi-bin/cgi_job_list_project.exe?";
	final public static String expChdd = "action=query&t=0";
	database_unavailable dbu;
	
	ActionListener aa = new ActionListener() {		
		public void actionPerformed(ActionEvent e) {						
			try {
				if (components.database.isDatabaseOK(cgipath.concat(expChdd))) {				
					getContentPane().remove(dbu);					
					jobListPanel = new JobPanel();                 	 
				 add(jobListPanel); 
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
						if (components.database.isDatabaseOK(cgipath.concat(expChdd))) {
							 jobListPanel = new JobPanel();                 	 
							 add(jobListPanel);
						} else {dbu = new database_unavailable(aa);//, main.this);
 						getContentPane().add(dbu);	}
						
					} catch (IOException e) {
						dbu = new database_unavailable(aa);//, main.this);
 						getContentPane().add(dbu);	
						e.printStackTrace();
					} 
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
    	}
    }
	
	public class JobPanel extends JPanel implements ItemListener, ComponentListener
	{ 
		
	// Это класс панели с флажками и обработкой нажатия этих флажков  	
		querys q = null;										// Ответ сервера после запроса к cgi 
    	JLabel titleLabel;    
    	JPanel mainJobPanel; 
		JPanel jobPanel, titlePanel, buttonPanel;    
    	JButton jbSend = new JButton();         
    	BorderLayout bl;    
    	ArrayList<Job> Jobs = new ArrayList<Job>();
    	ArrayList<JCheckBox> JobChecks = new ArrayList<JCheckBox>();    
    	ArrayList<String> job_selected = new ArrayList<String>();
    	String queryGetJobs = cgipath + "action=query&t=4";     
    
    	JPanel formPanel, userPanel, loginPanel, registerPanel,regclientPanel,clientPanel;
		JTextField loginField;
		JLabel loginLable, userLabel, resultLabel;
		JButton loginButton, unLoginButton, registerButton;		
		String queryGetClientId = cgipath + "action=login&id=";
		private String name;
		boolean isInitJobList, isInitRegPanel, isRegShowing = false;		
		
		JLabel      lcID, lcSurname, lcName, lcPatronymic, lcAdress, lcPhone;
		JTextField  tcID, tcSurname, tcName, tcPatronymic, tcAdress, tcPhone;
		JButton bYES, bOHNO;
		JPanel pReg,p2Reg;
		JLabel rez;
		
		    
    	public JobPanel() {										// Конструктор класса
    	
    		super(new BorderLayout());							// Диспетчер размещения родителя
    															// Размещение здесь (в этом компоненте) последовательное
    		initClientPanel();  	    		
    		initLoginPanel();       		
    		initRegClient();    	
    		initJobListPanel();
		
    }
    	
    	protected void initLoginPanel() {
    		/*	Смысл данной панели: вначале юзеру показывается форма ввода логина и пароля.
    		 *  Если он ввел правильные данные, эта панель пропадает и появляется
    		 *  другая панель с именем юзера, которое и будет фигурировать в дальнейшем	 *  
    		 *  
    		 *  Возвращает: номер юзера в main
    		 *  
    		 *  */
    		
    		loginPanel = new JPanel();    
    		
    		loginLable = new JLabel("Клиент:");
    		loginPanel.add(loginLable);		 
			 
			loginField = new JTextField();
			loginField.setMaximumSize(new Dimension(40, 21));
			loginField.setPreferredSize(new Dimension(40, 21));
			loginField.setVisible(true);
			loginPanel.add(loginField);
			 
			loginButton = new JButton();
			loginButton.setMaximumSize(new Dimension(100, 20));		// Устанавливаем кнопку
			loginButton.setPreferredSize(new Dimension(70, 20));
			loginButton.setText("Войти");
			loginButton.setVisible(true);		
			
			loginButton.addActionListener(new java.awt.event.ActionListener() {
				     public void actionPerformed(ActionEvent e) {	// Обработчик нажатия кнопки
				       loginButtonClick(e);
				     }
				});		
			loginPanel.add(loginButton);
			
			registerButton = new JButton();
			registerButton.setMaximumSize(new Dimension(150, 20));		// Устанавливаем кнопку
			registerButton.setPreferredSize(new Dimension(110, 20));
			registerButton.setText("Регистрация");    				
			
			registerButton.addActionListener(new java.awt.event.ActionListener() {
				     public void actionPerformed(ActionEvent e) {	// Обработчик нажатия кнопки
				    	 registerButtonClick(e);
				     }
				});					
			loginPanel.add(registerButton);
			
			resultLabel = new JLabel("");
			loginPanel.add(resultLabel);						
				
    		add(loginPanel, BorderLayout.NORTH);    		
    	}
    
    	protected void initClientPanel() {
    		clientPanel = new JPanel();        			
        			
        	userLabel = new JLabel();
        	clientPanel.add(userLabel);	
        			
        	unLoginButton = new JButton();
        	unLoginButton.setMaximumSize(new Dimension(100, 20));		// Устанавливаем кнопку
        	unLoginButton.setPreferredSize(new Dimension(75, 20));
        	unLoginButton.setText("Выйти");
        	unLoginButton.setVisible(true);	        			
        	unLoginButton.addActionListener(new java.awt.event.ActionListener() {
        				     public void actionPerformed(ActionEvent e) {	// Обработчик нажатия кнопки
        				       unLoginButtonClick(e);
        				     }
        				});		
        	clientPanel.add(unLoginButton); 
        	clientPanel.setBorder(new EtchedBorder());
        	
        			
    	}
    	
    	protected void clearFields() {
    		
    		
    		tcSurname.setText("");
    		tcName.setText("");
    		tcPatronymic.setText("");
    		tcAdress.setText("");
    		tcPhone.setText("");
    		
    	}
    	
    	
    	protected String regClientQuery() {
		// Непосредственно звапрос на добавление клиента и добавление	
    		String gg = null;
    		String regQuery = "";    				
    		
    		
    		if (!(tcSurname.getText().trim().equals("") || tcName.getText().trim().equals("") ||
    				tcPatronymic.getText().trim().equals("") || tcAdress.getText().trim().equals("") || 
    				tcPhone.getText().trim().equals(""))) {
    			
			regQuery = cgipath + "action=add"; 
			regQuery += "&1=NULL";
			regQuery += "&2=" + tcSurname.getText().trim();
    		regQuery += "&3=" + tcName.getText().trim();
    		regQuery += "&4=" + tcPatronymic.getText().trim();
    		regQuery += "&5=" + tcAdress.getText().trim();
    		regQuery += "&6=" + tcPhone.getText().trim();    			
    		regQuery += "&t=0";							// таблица
    			
        	System.out.print(regQuery);					
    					
    		q = new querys(regQuery);		// Запрос на поиск клиента по номеру
    					if (q!= null) {			
    						
    						String nextLine = "";								// -
    						nextLine = q.getNextQueryLine();
    						nextLine = q.getNextQueryLine();// Берем 2-ю строку ответа
    						
    						if (nextLine.equals("OK")) {						// Если строка ОК								
    							 gg = "OK " + q.getNextQueryLine();
    							
    											
    					} else if (nextLine.equals("BAD")) {
    						 gg = "BAD";
    						
    						} 
        		
    					}    	regQuery = null;	
    		} else gg = "Ошибка!";
    		
    			
						
						return gg;
						
							
    		
    		
    		
    	}
    	    	  	
    	protected void initRegClient() {    		
    		
    		registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    		//registerPanel.setPreferredSize(new Dimension(250, 350));
    		
			
    		pReg = new JPanel();
    		Border aquaBorder = UIManager.getBorder( "TitledBorder.aquaVariant" );
    		pReg.setBorder( new TitledBorder( aquaBorder, "Клиент" ) );
    		pReg.setPreferredSize(new Dimension(250, 330));
    		
    		
    		p2Reg = new JPanel();		
    		p2Reg.setLayout(new GridLayout(10,1));
    		p2Reg.setPreferredSize(new Dimension(200, 250));
    		
    		// ID field		
    	/*	lcID = new JLabel("ID"); p2Reg.add(lcID); 
    		tcID = new JTextField(); p2Reg.add(tcID);
    		*/
    		
    		//
    		lcSurname = new JLabel("Фамилия"); p2Reg.add(lcSurname);		
    		tcSurname = new JTextField(); p2Reg.add(tcSurname);
    		
    		//
    		lcName = new JLabel("Имя"); p2Reg.add(lcName);		
    		tcName = new JTextField(); p2Reg.add(tcName);
    		
    		//
    		lcPatronymic = new JLabel("Отчество"); p2Reg.add(lcPatronymic);		
    		tcPatronymic = new JTextField(); p2Reg.add(tcPatronymic);
    		
    		//
    		lcAdress = new JLabel("Адрес"); p2Reg.add(lcAdress);		
    		tcAdress = new JTextField(); p2Reg.add(tcAdress);
    		
    		//
    		lcPhone = new JLabel("Телефон"); p2Reg.add(lcPhone);		
    		tcPhone = new JTextField(); p2Reg.add(tcPhone);
    		
    		bYES = new JButton("Регистрация");
    		bYES.addActionListener(new java.awt.event.ActionListener() {
    		     public void actionPerformed(ActionEvent e) {	// Обработчик нажатия кнопки
    		    	 bYESButtonClick(e);
    		     }
    		});				
    		
    	/*	bOHNO = new JButton("Отмена");		
    		bOHNO.addActionListener(new java.awt.event.ActionListener() {
    		     public void actionPerformed(ActionEvent e) {	// Обработчик нажатия кнопки
    		    	 bOHNOButtonClick(e);
    		     }
    		});		*/
    		
    		JPanel bp = new JPanel(new FlowLayout());
    		
    		bp.add(bYES); 
    		
    		//bp.add(bOHNO);
    		
    		pReg.add(p2Reg);
    		pReg.add(bp);	
    				
    		rez = new JLabel("");		
    		pReg.add(rez);
    		
    		registerPanel.add(pReg);    		
    	}
    	
    	protected void initJobListPanel() {
    		mainJobPanel = new JPanel();
        	mainJobPanel.addComponentListener(this);
        	mainJobPanel.setLayout(new BorderLayout());
        	
        	titlePanel = new JPanel();
        	FlowLayout fl = new FlowLayout();    	
        	// Верхняя панель заголовка        
            jobPanel = new JPanel(fl);			// Панель для списка работ с флажками
            jobPanel.setPreferredSize(new Dimension(500,800));
            buttonPanel = new JPanel();							// Нижняя панель кнопок
            
            String tt = "Выберите, пожалуйста, нужные работы.";	// Устанавливаем заголовок
            titleLabel = new JLabel(tt);						// Создаем лейбл
            titleLabel.setFont(									// -
            		titleLabel.getFont().deriveFont(			// Устанавливаем "Итальянский" шрифт
            				Font.ITALIC));						// -        										
            titlePanel.add(titleLabel);							// Добавляем лейбл на панель
            													
            jbSend.setMaximumSize(new Dimension(399, 27));		// Устанавливаем кнопку
    		jbSend.setPreferredSize(new Dimension(100, 27));	// Размеры
    		jbSend.setText("Отправить");						// Надпись
    		jbSend.setVisible(true);							// Показывать +		 
    		jbSend.addActionListener(							// -
    			new java.awt.event.ActionListener() {			// -
    				public void actionPerformed(				// -
    						ActionEvent e) {					// - 
    		       actionJbadd(e);								// - Обработчик нажатия кнопки
    		     }												// -
    		});													// -
    		buttonPanel.add(jbSend);							// Добавить кнопку на панель        
            
            q = new querys(queryGetJobs);						// Выбираем все записи о работах из базы
       
    		if (q!= null) {										// Если запрос успешный
    			String nextLine, id, description, cost = "";
    			int i, j = 1;    	
    			  
    			nextLine = q.getNextQueryLine();				// 
        	
    			nextLine = q.getNextQueryLine(); 
        	  
        	    while (nextLine != null)
        	    	{    	   
        	    	
        	    	// Идентификатор
        	    	i = nextLine.indexOf(";");
        	    	id = nextLine.substring(0,i);  
        	    	
        	    	// Описание работы
        	    	description = nextLine.substring(i + 1, nextLine.indexOf(";", i + 1));    	    	
        	    	JLabel jlb = new JLabel(description); 
        	    	jlb.setPreferredSize(new Dimension(400, 21));
        	    	jobPanel.add(jlb);    	    	
        	    	
        	    	// Стоимость работы
        	    	i = nextLine.indexOf(";", i + 1);    	       
        	    	cost = nextLine.substring(i + 1, nextLine.indexOf(";", i + 1));
        	    	JLabel jlb2 = new JLabel(cost); 
        	    	jlb2.setPreferredSize(new Dimension(50, 21));
        	    	jobPanel.add(jlb2);
        	    	
        	    	JCheckBox jj = new JCheckBox();
        	    	jj.setName("check" + j);
        	    	jj.addItemListener(this); 
        	    	jj.setPreferredSize(new Dimension(21, 21));
        	    	JobChecks.add(jj);
        	    	
        	    	jobPanel.add(jj);
        	    	            	     	
                	j++;
                	
        	        nextLine = q.getNextQueryLine(); 
        	    	
        	    	Job jobRecord = new Job(id, cost);			// Создаем запись о работнике
        	    	Jobs.add(jobRecord);
        	    	
        	     }
    		}    		
    		mainJobPanel.add(titlePanel, BorderLayout.NORTH);	
    		mainJobPanel.add(jobPanel, BorderLayout.WEST);
    		mainJobPanel.add(buttonPanel, BorderLayout.EAST);    	
      
            setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    	}
    	
    	
    	protected void registerButtonClick(ActionEvent e) {
    		
    		
    		if (!isRegShowing) {
    			isRegShowing = true;
    			registerButton.setText("Скрыть");
    			
    			remove(jobListPanel);
    			add(registerPanel, BorderLayout.CENTER );      			
        		revalidate();
        		repaint();		
    		} else {
    			isRegShowing = false;
    			registerButton.setText("Регистрация");
    			rez.setText("");
    			remove(registerPanel);
    			revalidate();
        		repaint();	
        		clearFields();
    			
    		}
    	
    		
    		
		}    	
    	
    	protected void bOHNOButtonClick(ActionEvent e) {
			
    	}    	
    	
    	protected void bYESButtonClick(ActionEvent e) {    	
    		/*TODO
    		 * Может преместить в опап или заголовок сообщение об успешности
    		 * 
    		 */
    		rez.setText(regClientQuery());
    		
    		
    		//rez.setText("Успех! Ваш ID: ");    	
    		
    	}
    
    	protected void loginButtonClick(ActionEvent e) {			// Щелчок по кнопки Логин    		
    		q = new querys(queryGetClientId+loginField.getText()); // Запрос на поиск клиента по номеру    		
    		if (q!= null) {											// Если есть результат запроса (ответ)
    			String nextLine = "";								// -
    			nextLine = q.getNextQueryLine();					// Берем первую строку ответа
    			if (nextLine.equals("OK")) {						// Если строка ОК
    				String user = q.getNextQueryLine();				
    				// сбросить все
    				resultLabel.setText("");
    				loginField.setText("");
    				
    				
    				remove(loginPanel);
    				remove(registerPanel);
    				add(clientPanel, BorderLayout.NORTH);
    				add(mainJobPanel, BorderLayout.CENTER);
    				revalidate();
    	    		repaint();
    				
    				setName(q.getNextQueryLine());				
    				userLabel.setText(user + getName());    				
    					
    			} else
    				if (nextLine.equals("BAD")) {
    					resultLabel.setText("Нет клиента");
    				}
    		} 
    	}    	

    	protected void unLoginButtonClick(ActionEvent e) {			// Разлогиниться
    		userLabel.setText("");									// сбросить все
    		resultLabel.setText("");
    		loginField.setText("");	
    		setName("");
    		
    		add(loginPanel, BorderLayout.NORTH);    		
    		remove(clientPanel);
    		remove(mainJobPanel);    		
    		revalidate();
    		repaint();
    	}

    	protected void actionJbadd(ActionEvent e) {				// При шелчке на кноаку "Отправить"		
    		if (job_selected.size()!=0) {						// Если выбрали чего (чекбоксы чеканы)
    			int next, end, elem = 0;
    			Integer cost = 0;
    			end = job_selected.size()-1;
    			for (next=0; next<=end; next++) {					// Считаем общую цену выбранных работ
    				elem = Integer.parseInt(job_selected.get(next)) - 1;
    				cost += Jobs.get(elem).getCost();			
    			}
			Object[] options = {"Подтверждаю", "Отказываюсь"};
			int rez = JOptionPane.showOptionDialog(this,
				    "Сформировать заказ:\n" + 
				    job_selected.size() + " работ(-ы) " +
				    "на общую сумму: " + cost + " руб.?",
				    "Подтверждение",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
				
			if (rez == JOptionPane.YES_OPTION) {			// Выбрано "Подтверждаю"	
															// Подготовить запрос на добавление в базу
				
				String btnLabel = "";
				
				String cClient = getName();
				String cDate = this.now("yyyy-MM-dd");				
				String  qSetRequest  = cgipath + "action=add";
						qSetRequest += "&1=NULL";			// Автоинкремент
						qSetRequest += "&2=" + cDate; 		// текущая дата: YYYY-MM-DD
						qSetRequest += "&3=NULL";			// дата завершения
						qSetRequest += "&4=0";				// флаг выполнения (0/1)
						qSetRequest += "&5=" + cClient;		// клиент
						qSetRequest += "&t=1";				// таблица
						
						//System.out.print(qSetRequest);					
						
				q = new querys(qSetRequest);				// Запрос на поиск клиента по номеру
						if (q!= null) {											// Если есть результат запроса (ответ)
							String nextLine = "";								// -
							nextLine = q.getNextQueryLine();
							nextLine = q.getNextQueryLine();// Берем 2-ю строку ответа
							if (nextLine.equals("OK")) {						// Если строка ОК								
							
							String requestID = q.getNextQueryLine();		// Выбираем реквест новый
								
							String qSetJobs; 							
							
							for (int i=0; i <job_selected.size(); i++) {
								
								int j = Integer.parseInt(job_selected.get(i))-1;
								qSetJobs = cgipath + "action=add";							// Пишем работы
								qSetJobs += "&1=NULL";			// Автоинкремент
								qSetJobs += "&2=" + requestID; 		
								qSetJobs += "&3="+Jobs.get(j).getId();			
								qSetJobs += "&t=2";								
								
								q = new querys(qSetJobs);								// Запрос на поиск клиента по номеру
										if (q!= null) {											// Если есть результат запроса (ответ)
											String nextLine1 = "";								// -
											nextLine1 = q.getNextQueryLine();
											System.out.print(nextLine1 + " ");
											nextLine1 = q.getNextQueryLine();// Берем 2-ю строку ответа
											System.out.print(nextLine1 + " ");
											nextLine1 = q.getNextQueryLine();// Берем 2-ю строку ответа
											System.out.print(nextLine1 + "\n");
										}
								}	
								btnLabel = "Запрос №" + requestID + 
								" от " + cDate + " для клиента: " + cClient + " сформирован!" ;								
							} else
								if (nextLine.equals("BAD")) {
									btnLabel="Ошибка! См. Добавление запроса.";
								}
						}
						
						resetAll();
							
						/*TODO
							 * Зафигачит в отдельную процедуру
							 * 
							 */
						
				JButton button = new JButton(btnLabel);
				
					 button.setPreferredSize(new Dimension(500,21));
					 button.setContentAreaFilled(false);
					 button.setEnabled(false);
				      PopupFactory factory = PopupFactory.getSharedInstance();
				      int x = this.getLocationOnScreen().x + 30;
				      int y = this.getLocationOnScreen().y + 400;
				      final Popup popup = factory.getPopup(main.this, button, x, y);
				      popup.show();				      
				      ActionListener hider = new ActionListener() {
				        public void actionPerformed(ActionEvent e) {
				          popup.hide();
				        }
				      };
				      // Hide popup in 8 seconds
				      Timer timer = new Timer(8000, hider);
				      timer.start();
					
					
				} else
			if (rez == JOptionPane.NO_OPTION) {		
			//	System.out.print("N");	
			}
		} else {
			JOptionPane.showMessageDialog(this, "Не выбран ни один элемент.", "Внимание!", 2);
		}
	}
	
    	public String now(String dateFormat) {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		    return sdf.format(cal.getTime());
		  }
	
	
    	public void resetAll() {									// Сбросить все данные		
    		int end = 0;
    		while (!job_selected.isEmpty()) {						// Сбросить выбранные флажки
    			JobChecks.get(end).setSelected(false);			
    			end++; 
    		}
    		job_selected.clear();									// Сбросить выбранные работы
		/*
		 * TODO Сбросить перерменную, текущий пользователь логин
		 */
    	}
	
	
    
    public String getCheckedElement(Object source) {		
    	String propertyes, elem = "";
		int from, to = 0;		
	      
	    propertyes = source.toString();
	    
	    from = propertyes.indexOf("check") + "check".length();
    	to   = propertyes.indexOf(",");
	    elem = propertyes.substring(from,to);        	
    	
    	return elem;  	
    }


	
	public void itemStateChanged(ItemEvent e) {					// Изменилось состояние объекта		
		String elem;	    
	    elem = getCheckedElement(e.getItemSelectable());	       
	    if (e.getStateChange() == ItemEvent.DESELECTED) {		// Если сняли флажок
	    	job_selected.remove(elem); 	    	  
	   	    //System.out.print(elem + "[" + job_selected + "]");
	    } else
	    	if (e.getStateChange() == ItemEvent.SELECTED) {		// Если установили флажок
	    		job_selected.add(elem);
	    		//System.out.print(elem + "[" + job_selected + "]");  
	    	}
	}
	
	public void setName(String name) {							// Установить номер залогиненого пользователя
		this.name = name;
	}
	public String getName() {									// Считать номер залогиненого пользователя
		return name;
	}
	
	public void componentHidden(ComponentEvent arg0) {			// Если спрятан компонент 
		if (arg0.getComponent().getClass().getSimpleName().equals("JPanel")) {
			resetAll();											// Здесь по хайду труться все значения массивы и флажкииии			
		}		
	}
	public void componentMoved(ComponentEvent arg0){}
	public void componentResized(ComponentEvent arg0){}
	public void componentShown(ComponentEvent arg0) {}	
	
	private class Job {
    		
    	private Job(String i, String c) {
    		setId(i);    		
    		setCost(c);    		
    		
    	}
    	
    	public void setId(String id) {
			this.id = id;
		}
    	
    	public String getId() {
			return id;
		}
		
		public void setCost(String cost) {
			this.cost = cost;
		}

		public Integer getCost() {
			return Integer.parseInt(cost);
		}

		private String id, cost;		
    	
    }
	}	
	
}