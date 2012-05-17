package controller;

import java.util.EventListener;

import model.TripletEvent;

public interface TripletListener extends EventListener{
	public void tripletAdded(TripletEvent evt);
	public void tripletDeleted(TripletEvent evt);
	public void taskSpecFileOpened(TripletEvent evt);
	// there could be more events
}
