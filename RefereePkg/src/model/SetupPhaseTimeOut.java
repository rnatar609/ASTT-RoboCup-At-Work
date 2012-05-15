package model;

import java.util.TimerTask;

public class SetupPhaseTimeOut extends TimerTask {
	TaskServer tServer;
	
	public SetupPhaseTimeOut(TaskServer ts)
	{
		tServer = ts;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 tServer.sendStartMsgToClient();
	}	

}
