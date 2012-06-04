package Java;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.zeromq.ZMQ;


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
public class JavaClientGUI extends javax.swing.JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField jTextField_ipaddress;
	private JLabel jLabel_ipaddress;
	private JButton jButton_Complete;
	//private JButton jButton_Start;
	//private JButton jButton_Ready;
	private JButton jButton_Connect;
	private JLabel jStatusBar;
	private JLabel jLabel_port;
	private JTextField jTextField_port;
	
	String taskSpecFromServer;
	String teamName;
	String tripletAcknowledge;
	String ready;
	String start;
	String complete;
	String serverIP;
	String port;
	
	ZMQ.Context RobotModule = ZMQ.context(1);
	ZMQ.Socket RobotClient_Socket = RobotModule.socket(ZMQ.REQ);

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JavaClientGUI jClient = new JavaClientGUI();
				jClient.setLocationRelativeTo(null);
				jClient.setVisible(true);
				//System.out.println("ServerIP: " + args[0] + " Server port: " + args[1]);
				//inst.obtainTaskSpecFromServer(args[0], args[1]);
			}
		});
	}
	
	public JavaClientGUI() {
		super();
		initGUI();
		teamName = new String("Team-Name");
		ready = new String("Ready to Roll");
		complete = new String("Task Completed");
		tripletAcknowledge = new String("Task Specification Received");
	}
	
	private void initGUI() {
		try {
			this.setTitle("RoboCup@Work");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			//START >>  jTextField_ipaddress
			jTextField_ipaddress = new JTextField();
			getContentPane().add(jTextField_ipaddress);
			jTextField_ipaddress.setBounds(56, 61, 191, 28);
			//END <<  jTextField_ipaddress
			//START >>  jTextField_port
			jTextField_port = new JTextField();
			getContentPane().add(jTextField_port);
			jTextField_port.setBounds(259, 61, 102, 28);
			//END <<  jTextField_port
			//START >>  jLabel_ipaddress
			jLabel_ipaddress = new JLabel();
			getContentPane().add(jLabel_ipaddress);
			jLabel_ipaddress.setText("Server IP");
			jLabel_ipaddress.setBounds(62, 39, 53, 16);
			//END <<  jLabel_ipaddress
			//START >>  jLabel_port
			jLabel_port = new JLabel();
			getContentPane().add(jLabel_port);
			jLabel_port.setText("Port");
			jLabel_port.setBounds(265, 39, 25, 16);
			//END <<  jLabel_port
			//START >>  jStatusBar
			jStatusBar = new JLabel();
			getContentPane().add(jStatusBar);
			jStatusBar.setBounds(6, 254, 388, 18);
			//END <<  jStatusBar
			//START >>  jButton_Connect
			jButton_Connect = new JButton();
			getContentPane().add(jButton_Connect);
			jButton_Connect.setText("Connect");
			jButton_Connect.setBounds(127, 125, 138, 29);
			//END <<  jButton_Connect
			//START >>  jButton_Ready
			//END <<  jButton_Ready
			//START >>  jButton_Start
			//jButton_Start = new JButton();
			//getContentPane().add(jButton_Start);
			//jButton_Start.setText("Start");
			//jButton_Start.setBounds(127, 174, 138, 29);
			//END <<  jButton_Start
			//START >>  jButton_Complete
			jButton_Complete = new JButton();
			getContentPane().add(jButton_Complete);
			jButton_Complete.setText("Task Complete");
			jButton_Complete.setBounds(127, 198, 138, 29);
			//END <<  jButton_Complete
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
		jButton_Connect.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	serverIP = jTextField_ipaddress.getText();
            	port = jTextField_port.getText();
            	JavaClientGUI jClient = new JavaClientGUI();
            	jClient.obtainTaskSpecFromServer(serverIP, port);
            }
        });
		jButton_Complete.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	RobotClient_Socket.send(complete.getBytes(), 0);
        		System.out.println("Task Completed");
        		jStatusBar.setText("Task Completed");
        		RobotClient_Socket.close();
        		RobotModule.term();
            }
        });
	}

	
	
	public void readymodule(ZMQ.Socket RobotClient_Socket){
		RobotClient_Socket.send(ready.getBytes(), 1);
		System.out.println("Ready");
		jStatusBar.setText("Ready");
	}
	
	/*public void startmodule() {
	}{
		byte[] reply_start = RobotClient_Socket.recv(0);
		start = new String(reply_start);
		System.out.println("Starting the navigation");
	    jStatusBar.setText("Starting the navigation");
	}*/
	
	public void obtainTaskSpecFromServer(String serverIP, String port) {
		String connectStr = new String("tcp://" + serverIP + ":" + port);
		System.out.println("Connecting to server... ");
		RobotClient_Socket.connect(connectStr);
		RobotClient_Socket.send(teamName.getBytes(), 0);
		System.out.println("Sent team name to server... " + teamName);
		byte[] reply_taskspec = RobotClient_Socket.recv(0);
		taskSpecFromServer = new String(reply_taskspec);
		System.out.println("Received taskSpecification: " + taskSpecFromServer);
		jStatusBar.setText("Received taskSpecification from Referee");
		RobotClient_Socket.send(tripletAcknowledge.getBytes(), 0);
		//RobotClient_Socket.send(ready.getBytes(), 1);
		//System.out.println("Ready");
		//jStatusBar.setText("Ready");
		/*byte[] reply_start = RobotClient_Socket.recv(0);
		start = new String(reply_start);
		System.out.println("Starting the navigation");
		jStatusBar.setText("Starting the navigation");*/
		//RobotClient_Socket.close();
		//RobotModule.term();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
