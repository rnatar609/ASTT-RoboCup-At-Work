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
import controller.TaskListener;

public class TaskSpec {
	private ArrayList<BntTask> bntTaskList;
	private ArrayList<BmtTask> bmtTaskList;
	private ArrayList<BttTask> bttTaskList;
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
		bttTaskList = new ArrayList<BttTask>();
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
		switch (compIdent) {
		case BNT:
			BntTask bntTask = (BntTask) task;
			bntTaskList.add(bntTask);
			logg.LoggingFile(taskListName, bntTask.getString() + " no. "
					+ bntTaskList.indexOf(bntTask) + " added");
			notifyBntTaskSpecChanged(bntTask, bntTaskList.indexOf(bntTask),
					bntTaskList);
			break;
		case BMT:
			BmtTask bmtTask = (BmtTask) task;
			bmtTaskList.add(bmtTask);
			logg.LoggingFile(taskListName, bmtTask.getString() + " no. "
					+ bmtTaskList.indexOf(bmtTask) + " added");
			notifyBmtTaskSpecChanged(bmtTask, bmtTaskList.indexOf(bmtTask),
					bmtTaskList);
			break;
		case BTT:
			BttTask bttTask = (BttTask) task;
			bttTaskList.add(bttTask);
			logg.LoggingFile(taskListName, bttTask.getString() + " no. "
					+ bttTaskList.indexOf(bttTask) + " added");
			notifyBttTaskSpecChanged(bttTask, bttTaskList.indexOf(bttTask),
					bttTaskList);
			break;
		default:
			return;
		}
	}

	public Task deleteTask(int pos, CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			BntTask bntTask = bntTaskList.remove(pos);
			logg.LoggingFile(taskListName, bntTask.getString() + " no. "
					+ bntTaskList.indexOf(bntTask) + " deleted");
			notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
			return bntTask;
		case BMT:
			BmtTask bmtTask = bmtTaskList.remove(pos);
			logg.LoggingFile(taskListName, bmtTask.getString() + " no. "
					+ bmtTaskList.indexOf(bmtTask) + " deleted");
			notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
			return bmtTask;
		case BTT:
			BttTask bttTask = bttTaskList.remove(pos);
			logg.LoggingFile(taskListName, bttTask.getString() + " no. "
					+ bttTaskList.indexOf(bttTask) + " deleted");
			notifyBttTaskSpecChanged(bttTask, pos, bttTaskList);
			return bttTask;
		default:
			return null;
		}
	}

	public Task moveUp(int pos, CompetitionIdentifier compIdent) {

		if (pos == 0) {
			return null;
		} else {
			switch (compIdent) {
			case BNT:
				BntTask bntTask = bntTaskList.remove(pos);
				bntTaskList.add(pos - 1, bntTask);
				logg.LoggingFile(taskListName, bntTask.getString() + " no. "
						+ bntTaskList.indexOf(bntTask) + " moved up");
				notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
				return bntTask;
			case BMT:
				BmtTask bmtTask = bmtTaskList.remove(pos);
				bmtTaskList.add(pos - 1, bmtTask);
				logg.LoggingFile(taskListName, bmtTask.getString() + " no. "
						+ bmtTaskList.indexOf(bmtTask) + " moved up");
				notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
				return bmtTask;
			case BTT:
				BttTask bttTask = bttTaskList.remove(pos);
				bttTaskList.add(pos - 1, bttTask);
				logg.LoggingFile(taskListName, bttTask.getString() + " no. "
						+ bttTaskList.indexOf(bttTask) + " moved up");
				notifyBttTaskSpecChanged(bttTask, pos, bttTaskList);
				return bttTask;
			default:
				return null;
			}
		}
	}

	public Task moveDown(int pos, CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			if (bntTaskList.size() == pos + 1)
				return null;
			BntTask bntTask = bntTaskList.remove(pos);
			bntTaskList.add(pos + 1, bntTask);
			logg.LoggingFile(taskListName, bntTask.getString() + " no. "
					+ bntTaskList.indexOf(bntTask) + " moved down");
			notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
			return bntTask;
		case BMT:
			if (bmtTaskList.size() == pos + 1)
				return null;
			BmtTask bmtTask = bmtTaskList.remove(pos);
			bmtTaskList.add(pos + 1, bmtTask);
			logg.LoggingFile(taskListName, bmtTask.getString() + " no. "
					+ bmtTaskList.indexOf(bmtTask) + " moved down");
			notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
			return bmtTask;
		case BTT:
			if (bttTaskList.size() == pos + 1)
				return null;
			BttTask bttTask = bttTaskList.remove(pos);
			bttTaskList.add(pos + 1, bttTask);
			logg.LoggingFile(taskListName, bttTask.getString() + " no. "
					+ bttTaskList.indexOf(bttTask) + " moved down");
			notifyBttTaskSpecChanged(bttTask, pos, bttTaskList);
			return bttTask;
		default:
			return null;
		}
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

	public void addTripletListener(TaskListener tL) {
		listOfTaskListeners.add(TaskListener.class, tL);
	}

	public void removeTripletListener(TaskListener tL) {
		listOfTaskListeners.remove(TaskListener.class, tL);
	}

	private void notifyBntTaskSpecChanged(BntTask bntTask, int pos,
			ArrayList<BntTask> bntTaskList2) {
		Object[] listeners = listOfTaskListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TaskListener.class) {
				((TaskListener) listeners[i + 1]).bntTaskSpecChanged(bntTask,
						pos, bntTaskList);
			}
		}
	}

	private void notifyBmtTaskSpecChanged(BmtTask bmtTask, int pos,
			ArrayList<BmtTask> bntTaskList2) {
		Object[] listeners = listOfTaskListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TaskListener.class) {
				((TaskListener) listeners[i + 1]).bmtTaskSpecChanged(bmtTask,
						pos, bmtTaskList);
			}
		}
	}

	private void notifyBttTaskSpecChanged(BttTask bttTask, int pos,
			ArrayList<BttTask> bttTaskList) {
		Object[] listeners = listOfTaskListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TaskListener.class) {
				((TaskListener) listeners[i + 1]).bttTaskSpecChanged(bttTask,
						pos, bttTaskList);
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
