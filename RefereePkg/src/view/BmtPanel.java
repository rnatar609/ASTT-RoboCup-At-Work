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

import model.BmtTask;
import model.BntTask;
import model.Task;

public class BmtPanel extends CompetitionPanel {

	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private JComboBox<String> placeInitialBox;
	private JComboBox<String> placeSourceBox;
	private JComboBox<String> placeDestinationBox;
	private JComboBox<String> configurationBox;
	private JComboBox<String> objectBox;
	public DefaultComboBoxModel<String> placeInitialCbm;
	public DefaultComboBoxModel<String> placeSourceCbm;
	public DefaultComboBoxModel<String> placeDestinationCbm;
	private DefaultComboBoxModel<String> configurationCbm;
	private DefaultComboBoxModel<String> objectCbm;
	private JComboBox<String> placeFinalBox;
	public DefaultComboBoxModel<String> placeFinalCbm;

	BmtPanel(BorderLayout borderLayout) {
		super(borderLayout);
		createFlowPanelsInEastPanel();
		eastPanel.add(Box.createVerticalGlue());
		createButtons();
	}

	private void createFlowPanelsInEastPanel() {
		JPanel[] flowPanels = new JPanel[6];
		for (int i = 0; i < flowPanels.length; i++) {
			flowPanels[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		}
		placeInitialBox = new JComboBox<String>();
		flowPanels[0].add(new JLabel("Initial Place"));
		flowPanels[0].add(placeInitialBox);
		eastPanel.add(flowPanels[0]);
		placeSourceBox = new JComboBox<String>();
		flowPanels[1].add(new JLabel("Source Place"));
		flowPanels[1].add(placeSourceBox);
		eastPanel.add(flowPanels[1]);
		placeDestinationBox = new JComboBox<String>();
		flowPanels[2].add(new JLabel("Destination Place"));
		flowPanels[2].add(placeDestinationBox);
		eastPanel.add(flowPanels[2]);
		configurationBox = new JComboBox<String>();
		flowPanels[3].add(new JLabel("Configuration"));
		flowPanels[3].add(configurationBox);
		eastPanel.add(flowPanels[3]);
		objectBox = new JComboBox<String>();
		flowPanels[4].add(new JLabel("Object"));
		flowPanels[4].add(objectBox);
		eastPanel.add(flowPanels[4]);
		placeFinalBox = new JComboBox<String>();
		flowPanels[5].add(new JLabel("Final Place"));
		flowPanels[5].add(placeFinalBox);
		eastPanel.add(flowPanels[5]);
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
		}
		placeInitialCbm = new DefaultComboBoxModel<String>(posString);
		placeInitialBox.setModel(placeInitialCbm);
		placeSourceCbm = new DefaultComboBoxModel<String>(posString);
		placeSourceBox.setModel(placeSourceCbm);
		placeDestinationCbm = new DefaultComboBoxModel<String>(posString);
		placeDestinationBox.setModel(placeDestinationCbm);
		placeFinalCbm = new DefaultComboBoxModel<String>(posString);
		placeFinalBox.setModel(placeFinalCbm);
	}

	/**
	 * Update appropriate GUI components with the provided valid positions.
	 * 
	 * @param positions
	 *            A set of valid place labels and their pixel positions.
	 */
	public void setValidConfigurations(List<String> configuration) {
		configurationCbm = new DefaultComboBoxModel<String>(
				configuration.toArray(new String[configuration.size()]));
		configurationBox.setModel(configurationCbm);
	}

	/**
	 * Update appropriate GUI components with the provided valid orientations.
	 * 
	 * @param orientations
	 *            A list of strings representing valid orientations.
	 */
	public void setValidObjects(List<String> object) {
		objectCbm = new DefaultComboBoxModel<String>(
				object.toArray(new String[object.size()]));
		objectBox.setModel(objectCbm);
	}

	public void taskSpecChanged(ArrayList<BmtTask> bmtTaskList) {
		sequenceTableModel.clearColumn(0);
		sequenceTableModel.setRowCount(bmtTaskList.size());
		for (int i = 0; i < bmtTaskList.size(); i++) {
			sequenceTableModel.setValueAt(
					((Task) bmtTaskList.get(i)).getString(), i, 0);
		}
	}

	public BmtTask getSelectedTask() {
		BmtTask t = new BmtTask();
		BmtTask.setPlaceInitial((String) placeInitialBox.getSelectedItem());
		BmtTask.setPlaceSource((String) placeSourceBox.getSelectedItem());
		BmtTask.setPlaceDestination((String) placeDestinationBox
				.getSelectedItem());
		BmtTask.setPlaceFinal((String) placeFinalBox.getSelectedItem());
		t.setConfiguration((String) configurationBox.getSelectedItem());
		t.setObject((String) objectBox.getSelectedItem());
		return t;
	}

	public void setTaskBoxSected(Task task) {
		placeInitialCbm.setSelectedItem(BmtTask.getPlaceInitial());
		placeSourceCbm.setSelectedItem(BmtTask.getPlaceSource());
		placeDestinationCbm.setSelectedItem(BmtTask.getPlaceDestination());
		placeFinalCbm.setSelectedItem(BmtTask.getPlaceFinal());
		configurationCbm.setSelectedItem(((BmtTask) task).getConfiguration());
		objectCbm.setSelectedItem(((BmtTask) task).getObject());
	}
}
