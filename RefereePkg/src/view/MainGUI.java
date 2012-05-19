package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.TaskTriplet;
import model.TripletEvent;
import controller.ConnectionListener;
import controller.TripletListener;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * 
 */
public class MainGUI extends JFrame implements TripletListener,
		ConnectionListener {
	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private JList<String> tripletsList;
	private JScrollPane tripletListScrollPane;
	private JPanel editTripletPane;
	private JPanel statusPanel;
	private JPanel westPanel;
	private JButton saveButton;
	private JButton openButton;
	private JButton loadConfigButton;
	private JLabel statusLine;
	private JButton deleteTripletButton;
	private JButton addTripletButton;
	private JButton sendTripletsButton;
	private JPanel boxPanel;
	private JComboBox<String> orientationsBox;
	private JComboBox<Short> pausesBox;
	private JComboBox<String> placesBox;
	private JToolBar toolBar;
	private JPanel toolBarPanel;
	private JPanel contentPanel;
	private MapArea mapArea;
	private JMenuItem helpMenuItem;
	private JMenu helpMenu;
	private JMenuItem deleteMenuItem;
	private JMenuItem addMenuItem;
	private JMenu editMenu;
	private JMenuItem exitMenuItem;
	private JMenuItem saveMenuItem;
	private JLabel connectedIcon;
	private JButton disconnectButton;
	private JPanel upperServerPanel;
	private JPanel lowerServerPanel;
	private JLabel connectedLabel;
	private JPanel serverPanel;
	private JMenuItem openFileMenuItem;
	private JMenu fileMenu;
	private JMenuBar menuBar;
	private DefaultComboBoxModel<String> placesCbm;
	private DefaultComboBoxModel<String> orientationsCbm;
	private DefaultComboBoxModel<Short> pausesCbm;
	private DefaultListModel<String> tripletLm = new DefaultListModel<String>();
	private JButton updateTripletButton;
	private JButton downTripletButton;
	private JButton upTripletButton;
	private JMenuItem upMenuItem;
	private JMenuItem downMenuItem;
	private JMenuItem updateMenuItem;
	private JMenuItem loadConfigMenuItem;

	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initGUI();
	}

	private void initGUI() {

		this.setTitle("RoboCup@Work");
		BorderLayout panelLayout = new BorderLayout();
		this.setLayout(panelLayout);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		{
			contentPanel = new JPanel();
			BorderLayout contentPanelLayout = new BorderLayout();
			contentPanel.setLayout(contentPanelLayout);
			{
				westPanel = new JPanel();
				BorderLayout westPanelLayout = new BorderLayout();
				westPanel.setLayout(westPanelLayout);
				{
					tripletListScrollPane = new JScrollPane(
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					{
						tripletsList = new JList<String>(tripletLm);
						// align triplets in the list
						DefaultListCellRenderer renderer = (DefaultListCellRenderer) tripletsList
								.getCellRenderer();
						renderer.setHorizontalAlignment(JLabel.CENTER);
					}
					// get the width of one triplet string depending on font
					FontMetrics metrics = tripletsList
							.getFontMetrics(tripletsList.getFont());
					int width = metrics.stringWidth(" (O5, W, 5) ");
					tripletListScrollPane.setViewportView(tripletsList);
					// add some space for the scrollbar
					Dimension dim = new Dimension(width + 2 * GAP, 0);
					tripletListScrollPane.setPreferredSize(dim);
				}
				westPanel.add(tripletListScrollPane, BorderLayout.WEST);
				{
					editTripletPane = new JPanel();
					BoxLayout boxLayout = new BoxLayout(editTripletPane,
							BoxLayout.Y_AXIS);
					editTripletPane.setLayout(boxLayout);
					editTripletPane.add(Box.createVerticalStrut(GAP));
					{
						JLabel header = new JLabel("Place  Orientation  Time");
						header.setHorizontalAlignment(JLabel.CENTER);
						header.setHorizontalTextPosition(SwingConstants.CENTER);
						header.setIconTextGap(0);
						header.setAlignmentX(CENTER_ALIGNMENT);
						editTripletPane.add(header);

					}
					{
						boxPanel = new JPanel();
						boxPanel.setRequestFocusEnabled(false);
						{
							placesBox = new JComboBox<String>();
							boxPanel.add(placesBox);
						}
						{
							orientationsBox = new JComboBox<String>();
							boxPanel.add(orientationsBox);
						}
						{
							pausesBox = new JComboBox<Short>();
							boxPanel.add(pausesBox);
						}
					}
					editTripletPane.add(boxPanel);
					editTripletPane.add(Box.createVerticalGlue());
					{
						addTripletButton = new JButton();
						addTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						updateTripletButton = new JButton();
						updateTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						deleteTripletButton = new JButton();
						deleteTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						upTripletButton = new JButton();
						upTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						upTripletButton.setFocusable(false);
						downTripletButton = new JButton();
						downTripletButton.setAlignmentX(CENTER_ALIGNMENT);
					}
					editTripletPane.add(addTripletButton);
					editTripletPane.add(Box.createVerticalStrut(GAP));
					editTripletPane.add(updateTripletButton);
					editTripletPane.add(Box.createVerticalStrut(GAP));
					editTripletPane.add(deleteTripletButton);
					editTripletPane.add(Box.createVerticalStrut(GAP));
					editTripletPane.add(Box.createVerticalGlue());
					editTripletPane.add(upTripletButton);
					editTripletPane.add(Box.createVerticalStrut(GAP));
					editTripletPane.add(downTripletButton);
					editTripletPane.add(Box.createVerticalGlue());
				}
				westPanel.add(editTripletPane, BorderLayout.CENTER);
				{
					serverPanel = new JPanel();
					BoxLayout serverPanelLayout = new BoxLayout(serverPanel,
							javax.swing.BoxLayout.PAGE_AXIS);
					serverPanel.setLayout(serverPanelLayout);
					serverPanel.add(Box.createVerticalStrut(GAP));
					serverPanel.add(new JSeparator());
					serverPanel.add(Box.createVerticalStrut(GAP));
					{
						upperServerPanel = new JPanel();
						BoxLayout serverUpperPanelLayout = new BoxLayout(
								upperServerPanel,
								javax.swing.BoxLayout.LINE_AXIS);
						upperServerPanel.setLayout(serverUpperPanelLayout);
						{
							connectedIcon = new JLabel(
									new ImageIcon(
											getClass()
													.getResource(
															"/view/resources/icons/status-busy.png")));
						}
						upperServerPanel.add(connectedIcon);
						{
							connectedLabel = new JLabel();
							connectedLabel.setText("no team connected");
						}
						upperServerPanel.add(connectedLabel);
					}
					serverPanel.add(upperServerPanel);
					serverPanel.add(Box.createVerticalStrut(GAP));
					{
						lowerServerPanel = new JPanel();
						BoxLayout serverLowerPanelLayout = new BoxLayout(
								lowerServerPanel,
								javax.swing.BoxLayout.LINE_AXIS);
						lowerServerPanel.setLayout(serverLowerPanelLayout);
						{
							disconnectButton = new JButton();
							disconnectButton.setEnabled(false);
							disconnectButton
									.setHorizontalAlignment(SwingConstants.LEFT);
						}
						lowerServerPanel.add(disconnectButton);
						lowerServerPanel.add(Box.createHorizontalStrut(GAP));
						{
							sendTripletsButton = new JButton();
							sendTripletsButton.setEnabled(false);
							sendTripletsButton
									.setHorizontalAlignment(SwingConstants.RIGHT);
						}
						lowerServerPanel.add(sendTripletsButton);
					}
					serverPanel.add(lowerServerPanel);
				}
				westPanel.add(serverPanel, BorderLayout.SOUTH);
			}
			contentPanel.add(westPanel, BorderLayout.WEST);
			{
				mapArea = new MapArea();
				mapArea.setBackground(Color.white);
			}
			contentPanel.add(mapArea, BorderLayout.CENTER);
			{
				statusPanel = new JPanel();
				{
					statusLine = new JLabel();
					statusLine.setName("statusLine");
					statusLine.setHorizontalAlignment(JLabel.CENTER);
					// to avoid having an invisible status panel if empty
					FontMetrics metrics = statusLine.getFontMetrics(statusLine
							.getFont());
					int hight = metrics.getHeight();
					// width is determined by the parent container
					Dimension size = new Dimension(0, hight + GAP);
					statusPanel.setPreferredSize(size);
					statusPanel.add(statusLine);
				}
			}
			contentPanel.add(statusPanel, BorderLayout.SOUTH);
			{
				toolBarPanel = new JPanel();
				BorderLayout toolBarLayout = new BorderLayout();
				toolBarPanel.setLayout(toolBarLayout);
				{
					toolBar = new JToolBar();
					{
						openButton = new JButton();
						toolBar.add(openButton);
						openButton.setName("openButton");
					}
					{
						saveButton = new JButton();
						toolBar.add(saveButton);
						saveButton.setFocusable(false);
					}
					{
						loadConfigButton = new JButton();
						toolBar.add(loadConfigButton);
					}
				}
				toolBarPanel.add(toolBar, BorderLayout.CENTER);
			}
			contentPanel.add(toolBarPanel, BorderLayout.NORTH);
		}
		this.add(contentPanel, BorderLayout.CENTER);
		{
			menuBar = new JMenuBar();
			{
				fileMenu = new JMenu();
				fileMenu.setText("File");
				{
					openFileMenuItem = new JMenuItem();
				}
				fileMenu.add(openFileMenuItem);
				{
					saveMenuItem = new JMenuItem();
				}
				fileMenu.add(saveMenuItem);
				fileMenu.add(new JSeparator());
				{
					loadConfigMenuItem = new JMenuItem();
				}
				fileMenu.add(loadConfigMenuItem);
				fileMenu.add(new JSeparator());
				{
					exitMenuItem = new JMenuItem();
				}
				fileMenu.add(exitMenuItem);
			}
			menuBar.add(fileMenu);
			{
				editMenu = new JMenu();
				menuBar.add(editMenu);
				editMenu.setText("Edit");
				{
					addMenuItem = new JMenuItem();
					deleteMenuItem = new JMenuItem();
					upMenuItem = new JMenuItem();
					downMenuItem = new JMenuItem();
					updateMenuItem = new JMenuItem();
				}
				editMenu.add(addMenuItem);
				editMenu.add(updateMenuItem);
				editMenu.add(deleteMenuItem);
				editMenu.add(new JSeparator());
				editMenu.add(upMenuItem);
				editMenu.add(downMenuItem);
			}
			{
				helpMenu = new JMenu();
				menuBar.add(helpMenu);
				helpMenu.setText("Help");
				{
					helpMenuItem = new JMenuItem();
					helpMenuItem.setText("Help");
				}
				helpMenu.add(helpMenuItem);
			}
			this.setJMenuBar(menuBar);
		}
		this.pack();
	}

	public MapArea getMapArea() {
		return mapArea;
	}

	public void setValidPositions(LinkedHashMap<String, Point> positions) {
		String[] posString = new String[positions.size()];
		int i = 0;
		for (String pos : positions.keySet()) {
			posString[i] = pos;
			i++;
		}
		placesCbm = new DefaultComboBoxModel<String>(posString);
		placesBox.setModel(placesCbm);
		mapArea.setValidPositions(positions);
	}

	public void setValidOrientations(List<String> orientations) {
		orientationsCbm = new DefaultComboBoxModel<String>(
				orientations.toArray(new String[orientations.size()]));
		orientationsBox.setModel(orientationsCbm);
	}

	public void setValidPauses(List<Short> pauses) {
		pausesCbm = new DefaultComboBoxModel<Short>(
				pauses.toArray(new Short[pauses.size()]));
		pausesBox.setModel(pausesCbm);
	}

	public void connectLoadConfigAction(Action loadConfig) {
		loadConfigButton.setAction(loadConfig);
		loadConfigMenuItem.setAction(loadConfig);
	}

	public void connectSaveAction(Action save) {
		saveButton.setAction(save);
		saveMenuItem.setAction(save);
		saveButton.setEnabled(false);
		saveMenuItem.setEnabled(false);
	}

	public void connectOpenAction(Action open) {
		openButton.setAction(open);
		openFileMenuItem.setAction(open);
		openButton.setEnabled(false);
		openFileMenuItem.setEnabled(false);
	}

	public void connectExitAction(Action exit) {
		exitMenuItem.setAction(exit);
	}

	public void connectSendTriplets(Action sendTriplets) {
		sendTripletsButton.setAction(sendTriplets);
		sendTripletsButton.setEnabled(false);
	}

	public void connectDisconnet(Action disconnect) {
		disconnectButton.setAction(disconnect);
		disconnectButton.setEnabled(false);
	}

	public void connectHelpAction(Action help) {
		helpMenuItem.setAction(help);
	}

	public void connectUpTriplet(Action upTriplet) {
		upTripletButton.setAction(upTriplet);
		upMenuItem.setAction(upTriplet);
	}

	public void connectDownTriplet(Action downTriplet) {
		downTripletButton.setAction(downTriplet);
		downMenuItem.setAction(downTriplet);
	}

	public void connectEditTriplet(Action editTriplet) {
		updateTripletButton.setAction(editTriplet);
		updateMenuItem.setAction(editTriplet);
	}

	public void connectAddTriplet(Action addTriplet) {
		addTripletButton.setAction(addTriplet);
		addMenuItem.setAction(addTriplet);
	}

	public void connectDeleteTriplet(Action deleteTriplet) {
		deleteTripletButton.setAction(deleteTriplet);
		deleteMenuItem.setAction(deleteTriplet);
	}

	public File showSaveDialog(FileType ftype) {
		JFileChooser fc = new JFileChooser();
		if (ftype == FileType.FILETYPE_TSP)
			fc.setFileFilter(new TspFilter());
		if (fc.showSaveDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}

	public File showOpenDialog(FileType ftype) {
		JFileChooser fc = new JFileChooser();
		if (ftype == FileType.FILETYPE_TSP)
			fc.setFileFilter(new TspFilter());
		if (fc.showOpenDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}

	public void setStatusLine(String status) {
		statusLine.setText(status);
	}

	public int getUserConfirmation(String msg, String title) {
		return JOptionPane.showConfirmDialog(this, msg, title,
				JOptionPane.YES_NO_OPTION);
	}

	public JComboBox<String> getOrientationsBox() {
		return orientationsBox;
	}

	public JComboBox<Short> getPausesBox() {
		return pausesBox;
	}

	public JComboBox<String> getPlacesBox() {
		return placesBox;
	}

	public JList<String> getTripletsList() {
		return tripletsList;
	}

	public String getConnectedLabel() {
		return connectedLabel.getText();
	}

	@Override
	public void tripletAdded(TripletEvent evt) {
		tripletLm.removeAllElements();
		for (TaskTriplet tT : evt.getTaskTripletList()) {
			tripletLm.addElement(tT.getTaskTripletString());
		}
	}

	@Override
	public void tripletDeleted(TripletEvent evt) {
		tripletLm.removeAllElements();
		for (TaskTriplet tT : evt.getTaskTripletList()) {
			tripletLm.addElement(tT.getTaskTripletString());
		}
	}

	@Override
	public void taskSpecFileOpened(TripletEvent evt) {
		tripletLm.removeAllElements();
		for (TaskTriplet tT : evt.getTaskTripletList()) {
			tripletLm.addElement(tT.getTaskTripletString());
		}
	}

	@Override
	public void teamConnected(String teamName) {
		sendTripletsButton.setEnabled(true);
		disconnectButton.setEnabled(true);
		connectedIcon.setIcon(new ImageIcon(getClass().getResource(
				"/view/resources/icons/status.png")));
		// connectedLabel.setPreferredSize(new Dimension(150, 25));
		connectedLabel.setText(teamName);
	}

	@Override
	public void teamDisconnected() {
		sendTripletsButton.setEnabled(false);
		disconnectButton.setEnabled(false);
		connectedIcon.setIcon(new ImageIcon(getClass().getResource(
				"/view/resources/icons/status-busy.png")));
		connectedLabel.setText("no client connected");
	}

	@Override
	public void taskSpecSent() {
		sendTripletsButton.setEnabled(false);
		disconnectButton.setEnabled(false);
		connectedIcon.setIcon(new ImageIcon(getClass().getResource(
				"/view/resources/icons/status-busy.png")));
		connectedLabel.setText("no client connected");
	}

	public void configFileLoaded() {
		openButton.setEnabled(true);
		openFileMenuItem.setEnabled(true);
		saveButton.setEnabled(true);
		saveMenuItem.setEnabled(true);
		loadConfigButton.setEnabled(false);
	}

	public void setPlacesBoxSelected(String place) {
		placesBox.setSelectedItem(place);
	}

	public void setOrientationsBoxSelected(String orientation) {
		placesBox.setSelectedItem(orientation);
	}

	public void setPausesBoxSelected(Short pause) {
		placesBox.setSelectedItem(pause);
	}
}
