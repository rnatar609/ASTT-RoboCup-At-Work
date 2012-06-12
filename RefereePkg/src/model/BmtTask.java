package model;

public class BmtTask extends Task {
	static String placeInitial;
	static String placeSource;
	static String placeDestination;
	static String placeFinal;
	static String configuration;
	private String object;

	// private StateOfTask state;

	// empty bmttask
	public BmtTask() {
		object = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public BmtTask(BmtTask bmtTask) {
		placeInitial = bmtTask.placeInitial;
		placeSource = bmtTask.placeSource;
		placeDestination = bmtTask.placeDestination;
		configuration = bmtTask.configuration;
		object = bmtTask.object;
		state = bmtTask.state;
	}

	// init bmttask
	public BmtTask(String placeInit, String placeSource,
			String placeDestination, String placeFinal, String configuration,
			String object, String color) {
		this.placeInitial = placeInit;
		this.placeSource = placeSource;
		this.placeDestination = placeDestination;
		this.placeFinal = placeFinal;
		this.configuration = configuration;
		this.object = object;
		state = StateOfTask.INIT;
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
		return (new String(object));
	}
}