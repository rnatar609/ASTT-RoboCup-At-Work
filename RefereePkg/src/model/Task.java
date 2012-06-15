package model;

public class Task {

	protected StateOfTask state;

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
	
}