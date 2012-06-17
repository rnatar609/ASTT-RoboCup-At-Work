package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.*;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

//import model.TaskTriplet.State;

import view.Utils;
import controller.TaskListener;

public class TaskSpec {
	private ArrayList<BntTask> bntTaskList;
	private ArrayList<BmtTask> bmtTaskList;
	private ArrayList<BttTask> bttTaskList;
	private ArrayList<CttTask> cttTaskList;
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
		cttTaskList = new ArrayList<CttTask>();
		logg = Logging.getInstance();
	}

	public String getTaskSpecString(CompetitionIdentifier compIdent) {
		String s = new String();
		s = s.concat(compIdent.name());
		s = s.concat("<");
		switch (compIdent) {
		case BNT:
			if (bntTaskList.size() > 0) {
				Iterator<BntTask> itBnt = bntTaskList.iterator();
				while (itBnt.hasNext()) {
					s = s.concat(((Task) itBnt.next()).getString());
				}
			}
			break;
		case BMT:
			if (bmtTaskList.size() > 0) {
				Iterator<BmtTask> itBmt = bmtTaskList.iterator();
				BmtTask first = new BmtTask();
				if (itBmt.hasNext()) {
					first = itBmt.next();
					s = s.concat(BmtTask.getPlaceInitial());
					s = s.concat(",");
					s = s.concat(BmtTask.getPlaceSource());
					s = s.concat(",");
					s = s.concat(BmtTask.getPlaceDestination());
					s = s.concat(",");
					s = s.concat(first.getConfiguration());
					s = s.concat("(");
					s = s.concat(first.getObject());
				}
				BmtTask last = first;
				while (itBmt.hasNext()) {
					last = itBmt.next();
					s = s.concat(",");
					s = s.concat(last.getObject());
				}
				s = s.concat(")");
				s = s.concat(",");
				s = s.concat(BmtTask.getPlaceFinal());
			}
			break;
		case BTT:
			if (bttTaskList.size() > 0) {
				Iterator<BttTask> itBtt = bttTaskList.iterator();
				BttTask btt = itBtt.next();
				BttTask previous = new BttTask();
				do {
					s = s.concat(btt.getSituation() + "situation(");
					do {
						s = s.concat(btt.getPlace() + ",");
						s = s.concat(btt.getConfiguration() + ",(");
						do {
							s = s.concat(btt.getObject() + ",");
							previous = btt;
							if (itBtt.hasNext())
								btt = itBtt.next();
							else
								btt = new BttTask();
						} while (btt.getPlace().equals(previous.getPlace())
								&& (btt.getConfiguration().equals(previous
										.getConfiguration())));
						s = s.substring(0, s.length() - 1); // comma is no
															// longer needed
						s = s.concat(")");
					} while (btt.getSituation().equals(previous.getSituation()));
					s = s.concat(")");
					if (btt.getSituation().length() != 0) {
						s = s.concat(";");
					}
				} while (btt.getSituation().length() != 0);
			}
			break;
		default:
		}
		s = s.concat(">");
		return s;
	}

	/**
	 * @param compIdent
	 * @param task
	 */
	public void addTask(CompetitionIdentifier compIdent, Object task) {
		switch (compIdent) {
		case BNT:
			BntTask bntTask = (BntTask) task;
			bntTaskList.add(bntTask);
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BNT + bntTask.getString() + " no. "
							+ bntTaskList.indexOf(bntTask) + " added");
			notifyBntTaskSpecChanged(bntTask, bntTaskList.indexOf(bntTask),
					bntTaskList);
			break;
		case BMT:
			BmtTask bmtTask = (BmtTask) task;
			bmtTaskList.add(bmtTask);
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BMT + bmtTask.getString() + " no. "
							+ bmtTaskList.indexOf(bmtTask) + " added");
			notifyBmtTaskSpecChanged(bmtTask, bmtTaskList.indexOf(bmtTask),
					bmtTaskList);
			break;
		case BTT:
			BttTask bttTask = (BttTask) task;
			bttTaskList.add(bttTask);
			Collections.sort(bttTaskList);
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BTT + bttTask.getString() + " no. "
							+ bttTaskList.indexOf(bttTask) + " added");
			notifyBttTaskSpecChanged(bttTask, bttTaskList.indexOf(bttTask),
					bttTaskList);
			break;
		case CTT:
			CttTask cttTask = (CttTask) task;
			// bttTaskList.add(cttTask);
			logg.globalLogging(taskListName, CompetitionIdentifier.CTT
					+ "not implemented yet");
			/*
			 * logg.globalLogging(taskListName, CompetitionIdentifier.CTT +
			 * cttTask.getString() + " no. " + bttTaskList.indexOf(cttTask) +
			 * " added");
			 */
			notifyCttTaskSpecChanged(cttTask, bttTaskList.indexOf(cttTask),
					cttTaskList);
			break;
		default:
			return;
		}
	}

	public Task deleteTask(int pos, CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			BntTask bntTask = bntTaskList.remove(pos);
			logg.globalLogging(taskListName, CompetitionIdentifier.BNT
					+ bntTask.getString() + " no. " + pos + " deleted");
			notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
			return bntTask;
		case BMT:
			BmtTask bmtTask = bmtTaskList.remove(pos);
			logg.globalLogging(taskListName, CompetitionIdentifier.BMT
					+ bmtTask.getString() + " no. " + pos + " deleted");
			notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
			return bmtTask;
		case BTT:
			BttTask bttTask = bttTaskList.remove(pos);
			Collections.sort(bttTaskList);
			logg.globalLogging(taskListName, CompetitionIdentifier.CTT
					+ bttTask.getString() + " no. " + pos + " deleted");
			notifyBttTaskSpecChanged(bttTask, pos, bttTaskList);
			return bttTask;
		case CTT:
			CttTask cttTask = cttTaskList.remove(pos);
			logg.globalLogging(taskListName, CompetitionIdentifier.CTT
					+ "not implemented yet");
			/*
			 * logg.globalLogging(taskListName, CompetitionIdentifier.CTT +
			 * cttTask.getString() + " no. " + pos + " deleted");
			 */
			notifyCttTaskSpecChanged(cttTask, pos, cttTaskList);
			return cttTask;
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
				logg.globalLogging(taskListName,
						CompetitionIdentifier.BNT + bntTask.getString()
								+ " no. " + bntTaskList.indexOf(bntTask)
								+ " moved up");
				notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
				return bntTask;
			case BMT:
				BmtTask bmtTask = bmtTaskList.remove(pos);
				bmtTaskList.add(pos - 1, bmtTask);
				logg.globalLogging(taskListName,
						CompetitionIdentifier.BMT + bmtTask.getString()
								+ " no. " + bmtTaskList.indexOf(bmtTask)
								+ " moved up");
				notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
				return bmtTask;
			case BTT:
				BttTask bttTask = bttTaskList.remove(pos);
				bttTaskList.add(pos - 1, bttTask);
				Collections.sort(bttTaskList);
				logg.globalLogging(taskListName,
						CompetitionIdentifier.BTT + bttTask.getString()
								+ " no. " + bttTaskList.indexOf(bttTask)
								+ " moved up");
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
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BNT + bntTask.getString() + " no. "
							+ bntTaskList.indexOf(bntTask) + " moved down");
			notifyBntTaskSpecChanged(bntTask, pos, bntTaskList);
			return bntTask;
		case BMT:
			if (bmtTaskList.size() == pos + 1)
				return null;
			BmtTask bmtTask = bmtTaskList.remove(pos);
			bmtTaskList.add(pos + 1, bmtTask);
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BMT + bmtTask.getString() + " no. "
							+ bmtTaskList.indexOf(bmtTask) + " moved down");
			notifyBmtTaskSpecChanged(bmtTask, pos, bmtTaskList);
			return bmtTask;
		case BTT:
			if (bttTaskList.size() == pos + 1)
				return null;
			BttTask bttTask = bttTaskList.remove(pos);
			bttTaskList.add(pos + 1, bttTask);
			Collections.sort(bttTaskList);
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BTT + bttTask.getString() + " no. "
							+ bttTaskList.indexOf(bttTask) + " moved down");
			notifyBttTaskSpecChanged(bttTask, pos, bttTaskList);
			return bttTask;
		default:
			return null;
		}
	}

	public Task updateTask(int pos, Task task, CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			BntTask bntTask = bntTaskList.set(pos, (BntTask) task);
			logg.globalLogging(taskListName, CompetitionIdentifier.BNT
					+ bntTask.getString() + " no. " + bntTaskList.indexOf(task)
					+ " updated to " + task.getString());
			notifyBntTaskSpecChanged(bntTask, bntTaskList.indexOf(bntTask),
					bntTaskList);
			return bntTask;
		case BMT:
			BmtTask bmtTask = bmtTaskList.set(pos, (BmtTask) task);
			logg.globalLogging(taskListName, CompetitionIdentifier.BMT
					+ bmtTask.getString() + " no. " + bmtTaskList.indexOf(task)
					+ " updated to " + task.getString());
			notifyBmtTaskSpecChanged(bmtTask, bmtTaskList.indexOf(bmtTask),
					bmtTaskList);
			return bmtTask;
		case BTT:
			BttTask bttTask = bttTaskList.set(pos, (BttTask) task);
			Collections.sort(bttTaskList);
			logg.globalLogging(taskListName, CompetitionIdentifier.BTT
					+ bttTask.getString() + " no. " + bttTaskList.indexOf(task)
					+ " updated to " + task.getString());
			notifyBttTaskSpecChanged(bttTask, bttTaskList.indexOf(bttTask),
					bttTaskList);
			return bttTask;
		default:
			return null;
		}
	}

	/*
	 * public List<TaskTriplet> getTaskTripletList() { return null; //
	 * taskTripletList; }
	 */

	public Task getTaskAtIndex(int index, CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			return bntTaskList.get(index);
		case BMT:
			return bmtTaskList.get(index);
		case BTT:
			return bttTaskList.get(index);
		default:
			return null;
		}
	}

	public void addTaskListener(TaskListener tL) {
		listOfTaskListeners.add(TaskListener.class, tL);
	}

	public void removeTripletListener(TaskListener tL) {
		listOfTaskListeners.remove(TaskListener.class, tL);
	}

	public void notifyBntTaskSpecChanged(BntTask bntTask, int pos,
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

	public void notifyBmtTaskSpecChanged(BmtTask bmtTask, int pos,
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

	public void notifyBttTaskSpecChanged(BttTask bttTask, int pos,
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

	public void notifyCttTaskSpecChanged(CttTask cttTask, int pos,
			ArrayList<CttTask> cttTaskList) {
		Object[] listeners = listOfTaskListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TaskListener.class) {
				((TaskListener) listeners[i + 1]).cttTaskSpecChanged(cttTask,
						pos, cttTaskList);
			}
		}
	}

	public boolean saveTaskSpec(File file) {

		file = Utils.correctFile(file);
		try {
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getTaskSpecString(CompetitionIdentifier.BNT));
			out.write("\n");
			out.write(getTaskSpecString(CompetitionIdentifier.BMT));
			out.write("\n");
			out.write(getTaskSpecString(CompetitionIdentifier.BTT));
			out.write("\n");
			out.write(getTaskSpecString(CompetitionIdentifier.CTT));
			out.write("\n");
			out.close();
			logg.globalLogging("TODO", "saved actual task specification in >"
					+ file.getName() + "<");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			logg.globalLogging("TODO", "saving failed! >");
			return false;
		}

		return true;
	}

	public boolean openTaskSpec(File file) {

		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (!parseTaskSpecString(strLine))
					return false;
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}

		return true;
	}

	public boolean parseTaskSpecString(String tSpecStr) {
		tSpecStr = removeSpaces(tSpecStr);

		String competition = tSpecStr.substring(0, 3);
		System.out.println("competition " + competition);
		try {
			if (competition.equals(CompetitionIdentifier.BNT.toString())) {
				bntTaskList = new ArrayList<BntTask>();
				Pattern pat = Pattern.compile(ValidTaskElements
						.getInstance().getValidBNTPattern());
				Matcher m = pat.matcher(tSpecStr);
				do {
					BntTask nextTask = new BntTask();
					if (m.find()) {
						nextTask.setPlace(m.group(1));
						nextTask.setOrientation(m.group(2));
						nextTask.setPause(m.group(3));
						bntTaskList.add(nextTask);
						logg.globalLogging(taskListName, nextTask.getString()
								+ " no. " + bntTaskList.indexOf(nextTask)
								+ " added");
						notifyBntTaskSpecChanged(nextTask,
								bntTaskList.indexOf(nextTask), bntTaskList);
					}
				} while (!m.hitEnd());
			}
			if (competition.equals(CompetitionIdentifier.BMT.toString())) {

				bmtTaskList = new ArrayList<BmtTask>();
				String delims = "[<>,()]";
				String[] tokens = tSpecStr.split(delims);

				for (int i = 5; i < tokens.length - 2; i++) {
					BmtTask nextTask = new BmtTask();
					BmtTask.setPlaceInitial(tokens[1]);
					BmtTask.setPlaceSource(tokens[2]);
					BmtTask.setPlaceDestination(tokens[3]);
					nextTask.setConfiguration(tokens[4]);
					BmtTask.setPlaceFinal(tokens[tokens.length - 1]);
					nextTask.setObject(tokens[i]);
					bmtTaskList.add(nextTask);
					logg.globalLogging(taskListName, nextTask.getString()
							+ " no. " + bmtTaskList.indexOf(nextTask)
							+ " added");
					notifyBmtTaskSpecChanged(nextTask,
							bmtTaskList.indexOf(nextTask), bmtTaskList);
				}
			}
			if (competition.equals(CompetitionIdentifier.BTT.toString())) {

				bttTaskList = new ArrayList<BttTask>();
				String delimssemicolon = "[;]";
				String delimsbracket = "[()]";
				String delimskomma = "[,]";
				String[] taskspec = tSpecStr.split("[<>]");
				if(taskspec.length > 1){
					String[] initgoalsituation = taskspec[1].split(delimssemicolon);
	
					for (int d = 0; d < initgoalsituation.length; d++) {
	
						String[] tokens = initgoalsituation[d].split(delimsbracket);
	
						String pose;
						String config;
						String situation = "";
						if(tokens[0].contains("initial"))
							situation = "initial";
						if(tokens[0].contains("goal"))
							situation = "goal";
	
						for (int i = 1; i < tokens.length; i++) {
							String test[] = tokens[i].split(delimskomma);
							pose = test[0];
							if (test.length > 1)
								config = tokens[i].split(delimskomma)[1];
							else
								config = "";
							i++;
							String[] objects = tokens[i].split(delimskomma);
							for (int u = 0; u < objects.length; u++) {
	
								BttTask nextTask = new BttTask();
								
								// System.out.println("pose: "+ pose);
								// System.out.println("config: "+ config);
								// System.out.println("objects: "+ objects[u]);
								nextTask.setSituation(situation);
								nextTask.setPlace(pose);
								nextTask.setConfiguration(config);
								nextTask.setObject(objects[u]);
								bttTaskList.add(nextTask);
								logg.globalLogging(taskListName,
										nextTask.getString() + " no. "
												+ bttTaskList.indexOf(nextTask)
												+ " added");
								notifyBttTaskSpecChanged(nextTask,
										bttTaskList.indexOf(nextTask), bttTaskList);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Caught exception in parseTaskSpec. Error: "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	public void setTaskState(int tripletIndex, int column,
			CompetitionIdentifier compIdent) {
		Task tT = getTaskAtIndex(tripletIndex, compIdent);
		StateOfTask newState;
		if (column == 1)
			newState = StateOfTask.PASSED;
		else
			newState = StateOfTask.FAILED;
		if (tT.getState() == newState)
			tT.setState(StateOfTask.INIT);
		else
			tT.setState(newState);
		switch (compIdent) {
		case BNT:
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BNT + tT.getString() + " no. "
							+ tripletIndex + " new state: " + tT.getState());
			logg.competitionLogging(taskListName, CompetitionIdentifier.BNT
					+ tT.getString() + " no. " + tripletIndex + " new state: "
					+ tT.getState());
			notifyBntTaskSpecChanged(((BntTask) tT), column, bntTaskList);
			break;
		case BMT:
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BMT + tT.getString() + " no. "
							+ tripletIndex + " new state: " + tT.getState());
			logg.competitionLogging(taskListName, CompetitionIdentifier.BMT
					+ tT.getString() + " no. " + tripletIndex + " new state: "
					+ tT.getState());
			notifyBmtTaskSpecChanged(((BmtTask) tT), column, bmtTaskList);
			break;
		case BTT:
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BTT + tT.getString() + " no. "
							+ tripletIndex + " new state: " + tT.getState());
			logg.competitionLogging(taskListName, CompetitionIdentifier.BTT
					+ tT.getString() + " no. " + tripletIndex + " new state: "
					+ tT.getState());
			notifyBttTaskSpecChanged(((BttTask) tT), column, bttTaskList);
			break;
		case CTT:
			logg.globalLogging(taskListName,
					CompetitionIdentifier.BNT + tT.getString() + " no. "
							+ tripletIndex + " new state: " + tT.getState());
			logg.competitionLogging(taskListName, CompetitionIdentifier.BNT
					+ tT.getString() + " no. " + tripletIndex + " new state: "
					+ tT.getState());
			notifyCttTaskSpecChanged(((CttTask) tT), column, cttTaskList);
			break;
		default:
			;
		}
	}

	public void resetStates(CompetitionIdentifier compIdent) {
		switch (compIdent) {
		case BNT:
			for (BntTask tT : bntTaskList) {
				tT.setState(StateOfTask.INIT);
			}
		case BMT:
			for (BmtTask tT : bmtTaskList) {
				tT.setState(StateOfTask.INIT);
			}
		case BTT:
			for (BttTask tT : bttTaskList) {
				tT.setState(StateOfTask.INIT);
			}
		case CTT:
			for (CttTask tT : cttTaskList) {
				tT.setState(StateOfTask.INIT);
			}
		}
	}

	public ArrayList<BntTask> getBntTaskList() {
		return bntTaskList;
	}

	public ArrayList<BmtTask> getBmtTaskList() {
		return bmtTaskList;
	}

	public ArrayList<BttTask> getBttTaskList() {
		return bttTaskList;
	}

	public ArrayList<CttTask> getCttTaskList() {
		return cttTaskList;
	}
}
