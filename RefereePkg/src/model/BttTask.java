package model;

public class BttTask extends Task {
	private String situation;
	private String place;
	private String configuration;
	private String object;

	// empty btttask
	public BttTask() {
		situation = "";
		place = "";
		configuration = "";
		object = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public BttTask(BttTask bttTask) {
		situation = bttTask.situation;
		place = bttTask.place;
		configuration = bttTask.configuration;
		object = bttTask.object;
		state = bttTask.state;
	}

	// init bmttask
	public BttTask(String place, String configuration, String object,
			String color) {
		this.situation = situation;
		this.place = place;
		this.configuration = configuration;
		this.object = object;
		state = StateOfTask.INIT;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getConfiguration() {
		return configuration;
	}

	public String getObject() {
		return object;
	}

	public StateOfTask getState() {
		return state;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public void setState(StateOfTask state) {
		this.state = state;
	}

	public String getString() {
		return (new String("(" + situation + "," + place + "," + configuration + "," + configuration + ")"));
	}

	public void setSituation(String selectedItem) {
		// TODO Auto-generated method stub
		
	}

	public String getSituation() {
		return situation;
	}
}