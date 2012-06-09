package model;

public class Task {

	private StateOfTask state;

	// empty bmttask
	public Task() {
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public Task(Task task) {
		state = task.state;
	}

	public String getString() {
		return "error";
	}

	public String getPlace() {
		return null;
	}

	public String getOrientation() {
		return null;
	}

	public StateOfTask getState() {
		return null;
	}
}