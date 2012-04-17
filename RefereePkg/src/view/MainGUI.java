package view;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;
//
import javax.swing.*;//{Modified by JeyaPrakash, Ramesh, Marc}
import java.awt.*;   //{     14412                           }
//
import javax.swing.Action;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.TripletListener;

import model.TripletEvent;


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
	private JPanel jPanel8;
	private JPanel jPanel7;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JComboBox<String> orientationsBox;
	private JComboBox<Short> pausesBox;
	private JComboBox<String> placesBox;
	private JToolBar toolBar;
	private JPanel toolBarPanel;
	private JPanel contentPanel;
	//Added by Jeyaprakash, Ramesh, Marc
	private JPanel jPanelImgArea;
	private JLabel jLblImg;
	private ImageIcon imgIcn;	
	private BorderLayout panelLay;
	//170412
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

	public MainGUI() {
		try {
			UIManager
					.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
			//changed dimensions from (500,300) to (800,500) by Jeyaprakash, Ramesh, Marc
			this.setPreferredSize(new java.awt.Dimension(800, 500));
			this.setMinimumSize(new java.awt.Dimension(800, 500));
			//170412//
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			{
				contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);
				this.add(contentPanel, BorderLayout.CENTER);
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
							tripletsList = new JList<String>(tripletLm);
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
						statusLine.setPreferredSize(new java.awt.Dimension(490,
								14));
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
								jPanel6.add(jButton2);
								jButton2.setName("jButton2");
							}
							{
								jPanel7 = new JPanel();
								jPanel4.add(jPanel7);
								{
									jButton1 = new JButton();
									jPanel7.add(jButton1);
									jButton1.setName("jButton1");
								}
							}
							{
								jPanel8 = new JPanel();
								jPanel4.add(jPanel8);
								{
									jButton3 = new JButton();
									jPanel8.add(jButton3);
									jButton3.setName("jButton3");
								}
							}
						}
						//Modified by Jeyaprakash, Ramesh, Marc
						{
							jPanelImgArea = new JPanel();
							jLblImg = new JLabel();
							imgIcn = new ImageIcon("/home/1.png");
							panelLay = new BorderLayout();
							jPanelImgArea.setLayout(panelLay);
							jPanelImgArea.setPreferredSize(new java.awt.Dimension(500, 350));
							jLblImg.setIcon(imgIcn);
							jPanelImgArea.add(jLblImg);
							contentPanel.add(jPanelImgArea, BorderLayout.EAST);	
						}//14412
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
		// fc.set
		// (UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
		fc.setFileFilter(new TspFilter());
		// Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
	}

	public static void main(String[] args) {
		// launch(MainGUI.class, args);
	}

	public void copy() {
		statusLine
				.setText("<html><FONT COLOR=RED>not implemented yet! </FONT> copy triplet </html>");

	}

	public void setValidPlaces(List<String> places) {
		placesCbm = new DefaultComboBoxModel<String>(
				places.toArray(new String[places.size()]));
		placesBox.setModel(placesCbm);
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
	    return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
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
        //Assumption: the user does not change the selection while deletion is going on.
        //If not, then compare the string being deleted from the list to the one that has been deleted.
	}
}
