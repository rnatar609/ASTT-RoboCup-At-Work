package model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.event.EventListenerList;

import org.zeromq.*;

import controller.ConnectionListener;
import java.net.InetAddress;


import javax.swing.JOptionPane;
import java.util.Timer;

public class TaskServer implements Runnable{
	private String localHost;
	private int port;
	private ZMQ.Socket Referee_Socket;
	private EventListenerList listOfConnectionListeners = new EventListenerList();
	private Thread serverThread;
	public TaskServer() {
		port = 11111;
		try {
			localHost = new String();
			port = 11111;
			localHost = InetAddress.getLocalHost().getHostAddress();
			// Prepare context and socket
			ZMQ.Context context = ZMQ.context(1);
			Referee_Socket = context.socket(ZMQ.REP);
			Referee_Socket.bind("tcp://" + localHost + ":" + port);
			System.out.println("Server socket created: " + Referee_Socket + " ipAddress: " + localHost + " port: " + port);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listenForConnection() {
		serverThread = new Thread(this, "Task Server Thread");
		serverThread.start();
		System.out.println("Server thread started... ");
	}

	public void run() {
		System.out.println("Waiting for Client Requests on socket... " + Referee_Socket);
		byte bytes[] = Referee_Socket.recv(0);
		String teamName = new String(bytes);
		System.out.println("Received message: " + teamName + " from client.");
		notifyTeamConnected(teamName);
	}

	public void sendTaskSpecToClient(TaskSpec tSpec) {
		// Send task specification
		String ready = null;
		byte reply[] = tSpec.getTaskSpecString().getBytes();
		Referee_Socket.send(reply, 0);
		System.out.println("String sent to client: " + tSpec.getTaskSpecString());
		byte readyclient[] = Referee_Socket.recv(0);
		ready = new String(readyclient);
		System.out.println(ready);
		byte start[] = "Start the Robot".getBytes();
		Referee_Socket.send(start, 1);
		System.out.println("Intimated the Client to start the navigation");
		//notifyTaskSpecSent();
		//listenForConnection();
	}

	public void disconnectClient(String teamName) {
		// Send disconnect message
		String msg = new String("Disconnecting " + teamName);
		byte reply[] = msg.getBytes();
		Referee_Socket.send(reply, 0);
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

	private void notifyTaskSpecSent() {
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
