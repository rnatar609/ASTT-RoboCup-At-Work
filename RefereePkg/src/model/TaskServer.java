package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TaskServer 
{
	TaskSpec taskSpec;
	ServerSocket socket;
	int port = 8000;
	
	public TaskServer()
	{
		taskSpec = new TaskSpec();
	}
	
	private void getTaskSpecsFromUser() throws Exception
	{
		//At present, we are reading only one task spec.
		//Can be later extended to read n-number of task specs.
		taskSpec.getTaskSpecFromUser();
	}
	
	private void startTCPService()
	{
		char serveClient = 'n';
		char waitForConn = 'n';
		try
		{
			socket = new ServerSocket( port );
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
			do
			{
				System.out.println( "Listening on Server Socket..." );
				Socket clisock= socket.accept();
				System.out.println( "Connection Established with " + clisock.getInetAddress().getHostName() + " having IP address " + clisock.getInetAddress().getHostAddress() );
				System.out.println( "Would you like to serve this client (y/n)?" );
				serveClient = ( char ) br.read();
				br.read(); // read newline character 
				if ( serveClient == 'y' || serveClient == 'Y' )
				{
					sendTaskSpecToClient( clisock );
				}
				System.out.println( "Closing client socket..." );
				clisock.close();
				System.out.println( "Would you like to wait for a new connection (y/n)? " );
				waitForConn = (char) br.read();
				br.read(); //read the newline character
			} while ( waitForConn == 'y' || waitForConn == 'Y');
		} catch ( Exception e )
		{
			System.out.println( "In startTCPService: " + e.getMessage() );
		}
	}
	
	private void sendTaskSpecToClient( Socket clientSock ) 
	{
		try
		{
			OutputStream output= clientSock.getOutputStream();
			output.write( taskSpec.getTaskSpecString().getBytes() );
			System.out.println( "Task spec " + taskSpec.getTaskSpecString() + " sent to client." );
		} catch ( IOException e )
		{
			System.out.println( "While sending task specification to client: " + e.getMessage() );
		}
	}
	
	public static void main( String[] args ) throws Exception
	{
		//Step1: Read valid triplet elements from the config.txt file.
		if ( !TaskTriplet.getValidTripletElements() )
		{
			System.out.println( "Make appropriate corrections." );
			return; // exit application
		}
		//Step2: Get task specifications from user.
		TaskServer tServer = new TaskServer();
		tServer.getTaskSpecsFromUser();
		System.out.println( "Task Spec created successfully." );
		System.out.println( "The task spec is " + tServer.taskSpec.getTaskSpecString() );
		//Step3: Communicate with clients.
		tServer.startTCPService();
	}
}
