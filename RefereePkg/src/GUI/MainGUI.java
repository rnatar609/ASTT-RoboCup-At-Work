package GUI;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import Triplet.TaskSpec;
import Triplet.TaskTriplet;
import Triplet.ValidTripletElements;

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
public class MainGUI extends SingleFrameApplication {
	private static String[] places;
	private static String[] orientations;
	private static Short[] pauses;
	private JMenuBar menuBar;
	private JList<String> tripletsList;
	private JScrollPane jScrollPane1;
	private JLabel tripletsLabel;
	private JPanel jPanel4;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JPanel topPanel;
	private JMenuItem jMenuItem7;
	private JMenuItem jMenuItem6;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem4;
	private JMenu editMenu;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem2;
	private JMenu fileMenu;
	private JSeparator jSeparator;
	private JButton saveButton;
	private JButton openButton;
	private JComboBox<String> orientationsBox;
	private JLabel statusLine;
	private JButton jButton2;
	private JButton jButton1;
	private JPanel jPanel7;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JComboBox<Short> pausesBox;
	private JComboBox<String> placesBox;
	private JToolBar toolBar;
	private JPanel toolBarPanel;
	private JPanel contentPanel;

	ListModel<String> jListTripletsModel = new DefaultComboBoxModel<String>();
	private TaskSpec taskSpec = new TaskSpec();

	final JFileChooser fc = new JFileChooser();

	@Override
	protected void startup() {
		{
			topPanel = new JPanel();
			BorderLayout panelLayout = new BorderLayout();
			topPanel.setLayout(panelLayout);
			topPanel.setPreferredSize(new java.awt.Dimension(500, 300));
			{
				contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);
				topPanel.add(contentPanel, BorderLayout.CENTER);
				{
					jPanel1 = new JPanel();
					contentPanel.add(jPanel1, BorderLayout.WEST);
					jPanel1.setPreferredSize(new java.awt.Dimension(120, 220));
					{
						jScrollPane1 = new JScrollPane(
								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						jPanel1.add(jScrollPane1);
						jScrollPane1.setPreferredSize(new java.awt.Dimension(
								100, 200));
						{
							tripletsList = new JList<String>();
							jScrollPane1.setViewportView(tripletsList);
							// tripletsList.setPreferredSize(new
							// java.awt.Dimension(0,0));
						}
					}
				}
				{
					jPanel2 = new JPanel();
					contentPanel.add(jPanel2, BorderLayout.NORTH);
					jPanel2.setPreferredSize(new java.awt.Dimension(500, 28));
				}
				{
					jPanel3 = new JPanel();
					contentPanel.add(jPanel3, BorderLayout.SOUTH);
					jPanel3.setPreferredSize(new java.awt.Dimension(500, 33));
					{
						statusLine = new JLabel();
						jPanel3.add(statusLine);
						statusLine.setName("statusLine");
						statusLine.setHorizontalAlignment(JLabel.CENTER);
						statusLine.setPreferredSize(new java.awt.Dimension(490, 14));
					}
				}
				{
					jPanel4 = new JPanel();
					BoxLayout jPanel4Layout = new BoxLayout(jPanel4,
							javax.swing.BoxLayout.Y_AXIS);
					jPanel4.setLayout(jPanel4Layout);
					contentPanel.add(jPanel4, BorderLayout.CENTER);
					jPanel4.setPreferredSize(new java.awt.Dimension(379, 212));
					{
						jPanel5 = new JPanel();
						jPanel4.add(jPanel5);
						jPanel5.setPreferredSize(new java.awt.Dimension(404, 39));
						{
							tripletsLabel = new JLabel();
							jPanel5.add(tripletsLabel);
							tripletsLabel.setName("tripletsLabel");
						}
						{
							ComboBoxModel<String> placesBoxModel = new DefaultComboBoxModel<String>(
									places);
							placesBox = new JComboBox<String>();
							jPanel5.add(placesBox);
							placesBox.setModel(placesBoxModel);
						}
						{
							ComboBoxModel<String> orientationsBoxModel = new DefaultComboBoxModel<String>(
									orientations);
							orientationsBox = new JComboBox<String>();
							jPanel5.add(orientationsBox);
							orientationsBox.setModel(orientationsBoxModel);
						}
						{
							ComboBoxModel<Short> pausesBoxModel = new DefaultComboBoxModel<Short>(
									pauses);
							pausesBox = new JComboBox<Short>();
							jPanel5.add(pausesBox);
							pausesBox.setModel(pausesBoxModel);
						}
						{
							jPanel6 = new JPanel();
							jPanel4.add(jPanel6);
							jPanel6.setPreferredSize(new java.awt.Dimension(
									404, 149));
							{
								jButton2 = new JButton();
								jPanel6.add(jButton2);
								jButton2.setName("jButton2");
								jButton2.setAction(getAppActionMap().get(
										"addTriplet"));
							}
							{
								jPanel7 = new JPanel();
								jPanel4.add(jPanel7);
								{
									jButton1 = new JButton();
									jPanel7.add(jButton1);
									jButton1.setName("jButton1");
									jButton1.setAction(getAppActionMap().get(
											"sendTriplets"));
								}
							}
						}
					}
				}
			}
			{
				toolBarPanel = new JPanel();
				topPanel.add(toolBarPanel, BorderLayout.NORTH);
				BorderLayout jPanel1Layout = new BorderLayout();
				toolBarPanel.setLayout(jPanel1Layout);
				{
					toolBar = new JToolBar();
					toolBarPanel.add(toolBar, BorderLayout.CENTER);
					{
						openButton = new JButton();
						toolBar.add(openButton);
						openButton.setAction(getAppActionMap().get("open"));
						openButton.setName("openButton");
						openButton.setFocusable(false);
					}
					{
						saveButton = new JButton();
						toolBar.add(saveButton);
						saveButton.setAction(getAppActionMap().get("save"));
						saveButton.setName("saveButton");
						saveButton.setFocusable(false);
					}
				}
				{
					jSeparator = new JSeparator();
					toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
				}
			}
		}
		menuBar = new JMenuBar();
		{
			fileMenu = new JMenu();
			menuBar.add(fileMenu);
			fileMenu.setName("fileMenu");
			{
				jMenuItem2 = new JMenuItem();
				fileMenu.add(jMenuItem2);
				jMenuItem2.setAction(getAppActionMap().get("open"));
			}
			{
				jMenuItem3 = new JMenuItem();
				fileMenu.add(jMenuItem3);
				jMenuItem3.setAction(getAppActionMap().get("save"));
			}
		}
		{
			editMenu = new JMenu();
			menuBar.add(editMenu);
			editMenu.setName("editMenu");
			{
				jMenuItem4 = new JMenuItem();
				editMenu.add(jMenuItem4);
				jMenuItem4.setAction(getAppActionMap().get("copy"));
			}
			{
				jMenuItem5 = new JMenuItem();
				editMenu.add(jMenuItem5);
				jMenuItem5.setAction(getAppActionMap().get("cut"));
			}
			{
				jMenuItem6 = new JMenuItem();
				editMenu.add(jMenuItem6);
				jMenuItem6.setAction(getAppActionMap().get("paste"));
			}
			{
				jMenuItem7 = new JMenuItem();
				editMenu.add(jMenuItem7);
				jMenuItem7.setAction(getAppActionMap().get("delete"));
			}
		}
		fc.setFileFilter(new TspFilter());
		getMainFrame().setJMenuBar(menuBar);
		show(topPanel);
	}

