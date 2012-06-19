package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BmtTask;
import model.BntTask;
import model.BttTask;
import model.CompetitionIdentifier;
import model.ConfigFile;
import model.CttTask;
import model.Logging;
import model.Map;
import model.Task;
import model.TaskServer;
import model.TaskSpec;
import model.TaskTimer;
import model.ValidTaskElements;
import view.BmtPanel;
import view.BntPanel;
import view.BttPanel;
import view.CttPanel;
import view.DialogType;
import view.FileType;
import view.MainGUI;

/**
 * @author BRSU-MAS-ASTT-SoSe2012
 * 
 */
public class MainController implements TimerListener {
	private MainGUI mG;
	private TaskSpec tS;
	private ConfigFile cfgFile;
	private TaskServer tServer;
	private Logging logg;
	private boolean unsavedChanges = false;
	private boolean competitionMode = false;
	private TaskTimer taskTimer;
	private final String unsavedWarningMsg    = "Warning: Unsaved data will be lost. Proceed? ";
	private final String exitNotAllowedMsg    = "System is in Competition Mode. To exit, press Competition Finished button.";
	private final String uncheckedSubgoalsMsg = "One or more subgoals are still unchecked.";
	private final int NUMBEROFCOMPETITIONS = 4;
	protected CompetitionIdentifier compIdent = CompetitionIdentifier.BNT;

