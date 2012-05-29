package model;


public class TaskTriplet {
	private String place;
	private String orientation;
	private Short pause;
	private TripletState state;

	public TaskTriplet() {
		place = "D0";
		orientation = "N";
		pause = 1;
		state = TripletState.INIT;
	}

	public void setPlace(String s) {
		place = s;
	}

	public void setOrientation(String s) {
			orientation = s;
	}

	public boolean setPause(String s) {
		try {
				pause = Short.parseShort(s);
		} catch (Exception e) {
			System.out.println("Exception in TaskTriplet_setPause(): " + e.getMessage());
			return false;
		}
		return true;
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

	public TripletState getState() {
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

	public void setState(TripletState newState) {
		this.state = newState;
	}
}