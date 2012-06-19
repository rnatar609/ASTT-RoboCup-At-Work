package model;

public class CttTask extends Task implements Comparable<CttTask> {
	private String situation;
	private String configuration;
	private String place;
	private String object;

	// empty ctttask
	public CttTask() {
		situation = "";
		place = "";
		configuration = "";
		object = "";
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public CttTask(CttTask cttTask) {
		situation = cttTask.situation;
		place = cttTask.place;
		configuration = cttTask.configuration;
		object = cttTask.object;
		state = cttTask.state;
	}

	// init bmttask
	public CttTask(String situation, String configuration, String place,
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

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public void setObject(String object) {
		this.object = object;
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
	public int compareTo(CttTask cttTask) {

		int i = 0;
			i = this.situation.compareTo(cttTask.situation);
		if (i != 0)
			return i;
		i = this.configuration.compareTo(cttTask.configuration);
		if (i != 0)
			return i;
		i = this.place.compareTo(cttTask.place);
		// if (i != 0)
		return i;
		// return this.object.compareTo(cttTask.object);
	}

	public String getPlace() {
		return place;
	}
}