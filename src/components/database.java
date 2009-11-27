package components;

import java.io.IOException;

public class database {	

	/**
	 *  Хей-хей! Функия проверки работоспособности базы данных.
	 * 
	 * @param pathToCgi Url-адрес cgi-программы, работающей с базой
	 * @return Boolean Работает или нет база?
	 * @throws IOException А тровинг эксшепшинов нужен для отслеживания
	 * работоспособности веб-сервера
	 */
			
		
	public static boolean isDatabaseOK(String pathToCgi) throws IOException {			
		
		querys q = new querys(pathToCgi);	
		
		return ((q.isResult()) && (!q.getWholeQuery().contains("Error"))) ;		
	}
	
}
