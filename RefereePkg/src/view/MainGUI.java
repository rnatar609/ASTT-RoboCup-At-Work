package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.TripletEvent;
import controller.TripletListener;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing view Builder, which is free for non-commercial
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
public class MainGUI extends JFrame implements TripletListener {
	public MapArea getMapArea() {
		return mapArea;
	}

	private static final long serialVersionUID = 1L;
	private JList<String> tripletsList;
	private JScrollPane jScrollPane1;
	private JLabel tripletsLabel;
	private JPanel jPanel4;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JSeparator jSeparator;
	private JButton saveButton;
	private JButton openButton;
	private JLabel statusLine;
	private JButton jButton3;
	private JButton jButton2;
	private JButton jButton1;
	private JPanel jPanel7;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JComboBox<String> orientationsBox;
	private JComboBox<Short> pausesBox;
	private JComboBox<String> placesBox;
	private JToolBar toolBar;
	private JPanel toolBarPanel;
	private JPanel contentPanel;
	private MapArea mapArea;
	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	private JFileChooser fc;
	private DefaultComboBoxModel<String> placesCbm;
	private DefaultComboBoxModel<String> orientationsCbm;
	private DefaultComboBoxModel<Short> pausesCbm;
	private DefaultListModel<String> tripletLm = new DefaultListModel<String>();
	private JPanel jPanel10;
	private BorderLayout jPanel10Layout;

	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fc = new JFileChooser();
		initGUI();
	}

	private void initGUI() {

		{
			this.setTitle("RoboCup@work");
			BorderLayout panelLayout = new BorderLayout();
			this.setLayout(panelLayout);
			// this.setPreferredSize(new Dimension(1000, 660));
			// this.setMaximumSize(new Dimension(2000, 660));
			// 170412//
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			{
				contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);

				this.add(contentPanel, BorderLayout.CENTER);
				{
					jPanel1 = new JPanel();
					contentPanel.add(jPanel1, BorderLayout.WEST);
					{
						jScrollPane1 = new JScrollPane(
								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						jPanel1.add(jScrollPane1);
						jScrollPane1.setPreferredSize(new java.awt.Dimension(
						120, 400));
						{
							tripletsList = new JList<String>(tripletLm);
							jScrollPane1.setViewportView(tripletsList);
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
						statusLine.setPreferredSize(new java.awt.Dimension(490,
								14));
					}
				}
				{
					jPanel4 = new JPanel();
					BoxLayout jPanel4Layout = new BoxLayout(jPanel4,
							javax.swing.BoxLayout.Y_AXIS);
					jPanel4.setLayout(jPanel4Layout);
					jPanel4.setPreferredSize(new java.awt.Dimension(150, 400));
					jPanel10 = new JPanel();
					jPanel10Layout = new BorderLayout();
					jPanel10.setLayout(jPanel10Layout);
					jPanel10.add(jPanel4, BorderLayout.WEST);
					contentPanel.add(jPanel10, BorderLayout.CENTER);
					{
						jPanel5 = new JPanel();
						JLabel header = new JLabel("Place  Orientation  Time");
						header.setMinimumSize(new java.awt.Dimension(400, 40));
						header.setHorizontalAlignment(JLabel.CENTER);
						jPanel4.add(Box.createVerticalStrut(20));
						jPanel4.add(header);
						jPanel4.add(jPanel5);
						jPanel5.setPreferredSize(new java.awt.Dimension(404, 39));
						{
							tripletsLabel = new JLabel();
							jPanel5.add(tripletsLabel);
							tripletsLabel.setName("tripletsLabel");
						}
						{

							placesBox = new JComboBox<String>();
							jPanel5.add(placesBox);
						}
						{
							orientationsBox = new JComboBox<String>();
							jPanel5.add(orientationsBox);

						}
						{
							pausesBox = new JComboBox<Short>();
							jPanel5.add(pausesBox);
						}
						{
							jPanel6 = new JPanel();
							jPanel4.add(jPanel6);
							jPanel6.setPreferredSize(new java.awt.Dimension(
									404, 149));
							{
								jButton2 = new JButton();
								jButton2.setPreferredSize(new java.awt.Dimension(
										100, 25));
								jPanel6.add(jButton2);
								jButton2.setName("jButton2");
								jButton3 = new JButton();
								jButton3.setPreferredSize(new java.awt.Dimension(
										100, 25));
								jPanel6.add(jButton3);
								jButton3.setName("jButton3");
							}
							{
								jPanel7 = new JPanel();
								jPanel4.add(jPanel7);
								{
									jButton1 = new JButton();
									jButton1.setPreferredSize(new java.awt.Dimension(
											100, 25));
									jPanel7.add(jButton1);
									jButton1.setName("jButton1");
								}
							}
						}
						{
							mapArea = new MapArea();
							mapArea.setBackground(Color.black);
							jPanel10.add(mapArea, BorderLayout.CENTER);
						}
					}
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
						// saveButton.setName("saveButton");
						saveButton.setFocusable(false);
					}
				}
				{
					jSeparator = new JSeparator();
					toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
				}
			}
		}
		jMenuBar1 = new JMenuBar();
		setJMenuBar(jMenuBar1);
		{
			jMenu3 = new JMenu();
			jMenuBar1.add(jMenu3);
			jMenu3.setText("File");
			{
				newFileMenuItem = new JMenuItem();
				jMenu3.add(newFileMenuItem);
				newFileMenuItem.setText("New");
			}
			{
				openFileMenuItem = new JMenuItem();
				jMenu3.add(openFileMenuItem);
				openFileMenuItem.setText("Open");
			}
			{
				saveMenuItem = new JMenuItem();
				jMenu3.add(saveMenuItem);
				saveMenuItem.setText("Save");
			}
			{
				saveAsMenuItem = new JMenuItem();
				jMenu3.add(saveAsMenuItem);
				saveAsMenuItem.setText("Save As ...");
			}
			{
				closeFileMenuItem = new JMenuItem();
				jMenu3.add(closeFileMenuItem);
				closeFileMenuItem.setText("Close");
			}
			{
				jSeparator2 = new JSeparator();
				jMenu3.add(jSeparator2);
			}
			{
				exitMenuItem = new JMenuItem();
				jMenu3.add(exitMenuItem);
				exitMenuItem.setText("Exit");
			}
		}
		{
			jMenu4 = new JMenu();
			jMenuBar1.add(jMenu4);
			jMenu4.setText("Edit");
			{
				cutMenuItem = new JMenuItem();
				jMenu4.add(cutMenuItem);
				cutMenuItem.setText("Cut");
			}
			{
				copyMenuItem = new JMenuItem();
				jMenu4.add(copyMenuItem);
				copyMenuItem.setText("Copy");
			}
			{
				pasteMenuItem = new JMenuItem();
				jMenu4.add(pasteMenuItem);
				pasteMenuItem.setText("Paste");
			}
			{
				jSeparator1 = new JSeparator();
				jMenu4.add(jSeparator1);
			}
			{
				deleteMenuItem = new JMenuItem();
				jMenu4.add(deleteMenuItem);
				deleteMenuItem.setText("Delete");
			}
		}
		{
			jMenu5 = new JMenu();
			jMenuBar1.add(jMenu5);
			jMenu5.setText("Help");
			{
				helpMenuItem = new JMenuItem();
				jMenu5.add(helpMenuItem);
				helpMenuItem.setText("Help");
			}
		}
		pack();
		fc.setFileFilter(new TspFilter());
	}

	public void copy() {
		statusLine
				.setText("<html><FONT COLOR=RED>not implemented yet! </FONT> copy triplet </html>");

	}

	public void setValidPlaces(List<String> places) {
		placesCbm = new DefaultComboBoxModel<String>(
				places.toArray(new String[places.size()]));
		placesBox.setModel(placesCbm);
		mapArea.setValidPlaces(places);
	}

	public void setValidPoints(List<Point> validPoints) {
		mapArea.setValidPoints(validPoints);
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

	public void connectSaveAction(Action save) {
		saveButton.setAction(save);
		saveMenuItem.setAction(save);
	}

	public void connectOpenAction(Action open) {
		openButton.setAction(open);
		openFileMenuItem.setAction(open);
	}

	public void connectCloseAction(Action close) {
		closeFileMenuItem.setAction(close);
	}

	public void connectSendTriplets(Action sendTriplets) {
		jButton1.setAction(sendTriplets);
	}

	public void connectAddTriplet(Action addTriplet) {
		jButton2.setAction(addTriplet);
	}

	public void connectDeleteTriplet(Action deleteTriplet) {
		jButton3.setAction(deleteTriplet);
	}

	public File showSaveDialog() {
		if (fc.showSaveDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}

	public File showOpenDialog() {
		if (fc.showOpenDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}

	public void setStatusLine(String status) {
		statusLine.setText(status);
	}

	public int getUserConfirmation(String msg, String title) {
		return JOptionPane.showConfirmDialog(null, msg, title,
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

	@Override
	public void tripletAdded(TripletEvent evt) {
		int pos = tripletsList.getModel().getSize();
		String s = new String(evt.getTaskTriplet().getTaskTripletString());
		tripletLm.add(pos, s);
	}

	@Override
	public void tripletDeleted(TripletEvent evt) {
		int pos = tripletsList.getSelectedIndex();
		tripletLm.remove(pos);
		// Assumption: the user does not change the selection while deletion is
		// going on.
		// If not, then compare the string being deleted from the list to the
		// one that has been deleted.
	}
}
