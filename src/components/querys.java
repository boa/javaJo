package components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class querys {	
	
	URL url = null;
	String line = "";	
	private InputStream in;
	private BufferedReader reader;
	public static final String encoding = "UTF-8";
	private boolean result = false;
	
	public querys(String urlString){
		
				
		try {
			url = new URL(URLEncoder.encode(urlString, encoding) );
			in =  url.openStream();
			reader = new BufferedReader(new InputStreamReader(in, encoding));
			setResult(true);
		} catch (MalformedURLException e) {			
			e.printStackTrace();
			setResult(false);
		} catch (IOException e) {
			e.printStackTrace();
			setResult(false);
		}		
		
	}
	
	/**
     * Возвраящает полностью результат чтения ресурса
     * @param   Отсутствуют
     * @return  Строка <code>String</code>.
     */
	public String getWholeQuery() throws IOException {
		String result = "";		
		line = "";  
		
		try {				
			line = reader.readLine();			
			while ( line != null ) {				
				result = result + line + "\n";
				line = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
				
		return result;
	}
       
	public String getNextQueryLine() {
		line = "";
		try {
			line = reader.readLine();
			if ( line != null ) {
				return line;
			}
			else
			{
				reader.close();
				return null;
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}
}

