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
import model.CttTask;
import model.Task;

public class CttPanel extends CompetitionPanel {

	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private JComboBox<String> situationBox;
	private JComboBox<String> placeBox;
	private JComboBox<String> configurationBox;
	private JComboBox<String> objectBox;
	private DefaultComboBoxModel<String> situationCbm;
	public DefaultComboBoxModel<String> placeCbm;
	private DefaultComboBoxModel<String> configurationCbm;
	private DefaultComboBoxModel<String> objectCbm;

	CttPanel(BorderLayout borderLayout) {
		super(borderLayout);
		createFlowPanelsInEastPanel();
		eastPanel.add(Box.createVerticalGlue());
		createButtons();
	}

	private void createFlowPanelsInEastPanel() {
		JPanel[] flowPanels = new JPanel[4];
		for (int i = 0; i < flowPanels.length; i++) {
			flowPanels[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		}
		situationBox = new JComboBox<String>();
		flowPanels[0].add(new JLabel("Situation"));
		flowPanels[0].add(situationBox);
		eastPanel.add(flowPanels[0]);
		placeBox = new JComboBox<String>();
		flowPanels[1].add(new JLabel("Place"));
		flowPanels[1].add(placeBox);
		eastPanel.add(flowPanels[1]);
		configurationBox = new JComboBox<String>();
		flowPanels[2].add(new JLabel("Configuration"));
		flowPanels[2].add(configurationBox);
		eastPanel.add(flowPanels[2]);
		objectBox = new JComboBox<String>();
		flowPanels[3].add(new JLabel("Object"));
		flowPanels[3].add(objectBox);
		eastPanel.add(flowPanels[3]);
	}

	public void setValidSituations(List<String> situations) {
		situationCbm = new DefaultComboBoxModel<String>(
				situations.toArray(new String[situations.size()]));
		situationBox.setModel(situationCbm);
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
		placeCbm = new DefaultComboBoxModel<String>(posString);
		placeBox.setModel(placeCbm);
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

	public void taskSpecChanged(ArrayList<CttTask> cttTaskList) {
		sequenceTableModel.clearColumn(0);
		sequenceTableModel.setRowCount(cttTaskList.size());
		for (int i = 0; i < cttTaskList.size(); i++) {
			super.sequenceTableModel.setValueAt(
					((Task) cttTaskList.get(i)).getString(), i, 0);
		}
	}

	public CttTask getSelectedTask() {
		CttTask t = new CttTask();
		t.setSituation((String) situationBox.getSelectedItem());
		t.setConfiguration((String) configurationBox.getSelectedItem());
		t.setPlace((String) placeBox.getSelectedItem());
		t.setObject((String) objectBox.getSelectedItem());
		return t;
	}

	public void setTaskBoxSected(Task task) {
		situationCbm.setSelectedItem(((CttTask) task).getSituation());
		configurationCbm.setSelectedItem(((CttTask) task).getConfiguration());
		placeCbm.setSelectedItem(((CttTask) task).getPlace());
		configurationCbm.setSelectedItem(((CttTask) task).getConfiguration());
		objectCbm.setSelectedItem(((CttTask) task).getObject());
	}
}
