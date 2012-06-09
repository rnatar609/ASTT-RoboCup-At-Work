package model;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class TripletEvent {
	private static final long serialVersionUID = 1L;
	private Task task;
	private int number;
	private ArrayList<BntTask> taskList;

	TripletEvent(Object task, int num, ArrayList<BntTask> list) {
		this.task = (BntTask) task;
		this.number = num;
		this.taskList = list;
	}

	public List<BntTask> getTaskList() {
		return taskList;
	}

	public int getTripletNumber() {
		return number;
	}

	public Task getTask() {
		return task;
	}
}