	public static void main(String[] args) {
		initializeValidTriplets();
		launch(MainGUI.class, args);
	}

	private static void initializeValidTriplets() {
		ValidTripletElements vte = ValidTripletElements.getInstance();
		try {
			if (vte.readFromConfigFile()) {
				places = vte.getValidPlaces().toArray(
						new String[vte.getValidPlaces().size()]);
				orientations = vte.getValidOrientations().toArray(
						new String[vte.getValidOrientations().size()]);
				pauses = vte.getValidPauses().toArray(
						new Short[vte.getValidPauses().size()]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Action
	public void open() {
		int returnVal = fc.showOpenDialog(contentPanel);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				@SuppressWarnings("unused")
				String strLine;
				while ((strLine = br.readLine()) != null) {
					// todo
				}
				in.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			//statusLine.setText("opened task specification >" + file.getName() + "<");	
			statusLine.setText("<html><FONT COLOR=RED>not implemented yet! </FONT> opened task specification </html>");
		} else {
			statusLine.setText("open command cancelled by user");
		}
	}

	@Action
	public void save() {
		int returnVal = fc.showSaveDialog(contentPanel);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = Utils.correctFile(fc.getSelectedFile());
			try {
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(taskSpec.getTaskSpecString());
				out.close();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			statusLine.setText("saved actual task specification in >" + file.getName() + "<");
		} else {
			statusLine.setText("save command cancelled by user");
		}
	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	public void copy(){
		statusLine.setText("<html><FONT COLOR=RED>not implemented yet! </FONT> copy triplet </html>");
		
	}
	@Action
	public void sendTriplets() {
		// TODO 
		statusLine.setText("<html><FONT COLOR=RED>not implemented yet! </FONT> send task specification </html>");
	}

	@Action
	public void addTriplet() {
		TaskTriplet t = new TaskTriplet();
		if (t.setPlace((String) placesBox.getSelectedItem())
				&& t.setOrientation((String) orientationsBox.getSelectedItem())
				&& t.setPause((String) pausesBox.getSelectedItem().toString())) {
			taskSpec.addTriplet(t);
			statusLine.setText("added triplet (" + t.getPlace() + ", " + t.getOrientation() + ", " + t.getPause() + ")");
			updateTripletListBox();
		} else {
			statusLine.setText("error triplet");
		}
	}

	private void updateTripletListBox() {
		String[] s = new String[taskSpec.getTaskTripletList().size()];
		for (int i = 0; i < taskSpec.getTaskTripletList().size(); i++) {
			s[i] = taskSpec.getTaskTripletList().get(i).getTaskTripletString();
		}
		tripletsList.setListData(s);
	}

}
