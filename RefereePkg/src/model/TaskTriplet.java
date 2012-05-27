package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TaskTriplet {
	private String place;
	private String orientation;
	private Short pause;
	private State state;

	public enum State {
		INIT, PASSED, FAILED
	}

	/* Default constructor */
	public TaskTriplet() {
		place = "D0";
		orientation = "N";
		pause = 1;
		state = State.INIT;
	}

	public boolean setPlace(String s) {
		// if (TaskTriplet.isValidTripletElementPlace(s)) {
		place = s;
		return true;
		// }
		// return false;
	}

	public boolean setOrientation(String s) {
		if (TaskTriplet.isValidTripletElementOrientation(s)) {
			orientation = s;
			return true;
		}
		return false;
	}

	public boolean setPause(String s) {
		try {
			if (TaskTriplet.isValidTripletElementPause(s)) {
				pause = Short.parseShort(s);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception in TaskTriplet_setPause(): "
					+ e.getMessage());
			return false;
		}
	}

	public String getPlace() {
		return place;
	}

	public String getOrientation() {
		return orientation;
	}

	public Short getPause() {
		return pause;
	}

	public State getState() {
		return state;
	}

	public String getTaskTripletString() {
		return (new String("(" + place + ", " + orientation + ", " + pause
				+ ")"));
	}

	public static String getValidTripletPattern() {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.getValidTripletPattern();
	}

	private static boolean isValidTripletElementOrientation(String s) {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isOrientationValid(s);
	}

	private static boolean isValidTripletElementPause(String s) {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isPauseValid(s);
	}

	static boolean getValidTripletElements() throws Exception {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.readFromConfigFile();
	}

	public void setState(State newState) {
		this.state = newState;
	}
}