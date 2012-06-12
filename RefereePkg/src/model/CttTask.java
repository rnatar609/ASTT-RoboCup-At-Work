package model;

public class CttTask extends Task {
	private String place;
	private String configuration;
	private String object;
	private String color;
	private String size;
	private StateOfTask state;

	// empty bmttask
	public CttTask() {
		place = "";
		configuration = "";
		object = "";
		color = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public CttTask(CttTask bmtTask) {
		place = bmtTask.place;
		configuration = bmtTask.configuration;
		object = bmtTask.object;
		color = bmtTask.color;
		state = bmtTask.state;
	}

	// init bmttask
	public CttTask(String place, String configuration, String object,
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}