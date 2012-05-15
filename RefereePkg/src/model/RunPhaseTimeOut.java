package model;

import java.util.TimerTask;

public class RunPhaseTimeOut extends TimerTask {
	TaskServer tServer;

	public RunPhaseTimeOut(TaskServer ts) {
		tServer = ts;
	}

	@Override
	public void run() {
		tServer.listenForConnection();
	}

}
