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
import model.TaskServer;
import view.MainGUI;

public class MainController {
	private MainGUI mG;
	private TaskSpec tS;
	private TaskServer tServer;

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

	private Action upTriplet = new AbstractAction("Triplet up") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (!mG.getTripletsList().isSelectionEmpty()) {
				int pos = mG.getTripletsList().getSelectedIndex();
				TaskTriplet t = tS.moveUpTriplet(pos);
				if (t != null) {
					mG.getTripletsList().setSelectedIndex(pos - 1);
					mG.setStatusLine("Triplet (" + t.getPlace() + ", "
							+ t.getOrientation() + ", " + t.getPause()
							+ ") moved up.");
				} else
					mG.setStatusLine("Triplet already at the beginning of the list.");
			} else {
				mG.setStatusLine("No triplet selected for moving up.");
			}
		}
	};

	private Action downTriplet = new AbstractAction("Triplet down") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (!mG.getTripletsList().isSelectionEmpty()) {
				int pos = mG.getTripletsList().getSelectedIndex();
				TaskTriplet t = tS.moveDownTriplet(pos);
				if (t != null) {
					mG.getTripletsList().setSelectedIndex(pos + 1);
					mG.setStatusLine("Triplet (" + t.getPlace() + ", "
							+ t.getOrientation() + ", " + t.getPause()
							+ ") moved down.");
				} else
					mG.setStatusLine("Triplet already at the end of the list.");
			} else {
				mG.setStatusLine("No triplet selected for moving down.");
			}
		}
	};

	private Action updateTriplet = new AbstractAction("Update Triplet") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (!mG.getTripletsList().isSelectionEmpty()) {
				int pos = mG.getTripletsList().getSelectedIndex();
				TaskTriplet tt = new TaskTriplet();
				if (tt.setPlace((String) mG.getPlacesBox().getSelectedItem())
						&& tt.setOrientation((String) mG.getOrientationsBox()
								.getSelectedItem())
						&& tt.setPause((String) mG.getPausesBox()
								.getSelectedItem().toString())) {
					TaskTriplet t = tS.editTriplet(pos, tt);
					if (t != null)
						mG.setStatusLine("Updated Triplet (" + t.getPlace()
								+ ", " + t.getOrientation() + ", "
								+ t.getPause() + ").");
					else
						mG.setStatusLine("Triplet could not be updated.");
				}
			} else {
				mG.setStatusLine("No triplet selected for updating.");
			}
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
		}
	};

	private Action sendTriplets = new AbstractAction("Send Triplets") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			tServer.sendTaskSpecToClient(tS);
			mG.setStatusLine("Task specification sent to the team.");
		}
	};

	private Action disconnect = new AbstractAction("Disconnect") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			String teamName = mG.getConnectedLabel();
			tServer.disconnectClient(teamName);
			mG.setStatusLine("Team " + teamName + " disconnected.");
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
		tServer = new TaskServer();
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
		upTriplet.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Up16.gif")));
		downTriplet.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Down16.gif")));
		updateTriplet.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Edit16.gif")));
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
		mG.connectDownTriplet(downTriplet);
		mG.connectUpTriplet(upTriplet);
		mG.connectEditTriplet(updateTriplet);
		mG.connectDeleteTriplet(deleteTriplet);
		mG.connectDisconnet(disconnect);
		tS.addTripletListener(mG);
		tS.addTripletListener(mG.getMapArea());
		tServer.addConnectionListener(mG);
		tServer.listenForConnection();
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
