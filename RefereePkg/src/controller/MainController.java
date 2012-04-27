package controller;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import model.Map;
import model.TaskSpec;
import model.TaskTriplet;
import model.ValidTripletElements;
import view.MainGUI;

public class MainController {
	private MainGUI mG;
	private TaskSpec tS;

	private Action save = new AbstractAction("Save") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			File file = mG.showSaveDialog();
			if (file != null) {
				if (tS.saveTaskSpec(file)
						&& Map.saveTaskSpecMap(file, mG.getMapArea())) {
					mG.setStatusLine("saved actual task specification in >"
							+ file.getName() + "<");
				} else {
					mG.setStatusLine("<html><FONT COLOR=RED>Something went wrong!"
							+ "</FONT> No map saved </html>");
				}
			} else {
				mG.setStatusLine("save command cancelled by user");
			}
		}
	};

	private Action open = new AbstractAction("Open") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			File file = mG.showOpenDialog();
			if (file != null) {
				if (tS.openTaskSpec(file)) {
					mG.setStatusLine("Opened actual task specification >"
							+ file.getName() + "<");
				} else {
					mG.setStatusLine("<html><FONT COLOR=RED>Something went wrong!"
							+ "</FONT> No map opened </html>");
				}
				mG.setStatusLine("<html><FONT COLOR=RED>not implemented yet! </FONT> opened task specification </html>");
			} else {
				mG.setStatusLine("Open command cancelled by user");
			}
		}
	};

	private Action exit = new AbstractAction("Exit") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	};

	private Action addTriplet = new AbstractAction("Add Triplet") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			TaskTriplet t = new TaskTriplet();
			if (t.setPlace((String) mG.getPlacesBox().getSelectedItem())
					&& t.setOrientation((String) mG.getOrientationsBox()
							.getSelectedItem())
					&& t.setPause((String) mG.getPausesBox().getSelectedItem()
							.toString())) {
				tS.addTriplet(t);
				mG.setStatusLine("added triplet (" + t.getPlace() + ", "
						+ t.getOrientation() + ", " + t.getPause() + ")");
				// mG.updateTripletListBox();
			} else {
				mG.setStatusLine("error triplet");
			}
		}
	};

	private Action deleteTriplet = new AbstractAction("Delete Triplet") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (!mG.getTripletsList().isSelectionEmpty()) {
				int pos = mG.getTripletsList().getSelectedIndex();
				String msg = "Do you want to delete the triplet "
						+ tS.getTaskTripletList().get(pos)
								.getTaskTripletString() + "?";
				if (mG.getUserConfirmation(msg, "Confirm Triplet Deletion") == 0) {
					TaskTriplet t = tS.deleteTriplet(pos);
					mG.setStatusLine("Deleted triplet (" + t.getPlace() + ", "
							+ t.getOrientation() + ", " + t.getPause() + ")");
				} else {
					mG.setStatusLine("Triplet not deleted.");
				}
			} else {
				mG.setStatusLine("No triplet selected for deletion.");
			}
			// mG.updateTripletListBox();
		}
	};

	private Action sendTriplets = new AbstractAction("Send Triplets") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			// TODO
			mG.setStatusLine("<html><FONT COLOR=RED>not implemented yet! </FONT> send task specification </html>");
		}
	};

	private Action help = new AbstractAction("Help") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			mG.setStatusLine("<html><FONT COLOR=RED>not implemented yet! </FONT> Sorry, no help available </html>");
		}
	};

	public MainController(String[] args) {
		// here is the place to handle parameters from program start ie. a
		// special config file.
		tS = new TaskSpec();
		mG = new MainGUI();
		init();
	}

	private void init() {
		initializeValidTriplets();
		save.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Save16.gif")));
		open.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Open16.gif")));
		exit.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/exit.png")));
		help.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Help16.gif")));
		addTriplet.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Back16.gif")));
		deleteTriplet.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Delete16.gif")));
		sendTriplets.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Export16.gif")));
		mG.connectSaveAction(save);
		mG.connectOpenAction(open);
		mG.connectExitAction(exit);
		mG.connectHelpAction(help);
		mG.connectSendTriplets(sendTriplets);
		mG.connectAddTriplet(addTriplet);
		mG.connectDeleteTriplet(deleteTriplet);
		tS.addTripletListener(mG);
		tS.addTripletListener(mG.getMapArea());
	}

	private void initializeValidTriplets() {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		try {
			if (vte.readFromConfigFile()) {
				mG.setValidPositions(vte.getValidPositions());
				mG.setValidOrientations(vte.getValidOrientations());
				mG.setValidPauses(vte.getValidPauses());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showView() {
		mG.setVisible(true);
	}
}
