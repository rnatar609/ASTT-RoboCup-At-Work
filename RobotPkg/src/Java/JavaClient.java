package Java;

import org.zeromq.*;

public class JavaClient {
   
	public String obtainTaskSpecFromServer(String serverIP, String serverPort, String teamName){
		System.out.println(serverIP);
		System.out.println(serverPort);
		System.out.println("ServerIP: " + serverIP + " Server port: " + serverPort);
		String taskSpecFromServer;
		String tripletAcknowledge = new String();
		ZMQ.Context RobotModule = ZMQ.context(1);
		ZMQ.Socket RobotClient_Socket = RobotModule.socket(ZMQ.REQ);
		String connectStr = new String("tcp://" + serverIP + ":" + serverPort);
		System.out.println("Connecting to server... ");
		RobotClient_Socket.connect(connectStr);
		RobotClient_Socket.send(teamName.getBytes(), 0);
		System.out.println("Sent team name to server... " + teamName);
		byte[] reply_taskspec = RobotClient_Socket.recv(0);
		taskSpecFromServer = new String(reply_taskspec);
		RobotClient_Socket.send(tripletAcknowledge.getBytes(), 0);
		System.out.println("Received taskSpecification: " + taskSpecFromServer);
		RobotClient_Socket.close();
		RobotModule.term();
		return taskSpecFromServer;
	}
}
