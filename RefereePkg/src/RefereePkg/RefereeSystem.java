package RefereePkg;

import model.Logging;
import controller.MainController;

public class RefereeSystem {

	static MainController mC;
	private static Logging logg;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logging.setFileName("TaskLog.log");
		logg = Logging.getInstance();
		logg.LoggingFile("Application", "started");
		mC = new MainController(args);
		mC.showView();

	}
}
