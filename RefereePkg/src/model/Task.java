package model;

public abstract class Task {

	protected StateOfTask state;

	// empty bmttask
	public Task() {
		state = StateOfTask.INIT;
	}

	// copy bmttask
	public Task(Task task) {
		state = task.state;
	}

	public abstract String getString();

	public StateOfTask getState() {
		return state;
	}

	public void setState(StateOfTask state) {
		this.state = state;
	}
}