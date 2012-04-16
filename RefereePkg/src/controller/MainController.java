package controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import view.MainGUI;
import view.Utils;

import model.TaskSpec;
import model.TaskTriplet;
import model.ValidTripletElements;

public class MainController {
	private MainGUI mG;
	private TaskSpec tS;

	private Action save = new AbstractAction("Save", new ImageIcon("src/view/recources/icons/save.png")) {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			File file = mG.showSaveDialog();

			if (file != null) {
				file = Utils.correctFile(file);
				try {
					FileWriter fstream = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(tS.getTaskSpecString());
					out.close();
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
				mG.setStatusLine("saved actual task specification in >"
						+ file.getName() + "<");
			} else {
				mG.setStatusLine("save command cancelled by user");
			}
		}
	};

	private Action open = new AbstractAction("Open", new ImageIcon(
			"view/recources/icons/open.png")) {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			File file = mG.showOpenDialog();

			if (file != null) {
				try {
					FileInputStream fstream = new FileInputStream(file);
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					@SuppressWarnings("unused")
					String strLine;
					while ((strLine = br.readLine()) != null) {
						// TODO
					}
					in.close();
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
				// statusLine.setText("opened task specification >" +
				// file.getName()
				// + "<");
				mG.setStatusLine("<html><FONT COLOR=RED>not implemented yet! </FONT> opened task specification </html>");
			} else {
				mG.setStatusLine("open command cancelled by user");
			}
		}
	};

	private Action close = new AbstractAction("Close") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Action close");
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
			    String msg = "Do you want to delete the triplet" + tS.getTaskTripletList().get(pos).getTaskTripletString() + "?";
			    if(mG.getUserConfirmation(msg, "Confirm Triplet Deletion") == 0) {
			        TaskTriplet t = tS.deleteTriplet(pos);
			        mG.setStatusLine("Deleted triplet (" + t.getPlace() + ", "
						+ t.getOrientation() + ", " + t.getPause() + ")");
			    }
			    else {
                    mG.setStatusLine("Triplet not deleted.");
			    }
			}
			else {
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

	public MainController(String[] args) {
		// here is the place to handle parameters from program start ie. a
		// special config file.
		tS = new TaskSpec();
		mG = new MainGUI();
		init();
	}

	private void init() {
		initializeValidTriplets();
		mG.connectSaveAction(save);
		mG.connectOpenAction(open);
		mG.connectCloseAction(close);
		mG.connectSendTriplets(sendTriplets);
		mG.connectAddTriplet(addTriplet);
		mG.connectDeleteTriplet(deleteTriplet);
		tS.addTripletListener(mG);
	}

	private void initializeValidTriplets() {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		try {
			if (vte.readFromConfigFile()) {
				mG.setValidPlaces(vte.getValidPlaces());
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
