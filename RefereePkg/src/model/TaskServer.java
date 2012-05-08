package model;

import javax.swing.event.EventListenerList;

import org.zeromq.*;

import controller.ConnectionListener;

public class TaskServer implements Runnable{
	private String ipAddress;
	private int port;
	private ZMQ.Socket socket;
	private EventListenerList listOfConnectionListeners = new EventListenerList();
	private Thread serverThread;

	public TaskServer() {
		ipAddress = new String("10.20.118.89");
		port = 11111;
		// Prepare context and socket
	    ZMQ.Context context = ZMQ.context(1);
		socket = context.socket(ZMQ.REP);
		socket.bind("tcp://" + ipAddress + ":" + port);
		System.out.println("Server socket created: " + socket + " ipAddress: " + ipAddress + " port: " + port);
	}

	public void listenForConnection() {
		serverThread = new Thread(this, "Task Server Thread");
		serverThread.start();
        System.out.println("Server thread started... ");
	}
	
	public void run()
	{
		System.out.println("Waiting for Client Requests on socket... " + socket);
		byte bytes[] = socket.recv(0);
		String teamName = new String(bytes);
		System.out.println("Received message: " + teamName + " from client.");
		notifyTeamConnected(teamName);
	}
	
	public void sendTaskSpecToClient(TaskSpec tSpec)
	{
		//Send task specification
		byte reply[] = tSpec.getTaskSpecString().getBytes();
		socket.send(reply, 0);
		System.out.println("String sent to client: " + tSpec.getTaskSpecString());		
		notifyTaskSpecSent();
		listenForConnection();
	}
	
	public void disconnectClient(String teamName)
	{
		//Send disconnect message
		String msg = new String("Disconnecting " + teamName);
		byte reply[] = msg.getBytes();
		socket.send(reply, 0);
		System.out.println("String sent to client: " + msg);
		notifyTeamDisconnected();
		listenForConnection();
	}
	
	public void addConnectionListener(ConnectionListener cL) {
		listOfConnectionListeners.add(ConnectionListener.class, cL);
	}

	public void removeConenctionListener(ConnectionListener cL) {
		listOfConnectionListeners.remove(ConnectionListener.class, cL);
	}

	private void notifyTeamConnected(String teamName) {
		Object[] listeners = listOfConnectionListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ConnectionListener.class) {
				((ConnectionListener) listeners[i + 1]).teamConnected(teamName);
			}
		}
	}
	
	private void notifyTeamDisconnected() {
		Object[] listeners = listOfConnectionListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ConnectionListener.class) {
				((ConnectionListener) listeners[i + 1]).teamDisconnected();
			}
		}
	}
	
	private void notifyTaskSpecSent(){
		Object[] listeners = listOfConnectionListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ConnectionListener.class) {
				((ConnectionListener) listeners[i + 1]).taskSpecSent();
			}
		}
	}
}
