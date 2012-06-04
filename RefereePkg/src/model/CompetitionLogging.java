package model;

import java.io.*;
import java.util.Date;
import java.text.*;

public class CompetitionLogging {
	static String teamName = "";
	static String taskSpecString = "";
	static String clientIP = "";
	static String runTimeStart = "";
	static String stopTime = "";
	static int competitionNumber = 0;
	static boolean receivedACK = false; 
	static int configurationTimeInSeconds = 0;
	static int runTimeInSeconds = 0;
	static int competitionTimeInSeconds = 0;
	
	public static void setTeamName(String s) {
		teamName = s;
	}
	
	public static void setTaskSpecString(String s) {
		taskSpecString = s;
	}
	
	public static void setClientIP(String s) {
		clientIP = s;
	}
	
	public static void setReceivedACK(boolean b) {
		receivedACK = b;
	}
	
	public static void setRunTimeStart(String s) {
		runTimeStart = s;
	}
	
	public static void setStopTime(String s) {
		stopTime = s;
	}
	
	public static void setTripletStates() {
		
	}
	
	public static void storeParams() {
		if(!teamName.equals("")) {
			DateFormat dateformat = new SimpleDateFormat("yyMMddHHmmss");
			Date date = new Date();
			String fileName = new String("CompetitionLog_" + teamName + "_" + dateformat.format(date.getTime()) +  ".log");
			File file = new File(fileName);
			boolean exists = file.exists();
			try {
				if(!exists) {			
					file.createNewFile();
			    }
			}
			catch(IOException e) {
				System.out.println("Exception caught in TeamLogging saveVariablesToFile: " + e);
			}
			writeParamsToFile(file);
		}
		else {
			System.out.println("No Teamname available, therefore no competition performed");
		}
	}
	
	private static void writeParamsToFile(File file) {
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file,true));
			output.write("Teamname: " + teamName + "\n");
			output.write("Task specification: " + taskSpecString + "\n");
			output.write("Client IP: " + clientIP + "\n");
			output.write("Acknowledge received: " + receivedACK + "\n");
			output.write("Run time start time: " + runTimeStart + "\n");
			output.write("Stop time: " + stopTime + "\n");
			//output.write("" + "\n");
			output.close();
		}
		catch(IOException e) {
			System.out.println("Exception caught in TeamLogging writeParamToFile method: " + e);
		}
	}
	
	public static void resetParams() {
		teamName="";
		taskSpecString = "";
		clientIP = "";
		runTimeStart = "";
		stopTime = "";
		competitionNumber = 0;
		receivedACK = false;
		configurationTimeInSeconds = 0;
		runTimeInSeconds = 0;
		competitionTimeInSeconds = 0;
	}
}
