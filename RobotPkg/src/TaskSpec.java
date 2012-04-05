
import java.util.*;
import java.util.regex.*;

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
	
	public void displayByTripletElements()
	{
		short count = 0;
		Iterator<TaskTriplet> iterator = taskTripletList.iterator();
		while ( iterator.hasNext() )
		{
			System.out.println( "Task Triplet " + count );
			iterator.next().displayTripletElements();
			count++;
		}
	}
	
	public void addTriplet( TaskTriplet triplet )
	{
		taskTripletList.add( triplet );
	}
	
	private String removeSpaces( String str )
	{
		StringTokenizer tokens = new StringTokenizer(str," ",false);
		String newStr = "";
		while ( tokens.hasMoreElements() ) 
		{
			newStr += tokens.nextElement();
		}
		return newStr;
	}
	/**
	 This method parses a task specification string and stores the triplets in the invoking object.
	*/
	public boolean setTaskSpec( String specStr )
	{
		boolean atLeastOneTriplet = false;
		specStr = removeSpaces( specStr );
		System.out.println( "After removing spaces " + specStr );
		try
		{
			Pattern pat = Pattern.compile( TaskTriplet.getValidTripletPattern() );
			Matcher m = pat.matcher( specStr );
			do
			{
				TaskTriplet nextTaskTriplet =  new TaskTriplet();
				if( m.find() )
				{
					nextTaskTriplet.setPlace( m.group( 1 ) );
					nextTaskTriplet.setOrientation( m.group( 2 ) );
					nextTaskTriplet.setPause( m.group( 3 ) );
					addTriplet( nextTaskTriplet );
					atLeastOneTriplet = true;
				}
			} while ( !m.hitEnd() );
		} catch ( Exception e )
		{
			System.out.println( "Caught exception in setTaskSpec. Error: " + e.getMessage() );
			return false;
		}
		if ( !atLeastOneTriplet )
		{
			System.out.println( "No valid triplet strings found in task specification." );
		}
		return atLeastOneTriplet;
	}
	
	public boolean compareTaskSpecStrings( String compareToStr )
	{
		String taskSpecInClient = removeSpaces( getTaskSpecString() );
		if ( taskSpecInClient.equals( removeSpaces( compareToStr ) ) )
		{
			return true;
		}
		return false;
	}
}