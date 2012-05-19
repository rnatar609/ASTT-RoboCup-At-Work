package model;

import java.net.InetAddress;

import javax.swing.event.EventListenerList;

import org.zeromq.*;

import controller.ConnectionListener;
import controller.TimeKeeper;

public class TaskServer implements Runnable{

	private String localHost;
	private int port;
	private ZMQ.Socket refereeSocket;
	private EventListenerList listOfConnectionListeners = new EventListenerList();
	private TimeKeeper timekeeper = TimeKeeper.getInstance();
	private TaskScheduler taskscheduler = TaskScheduler.getInstance();;
	private Thread serverThread;
	private String teamName;
	private byte[] clientID;

	public TaskServer() {
		try {
			localHost = new String();
			port = 11111;
			localHost = InetAddress.getLocalHost().getHostAddress();
			// Prepare context and socket
			ZMQ.Context context = ZMQ.context(1);
			refereeSocket = context.socket(ZMQ.REP);
			refereeSocket.bind("tcp://" + localHost + ":" + port);
			System.out.println("Server socket created: " + refereeSocket
					+ " ipAddress: " + localHost + " port: " + port);
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
		System.out.println("Waiting for Client Requests on socket... "
				+ refereeSocket);
		byte bytes[] = refereeSocket.recv(0);
		clientID = refereeSocket.getIdentity();
		System.out.println(clientID);
		teamName = new String(bytes);
		System.out.println("Received message: " + teamName + " from client.");
		notifyTeamConnected();
	}

	public void sendTaskSpecToClient(TaskSpec tSpec) {
		// Send task specification
		byte reply[] = tSpec.getTaskSpecString().getBytes();
		refereeSocket.send(reply, 0);
		System.out.println("String sent to client: "+ tSpec.getTaskSpecString());
		notifyTaskSpecSent();
		// Start setup phase timer
		taskscheduler.timeOut();
		sendStartMsgToClient();
	}

	public void taskComplete() {
		//byte recvdMsg[] = refereeSocket.recv(0);
		//System.out.println("In WAIT_FOR_COMPLETE state, received msg: " + recvdMsg.toString());
		taskscheduler.timer.cancel();
		timekeeper.stopTimer();
		listenForConnection();
		//Send disconnect message
		//disconnectClient(teamName);
	}

	public void sendStartMsgToClient() {
		byte startMsg[] = "Start the Robot...... Runtime Started.......".getBytes();
		refereeSocket.send(startMsg, 0);
		System.out.println("In SEND_START state, sent start msg: " + startMsg.toString());
		// Start run phase timer
		//timer.schedule(runTimeOut, TaskServer.runTime);
		// Wait for Completed message from client
		taskComplete();
	}

	public void disconnectClient(String teamName) {
		// Send disconnect message
		String msg = new String("Disconnecting " + teamName);
		byte reply[] = msg.getBytes();
		refereeSocket.send(reply, 0);
		System.out.println("Disconnect msg sent to client: " + msg);
		notifyTeamDisconnected();
		// Listen for new connection requests
		listenForConnection();
	}

	public void addConnectionListener(ConnectionListener cL) {
		listOfConnectionListeners.add(ConnectionListener.class, cL);
	}

	public void removeConenctionListener(ConnectionListener cL) {
		listOfConnectionListeners.remove(ConnectionListener.class, cL);
	}

	private void notifyTeamConnected() {
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
	
	/*public void waitForClientReady() {
	byte readyMsg[] = refereeSocket.recv(0);
	System.out
			.println("In WAIT_FOR_READY state, received msg: " + readyMsg.toString());
	timer.cancel();
	// Send start message
	sendStartMsgToClient();
    }*/
	
}
