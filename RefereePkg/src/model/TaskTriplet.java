package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TaskTriplet {
	private String place;
	private String orientation;
	private Short pause;

	/* Default constructor */
	public TaskTriplet() {
		place = "D0";
		orientation = "N";
		pause = 1;
	}

	private void getOrientationFromUser() throws Exception {
		boolean repeatLoop = false;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			do {
				repeatLoop = false;
				System.out.print("Enter orientation: ");
				String s = br.readLine();
				if (setOrientation(s)) {
					System.out.println("Orientation " + s + " is valid.");
				} else {
					System.out.println("Orientation " + s
							+ " is invalid. Retry.");
					repeatLoop = true;
				}
			} while (repeatLoop);

		} catch (Exception e) {
			System.out.println("While reading Orientation from user: "
					+ e.getMessage());
			throw e;
		}
	}

	private void getPauseFromUser() throws Exception {
		boolean repeatLoop = false;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			do {
				repeatLoop = false;
				System.out.print("Enter pause: ");
				String s = br.readLine();
				if (setPause(s)) {
					System.out.println("Pause " + s + " is valid.");
				} else {
					System.out.println("Pause " + s + " is invalid. Retry.");
					repeatLoop = true;
				}
			} while (repeatLoop);

		} catch (Exception e) {
			System.out.println("While reading Pause from user: "
					+ e.getMessage());
			throw e;
		}
	}

	public boolean setPlace(String s) {
		if (TaskTriplet.isValidTripletElementPlace(s)) {
			place = s;
			return true;
		}
		return false;
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

	private boolean setTaskTriplet(String tripletString) {
		if (TaskTriplet.isValidTriplet(tripletString)) {
			Scanner input = new Scanner(tripletString)
					.useDelimiter("((\\s*\\,\\s*)|(\\s*\\)))");
			input.skip("\\(");
			place = input.next();
			orientation = input.next();
			pause = Short.parseShort(input.next());
			return true;
		}
		return false;
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

	public String getTaskTripletString() {
		return (new String("(" + place + ", " + orientation + ", " + pause
				+ ")"));
	}

	/**
	 * This method reads a task triplet from the user and stores in the invoking
	 * object.
	 */
	void getTaskTripletFromUser() throws Exception {
		boolean repeatLoop = false;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			do {
				repeatLoop = false;
				ValidTripletElements.displayValidTripletElements(); // To assist
																	// user in
																	// entering
																	// valid
																	// task
																	// specification.
				System.out
						.println("Enter triplet as (PLACE, ORIENTATION, PAUSE) or enter PLACE element of triplet: ");
				String s = br.readLine();
				if (s.charAt(0) == '(') {
					if (s.matches("(\\([^,]+\\,[^,]+\\,[^,]+\\))")) {
						System.out
								.println("User has entered a triplet. Checking for validity...");
						if (setTaskTriplet(s)) {
							System.out.println("model " + s + " is valid.");
						} else {
							System.out.println("model " + s
									+ " is invalid. Retry.");
							repeatLoop = true;
						}
					} else {
						System.out
								.println("Input format not recognised. Retry.");
						repeatLoop = true;
					}
				} else {
					System.out
							.println("User appears to have entered place. Checking for validity...");
					if (setPlace(s)) {
						System.out.println("Place " + s + " is valid.");
						getOrientationFromUser();
						getPauseFromUser();
					} else {
						System.out
								.println("Place " + s + " is invalid. Retry.");
						repeatLoop = true;
					}
				}
			} while (repeatLoop);
		} catch (Exception e) {
			System.out.println("Caught exception in getTaskTripletFromUser: "
					+ e.getMessage());
			throw e;
		}
	}

	private static boolean isValidTriplet(String triplet) {
		boolean valid = false;
		ValidTripletElements vte = ValidTripletElements.getInstance();
		Scanner input = new Scanner(triplet)
				.useDelimiter("((\\s*\\,\\s*)|(\\s*\\)))");
		input.skip("\\(");
		valid = (vte.isPlaceValid(input.next())
				&& vte.isOrientationValid(input.next()) && vte
				.isPauseValid(input.next()));
		return valid;
	}

	private static boolean isValidTripletElementPlace(String s) {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isPlaceValid(s);
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
}