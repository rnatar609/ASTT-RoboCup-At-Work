package Java;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import org.zeromq.*;

public class JavaClient {
	private String taskSpecFromServer;
	private String teamName;
	private String tripletAcknowledge;
	private String start;
	private String taskcomplete;
	private String serverIP;
	private String port;
	private Properties serverProperties;
	//static private JavaClient javaclient;

	public JavaClient() {
		teamName = new String("bit-bots");
		tripletAcknowledge = new String("Task Specification Received");
		serverIP = new String("127.0.0.1");
		port = new String("11111");
		serverProperties = new Properties();
	}

	public void obtainTaskSpecFromServer() throws Exception {
		try {
			FileInputStream in = new FileInputStream("/Users/Rnatar609/Documents/ASTT-RoboCup-At-Work/RobotPkg/src/Java/config.txt");
			serverProperties.load(in);
			in.close();
		} catch (Exception e) {
			System.out.println("Exception in loading Server Properties: "
					+ e.getMessage());
			throw e;
		}
		System.out.println(serverIP);
		System.out.println(port);
		serverIP = this.getServerIP();//javaclient.getServerIP();
		port = this.getPortaddr();//javaclient.getPortaddr();
		System.out.println("ServerIP: " + serverIP + " Server port: " + port);
		ZMQ.Context RobotModule = ZMQ.context(1);
		ZMQ.Socket RobotClient_Socket = RobotModule.socket(ZMQ.REQ);
		String connectStr = new String("tcp://" + serverIP + ":" + port);
		System.out.println("Connecting to server... ");
		RobotClient_Socket.connect(connectStr);
		RobotClient_Socket.send(teamName.getBytes(), 0);
		System.out.println("Sent team name to server... " + teamName);
		byte[] reply_taskspec = RobotClient_Socket.recv(0);
		taskSpecFromServer = new String(reply_taskspec);
		RobotClient_Socket.send(tripletAcknowledge.getBytes(), 0);
		System.out.println("Received taskSpecification: " + taskSpecFromServer);
		//byte[] reply_start = RobotClient_Socket.recv(0);
		//start = new String(reply_start);
		//System.out.println("Starting the navigation");
		RobotClient_Socket.close();
		RobotModule.term();
	}

	/*private void getServerIPaddrPort() throws Exception {
		try {
			FileInputStream in = new FileInputStream("config.txt");
			serverProperties.load(in);
			in.close();
		} catch (Exception e) {
			System.out.println("Exception in loading Server Properties: "
					+ e.getMessage());
			throw e;
		}
	}*/
	
	private String getServerIP() {
		String str = serverProperties.getProperty("ServerIP");
		String refIP = null;
		Scanner scnr = new Scanner(str);
		if (scnr.hasNext()) {
			refIP = scnr.next();
			System.out.println(refIP);
		} else
			System.out.println("No ServerIP specified in the config file");
		return refIP;
	}
	
	private String getPortaddr() {
		String str = serverProperties.getProperty("Port");
		String refPort = null;
		Scanner scnr = new Scanner(str);
		if (scnr.hasNext()) {
			refPort = scnr.next();
			System.out.println(refPort);
		} else
			System.out.println("No ServerIP specified in the config file");
		return refPort;
	}
	
	public static void main(String[] args) throws Exception {
		JavaClient javaclient = new JavaClient();
		// Obtain task specification from server
		javaclient.obtainTaskSpecFromServer();
	}
}
