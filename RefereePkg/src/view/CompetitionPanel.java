package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import model.TaskTriplet;
import model.TripletEvent;

public class CompetitionPanel extends JPanel{
	
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
	private SequenceTableM sequenceTableM;
	private DefaultTableCellRenderer rendEntries;
    private JPanel eastPanel;
    private JComboBox<String>[] dropDownLists;
    private String[] dropDownListTitles;
    private int numberOfDropDownLists;
    
	private class SequenceTableM extends DefaultTableModel {
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

	
	private void addButtonsToEastPanel() {
		eastPanel.add(addButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		eastPanel.add(updateButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		eastPanel.add(deleteButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		eastPanel.add(Box.createVerticalGlue());
		eastPanel.add(upButton);
		eastPanel.add(Box.createVerticalStrut(GAP));
		eastPanel.add(downButton);
		eastPanel.add(Box.createVerticalGlue());
	}
	
	private void createButtons(){
		addButton = new JButton();
	    addButton.setAlignmentX(CENTER_ALIGNMENT);
	    updateButton = new JButton();
	    updateButton.setAlignmentX(CENTER_ALIGNMENT);
	    deleteButton = new JButton();
	    deleteButton.setAlignmentX(CENTER_ALIGNMENT);
	    upButton = new JButton();
	    upButton.setAlignmentX(CENTER_ALIGNMENT);
	    downButton = new JButton();
	    downButton.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	public void createTableScrollPaneInPanel() {
		sequenceTableScrollPane = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sequenceTableM = new SequenceTableM();
		sequenceTableM.addColumn("Subgoals");
		sequenceTable = new JTable(sequenceTableM);
		rendEntries = new DefaultTableCellRenderer();
		sequenceTable.getColumn("Subgoals").setCellRenderer(rendEntries);
		rendEntries.setHorizontalAlignment(JLabel.CENTER);
		sequenceTableScrollPane.setViewportView(sequenceTable);
		sequenceTableScrollPane
				.setPreferredSize(sequenceTable.getPreferredSize());
		this.add(sequenceTableScrollPane, BorderLayout.CENTER);
	}
	
	public void createEastPanel(String listNames){
			eastPanel = new JPanel();
			eastPanel.setLayout(new BoxLayout(eastPanel,
					BoxLayout.Y_AXIS));
			eastPanel.add(Box.createVerticalStrut(GAP));
			createFlowPanelsInEastPanel(listNames);
			eastPanel.add(Box.createVerticalGlue());
	    	createButtons();
		    addButtonsToEastPanel();
			this.add(eastPanel, BorderLayout.EAST);
	}
	
	private void createFlowPanelsInEastPanel(String listNames) {
		StringTokenizer strTokens = new StringTokenizer(listNames);
		JPanel[] flowPanels = new JPanel[strTokens.countTokens()];
		dropDownLists = new JComboBox[strTokens.countTokens()];
		numberOfDropDownLists = strTokens.countTokens();
		dropDownListTitles = new String[numberOfDropDownLists];
		int i=0;
		while (strTokens.hasMoreTokens()){
			flowPanels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			dropDownListTitles[i]=strTokens.nextToken();
			JLabel title = new JLabel(dropDownListTitles[i]);
			title.setHorizontalAlignment(JLabel.LEFT);
			title.setHorizontalTextPosition(SwingConstants.LEFT);
			flowPanels[i].add(title);
			dropDownLists[i] = new JComboBox<String>();
			flowPanels[i].add(dropDownLists[i]);
			eastPanel.add(flowPanels[i]);
			i++;
		}
	}
	
    public CompetitionPanel(String name){
	    super();
    	competitionName = name;
   }
    
   public String getCompetitionName(){
	   return competitionName;
   }
   
   public JTable getSequenceTable(){
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
   
   //This will be invoked only for navigation test. 
   public void taskSpecChanged(TripletEvent evt) {
	  sequenceTableM.clearColumn(0);
	  sequenceTableM.setRowCount(evt.getTaskTripletList().size());
      List<TaskTriplet> tTL = evt.getTaskTripletList();
      for (int i = 0; i < tTL.size(); i++) {
	      sequenceTableM.setValueAt(tTL.get(i).getTaskTripletString(), i, 0);
      }
   }
   
   public void setComboBoxSelected(String listTitle, String selectedValue) {
	   for (int i = 0; i < numberOfDropDownLists; i++){
		   if(dropDownListTitles[i].equals(listTitle)){
			   dropDownLists[i].setSelectedItem(selectedValue);
			   break;
		   }
	   }
   }
   
   public JComboBox<String> getComboBoxByName(String boxName){
	   for (int i = 0; i < numberOfDropDownLists; i++){
		   if(dropDownListTitles[i].equals(boxName)){
			   return dropDownLists[i];
		   }
	   }
	   return null;
   }
}
