package RefereePkg;

import controller.MainController;

public class RefereeSystem {

	static MainController mC;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mC = new MainController(args);
		mC.showView();
	}
}
