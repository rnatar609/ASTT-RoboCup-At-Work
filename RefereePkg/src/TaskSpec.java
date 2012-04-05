
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class TaskSpec 
{
	List<TaskTriplet> taskTripletList;

	public TaskSpec()
	{
		taskTripletList = new ArrayList<TaskTriplet>();
	}
	
	public String getTaskSpecString()
	{
		String s =  new String();
		s = s.concat( "<" );
		Iterator<TaskTriplet> iterator = taskTripletList.iterator();
		while ( iterator.hasNext() )
		{
			s = s.concat( iterator.next().getTaskTripletString() );
			if ( iterator.hasNext() )
			{
				s = s.concat( ", " );
			}
		}
		s = s.concat( ">" );
		return s;
	}
	
	public void addTriplet( TaskTriplet triplet )
	{
		taskTripletList.add( triplet );
	}
	
	/**
	 This method reads a task specification from the user and stores in the invoking object.
	*/
	public void getTaskSpecFromUser() throws Exception
	{
		char choice = 'n';
		boolean atLeastOneTriplet = false;
		try
		{
			BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
			do
			{			
				TaskTriplet nextTriplet = new TaskTriplet();
				nextTriplet.getTaskTripletFromUser();
				System.out.println( "Add triplet " + nextTriplet.getTaskTripletString() + " to task (y/n)?" );
				char confirm = ( char ) br.read();
				br.read(); //reading newline character
				if ( confirm == 'y' | confirm == 'Y' )
				{
					this.addTriplet( nextTriplet );
					System.out.println( "Added " + nextTriplet.getTaskTripletString() + " to task successfully." );
					System.out.println( "The task specification string now looks like: " + getTaskSpecString() );
					atLeastOneTriplet = true;
				}
				if ( atLeastOneTriplet )
				{
					System.out.println( "Would you like to enter more triplets (y/n)?" );
					choice = ( char ) br.read();
					br.read(); //reading newline character
				}
				else
				{
					System.out.println("At least one triplet expected in task specification.");
				}
			} while ( (choice == 'y' || choice == 'Y' ) || !atLeastOneTriplet );
		} catch ( Exception e )
		{
			System.out.println( "Caught exception in TaskServer.getTaskSpec(): " + e.getMessage() );
			throw e;
		}
	}
}
