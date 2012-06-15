package model;

public class BttTask extends Task implements Comparable<BttTask> {
	private String situation;
	private String configuration;
	private String place;
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
	public BttTask(String situation, String configuration, String place,
			String object) {
		this.situation = situation;
		this.configuration = configuration;
		this.place = place;
		this.object = object;
		state = StateOfTask.INIT;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getConfiguration() {
		if (configuration.equals(" "))
			return new String("");
		else
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
		if (configuration.equals(" "))
			return (new String("(" + situation + "," + place + "," + object
					+ ")"));
		else
			return (new String("(" + situation + "," + place + ","
					+ configuration + "," + object + ")"));
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getSituation() {
		return situation;
	}

	@Override
	public int compareTo(BttTask bttTask) {

		int i = bttTask.situation.compareTo(this.situation);
		if (i != 0)
			return i;
		i = this.configuration.compareTo(bttTask.configuration);
		if (i != 0)
			return i;
		i = this.place.compareTo(bttTask.place);
		// if (i != 0)
		return i;
		// return this.object.compareTo(bttTask.object);
	}

	public String getPlace() {
		return place;
	}
}