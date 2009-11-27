package admin;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import components.querys;

public class Request {
		
		private Client client;
		private ArrayList<Job> JobList = new ArrayList<Job>();	
		private ArrayList<Worker> WorkerList = new ArrayList<Worker>();
		
		private String id, DateIn, DateOut, clientid;
		private String isDone;
		
		querys Q;
		String q_select_request_client = main.cgipath + 
									   "action=query&" + 
									   "w=@where&t=0";
		String q_select_joined_jobs = main.cgipath+ 
		   							  "action=sRj&" + 
		   							  "r=@request";
		String q_select_joined_workers = main.cgipath+ 
			                             "action=sRw&" + 
			                             "r=@request";
		
		public Request(Object[] o) {
			
			setId((String)o[0]);
			setDateIn((String)o[1]);
			setDateOut((String)o[2]);
			setIsDone((String)o[3]);
			setclientid((String)o[4]);			
			
			
		   buildRequestTreeById(this.getId());
		}
		
		
		
		
		
		
	    
		
		
		
		
		
		public static boolean GrowForestIn(DefaultMutableTreeNode rootTreeModel) {			
			Object[] aRow = null;			
			querys q = new querys(main.cgipath + "action=query&t=1");	
			Boolean isGrown = false;
			
			//IFEXISTS		
			if ( q != null ) {			
				rootTreeModel.removeAllChildren();
				String nextLine = q.getNextQueryLine();
				//READ_EM_ALL
				while (nextLine != null) {
					nextLine = q.getNextQueryLine();  
					if (nextLine!=null) {
						
						aRow = nextLine.split(";");
						//TABLE_ADD_F****D_ROW					
						
						Request r = new Request(aRow);
						
					
						
						DefaultMutableTreeNode parent = new DefaultMutableTreeNode(r);					
						rootTreeModel.add(parent);
						
						DefaultMutableTreeNode cc = new DefaultMutableTreeNode(r.getClient());
						parent.add(cc);						
						
						DefaultMutableTreeNode jobsTree = new DefaultMutableTreeNode("Работы");
						for (int i=0; i < r.getJobList().size(); i++) {
							
							DefaultMutableTreeNode child = new DefaultMutableTreeNode(r.getJobList().get(i));
							jobsTree.add(child);
						}
						
						DefaultMutableTreeNode workersTree = new DefaultMutableTreeNode("Работники");
						for (int i=0; i < r.getWorkerList().size(); i++) {
							
							DefaultMutableTreeNode child = new DefaultMutableTreeNode(r.getWorkerList().get(i));
							workersTree.add(child);
						}						
						
						parent.add(jobsTree);
						parent.add(workersTree);
						
					}
				}
				
				isGrown = true;
			}
			
			return isGrown;		
		}
		

		
				
