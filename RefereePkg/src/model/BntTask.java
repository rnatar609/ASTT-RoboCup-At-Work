package model;

public class BntTask extends Task {
	private String place;
	private String orientation;
	private String pause;

	// empty bnttask
	public BntTask() {
		place = "";
		orientation = "";
		pause = "";
		state = StateOfTask.INIT;
	}

	// copy bnttask
	public BntTask(BntTask bntTask) {
		place = bntTask.place;
		orientation = bntTask.orientation;
		pause = bntTask.place;
		state = bntTask.state;
	}

	// init bnttask
	public BntTask(String place, String orientation, String pause) {
		this.place = place;
		this.orientation = orientation;
		this.pause = place;
		state = StateOfTask.INIT;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public void setPause(String pause) {
		this.pause = pause;
	}

	public void setState(StateOfTask state) {
		this.state = state;
	}

	public String getPlace() {
		return place;
	}

	public String getOrientation() {
		return orientation;
	}

	public String getPause() {
		return pause;
	}

	public StateOfTask getState() {
		return state;
	}

	public String getString() {
		return (new String("(" + place + "," + orientation + "," + pause + ")"));
	}
}