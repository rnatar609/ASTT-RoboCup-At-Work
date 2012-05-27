package RefereePkg;

import model.Logging;
import controller.MainController;

public class RefereeSystem {

	static MainController mC;
	private static Logging logg = Logging.getInstance("TaskLog.log");
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logg.LoggingFile("Application", 
				"started");
		mC = new MainController(args);
		mC.showView();
		
	}
}
