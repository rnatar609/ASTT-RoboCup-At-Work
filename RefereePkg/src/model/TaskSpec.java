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

import javax.swing.event.EventListenerList;

import view.Utils;
import controller.TripletListener;

public class TaskSpec {
	private List<TaskTriplet> taskTripletList;
	private EventListenerList listOfTripletListeners = new EventListenerList();

	public TaskSpec() {
		taskTripletList = new ArrayList<TaskTriplet>();
	}

	public String getTaskSpecString() {
		String s = new String();
		s = s.concat("<");
		Iterator<TaskTriplet> iterator = taskTripletList.iterator();
		while (iterator.hasNext()) {
			s = s.concat(iterator.next().getTaskTripletString());
			if (iterator.hasNext()) {
				s = s.concat(", ");
			}
		}
		s = s.concat(">");
		return s;
	}

	public void addTriplet(TaskTriplet triplet) {
		taskTripletList.add(triplet);
		notifyTripletAdded(new TripletEvent(triplet, taskTripletList.size(),
				taskTripletList));
	}

	public TaskTriplet deleteTriplet(int tripletIndex) {
		TaskTriplet triplet = taskTripletList.remove(tripletIndex);
		notifyTripletDeleted(new TripletEvent(triplet, tripletIndex,
				taskTripletList));
		return triplet;
	}

	public TaskTriplet moveUpTriplet(int tripletIndex) {
		if (tripletIndex == 0) {
			return null;
		} else {
			TaskTriplet triplet = taskTripletList.remove(tripletIndex);
			taskTripletList.add(tripletIndex - 1, triplet);
			notifyTripletMoved(new TripletEvent(triplet, tripletIndex,
					taskTripletList));
			return triplet;
		}
	}

	public TaskTriplet moveDownTriplet(int tripletIndex) {
		if (taskTripletList.size() == tripletIndex + 1) {
			return null;
		} else {
			TaskTriplet triplet = taskTripletList.remove(tripletIndex);
			taskTripletList.add(tripletIndex + 1, triplet);
			notifyTripletMoved(new TripletEvent(triplet, tripletIndex,
					taskTripletList));
			return triplet;
		}
	}
	
	public TaskTriplet editTriplet(int tripletIndex, TaskTriplet updateTriplet) {
		
		try {
			TaskTriplet tt = taskTripletList.set(tripletIndex, updateTriplet);
			notifyTripletMoved(new TripletEvent(updateTriplet, tripletIndex,
					taskTripletList));
			return tt;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * This method reads a task specification from the user and stores in the
	 * invoking object.
	 */
	/*
	 * void getTaskSpecFromUser() throws Exception { char choice = 'n'; boolean
	 * atLeastOneTriplet = false; try { BufferedReader br = new
	 * BufferedReader(new InputStreamReader( System.in)); do { TaskTriplet
	 * nextTriplet = new TaskTriplet(); nextTriplet.getTaskTripletFromUser();
	 * System.out.println("Add triplet " + nextTriplet.getTaskTripletString() +
	 * " to task (y/n)?"); char confirm = (char) br.read(); br.read(); //
	 * reading newline character if (confirm == 'y' | confirm == 'Y') {
	 * this.addTriplet(nextTriplet); System.out.println("Added " +
	 * nextTriplet.getTaskTripletString() + " to task successfully.");
	 * System.out .println("The task specification string now looks like: " +
	 * getTaskSpecString()); atLeastOneTriplet = true; } if (atLeastOneTriplet)
	 * { System.out .println("Would you like to enter more triplets (y/n)?");
	 * choice = (char) br.read(); br.read(); // reading newline character } else
	 * { System.out
	 * .println("At least one triplet expected in task specification."); } }
	 * while ((choice == 'y' || choice == 'Y') || !atLeastOneTriplet); } catch
	 * (Exception e) {
	 * System.out.println("Caught exception in TaskServer.getTaskSpec(): " +
	 * e.getMessage()); throw e; } }
	 */

	public List<TaskTriplet> getTaskTripletList() {
		return taskTripletList;
	}

	public void addTripletListener(TripletListener tL) {
		listOfTripletListeners.add(TripletListener.class, tL);
	}

	public void removeTripletListener(TripletListener tL) {
		listOfTripletListeners.remove(TripletListener.class, tL);
	}

	private void notifyTripletAdded(TripletEvent evt) {
		Object[] listeners = listOfTripletListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TripletListener.class) {
				((TripletListener) listeners[i + 1]).tripletAdded(evt);
			}
		}
	}

	private void notifyTripletDeleted(TripletEvent evt) {
		Object[] listeners = listOfTripletListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TripletListener.class) {
				((TripletListener) listeners[i + 1]).tripletDeleted(evt);
			}
		}
	}

	private void notifyTripletMoved(TripletEvent evt) {
		Object[] listeners = listOfTripletListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TripletListener.class) {
				((TripletListener) listeners[i + 1]).tripletDeleted(evt);
			}
		}
	}

	public boolean saveTaskSpec(File file) {

		file = Utils.correctFile(file);
		try {
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getTaskSpecString());
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
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
				// TODO
			}
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
		return true;
	}
}
