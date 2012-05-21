package Java;

import org.zeromq.*;

public class JavaClient {
	String taskSpecFromServer;
	String teamName;
	String ready;
	String start;
	String taskcomplete;

	public JavaClient() {
		teamName = new String("Team-Name");
		ready = new String("Ready to Roll");
	}

	public void obtainTaskSpecFromServer(String serverIP, String port) {
		ZMQ.Context RobotModule = ZMQ.context(1);
		ZMQ.Socket RobotClient_Socket = RobotModule.socket(ZMQ.REQ);
		String connectStr = new String("tcp://" + serverIP + ":" + port);
		System.out.println("Connecting to server... ");
		RobotClient_Socket.connect(connectStr);
		RobotClient_Socket.send(teamName.getBytes(), 0);
		System.out.println("Sent team name to server... " + teamName);
		byte[] reply_taskspec = RobotClient_Socket.recv(0);
		taskSpecFromServer = new String(reply_taskspec);
		System.out.println("Received taskSpecification: " + taskSpecFromServer);
		//RobotClient_Socket.send(ready.getBytes(), 0);
		//System.out.println("Ready to Roll");
		byte[] reply_start = RobotClient_Socket.recv(0);
		start = new String(reply_start);
		System.out.println("Starting the navigation");
		RobotClient_Socket.close();
		RobotModule.term();
	}

	public static void main(String[] args) throws Exception {
		// Note: The server IP address & port should be entered as command-line argument.
		if (args.length < 2) {
			System.out.println("Insufficient arguments:"+ args.length + "\nEnter server IP addr and port as Command-line arguments.");
			return;
		}
		// Obtain task specification from server
		JavaClient jClient = new JavaClient();
		System.out.println("ServerIP: " + args[0] + " Server port: " + args[1]);
		jClient.obtainTaskSpecFromServer(args[0], args[1]);
	}
}
