
public class RobotModule {
	private static String serverIP = new String("127.0.1.1");
	private static String serverPort = new String("11111");
	private static String teamName = new String("b-it-bots");
	
	public static void main(String[] args) throws Exception {
		JavaClient jclient = new JavaClient();
		// Obtain task specification from server
		jclient.obtainTaskSpecFromServer(serverIP,serverPort,teamName);
	}
}
