package Java;

import org.zeromq.*;

public class JavaClient {
	String taskSpecFromServer;
	String teamName;

	public JavaClient() {
		teamName = new String("Team-Name");
	}

	public void obtainTaskSpecFromServer(String serverIP, String port) {
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket requester = context.socket(ZMQ.REQ);
		String connectStr = new String("tcp://" + serverIP + ":" + port);
		System.out.println("Connecting to server... ");
		requester.connect(connectStr);
		requester.send(teamName.getBytes(), 0);
		byte[] reply = requester.recv(0);
		taskSpecFromServer = new String(reply);
		System.out.println("Received taskSpecification: " + taskSpecFromServer);
		requester.close();
		context.term();
	}

	public static void main(String[] args) throws Exception {
		// Note: The server IP address & port should be entered as command-line argument.
		if (args.length < 2) {
			System.out
					.println("Insufficient arguments:"
							+ args.length
							+ "\nEnter server IP addr and port as Command-line arguments.");
			return;
		}
		// Obtain task specification from server
		JavaClient jClient = new JavaClient();
		jClient.obtainTaskSpecFromServer(args[0], args[1]);
	}
}
