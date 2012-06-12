package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.BntTask;
import model.Task;

public class BntPanel extends CompetitionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private JComboBox<String> placeBox;
	private JComboBox<String> orientationBox;
	private JComboBox<String> pauseBox;
	public DefaultComboBoxModel<String> placeCbm;
	private DefaultComboBoxModel<String> orientationCbm;
	private DefaultComboBoxModel<String> pauseCbm;

	BntPanel(BorderLayout borderLayout) {
		super(borderLayout);
		createFlowPanelsInEastPanel();
		eastPanel.add(Box.createVerticalGlue());
		createButtons();
	}

	private void createFlowPanelsInEastPanel() {
		JPanel[] flowPanels = new JPanel[3];
		flowPanels[0] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		flowPanels[1] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		flowPanels[2] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		placeBox = new JComboBox<String>();
		flowPanels[0].add(new JLabel("Place"));
		flowPanels[0].add(placeBox);
		eastPanel.add(flowPanels[0]);
		orientationBox = new JComboBox<String>();
		flowPanels[1].add(new JLabel("Orientation"));
		flowPanels[1].add(orientationBox);
		eastPanel.add(flowPanels[1]);
		pauseBox = new JComboBox<String>();
		flowPanels[2].add(new JLabel("Pause"));
		flowPanels[2].add(pauseBox);
		eastPanel.add(flowPanels[2]);
	}

	/**
	 * Update appropriate GUI components with the provided valid positions.
	 * 
	 * @param positions
	 *            A set of valid place labels and their pixel positions.
	 */
	public void setValidPositions(LinkedHashMap<String, Point> positions) {
		String[] posString = new String[positions.size()];
		int i = 0;
		for (String pos : positions.keySet()) {
			posString[i] = pos;
			i++;
			System.out.println("Entered bnt panel set positions: " + posString[i-1]);

		}
		placeCbm = new DefaultComboBoxModel<String>(posString);
		placeBox.setModel(placeCbm);
	}

	/**
	 * Update appropriate GUI components with the provided valid orientations.
	 * 
	 * @param orientations
	 *            A list of strings representing valid orientations.
	 */
	public void setValidOrientations(List<String> orientations) {
		orientationCbm = new DefaultComboBoxModel<String>(
				orientations.toArray(new String[orientations.size()]));
		orientationBox.setModel(orientationCbm);
	}

	/**
	 * Update appropriate GUI components with the provided valid pause
	 * durations.
	 * 
	 * @param pauses
	 *            A list of short integers representing valid pauses.
	 */
	public void setValidPauses(List<String> pauses) {
		pauseCbm = new DefaultComboBoxModel<String>(
				pauses.toArray(new String[pauses.size()]));
		pauseBox.setModel(pauseCbm);
	}

	public BntTask getSelectedTask() {
		BntTask t = new BntTask();
		t.setPlace((String) placeBox.getSelectedItem());
		t.setOrientation((String) orientationBox.getSelectedItem());
		t.setPause((String) pauseBox.getSelectedItem());
		System.out.println(t.getString());
		return t;
	}

	public void taskSpecChanged(ArrayList<BntTask> bntTaskList) {
		sequenceTableModel.getDataVector().removeAllElements();
		sequenceTableModel.setRowCount(0);
		sequenceTableModel.fireTableDataChanged();
		while(sequenceTableModel.getRowCount() > 0)
		{
		    sequenceTableModel.removeRow(0);
		}
	 	sequenceTableModel.setRowCount(bntTaskList.size());
		for (int i = 0; i < bntTaskList.size(); i++) {
			sequenceTableModel.setValueAt(
					((Task) bntTaskList.get(i)).getString(), i, 0);
		}
	}

	public void setTaskBoxSected(Task task) {
		placeCbm.setSelectedItem(task.getPlace());
		orientationCbm.setSelectedItem(task.getOrientation());
		pauseCbm.setSelectedItem(task.getPause());
	}
}
