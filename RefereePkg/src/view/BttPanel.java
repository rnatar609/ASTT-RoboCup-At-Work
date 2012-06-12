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
import model.Task;

public class BttPanel extends CompetitionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GAP = 10;
	private JComboBox<String> placeBox;
	private JComboBox<String> configurationBox;
	private JComboBox<String> objectBox;
	private JComboBox<String> colorBox;
	private JComboBox<String> sizeBox;	
	public DefaultComboBoxModel<String> placeCbm;
	private DefaultComboBoxModel<String> configurationCbm;
	private DefaultComboBoxModel<String> objectCbm;
	private DefaultComboBoxModel<String> colorCbm;
	private DefaultComboBoxModel<String> sizeCbm;

	BttPanel(BorderLayout borderLayout) {
		super(borderLayout);
		createFlowPanelsInEastPanel();
		eastPanel.add(Box.createVerticalGlue());
		createButtons();
	}

	private void createFlowPanelsInEastPanel() {
		JPanel[] flowPanels = new JPanel[5];
		for (int i = 0; i < flowPanels.length; i++) {
			flowPanels[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		}
		placeBox = new JComboBox<String>();
		flowPanels[0].add(new JLabel("Place"));
		flowPanels[0].add(placeBox);
		eastPanel.add(flowPanels[0]);
		configurationBox = new JComboBox<String>();
		flowPanels[1].add(new JLabel("Configuration"));
		flowPanels[1].add(configurationBox);
		eastPanel.add(flowPanels[1]);
		objectBox = new JComboBox<String>();
		flowPanels[2].add(new JLabel("Object"));
		flowPanels[2].add(objectBox);
		eastPanel.add(flowPanels[2]);
		colorBox = new JComboBox<String>();
		flowPanels[3].add(new JLabel("Color"));
		flowPanels[3].add(colorBox);
		eastPanel.add(flowPanels[3]);
		sizeBox = new JComboBox<String>();
		flowPanels[4].add(new JLabel("Size"));
		flowPanels[4].add(sizeBox);
		eastPanel.add(flowPanels[4]);
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

	/**
	 * Update appropriate GUI components with the provided valid pause
	 * durations.
	 * 
	 * @param pauses
	 *            A list of short integers representing valid pauses.
	 */
	public void setValidColor(List<String> color) {
		colorCbm = new DefaultComboBoxModel<String>(
				color.toArray(new String[color.size()]));
		colorBox.setModel(colorCbm);
	}
	
	public void setValidSize(List<String> size) {
		sizeCbm = new DefaultComboBoxModel<String>(
				size.toArray(new String[size.size()]));
		sizeBox.setModel(sizeCbm);
	}
	
	public void taskSpecChanged(ArrayList<BmtTask> bmtTaskList) {
		sequenceTableModel.clearColumn(0);
		sequenceTableModel.setRowCount(bmtTaskList.size());
		for (int i = 0; i < bmtTaskList.size(); i++) {
			sequenceTableModel.setValueAt(((Task) bmtTaskList.get(i)).getString(),
					i, 0);
		}
	}
}
