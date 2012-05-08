package controller;

import java.util.EventListener;

public interface ConnectionListener extends EventListener{
	public void teamConnected(String teamName);
	public void teamDisconnected();
	public void taskSpecSent();
	// there could be more events
}
