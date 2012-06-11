package model;

import java.io.*;
import javax.swing.event.EventListenerList;
import org.zeromq.*;
import controller.ConnectionListener;
import model.CompetitionLogging;
import model.Logging;

public class TaskServer implements Runnable{

	private ZMQ.Socket refereeSocket;
	private EventListenerList listOfConnectionListeners = new EventListenerList();
	private Logging logg;
	private Thread serverThread;
	private String teamName;
	private String commLogID= "Communication";
	
	public TaskServer() {
		logg = Logging.getInstance();
	}

	public void createServerSocket(String ServerIP, String ServerPort){
		// Prepare context and socket
		ZMQ.Context context = ZMQ.context(1);
		refereeSocket = context.socket(ZMQ.REP);
		try {
			refereeSocket.bind("tcp://" + ServerIP + ":" + ServerPort);
		}
		catch(Exception e) {
			System.out.println("An exception occured the application will be terminated." + "\n" + "Exception: " + e);
			File file = new File(Logging.filename);
			if (!file.delete()) {
				System.out.println("Deletion of file >" + Logging.filename + "< failed.");
			}
		} 
		System.out.println("Server socket created: " + refereeSocket
				+ " ipAddress: " + ServerIP + " port: " + ServerPort);
		logg.LoggingFile(commLogID, "Server socket created: "
				+ " ipAddress: " + ServerIP + " port: " + ServerPort);
	}
	
	public void listenForConnection() {
		teamName = new String();
		serverThread = new Thread(this, "Task Server Thread");
		serverThread.start();
		System.out.println("Server thread started... ");
		logg.LoggingFile(commLogID, "Server thread started... ");
	}

	public void run() {
		System.out.println("Waiting for Client Requests on socket... "
				+ refereeSocket);
		logg.LoggingFile(commLogID, "Waiting for Client Requests on socket... ");
		refereeSocket.setReceiveTimeOut(-1);
		byte bytes[] = refereeSocket.recv(0);
		teamName = new String(bytes);
		System.out.println("Received message: " + teamName + " from client.");
		logg.LoggingFile(commLogID, "Received message: " + teamName + " from client.");
		notifyTeamConnected();
	}

	public ZMQ.Socket getrefereeSocket(){
		return refereeSocket;
	}
	
	public boolean sendTaskSpecToClient(TaskSpec tSpec) {
		// Send task specification
		CompetitionLogging.setTaskSpecString(tSpec.getTaskSpecString());
		refereeSocket.send(tSpec.getTaskSpecString().getBytes(), 0);
		System.out.println("String sent to client: "+ tSpec.getTaskSpecString());
		logg.LoggingFile(commLogID, "String sent to client: "+ tSpec.getTaskSpecString());
		
		refereeSocket.setReceiveTimeOut(1000);
		String tripletAcknowledge = "";
		byte bytes[] = refereeSocket.recv(0);

		if(!(bytes==null)) {
			tripletAcknowledge = new String(bytes);
		}

		if(!tripletAcknowledge.equals("ACK")){
			System.out.println("Could not send the task specification to the team: " + teamName);
			logg.LoggingFile(commLogID, "Could not send the task specification to the team: " + teamName);
			CompetitionLogging.setReceivedACK(false);
			return false;
		}else{
			System.out.println("Message from " + teamName + ": " + tripletAcknowledge);
			logg.LoggingFile(commLogID, "Message from " + teamName + ": " + tripletAcknowledge);
			CompetitionLogging.setReceivedACK(true);
			notifyTaskSpecSent();
			return true;
		}
	}

	public boolean disconnectClient() {
		try{
			refereeSocket.close();	
			System.out.println("Client Disconnected");
			logg.LoggingFile(commLogID, "Client Disconnected");
			notifyTeamDisconnected();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
	
	public String getTeamName() {
		return teamName;
	}
}
