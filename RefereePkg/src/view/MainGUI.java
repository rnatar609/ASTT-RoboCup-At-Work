package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
	private JScrollPane tripletTableScrollPane;
	private JPanel editTripletPane;
	private JPanel statusPanel;
	private JPanel westPanel;
	private JButton saveButton;
	private JButton openButton;
	private JButton loadConfigButton;
	private JLabel statusLine;
	private JLabel timerLabel;
	private JButton deleteTripletButton;
	private JButton addTripletButton;
	private JButton sendTripletsButton;
	private JToggleButton timerStartStopButton;
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
	private JPanel middleServerPanel;
	private JPanel lowerServerPanel;
	private JLabel connectedLabel;
	private JPanel serverPanel;
	private JMenuItem openFileMenuItem;
	private JMenu fileMenu;
	private JMenuBar menuBar;
	private DefaultComboBoxModel<String> placesCbm;
	private DefaultComboBoxModel<String> orientationsCbm;
	private DefaultComboBoxModel<Short> pausesCbm;
	private JButton updateTripletButton;
	private JButton downTripletButton;
	private JButton upTripletButton;
	private JMenuItem upMenuItem;
	private JMenuItem downMenuItem;
	private JMenuItem updateMenuItem;
	private JMenuItem loadConfigMenuItem;
	private JTable tripletTable;
	private TripletTableM tripletTableM;
	private DefaultTableCellRenderer rendTriplets;
	private DefaultTableCellRenderer rendPassed;
	private DefaultTableCellRenderer rendFailed;

	class TripletTableM extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public Class getColumnClass(int c) {
			switch (c) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			case 2:
				return Boolean.class;
			}
			return null;
		}

		public void clearColumn(int c) {
			for (int i = 0; i < this.getRowCount(); i++) {
				this.setValueAt(null, i, c);
			}
		}

		public boolean isCellEditable(int row, int col) {
			if (col == 0)
				return false;
			else
				return true;
		}
	}

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
					tripletTableScrollPane = new JScrollPane(
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					{
						tripletTableM = new TripletTableM();
						tripletTableM.addColumn("Triplets");
						// tripletTableM.addColumn("Passed");
						// tripletTableM.addColumn("Failed");
						tripletTable = new JTable(tripletTableM);
						rendTriplets = new DefaultTableCellRenderer();
						rendPassed = new DefaultTableCellRenderer();
						rendFailed = new DefaultTableCellRenderer();
						tripletTable.getColumn("Triplets").setCellRenderer(
								rendTriplets);
						rendTriplets.setHorizontalAlignment(JLabel.CENTER);
					}
					tripletTableScrollPane.setViewportView(tripletTable);
					tripletTableScrollPane.setPreferredSize(tripletTable
							.getPreferredSize());
				}
				westPanel.add(tripletTableScrollPane, BorderLayout.WEST);
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
						middleServerPanel = new JPanel();
						BoxLayout serverMiddlePanelLayout = new BoxLayout(
								middleServerPanel,
								javax.swing.BoxLayout.LINE_AXIS);
						middleServerPanel.setLayout(serverMiddlePanelLayout);
						{
							disconnectButton = new JButton();
							disconnectButton.setEnabled(false);
							disconnectButton
									.setHorizontalAlignment(SwingConstants.LEFT);
						}
						middleServerPanel.add(disconnectButton);
						middleServerPanel.add(Box.createHorizontalStrut(GAP));
						{
							sendTripletsButton = new JButton();
							sendTripletsButton.setEnabled(false);
							sendTripletsButton
									.setHorizontalAlignment(SwingConstants.RIGHT);
						}
						middleServerPanel.add(sendTripletsButton);
					}
					serverPanel.add(middleServerPanel);
					
					serverPanel.add(Box.createVerticalStrut(GAP));
					{
						lowerServerPanel = new JPanel();
						BoxLayout serverLowerPanelLayout = new BoxLayout(
						        lowerServerPanel,
						        javax.swing.BoxLayout.LINE_AXIS);
						lowerServerPanel.setLayout(serverLowerPanelLayout);
						{
							timerStartStopButton = new JToggleButton("Timer Start");
							// timerStartStopButton.setEnabled(false);
							timerStartStopButton.setAlignmentX(SwingConstants.LEFT);
						}
						lowerServerPanel.add(timerStartStopButton);
						lowerServerPanel.add(Box.createHorizontalStrut(GAP));
						{
							timerLabel = new JLabel();
							timerLabel.setAlignmentX(SwingConstants.RIGHT);
							timerLabel.setText("00:00"); 
						}
						lowerServerPanel.add(timerLabel);
					}	
					serverPanel.add(lowerServerPanel);
				}
				westPanel.add(serverPanel, BorderLayout.SOUTH);
			}
			contentPanel.add(westPanel, BorderLayout.WEST);
			{
				mapArea = new MapArea();
				// mapArea.setBackground(Color.white);
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

	public JTable getTripletsTable() {
		return tripletTable;
	}

	public String getConnectedLabel() {
		return connectedLabel.getText();
	}

	public void taskSpecChanged(TripletEvent evt) {
		tripletTableM.clearColumn(0);
		tripletTableM.setRowCount(evt.getTaskTripletList().size());
		List<TaskTriplet> tTL = evt.getTaskTripletList();
		for (int i = 0; i < tTL.size(); i++) {
			tripletTableM.setValueAt(tTL.get(i).getTaskTripletString(), i, 0);
		}
	}

	@Override
	public void teamConnected(String teamName) {
		sendTripletsButton.setEnabled(true);
		disconnectButton.setEnabled(true);
		connectedIcon.setIcon(new ImageIcon(getClass().getResource(
				"/view/resources/icons/status.png")));
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
		loadConfigMenuItem.setEnabled(false);
	}

	public void setPlacesBoxSelected(String place) {
		placesBox.setSelectedItem(place);
	}

	public void setOrientationsBoxSelected(String orientation) {
		orientationsBox.setSelectedItem(orientation);
	}

	public void setPausesBoxSelected(Short pause) {
		pausesBox.setSelectedItem(pause);
	}

	public JToggleButton getTimerStartStopButton() {
		return timerStartStopButton;
	}

	public void addTimerListener(ItemListener timerListener) {
		timerStartStopButton.addItemListener(timerListener);
	}

	public void setTimerStartStopButtonText(String text) {
		timerStartStopButton.setText(text);
	}

	public void setButtonDimension() {
		int width = 0;
		Component[] comp = editTripletPane.getComponents();
		// remember the widest Button
		for (int i = 0; i < comp.length; i++) {
			if (comp[i].getPreferredSize().width > width) {
				width = comp[i].getPreferredSize().width;
			}
		}
		// set all Button widths to the widest one
		for (int i = 0; i < comp.length; i++) {
			// don't change the glues!
			if (comp[i].getPreferredSize().width != 0) {
				Dimension dim = comp[i].getPreferredSize();
				dim.width = width;
				comp[i].setMaximumSize(dim);
			}
		}
	}

	public void setCompetitionMode(Boolean enable) {
		if (enable) {
			tripletTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tripletTableM.addColumn("Passed");
			tripletTableM.addColumn("Failed");
			tripletTable.getColumn("Triplets").setCellRenderer(rendTriplets);
			rendTriplets.setHorizontalAlignment(JLabel.CENTER);
			tripletTableScrollPane.setPreferredSize(tripletTable
					.getPreferredSize());
			Component[] comp = editTripletPane.getComponents();
			for (int i = 0; i < comp.length; i++) {
				// don't change the glues!
				if (comp[i].getPreferredSize().width != 0) {
					comp[i].setEnabled(false);
				}
			}
		} else {
			tripletTableM.setColumnCount(1);
			tripletTable.getColumn("Triplets").setCellRenderer(rendTriplets);
			rendTriplets.setHorizontalAlignment(JLabel.CENTER);
			tripletTableScrollPane.setPreferredSize(tripletTable
					.getPreferredSize());
			Component[] comp = editTripletPane.getComponents();
			for (int i = 0; i < comp.length; i++) {
				// don't change the glues!
				if (comp[i].getPreferredSize().width != 0) {
					comp[i].setEnabled(true);
				}
			}
		}
		this.validate();
	}

	public void addtripletTableListener(MouseListener tL) {
		tripletTable.addMouseListener(tL);
	}

	public void setTableCellCorrected(int row, int column) {
		if (column == 1)
			tripletTableM.setValueAt(Boolean.FALSE, row, 2);
		if (column == 2)
			tripletTableM.setValueAt(Boolean.FALSE, row, 1);
		tripletTable.repaint();
	}
	
	public void setTimerLabelText(String s)
	{
		timerLabel.setText(s);
	}
}
