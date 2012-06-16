package model;

public class BmtTask extends Task {
	static String placeInitial;
	static String placeSource;
	static String placeDestination;
	static String placeFinal;
	static String configuration;
	private String object;

	// empty bmttask
	public BmtTask() {
		object = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public BmtTask(BmtTask bmtTask) {
		object = bmtTask.object;
		state = bmtTask.state;
	}

	// init bmttask
	public BmtTask(String placeInit, String placeSource,
			String placeDestination, String placeFinal, String configuration,
			String object) {
		BmtTask.placeInitial = placeInit;
		BmtTask.placeSource = placeSource;
		BmtTask.placeDestination = placeDestination;
		BmtTask.placeFinal = placeFinal;
		BmtTask.configuration = configuration;
		this.object = object;
		this.state = StateOfTask.INIT;
	}

	public static String getConfiguration() {
		return configuration;
	}

	public String getObject() {
		return object;
	}

	public void setConfiguration(String configuration) {
		BmtTask.configuration = configuration;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getString() {
		return (new String(object));
	}

	public static String getPlaceInitial() {
		return placeInitial;
	}

	public static String getPlaceSource() {
		return placeSource;
	}

	public static String getPlaceDestination() {
		return placeDestination;
	}

	public static String getPlaceFinal() {
		return placeFinal;
	}

	public static void setPlaceInitial(String placeInitial) {
		BmtTask.placeInitial = placeInitial;
	}

	public static void setPlaceSource(String placeSource) {
		BmtTask.placeSource = placeSource;
	}

	public static void setPlaceDestination(String placeDestination) {
		BmtTask.placeDestination = placeDestination;
	}

	public static void setPlaceFinal(String placeFinal) {
		BmtTask.placeFinal = placeFinal;
	}
}