	private WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent evt) {
			// redirect to the exit action from file menu
			exit.actionPerformed(null);
		}
	};

	private Action save = new AbstractAction("Save") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			File file = mG.showFolderDialog(FileType.FILETYPE_TSP,
					DialogType.DIALOG_SAVE);
			if (file != null) {
				if (tS.saveTaskSpec(file)
						&& Map.saveTaskSpecMap(file, mG.getMapArea())) {
					mG.setStatusLine("saved actual task specification in >"
							+ file.getName() + "<");
					setSavedChanges();
					logg.setFilePath(file);
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
			if (unsavedChanges && warningIgnored("Open")) {
				return;
			}
			File file = mG.showFolderDialog(FileType.FILETYPE_TSP,
					DialogType.DIALOG_OPEN);
			if (file != null) {

				if (tS.openTaskSpec(file)) {
					mG.setStatusLine("Opened task specification >"
							+ file.getName() + "<");
					setSavedChanges();
					mG.getTabbedPane().setSelectedIndex(-1); // to repaint the
																// map
					logg.setFilePath(file);
				} else {
					mG.setStatusLine("<html><FONT COLOR=RED>Something went wrong!"
							+ "</FONT> No task spec file opened </html>");
				}
			} else {
				mG.setStatusLine("Open command cancelled by user.");
			}
		}
	};

	private Action loadConfig = new AbstractAction("Load Config") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if (unsavedChanges && warningIgnored("Load Config")) {
				return;
			}
			File file = mG.showFolderDialog(FileType.FILETYPE_ALL,
					DialogType.DIALOG_OPEN);
			if (file != null) {
				loadConfigurationFile(file);
				tServer.createServerSocket(cfgFile.getServerIP(),
						cfgFile.getPortaddr());
				tServer.listenForConnection();
			} else {
				mG.setStatusLine("Load Config command cancelled by user.");
			}
		}
	};

	private Action exit = new AbstractAction("Exit") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if (competitionMode) {
				mG.showMessageDialog(exitNotAllowedMsg, "Competition Running");
				return;
			}
			if (unsavedChanges && warningIgnored("Exit")) {
				return;
			}
			System.exit(0);
		}
	};

	private Action up = new AbstractAction("Up") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			if (mG.getCompetitionPanel(compIdent.ordinal()).getSequenceTable()
					.getSelectedRowCount() > 0) {
				int pos = mG.getCompetitionPanel(compIdent.ordinal())
						.getSequenceTable().getSelectedRow();
				Task t = tS.moveUp(pos, compIdent);
				if (t != null) {
					mG.getCompetitionPanel(compIdent.ordinal())
							.getSequenceTable()
							.changeSelection(pos - 1, -1, false, false);
					mG.setStatusLine("Task " + t.getString() + " moved up.");
					unsavedChanges = true;
				} else
					mG.setStatusLine("Task already at the beginning of the list.");
			} else {
				mG.setStatusLine("No task selected for moving up.");
			}
		}
	};

	private Action down = new AbstractAction("Down") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (mG.getCompetitionPanel(compIdent.ordinal()).getSequenceTable()
					.getSelectedRowCount() > 0) {
				int pos = mG.getCompetitionPanel(compIdent.ordinal())
						.getSequenceTable().getSelectedRow();
				Task t = tS.moveDown(pos, compIdent);
				if (t != null) {
					mG.getCompetitionPanel(compIdent.ordinal())
							.getSequenceTable()
							.changeSelection(pos + 1, -1, false, false);
					mG.setStatusLine("Task " + t.getString() + " moved down.");
					unsavedChanges = true;
				} else
					mG.setStatusLine("Task already at the beginning of the list.");
			} else {
				mG.setStatusLine("No task selected for moving down.");
			}
		}
	};

	private Action update = new AbstractAction("Update") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (mG.getCompetitionPanel(compIdent.ordinal()).getSequenceTable()
					.getSelectedRowCount() > 0) {
				int pos = mG.getCompetitionPanel(compIdent.ordinal())
						.getSequenceTable().getSelectedRow();
				Task t = mG.getCompetitionPanel(compIdent.ordinal())
						.getSelectedTask();
				Task tUpdate = tS.updateTask(pos, t, compIdent);
				if (tUpdate != null) {
					mG.setStatusLine("Updated Task " + tUpdate.getString()
							+ " to " + t.getString() + ".");
					setUnsavedChanges();
				} else {
					mG.setStatusLine("Task could not be updated.");
				}
			} else {
				mG.setStatusLine("No task selected for updating.");
			}

		}
	};

	private Action add = new AbstractAction("Add") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			Task t = mG.getCompetitionPanel(compIdent.ordinal())
					.getSelectedTask();
			tS.addTask(compIdent, t);
			mG.setStatusLine("added task " + t.getString() + ".");
			unsavedChanges = true;
		}
	};

	private Action delete = new AbstractAction("Delete") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (mG.getCompetitionPanel(compIdent.ordinal()).getSequenceTable()
					.getSelectedRowCount() > 0) {
				int pos = mG.getCompetitionPanel(compIdent.ordinal())
						.getSequenceTable().getSelectedRow();
				String msg = "Do you want to delete the  task "
						+ mG.getCompetitionPanel(compIdent.ordinal())
								.getSelectedTask().getString() + "?";
				if (mG.getUserConfirmation(msg, "Confirm Task Deletion") == 0) {
					Task t = tS.deleteTask(pos, compIdent);
					mG.setStatusLine("Deleted task " + t.getString() + ".");
					setUnsavedChanges();
				} else {
					mG.setStatusLine("Task not deleted.");
				}
			} else {
				mG.setStatusLine("No task selected for deletion.");
			}
		}
	};

	private Action sendSpec = new AbstractAction("Send Spec") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {

			if (tServer.sendTaskSpecToClient(tS.getTaskSpecString(compIdent)))
				mG.setStatusLine("Task specification sent to the team.");
			else
				mG.setStatusLine("<html><FONT COLOR=RED>Task specification could not be send to the team!</FONT>  </html>");
		}
	};

	private Action disconnect = new AbstractAction("Disconnect") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			String teamName = tServer.getTeamName();
			tServer.disconnectClient();
			tServer.createServerSocket(cfgFile.getServerIP(),
					cfgFile.getPortaddr());
			tServer.listenForConnection();
			mG.setStatusLine("Team " + teamName
					+ " disconnected. Listening for new connection.....");
		}
	};

	private Action help = new AbstractAction("Help") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			mG.setStatusLine("<html><FONT COLOR=RED>not implemented yet! </FONT> Sorry, no help available </html>");
		}
	};

	public ItemListener timerListener = new ItemListener() {
		public void itemStateChanged(ItemEvent evt) {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				long configTime = cfgFile.getConfigurationTime();
				long runTime = cfgFile.getRunTime();
				taskTimer.startNewTimer(configTime, runTime);
				setCompetitionMode(true);
				logg.setCompetitionLogging(true);
			} else {
				taskTimer.stopTimer();
				tServer.disconnectClient();
				mG.setTimerStartStopButtonText("Timer Start");
				mG.getTimerStartStopButton().setEnabled(false);
				mG.getCompetitionFinishedButton().setEnabled(true);
				mG.getSendSpecButton().setEnabled(false);
			}
		}
	};

	public Action competitionFinished = new AbstractAction(
			"Competition Finished") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent evt) {
			if (!tS.checkAllSubgoals(compIdent)) {
				mG.showMessageDialog(uncheckedSubgoalsMsg, "Unchecked subgoals");
				return;
			}
			logg.renameCompetitionFile();
			logg.setCompetitionLogging(false);
			mG.getCompetitionFinishedButton().setEnabled(false);
			setCompetitionMode(false);
			taskTimer.resetTimer();
			mG.getTimerStartStopButton().setEnabled(true);
			tS.resetStates(compIdent);
			tServer.disconnectClient();
			tServer.createServerSocket(cfgFile.getServerIP(),
					cfgFile.getPortaddr());
			tServer.listenForConnection();
			mG.setStatusLine("Competition finished. Listening for next team.");
		}
	};

	public MouseListener taskTableListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int selectedRow = mG.getSequenceTable(compIdent.ordinal())
					.getSelectedRow();
			if (selectedRow >= 0) {
				Task tT = tS.getTaskAtIndex(selectedRow, compIdent);
				mG.setTaskBoxSelected(tT, compIdent);
				mG.setStatusLine("Selected task " + tT.getString() + ".");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			int selectedRow = mG.getSequenceTable(compIdent.ordinal())
					.getSelectedRow();
			int selectedColumn = mG.getSequenceTable(compIdent.ordinal())
					.getSelectedColumn();
			if (selectedColumn > 0) {
				tS.setTaskState(selectedRow, selectedColumn, compIdent);
				mG.setStatusLine("Updated task state of "
						+ tS.getTaskAtIndex(selectedRow, compIdent).getString());
				mG.setTableCellCorrected(selectedRow, selectedColumn, compIdent);
				mG.getSequenceTable(compIdent.ordinal()).clearSelection();
			}

		}
	};

	public ChangeListener tabbChangeListener = new ChangeListener() {
		public void stateChanged(ChangeEvent evt) {
			JTabbedPane pane = (JTabbedPane) evt.getSource();
			if (competitionMode || pane.getSelectedIndex() == -1) // -1 to
																	// repaint
																	// the map
				mG.getTabbedPane().setSelectedIndex(compIdent.ordinal());
			else {
				compIdent = CompetitionIdentifier.values()[pane
						.getSelectedIndex()];
				if (compIdent == CompetitionIdentifier.BNT) {
					tS.notifyBntTaskSpecChanged(new BntTask(), 0,
							tS.getBntTaskList());
					return;
				}
				if (compIdent == CompetitionIdentifier.BMT) {
					tS.notifyBmtTaskSpecChanged(new BmtTask(), 0,
							tS.getBmtTaskList());
					return;
				}
				if (compIdent == CompetitionIdentifier.BTT) {
					tS.notifyBttTaskSpecChanged(new BttTask(), 0,
							tS.getBttTaskList());
					return;
				}
				if (compIdent == CompetitionIdentifier.CTT) {
					tS.notifyCttTaskSpecChanged(new CttTask(), 0,
							tS.getCttTaskList());
					return;
				}
			}
		}
	};

	public MainController(String[] args) {
		tS = new TaskSpec(compIdent.ordinal());
		mG = new MainGUI(NUMBEROFCOMPETITIONS);
		cfgFile = new ConfigFile();
		tServer = new TaskServer();
		taskTimer = new TaskTimer();
		unsavedChanges = false;
		competitionMode = false;
		logg = Logging.getInstance();
		// compLogging = new CompetitionLogging();
		init();
		if (args.length > 0) {
			File file = new File(System.getProperty("user.home")
					+ System.getProperty("file.separator") + args[0]);
			loadConfigurationFile(file);
			tServer.createServerSocket(cfgFile.getServerIP(),
					cfgFile.getPortaddr());
			tServer.listenForConnection();
		}
	}

	private void init() {
		save.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Save16.gif")));
		open.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Open16.gif")));
		loadConfig.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Import16.gif")));
		exit.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/exit.png")));
		help.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Help16.gif")));
		up.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Up16.gif")));
		down.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Down16.gif")));
		update.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Edit16.gif")));
		add.putValue(
				Action.SMALL_ICON,
				new ImageIcon(getClass().getResource(
						"/view/resources/icons/Back16.gif")));
		delete.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Delete16.gif")));
		sendSpec.putValue(Action.SMALL_ICON, new ImageIcon(getClass()
				.getResource("/view/resources/icons/Export16.gif")));
		mG.connectSaveAction(save);
		mG.connectOpenAction(open);
		mG.connectLoadConfigAction(loadConfig);
		mG.connectExitAction(exit);
		mG.connectHelpAction(help);
		mG.connectSendSpecAction(sendSpec);
		mG.connectAdd(add);
		mG.connectDown(down);
		mG.connectUp(up);
		mG.connectUpdate(update);
		mG.connectDelete(delete);
		mG.connectDisconnet(disconnect);
		mG.connectCompetitionFinished(competitionFinished);
		mG.addTabbChangedListener(tabbChangeListener);
		tS.addTaskListener(mG);
		tS.addTaskListener(mG.getMapArea());
		tServer.addConnectionListener(mG);
		mG.addWindowListener(windowAdapter);
		mG.addtaskTableListener(taskTableListener);
		mG.addTimerListener(timerListener);
		mG.setButtonDimension();
		taskTimer.addTimerListener(mG);
		taskTimer.addTimerListener(this);
		tServer.addConnectionListener(taskTimer);
		mG.pack();
	}

	private void populateValidItems() {
		ValidTaskElements vte = ValidTaskElements.getInstance();
		try {
			if (vte.readFromConfigFile(cfgFile)) {
				mG.getMapArea().setValidPositions(vte.getValidBNTPositions());

				((BntPanel) mG.getCompetitionPanel(CompetitionIdentifier.BNT
						.ordinal())).setValidPositions(vte
						.getValidBNTPositions());
				((BntPanel) mG.getCompetitionPanel(CompetitionIdentifier.BNT
						.ordinal())).setValidOrientations(vte
						.getValidBNTOrientations());
				((BntPanel) mG.getCompetitionPanel(CompetitionIdentifier.BNT
						.ordinal())).setValidPauses(vte.getValidBNTPauses());

				((BmtPanel) mG.getCompetitionPanel(CompetitionIdentifier.BMT
						.ordinal())).setValidPositions(vte
						.getValidBMTPositions());
				((BmtPanel) mG.getCompetitionPanel(CompetitionIdentifier.BMT
						.ordinal())).setValidConfigurations(vte
						.getValidBMTConfigurations());
				((BmtPanel) mG.getCompetitionPanel(CompetitionIdentifier.BMT
						.ordinal())).setValidObjects(vte.getValidBMTObjects());

				((BttPanel) mG.getCompetitionPanel(CompetitionIdentifier.BTT
						.ordinal())).setValidPositions(vte
						.getValidBTTPositions());
				((BttPanel) mG.getCompetitionPanel(CompetitionIdentifier.BTT
						.ordinal())).setValidConfigurations(vte
						.getValidBTTConfigurations());
				((BttPanel) mG.getCompetitionPanel(CompetitionIdentifier.BTT
						.ordinal())).setValidObjects(vte.getValidBTTObjects());
				((BttPanel) mG.getCompetitionPanel(CompetitionIdentifier.BTT
						.ordinal())).setValidSituations(vte
						.getValidBTTSituations());
				((CttPanel) mG.getCompetitionPanel(CompetitionIdentifier.CTT
						.ordinal())).setValidPositions(vte
						.getValidCTTPositions());
				((CttPanel) mG.getCompetitionPanel(CompetitionIdentifier.CTT
						.ordinal())).setValidConfigurations(vte
						.getValidCTTConfigurations());
				((CttPanel) mG.getCompetitionPanel(CompetitionIdentifier.CTT
						.ordinal())).setValidObjects(vte.getValidCTTObjects());
				((CttPanel) mG.getCompetitionPanel(CompetitionIdentifier.CTT
						.ordinal())).setValidSituations(vte
						.getValidCTTSituations());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean initializeBackgroundMap(String pathPrefix) {
		BufferedImage map = Map.loadBackgroundMap(cfgFile, pathPrefix);
		if (map != null) {
			mG.getMapArea().setBackgroundMap(map);
			return true;
		}
		return false;
	}

	public void showView() {
		mG.setVisible(true);
	}

	private boolean warningIgnored(String actionName) {
		return (mG.getUserConfirmation(unsavedWarningMsg, "Confirm "
				+ actionName) != 0);
	}

	private void setUnsavedChanges() {
		unsavedChanges = true;
		mG.getTimerStartStopButton().setEnabled(false);
	}

	private void setSavedChanges() {
		unsavedChanges = false;
		mG.getTimerStartStopButton().setEnabled(true);
	}

	private void setCompetitionMode(boolean mode) {
		competitionMode = mode;
		mG.setCompetitionMode(mode, compIdent);
	}

	private void loadConfigurationFile(File file) {
		if (cfgFile.setConfigFile(file)) {
			try {
				cfgFile.loadProperties();
			} catch (Exception e) {
				System.out
						.println("Exception while loading config properties: "
								+ e);
			}
			populateValidItems();
			if (initializeBackgroundMap(file.getParent())) {
				mG.pack();
				mG.configFileLoaded();
				mG.setStatusLine("Loaded configuration file >" + file.getName()
						+ "<");
			} else {
				mG.setStatusLine("<html><FONT COLOR=RED>Something went wrong!"
						+ "</FONT> No background file loaded. </html>");
			}
		} else {
			mG.setStatusLine("<html><FONT COLOR=RED>Something went wrong!"
					+ "</FONT> No config file loaded. </html>");
		}
	}

	@Override
	public void timerTick(String currentTime, boolean inTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timerReset(String resetTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timerSetMaximumTime(String maxTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void timerOverrun() {
		mG.setTimerStartStopButtonText("Timer Start");
		mG.getTimerStartStopButton().setSelected(false);
		mG.getCompetitionFinishedButton().setEnabled(true);
		tServer.disconnectClient();
		mG.setStatusLine("Timeout. Team disconnected");
	}

}
