package model;

import java.util.EventObject;

public class TripletEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private TaskTriplet taskTriplet;

	TripletEvent(TaskTriplet taskTriplet) {
		super(taskTriplet);
		this.taskTriplet = taskTriplet;
	}

	public TaskTriplet getTaskTriplet() {
		return taskTriplet;
	}
}
