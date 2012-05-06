package model;

import org.zeromq.*;

public class TaskServer {
	TaskSpec taskSpec;
	String ipAddress;
	int port;

	public TaskServer() {
		taskSpec = new TaskSpec();
		ipAddress = new String("10.20.118.41");
		port = 5555;
	}

	private void startServer() {
		// Prepare context and socket
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://" + ipAddress + ":" + port);
		while (true) { // Till Server is stopped.
			System.out.println("Waiting for Client Requests...");
			byte bytes[] = socket.recv(0);
			String msg = new String(bytes);
			System.out.println("Received message: " + msg + "from client.");
			// Confirm whether to serve the client??
			// Send task specification
			String taskSpecStr = new String("<(D1,1,N),(O1,2,W)>"); // Modify to read from file
			byte reply[] = taskSpecStr.getBytes();
			socket.send(reply, 0);
			System.out.println("String sent to client: " + taskSpecStr);
		}
	}
}