		public String toString() {
			/*
			 * Выводится в дереве при рендере
			 * 
			 */
			
			String what = "Заявка (" + getId() + 
			") от " + getDateIn();
			
            return what;
        }
		
		
		public String prepareToState(Boolean da) {
			String Hello = null;
			
			if (da) {
			
			Hello = 			
			"&state=".concat("$ouch!") +			
			"&t=1"+
			"&date=".concat("$datuuum!") + 
			"&0=".concat(getId());
			
			} else {
				
				Hello = 			
					"&state=".concat("$ouch!") +			
					"&t=1"+					
					"&0=".concat(getId());
			}
			
			
			
			return Hello;
		}
		
		
		public Boolean buildRequestTreeById(String id) {
		
		/*
		 * Метод предназначен для построения 
		 * дерева заявки по номеру заявки:
		 * 
		 * Заявка
		 * 	|
		 * 	Клиент
		 * 	|
		 * 	Работы
		 * 	  |
		 *    Работа 1
		 *    |
		 *    ...
		 *  Работники
		 *    |
		 *    Работик 1
		 *    ...	
		 * 
		 */
			
			//Boolean whatResultMan = false;			
			
		 /*
		  * 1 информацию первого уровня имеется
		  * Взять связанного клиента
		  * У заявки один клиент всегда
		  */
						
			//QUERY			
			q_select_request_client = 
				q_select_request_client.replaceFirst("@where", 
						"Client_num="+getclientid());
			Q = new querys(q_select_request_client);
			//IFEXISTS
			if ( Q != null ) {
				//READ_QUERY	
				//SKIP_FIRST
				String nextLine = Q.getNextQueryLine();
				nextLine = Q.getNextQueryLine();
				//IsResult
				if (nextLine!=null) {
					//F**K_PATTERN - "something;something;something;...;something"
					Object[] aRow = nextLine.split(";");
					//FILL
					setClient(new Client((String)aRow[1], 
										 (String)aRow[2], 
										 (String)aRow[3],
										 (String)aRow[0])
					);
				}
			}
			
		 /*
		  * 2 Все связанные работы
		  * Их может быть много!
		  * 
		  */
			
			//QUERY			
			q_select_joined_jobs = 
				q_select_joined_jobs.replaceFirst("@request", 
						getId());
			Q = new querys(q_select_joined_jobs);
			//IFEXISTS
			if ( Q != null ) {
				
				//READ_QUERY	
				//SKIP_FIRST
				String nextLine = Q.getNextQueryLine();
				//READ_EM_ALL
				while (nextLine!=null) {					
					nextLine = Q.getNextQueryLine();
					if (nextLine!=null) {						
					//F**K_PATTERN - "something;something;something;...;something"
					Object[] aRow = nextLine.split(";");
					//FILL
					Job j = new Job((String)aRow[1], //id in list, not job id 
							(String)aRow[2], 
							(String)aRow[3]);
					
					setJobList(j);
					}
				}
			}
			
		 /*
		  * 3 Все связанные работники
		  * Их может быть много!
		  * 
		  */
				
				//QUERY			
				q_select_joined_workers = 
				q_select_joined_workers.replaceFirst("@request", 
							getId());
				Q = new querys(q_select_joined_workers);
				//IFEXISTS
				if ( Q != null ) {
					
					//READ_QUERY	
					//SKIP_FIRST
					String nextLine = Q.getNextQueryLine();
					//READ_EM_ALL
					while (nextLine!=null) {					
						nextLine = Q.getNextQueryLine();
						if (nextLine!=null) {						
						//F**K_PATTERN - "something;something;something;...;something"
						Object[] aRow = nextLine.split(";");
						//FILL
						Worker w = new Worker((String)aRow[1], //id in list, not worker id 
								(String)aRow[2], 
								(String)aRow[3], 
								(String)aRow[4],
								(String)aRow[5]);
						
						
						setWorkerList(w);
						}
					}
				}
			
			
			
			
			
				/*setClient(c);
				setJobList(j);
				setWorkerList(w);
			
			*/
			
			
			
			
			return true;
			
			
			
			
		}
				
		@Deprecated
		public Object[] buildRequestViewTree() {
			Object[] result = null;
			result = new Object[3];
			result[0] =  getClient();
			result[1] =  getJobList().toArray();
			result[2] =  getWorkerList().toArray();
			
			return result;
		}
				
		@Deprecated
		public String printe() {
			
			String r = "";
			
			
			r = "("+this.getId() + " " + this.getDateIn() + " " +
			this.getDateOut() + " " + 
			this.isDone + " " + this.getclientid()+")";
			//CLIENT
			r += "\n" + this.getClient().getId() +" "+ this.getClient().getName(); 
			//JOBS
			r += "\nJobs-\n";
			for (int i=0; i<getJobList().size(); i++) {
				r += this.getJobList().get(i).getId() +" "+ 
				this.getJobList().get(i).getDescription() +" " + 
				this.getJobList().get(i).getCost()+ "\n"; 				
			}
			//WORKE
			r += "\nWorkers-\n";
			for (int i=0; i<getWorkerList().size(); i++) {
				r += this.getWorkerList().get(i).getId() +" "+ 
				this.getWorkerList().get(i).getSur() +" " + this.getWorkerList().get(i).getName() +" " + 
				this.getWorkerList().get(i).getPatro() + " " +
				this.getWorkerList().get(i).getExp()+ "\n"; 				
			}
			
			
			return r;
			
			
			
			
		}
		
		
		public void setClient(Client client) {
			this.client = client;
		}

