package model;

import java.util.TimerTask;

public class TaskComplete extends TimerTask {
	TaskServer tServer;
	
	public TaskComplete(TaskServer ts)
	{
		tServer = ts;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		tServer.listenForConnection();

	}

}
