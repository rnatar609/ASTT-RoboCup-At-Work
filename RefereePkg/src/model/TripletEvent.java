package model;

import java.util.EventObject;
import java.util.List;

public class TripletEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private TaskTriplet taskTriplet;
	private int tripletNumber;
	private List<TaskTriplet> taskTripletList;

	TripletEvent(TaskTriplet taskTriplet, int tripletNum,
			List<TaskTriplet> taskTripletList) {
		super(taskTriplet);
		this.taskTriplet = taskTriplet;
		this.tripletNumber = tripletNum;
		this.taskTripletList = taskTripletList;
	}

	public List<TaskTriplet> getTaskTripletList() {
		return taskTripletList;
	}

	public int getTripletNumber() {
		return tripletNumber;
	}

	public TaskTriplet getTaskTriplet() {
		return taskTriplet;
	}
}
