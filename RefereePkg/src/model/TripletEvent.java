package model;

import java.util.EventObject;
import java.util.List;

public class TripletEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private TaskTriplet taskTriplet;
	private int tripNumber;
	private List<TaskTriplet> taskTripletList;

	TripletEvent(TaskTriplet taskTriplet, int tripNumber,
			List<TaskTriplet> taskTripletList) {
		super(taskTriplet);
		this.taskTriplet = taskTriplet;
		this.tripNumber = tripNumber;
		this.taskTripletList = taskTripletList;
	}

	public List<TaskTriplet> getTaskTripletList() {
		return taskTripletList;
	}

	public int getTripNumber() {
		return tripNumber;
	}

	public TaskTriplet getTaskTriplet() {
		return taskTriplet;
	}
}
