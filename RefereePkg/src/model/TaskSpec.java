package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.*;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

import model.TaskTriplet.State;

import view.Utils;
import controller.TripletListener;

public class TaskSpec {
	private ArrayList<BntTask> bntTaskList;
	private ArrayList<BmtTask> bmtTaskList;
	private ArrayList<BmtTask> bttTaskList;
	private EventListenerList listOfTaskListeners = new EventListenerList();
	private Logging logg;
	private String taskListName = "TaskList";

	private String removeSpaces(String str) {
		StringTokenizer tokens = new StringTokenizer(str, " ", false);
		String newStr = "";
		while (tokens.hasMoreElements()) {
			newStr += tokens.nextElement();
		}
		return newStr;
	}

	public TaskSpec(int num) {
		bntTaskList = new ArrayList<BntTask>();
		bmtTaskList = new ArrayList<BmtTask>();
		bttTaskList = new ArrayList<BmtTask>();
		logg = Logging.getInstance();
	}

	public String getTaskSpecString(CompetitionIdentifier compIdent) {
		String s = new String();
		s = s.concat(compIdent.name());
		s = s.concat("<");
		switch (compIdent) {
		case BNT:
			Iterator<BntTask> itBnt = bntTaskList.iterator();
			while (itBnt.hasNext()) {
				s = s.concat(((Task) itBnt.next()).getString());
			}
		case BMT:
			Iterator<BmtTask> itBmt = bmtTaskList.iterator();
			while (itBmt.hasNext()) {
				s = s.concat(((Task) itBmt.next()).getString());
			}
		case BTT:
		default:
		}
		s = s.concat(">");
		return s;
	}

	public void addTask(CompetitionIdentifier compIdent, Object task) {
		if (task instanceof BntTask)
	        bntTaskList.add((BntTask) task);
		logg.LoggingFile(taskListName, ((Task) task).getString() + " no. "
				+ bntTaskList.indexOf(task) + " added");
		notifyTaskSpecChanged(new TripletEvent(task, bntTaskList.size(),bntTaskList));
	}

	public TaskTriplet deleteTriplet(int tripletIndex) {
		/*
		 * TaskTriplet triplet = taskTripletList.remove(tripletIndex);
		 * logg.LoggingFile(taskListName, triplet.getTaskTripletString() +
		 * " no. " + tripletIndex + " deleted"); notifyTaskSpecChanged(new
		 * TripletEvent(triplet, tripletIndex, taskTripletList)); return
		 * triplet;
		 */
		return null;
	}

	public TaskTriplet moveUpTriplet(int tripletIndex) {
		/*
		 * if (tripletIndex == 0) { return null; } else { TaskTriplet triplet =
		 * taskTripletList.remove(tripletIndex);
		 * taskTripletList.add(tripletIndex - 1, triplet);
		 * logg.LoggingFile(taskListName, triplet.getTaskTripletString() +
		 * " no. " + taskTripletList.indexOf(triplet) + " moved up");
		 * notifyTaskSpecChanged(new TripletEvent(triplet, tripletIndex,
		 * taskTripletList)); return triplet; }
		 */
		return null;
	}

	public TaskTriplet moveDownTriplet(int tripletIndex) {
		/*
		 * if (taskTripletList.size() == tripletIndex + 1) { return null; } else
		 * { TaskTriplet triplet = taskTripletList.remove(tripletIndex);
		 * taskTripletList.add(tripletIndex + 1, triplet);
		 * logg.LoggingFile(taskListName, triplet.getTaskTripletString() +
		 * " no. " + taskTripletList.indexOf(triplet) + " moved down");
		 * notifyTaskSpecChanged(new TripletEvent(triplet, tripletIndex,
		 * taskTripletList)); return triplet; }
		 */
		return null;
	}

	public TaskTriplet editTriplet(int tripletIndex, TaskTriplet updateTriplet) {

		/*
		 * try { TaskTriplet tt = taskTripletList.set(tripletIndex,
		 * updateTriplet); notifyTaskSpecChanged(new TripletEvent(updateTriplet,
		 * tripletIndex, taskTripletList)); logg.LoggingFile(taskListName,
		 * tt.getTaskTripletString() + " no. " +
		 * taskTripletList.indexOf(updateTriplet) + " updated to " +
		 * updateTriplet.getTaskTripletString()); return tt; } catch (Exception
		 * e) { return null; }
		 */
		return null;
	}

	public List<TaskTriplet> getTaskTripletList() {
		return null; // taskTripletList;
	}

	public TaskTriplet getTaskTripletAtIndex(int index) {
		return null; // taskTripletList.get(index);
	}

	public void addTripletListener(TripletListener tL) {
		listOfTaskListeners.add(TripletListener.class, tL);
	}

	public void removeTripletListener(TripletListener tL) {
		listOfTaskListeners.remove(TripletListener.class, tL);
	}

	private void notifyTaskSpecChanged(TripletEvent evt) {
		Object[] listeners = listOfTaskListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TripletListener.class) {
				((TripletListener) listeners[i + 1]).taskSpecChanged(evt);
			}
		}
	}

	public boolean saveTaskSpec(File file) {

		/*
		 * file = Utils.correctFile(file); try { FileWriter fstream = new
		 * FileWriter(file); BufferedWriter out = new BufferedWriter(fstream);
		 * out.write(getTaskSpecString()); out.close();
		 * logg.LoggingFile(taskListName, "saved actual task specification in >"
		 * + file.getName() + "<"); } catch (Exception e) {
		 * System.err.println("Error: " + e.getMessage());
		 * logg.LoggingFile(taskListName, "saving failed! >"); return false; }
		 */
		return true;
	}

	public boolean openTaskSpec(File file) {
		/*
		 * try { FileInputStream fstream = new FileInputStream(file);
		 * DataInputStream in = new DataInputStream(fstream); BufferedReader br
		 * = new BufferedReader(new InputStreamReader(in)); String strLine;
		 * while ((strLine = br.readLine()) != null) { taskTripletList = new
		 * ArrayList<TaskTriplet>(); if (!parseTaskSpecString(strLine)) return
		 * false; System.out.println("Found and parsed task spec string: " +
		 * getTaskSpecString()); notifyTaskSpecChanged(new
		 * TripletEvent(taskTripletList.get(0), taskTripletList.size(),
		 * taskTripletList)); } in.close(); } catch (Exception e) {
		 * System.err.println("Error: " + e.getMessage()); return false; }
		 */
		return true;
	}

	public TaskTriplet setTripletState(int tripletIndex, int column) {
		/*
		 * TaskTriplet tT = taskTripletList.get(tripletIndex); State newState;
		 * if (column == 1) newState = State.PASSED; else newState =
		 * State.FAILED; if (tT.getState() == newState) tT.setState(State.INIT);
		 * else tT.setState(newState); logg.LoggingFile(taskListName,
		 * tT.getTaskTripletString() + " no. " + taskTripletList.indexOf(tT) +
		 * " new state: " + tT.getState()); notifyTaskSpecChanged(new
		 * TripletEvent(tT, taskTripletList.indexOf(tT), taskTripletList));
		 */
		return null; // tT;
	}

	public void resetStates() {
		/*
		 * for (TaskTriplet tT : taskTripletList) { tT.setState(State.INIT);
		 * logg.LoggingFile(taskListName, tT.getTaskTripletString() + " no. " +
		 * taskTripletList.indexOf(tT) + " new state: INIT"); }
		 */
	}
}
