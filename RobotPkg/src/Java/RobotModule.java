package Java;

public class RobotModule {
	private static String serverIP = new String("192.168.88.187");
	private static String serverPort = new String("11111");
	private static String teamName = new String("bit-bots");
	
	public static void main(String[] args) throws Exception {
		JavaClient jclient = new JavaClient();
		// Obtain task specification from server
		jclient.obtainTaskSpecFromServer(serverIP,serverPort,teamName);
	}
}
