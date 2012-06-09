package model;

public class BttTask extends Task {
	private String place;
	private String configuration;
	private String object;
	private String color;
	private StateOfTask state;

	// empty bmttask
	public BttTask() {
		place = "";
		configuration = "";
		object = "";
		color = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public BttTask(BttTask bmtTask) {
		place = bmtTask.place;
		configuration = bmtTask.configuration;
		object = bmtTask.object;
		color = bmtTask.color;
		state = bmtTask.state;
	}

	// init bmttask
	public BttTask(String place, String configuration, String object,
			String color) {
		this.place = place;
		this.configuration = configuration;
		this.object = object;
		this.color = color;
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

	public String getColor() {
		return color;
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

	public void setColor(String color) {
		this.color = color;
	}

	public void setState(StateOfTask state) {
		this.state = state;
	}

	public String getString() {
		if (!place.equals(""))
			return place;
		if (!configuration.equals(""))
			return configuration;
		if (!object.equals("") && !color.equals(""))
			return (new String(object + "(" + color + ")"));
		if (!object.equals(""))
			return object;
		return null;
	}
}