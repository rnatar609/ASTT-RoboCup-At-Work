package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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
import controller.TripletListener;
import controller.ConnectionListener;

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
	private static final Dimension buttonDimension = new Dimension(120, 25);
	private JList<String> tripletsList;
	private JScrollPane tripletListScrollPane;
	private JPanel editTripletPane;
	private JPanel statusPanel;
	private JPanel westPanel;
	private JSeparator jSeparator;
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
	//private JFileChooser fc;
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

	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initGUI();
	}

	private void initGUI() {

		this.setTitle("RoboCup@Work");
		BorderLayout panelLayout = new BorderLayout();
		this.setLayout(panelLayout);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		{
			contentPanel = new JPanel();
			BorderLayout contentPanelLayout = new BorderLayout();
			contentPanel.setLayout(contentPanelLayout);

			this.add(contentPanel, BorderLayout.CENTER);
			{
				westPanel = new JPanel();
				BorderLayout westPanelLayout = new BorderLayout();
				westPanel.setLayout(westPanelLayout);
				contentPanel.add(westPanel, BorderLayout.WEST);
				{
					tripletListScrollPane = new JScrollPane(
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					westPanel.add(tripletListScrollPane, BorderLayout.WEST);
					tripletListScrollPane
							.setPreferredSize(new java.awt.Dimension(120, 0));
					{
						tripletsList = new JList<String>(tripletLm);
						tripletListScrollPane.setViewportView(tripletsList);
					}
				}
			}
			{
				statusPanel = new JPanel();
				contentPanel.add(statusPanel, BorderLayout.SOUTH);
				{
					statusLine = new JLabel();
					statusPanel.add(statusLine);
					statusLine.setName("statusLine");
					statusLine.setHorizontalAlignment(JLabel.CENTER);
					statusPanel.setPreferredSize(new java.awt.Dimension(0, 25));
				}
			}
			{
				editTripletPane = new JPanel();
				BoxLayout jPanel4Layout = new BoxLayout(editTripletPane,
						javax.swing.BoxLayout.Y_AXIS);
				editTripletPane.setLayout(jPanel4Layout);
				editTripletPane
						.setPreferredSize(new java.awt.Dimension(150, 0));
				westPanel.add(editTripletPane, BorderLayout.CENTER);
				{
					boxPanel = new JPanel();
					JLabel header = new JLabel("Place  Orientation  Time");
					header.setMinimumSize(new java.awt.Dimension(400, 40));
					header.setHorizontalAlignment(JLabel.CENTER);
					editTripletPane.add(Box.createVerticalStrut(20));
					editTripletPane.add(header);
					header.setHorizontalTextPosition(SwingConstants.CENTER);
					header.setIconTextGap(0);
					header.setAlignmentX(CENTER_ALIGNMENT);
					editTripletPane.add(boxPanel);
					boxPanel.setPreferredSize(new java.awt.Dimension(200, 39));
					boxPanel.setMinimumSize(new java.awt.Dimension(120, 40));
					boxPanel.setMaximumSize(new java.awt.Dimension(200, 40));
					boxPanel.setRequestFocusEnabled(false);
					boxPanel.setSize(200, 40);
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
					{
						//editTripletPane.add(Box.createVerticalGlue());
						editTripletPane.add(Box.createVerticalStrut(10));
						addTripletButton = new JButton();
						addTripletButton.setPreferredSize(buttonDimension);
						editTripletPane.add(addTripletButton);
						addTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						addTripletButton.setMaximumSize(buttonDimension);
						addTripletButton.setMinimumSize(buttonDimension);
						editTripletPane.add(Box.createVerticalStrut(10));
						updateTripletButton = new JButton();
						updateTripletButton.setPreferredSize(buttonDimension);
						updateTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						updateTripletButton.setMaximumSize(buttonDimension);
						updateTripletButton.setMinimumSize(buttonDimension);
						editTripletPane.add(updateTripletButton);
						editTripletPane.add(Box.createVerticalStrut(10));
						deleteTripletButton = new JButton();
						deleteTripletButton.setPreferredSize(buttonDimension);
						editTripletPane.add(deleteTripletButton);
						deleteTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						deleteTripletButton.setMinimumSize(buttonDimension);
						deleteTripletButton.setMaximumSize(buttonDimension);
						editTripletPane.add(Box.createVerticalStrut(40));
						upTripletButton = new JButton();
						upTripletButton.setPreferredSize(buttonDimension);
						upTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						upTripletButton.setMaximumSize(buttonDimension);
						upTripletButton.setMinimumSize(buttonDimension);
						upTripletButton.setFocusable(false);
						editTripletPane.add(upTripletButton);
						editTripletPane.add(Box.createVerticalStrut(10));
						downTripletButton = new JButton();
						downTripletButton.setPreferredSize(buttonDimension);
						downTripletButton.setAlignmentX(CENTER_ALIGNMENT);
						downTripletButton.setMaximumSize(buttonDimension);
						downTripletButton.setMinimumSize(buttonDimension);
						downTripletButton.setFocusable(false);
						editTripletPane.add(downTripletButton);
						editTripletPane.add(Box.createVerticalStrut(10));
					}
					{
						mapArea = new MapArea();
						mapArea.setBackground(Color.black);
						contentPanel.add(mapArea, BorderLayout.CENTER);
					}

				}
				{
					toolBarPanel = new JPanel();
					contentPanel.add(toolBarPanel, BorderLayout.NORTH);
					BorderLayout jPanel1Layout = new BorderLayout();
					toolBarPanel.setLayout(jPanel1Layout);
					{
						toolBar = new JToolBar();
						toolBarPanel.add(toolBar, BorderLayout.CENTER);
						{
							openButton = new JButton("open...");
							toolBar.add(openButton);
							openButton.setName("openButton");
							openButton.setFocusable(false);
						}
						{
							saveButton = new JButton();
							toolBar.add(saveButton);
							saveButton.setFocusable(false);
						}
						{
							loadConfigButton = new JButton();
							toolBar.add(loadConfigButton);
							loadConfigButton.setName("loadConfigButton");
							loadConfigButton.setText("Load Config");
							loadConfigButton.setFocusable(false);
						}
					}
					{
						jSeparator = new JSeparator();
						toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
					}
				}
			}
			{
				serverPanel = new JPanel();
				BoxLayout serverPanelLayout = new BoxLayout(serverPanel,
						javax.swing.BoxLayout.PAGE_AXIS);
				serverPanel.setLayout(serverPanelLayout);
				westPanel.add(serverPanel, BorderLayout.SOUTH);
				serverPanel.setPreferredSize(new java.awt.Dimension(300, 89));
				{
					serverPanel.add(Box.createVerticalStrut(10));
					serverPanel.add(new JSeparator());
					serverPanel.add(Box.createVerticalStrut(5));
					{
						// serverHeader = new JLabel();
						// serverPanel.add(serverHeader);
						// serverHeader.setText("Server Stuff");
						// serverHeader.setAlignmentX(CENTER_ALIGNMENT);
					}
					upperServerPanel = new JPanel();
					upperServerPanel.setPreferredSize(new java.awt.Dimension(
							300, 57));
					BoxLayout serverUpperPanelLayout = new BoxLayout(
							upperServerPanel, javax.swing.BoxLayout.LINE_AXIS);
					upperServerPanel.setLayout(serverUpperPanelLayout);
					serverPanel.add(upperServerPanel);
					{

						connectedIcon = new JLabel(
								new ImageIcon(
										getClass()
												.getResource(
														"/view/resources/icons/status-busy.png")));
						// should be overwritten by
						// "/view/resources/icons/status-busy.png", if client
						// connected
						upperServerPanel.add(connectedIcon);
					}
					{
						connectedLabel = new JLabel();
						upperServerPanel.add(connectedLabel);
						connectedLabel.setText("no team connected");
						// should be overwritten by connected client
						connectedLabel.setPreferredSize(new Dimension(150, 25));
					}
					lowerServerPanel = new JPanel();
					lowerServerPanel.setPreferredSize(new java.awt.Dimension(
							300, 57));
					BoxLayout serverLowerPanelLayout = new BoxLayout(
							lowerServerPanel, javax.swing.BoxLayout.LINE_AXIS);
					lowerServerPanel.setLayout(serverLowerPanelLayout);
					serverPanel.add(lowerServerPanel);
					{
						disconnectButton = new JButton();
						lowerServerPanel.add(disconnectButton);
						disconnectButton.setEnabled(false);
						disconnectButton
								.setHorizontalAlignment(SwingConstants.LEFT);
						disconnectButton.setPreferredSize(buttonDimension);
						disconnectButton.setMaximumSize(buttonDimension);
					}
					{
						sendTripletsButton = new JButton();
						lowerServerPanel.add(sendTripletsButton);
						// sendTripletsButton.setAlignmentX(CENTER_ALIGNMENT);
						sendTripletsButton.setEnabled(false);
						sendTripletsButton
								.setHorizontalAlignment(SwingConstants.RIGHT);
						sendTripletsButton.setPreferredSize(buttonDimension);
						sendTripletsButton.setMaximumSize(buttonDimension);
					}
				}
			}
			{
				// tripletsHeader = new JLabel();
				westPanel.setAlignmentY(CENTER_ALIGNMENT);
				westPanel.setAlignmentX(CENTER_ALIGNMENT);
				// westPanel.add(tripletsHeader, BorderLayout.NORTH);
				// tripletsHeader.setText("Triplets Stuff");
				// tripletsHeader.setAlignmentX(CENTER_ALIGNMENT);
				// tripletsHeader.setPreferredSize(new java.awt.Dimension(0,
				// 25));
				// tripletsHeader.setSize(0, 0);
				// tripletsHeader.setHorizontalTextPosition(SwingConstants.CENTER);
				// tripletsHeader.setHorizontalAlignment(SwingConstants.CENTER);
			}
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				fileMenu = new JMenu();
				menuBar.add(fileMenu);
				fileMenu.setText("File");
				{
					openFileMenuItem = new JMenuItem();
					fileMenu.add(openFileMenuItem);
					openFileMenuItem.setText("Open");
				}
				{
					saveMenuItem = new JMenuItem();
					fileMenu.add(saveMenuItem);
					saveMenuItem.setText("Save");
				}
				{
					new JSeparator();
					fileMenu.add(new JSeparator());
				}
				{
					exitMenuItem = new JMenuItem();
					fileMenu.add(exitMenuItem);
					exitMenuItem.setText("Exit");
				}
			}
			{
				editMenu = new JMenu();
				menuBar.add(editMenu);
				editMenu.setText("Edit");
				{
					addMenuItem = new JMenuItem();
					editMenu.add(addMenuItem);
					deleteMenuItem = new JMenuItem();
					editMenu.add(deleteMenuItem);
					editMenu.add(new JSeparator());
					upMenuItem = new JMenuItem();
					editMenu.add(upMenuItem);
					downMenuItem = new JMenuItem();
					editMenu.add(downMenuItem);
					updateMenuItem = new JMenuItem();
					editMenu.add(updateMenuItem);		
				}
			}
			{
				helpMenu = new JMenu();
				menuBar.add(helpMenu);
				helpMenu.setText("Help");
				{
					helpMenuItem = new JMenuItem();
					helpMenu.add(helpMenuItem);
					helpMenuItem.setText("Help");
				}
			}
		}
		pack();
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
		connectedLabel.setPreferredSize(new Dimension(150, 25));
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
