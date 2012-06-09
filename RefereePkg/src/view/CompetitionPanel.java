package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.BntTask;
import model.Task;
import model.TaskTriplet;
import model.TripletEvent;

public class CompetitionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private String competitionName;
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton upButton;
	private JButton downButton;
	private JScrollPane sequenceTableScrollPane;
	private JTable sequenceTable;
	private SequenceTableModel sequenceTableModel;
	private DefaultTableCellRenderer tableCellRenderer;
	protected JPanel eastPanel;

	public CompetitionPanel(BorderLayout borderLayout) {
		super(borderLayout);
		init();
	}

	private void init() {
		createTableScrollPaneInPanel();
		createEastPanel();
	}

	private class SequenceTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		public Class getColumnClass(int column) {
			if (column >= 1)
				return Boolean.class;
			else
				return String.class;
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

	protected final void createButtons() {
		addButton = new JButton();
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		eastPanel.add(addButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		updateButton = new JButton();
		updateButton.setAlignmentX(CENTER_ALIGNMENT);
		eastPanel.add(updateButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		deleteButton = new JButton();
		deleteButton.setAlignmentX(CENTER_ALIGNMENT);
		eastPanel.add(deleteButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		upButton = new JButton();
		upButton.setAlignmentX(CENTER_ALIGNMENT);
		eastPanel.add(Box.createVerticalGlue());
		eastPanel.add(upButton);
		downButton = new JButton();
		downButton.setAlignmentX(CENTER_ALIGNMENT);
		eastPanel.add(Box.createVerticalStrut(GAP));
		eastPanel.add(downButton);
		eastPanel.add(Box.createVerticalGlue());
	}

	private final void createTableScrollPaneInPanel() {
		sequenceTableScrollPane = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sequenceTableModel = new SequenceTableModel();
		sequenceTableModel.addColumn("Subgoals");
		sequenceTable = new JTable(sequenceTableModel);
		tableCellRenderer = new DefaultTableCellRenderer();
		sequenceTable.getColumn("Subgoals").setCellRenderer(tableCellRenderer);
		tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		sequenceTableScrollPane.setViewportView(sequenceTable);
		sequenceTableScrollPane.setPreferredSize(sequenceTable
				.getPreferredSize());
		this.add(sequenceTableScrollPane, BorderLayout.WEST);
	}

	private final void createEastPanel() {
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		this.add(eastPanel, BorderLayout.CENTER);
	}

	private void createFlowPanelsInEastPanel() {
		// this should be done in each of the children of this class
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public JTable getSequenceTable() {
		return sequenceTable;
	}

	public void connectDelete(Action delete) {
		deleteButton.setAction(delete);
	}

	public void connectAdd(Action add) {
		addButton.setAction(add);
	}

	public void connectUpdate(Action update) {
		updateButton.setAction(update);
	}

	public void connectUp(Action up) {
		upButton.setAction(up);
	}

	public void connectDown(Action down) {
		downButton.setAction(down);
	}

	// This will be invoked only for navigation test.
	public void taskSpecChanged(TripletEvent evt) {
		sequenceTableModel.clearColumn(0);
		sequenceTableModel.setRowCount(evt.getTaskList().size());
		List<BntTask> tTL = evt.getTaskList();
		for (int i = 0; i < tTL.size(); i++) {
			sequenceTableModel.setValueAt(((Task) tTL.get(i)).getString(), i,
					0);
		}
	}

	public void setComboBoxSelected(String listTitle, String selectedValue) {
		/*
		 * for (int i = 0; i < numberOfDropDownLists; i++) { if
		 * (dropDownListTitles[i].equals(listTitle)) {
		 * dropDownLists[i].setSelectedItem(selectedValue); break; } }
		 */
	}

	public JComboBox<String> getComboBoxByName(String boxName) {
		/*
		 * for (int i = 0; i < numberOfDropDownLists; i++) { if
		 * (dropDownListTitles[i].equals(boxName)) { return dropDownLists[i]; }
		 * }
		 */
		return null;
	}

	public void setButtonDimension() {
		int width = 0;
		Component[] comp = eastPanel.getComponents();
		// remember the widest Button
		for (int i = comp.length - 1; i >= comp.length - 11; i--) {
			if (comp[i].getPreferredSize().width > width) {
				width = comp[i].getPreferredSize().width;
			}
		}
		// set all Button widths to the widest one
		for (int i = comp.length - 1; i >= comp.length - 11; i--) {
			// don't change the glues!
			if (comp[i].getPreferredSize().width != 0) {
				Dimension dim = comp[i].getPreferredSize();
				dim.width = width;
				comp[i].setMaximumSize(dim);
				//comp[i].setPreferredSize(dim);
				//comp[i].setMinimumSize(dim);
			}
		}
	}

	public Task getSelectedTask() {
		return null;
	}
}