package controller;

import java.util.ArrayList;
import java.util.EventListener;

import model.BntTask;

public interface ConnectionListener extends EventListener{
	public void teamConnected(String teamName);
	public void teamDisconnected();
	public void taskSpecSent();
	// there could be more events
}