		public Client getClient() {
			return client;
		}

		public void setJobList(Job job) {
			JobList.add(job);
		}

		public ArrayList<Job> getJobList() {
			return JobList;
		}

		public void setWorkerList(Worker worker) {
			WorkerList.add(worker);
		}

		public ArrayList<Worker> getWorkerList() {
			return WorkerList;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public void setDateIn(String dateIn) {
			DateIn = dateIn;
		}

		public String getDateIn() {
			return DateIn;
		}

		public void setDateOut(String dateOut) {
			DateOut = dateOut;
		}

		public String getDateOut() {
			return DateOut;
		}

		public void setIsDone(String o) {
			this.isDone = o;
		}

		public String getIsDone() {
			return isDone;
		}
		public void setclientid(String c) {
			 clientid = c;
		}

		public String getclientid() {
			return clientid;
		}

		
		
	public static abstract class Entity {
		
		
		abstract Object[] toArray();
	}
	
	
	
	public static class Person {
		
		private String sur, name, patro;
		
		public Person(String a, String b, String c) {
			setSur(a);
			setName(b);
			setPatro(c);
		}

		public void setSur(String sur) {
			this.sur = sur;
		}

		public String getSur() {
			return sur;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setPatro(String patro) {
			this.patro = patro;
		}

		public String getPatro() {
			return patro;
		}
		
		
	}
	
	 public static class Client extends Person {
		
		private String id;		
		
		public Client(String a, String b, String c, String d) {
			
			super(a,b,c);
			
			setId(d);			
			
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
		
		public String toString() {
			/*
			 * Выводится в дереве при рендере
			 * 
			 */
			
			String what = getSur() + " " + 
			getName() + " " + 
			getPatro() + " (" + 
			getId() + ")";
				
			
            return what;
        }
		
		
		
		
	}
	
	public static class Worker extends Person {
		
		private String exp;	
		private String id;
		
		public Worker(String i, String a, String b, String c, String e) {
			
			super(a,b,c);
			setId(i);
			setExp(e);			
			
		}

		public void setExp(String exp) {
			this.exp = exp;
		}

		public String getExp() {
			return exp;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
		
		public String toString() {
			/*
			 * Выводится в дереве при рендере
			 * 
			 */
			
			String what = getSur() + " " + 
			getName() + " " + 
			getPatro() + " (:" + 
			getExp() + ")";
			
            return what;
        }
		
		public Object[] toArray() {
			/*
			 * В добавлялкахх
			 * 
			 */
			
			ArrayList<String> sic = new ArrayList<String>();
			
			sic.add(getId());
			sic.add(getSur());
			sic.add(getName());
			sic.add(getPatro());		
			sic.add(getExp());
			
            return sic.toArray();
        }
		
	}
	
	public static class Job {
		
		private String id, Description, cost;
		
		
		public Job(String a, String b, String c) {
			
			setId(a);
			setDescription(b);
			setCost(c);
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getId() {
			return id;
		}


		public void setDescription(String description) {
			Description = description;
		}


		public String getDescription() {
			return Description;
		}


		public void setCost(String cost) {
			this.cost = cost;
		}


		public String getCost() {
			return cost;
		}
		
		public String toString() {
			/*
			 * Выводится в дереве при рендере
			 * 
			 */
			
									
			
			String what = "(" + getId()+ ") " + 
			getDescription() + " (" + 
			getCost()+ ") руб.";
				
			
            return what;
        }
		
	}


	

}
