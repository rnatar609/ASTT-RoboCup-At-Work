package controller;

import java.util.EventListener;

import model.TripletEvent;

public interface TripletListener extends EventListener{
	public void taskSpecChanged(TripletEvent evt);
	// there could be more events
}
