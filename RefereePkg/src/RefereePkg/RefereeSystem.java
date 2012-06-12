package RefereePkg;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Logging;
import controller.MainController;

public class RefereeSystem {

	static MainController mC;
	private static Logging logg;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String fileName = new String("Tasklog" + "_" + dateformat.format(date.getTime()) +  ".log");
		Logging.setFileName(fileName);
		logg = Logging.getInstance();
		logg.LoggingFileAndCompetitionFile("Application", "started", false);
		mC = new MainController(args);
		mC.showView();

	}
}
