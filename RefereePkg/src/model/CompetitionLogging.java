package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

import controller.TaskListener;

//import model.TaskTriplet.State;

public class CompetitionLogging implements TaskListener {
	static String fileParent = "";
	static String teamName = "";
	static String taskSpecString = "Triplets not sent";
	static String clientIP = "N/A";
	static String runTimeStart = "";
	static String stopTime = "";
	static int competitionNumber = 0;
	static boolean receivedACK = false;
	static int configurationTimeInSeconds = 0;
	static int runTimeInSeconds = 0;
	static int competitionTimeInSeconds = 0;
	static int taskTripletListLength = 0;
	static boolean lengthAlreadySet = false;
	static StateOfTask taskState[];

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

	public static void setTaskState(int index, StateOfTask stateOfTask) {
		taskState[index] = stateOfTask;
	}

	public static void setCompetitionNumber(int i) {
		competitionNumber = i;
	}

	public static void setTaskTripletListLength(TaskSpec tS,
			CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			taskTripletListLength = tS.getBntTaskList().size();
		case BMT:
			taskTripletListLength = tS.getBntTaskList().size();
		case BTT:
			taskTripletListLength = tS.getBntTaskList().size();
		default:
			taskTripletListLength = 0;
		}
		taskState = new StateOfTask[taskTripletListLength];
		for (int i = 0; i < taskTripletListLength; i++) {
			taskState[i] = StateOfTask.INIT;
		}
	}

	public static void setFilePath(File file) {
		// filePath = file.getAbsolutePath();
		fileParent = file.getParent();
	}

	public static void storeParams() {
		if (teamName == null || teamName.equals("")) {
			teamName = "Unknown";
		}
		DateFormat dateformat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String fileName = new String(teamName + "_Competition_"
				+ competitionNumber + "_" + dateformat.format(date.getTime())
				+ ".log");
		File file = new File(fileParent + File.separatorChar + fileName);
		System.out.println("CompetitionLogging filePath: " + file.getPath());
		boolean exists = file.exists();
		try {
			if (!exists) {
				file.createNewFile();
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught in TeamLogging saveVariablesToFile: "
							+ e);
		}
		writeParamsToFile(file);
	}

	private static void writeParamsToFile(File file) {
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file, true));
			output.write("Teamname: " + teamName + "\n");
			output.write("Competition number: " + competitionNumber + "\n");
			output.write("Length of Task list: " + taskTripletListLength + "\n");
			output.write("Task specification: " + taskSpecString + "\n");
			output.write("Client IP: " + clientIP + "\n");
			output.write("Acknowledge received: " + receivedACK + "\n");
			output.write("Start time of runtime: " + runTimeStart + "\n");
			output.write("Stop time: " + stopTime + "\n");
			output.write("The first triplet number is 0 (because of consistency to the general logger)\n");
			for (int i = 0; i < taskTripletListLength; i++) {
				output.write("Triplet no. " + i + ": " + taskState[i] + "\n");
			}
			output.close();
		} catch (IOException e) {
			System.out
					.println("Exception caught in TeamLogging writeParamToFile method: "
							+ e);
		}
	}

	public static void resetParams() {
		fileParent = "";
		teamName = "";
		taskSpecString = "Triplets not sent";
		clientIP = "N/A";
		runTimeStart = "";
		stopTime = "";
		competitionNumber = 0;
		receivedACK = false;
		configurationTimeInSeconds = 0;
		runTimeInSeconds = 0;
		competitionTimeInSeconds = 0;
		taskTripletListLength = 0;
		taskState = new StateOfTask[taskTripletListLength];
		lengthAlreadySet = false;
	}

	@Override
	public void bntTaskSpecChanged(BntTask bntTask, int pos,
			ArrayList<BntTask> bntTaskList) {
		setTaskState(pos, bntTask.getState());
	}

	@Override
	public void bmtTaskSpecChanged(BmtTask bmtTask, int pos,
			ArrayList<BmtTask> bmtTaskList) {
		setTaskState(pos, bmtTask.getState());
	}

	@Override
	public void bttTaskSpecChanged(BttTask bttTask, int pos,
			ArrayList<BttTask> bttTaskList) {
		setTaskState(pos, bttTask.getState());
	}
}
