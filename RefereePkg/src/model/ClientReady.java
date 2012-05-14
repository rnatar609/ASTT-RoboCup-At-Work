package model;

import java.util.TimerTask;

public class ClientReady extends TimerTask {
	TaskServer tServer;
	
	public ClientReady(TaskServer ts)
	{
		tServer = ts;
	}
	
	@Override
	public void run() {
		 tServer.sendStart();

	}

}
