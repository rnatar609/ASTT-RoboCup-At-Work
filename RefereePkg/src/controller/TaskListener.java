package controller;

import java.util.ArrayList;
import java.util.EventListener;

import model.BmtTask;
import model.BntTask;
import model.BttTask;

public interface TaskListener extends EventListener{
	public void bntTaskSpecChanged(BntTask bntTask, int pos, ArrayList<BntTask> bntTaskList);
	public void bmtTaskSpecChanged(BmtTask bmtTask, int pos, ArrayList<BmtTask> bmtTaskList);
	public void bttTaskSpecChanged(BttTask bttTask, int pos, ArrayList<BttTask> bttTaskList);
}
