/**
 * 
 */
package model;

import java.io.*;
import java.util.Date;
import java.text.*;

/**
 * @author Jeyaprakash Rajagopal, 
 * 		   Marc Wollenweber
 * 
 * 
 *
 */
public class Logging {

	private static Logging instance = null;
	static String filename = new String("RefereeSystemDefaultLog.log"); //default file name
	File file;	
	DateFormat dateformat;
	
	protected Logging() {
		file=new File(filename);
		BufferedWriter output;
		boolean exists = file.exists();
		try {
			if(!exists) {			
				file.createNewFile();
		    }
			else 
			{
		    	output = new BufferedWriter(new FileWriter(file));
				output.write("");
				output.close();
		    }	
		}
		catch(IOException e){
			System.out.println("Exception caught in Logging constructor: " + e);
		}
	}
	
	public static void setFileName(String fileName)
	{
		filename = fileName;
	}
	
	public static Logging getInstance() {
	    if(instance == null) {
		         instance = new Logging();
		}
		return instance;
	}
	
    public void LoggingFile(String logIdentifier, String args) {
    	dateformat = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date = new Date();
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(file,true));
			output.write("[" + dateformat.format(date.getTime()) + "] " + logIdentifier + ": " + args + "\n");
			output.close();
		}
		catch(IOException e) {
			System.out.println("Exception caught in during File Logging: " + e);
		}
    }
}
