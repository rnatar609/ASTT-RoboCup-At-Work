
import java.net.*;
import java.io.*;

public class TaskClient 
{
	TaskSpec taskSpec;
	String taskSpecFromServer;

	public TaskClient()
	{
		taskSpec = new TaskSpec();
	}
	
	public boolean obtainTaskSpecFromServer( String serverIP, String port ) 
	{
		try
		{
			Socket clientSocket = new Socket( serverIP , Integer.parseInt( port ) );
			BufferedReader bufferedReader =  new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
			char[] buffer = new char[ 500 ];
			int count= bufferedReader.read( buffer, 0, 500);
			String str = new String( buffer, 0, count );
			taskSpecFromServer = str;
			System.out.println( "Task spec string received: " + taskSpecFromServer );
			return ( taskSpec.setTaskSpec( str ) );
		} catch ( Exception e )
		{
			System.out.println( "Caught exception in obtainTaskSpecFromServer: " + e.getMessage() );
			return false;
		}
	}
	
	public static void main( String[] args ) throws Exception
	{
		// Note: The server IP address & port should be entered as command-line argument.
		if( args.length < 2 )
		{
			System.out.println( "Insufficient arguments:" + args.length + "\nEnter server IP addr and port as Command-line arguments." );
			return;
		}
		//Step1: Enlist valid triplet elements.
		if ( !TaskTriplet.getValidTripletElements() )
		{
			System.out.println( "Make appropriate corrections." );
			return; // exit application
		}
		//Step2: Obtain task specification from server
		TaskClient tClient = new TaskClient();
		if ( !tClient.obtainTaskSpecFromServer( args[ 0 ], args[ 1 ] ) )
		{
			System.out.println( "Cannot perform task. Aborting..." ); 
			return; //Present implementation: aborts execution. Alternatively, can connect to server again.
		}
		//Step3: Display task specification
		tClient.taskSpec.displayByTripletElements();
		//Compare if the task specification strings are the same. If not same, then some triplets in string from server were invalid.
		if ( tClient.taskSpec.compareTaskSpecStrings( tClient.taskSpecFromServer ) )
		{
			System.out.println( "Task specifications are identical.");
		}
		else
		{
			System.out.println( "Task specifications are not identical." );
		}
	}
}