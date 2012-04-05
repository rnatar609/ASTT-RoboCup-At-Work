
import java.util.Scanner;

public class TaskTriplet 
{	
	String place;
	String orientation;
	Short pause;
		
	/* Default constructor */
	public TaskTriplet()
	{
		place = "D0";
		orientation = "N";
		pause = 1;
	}
		
	public void setPlace( String s )
	{
		place = s;
	}
	
	public void setOrientation( String s )
	{
		orientation = s;
	}
	
	public void setPause( String s )
	{
		pause = Short.parseShort( s );
	}
	
	public void setTaskTriplet( String tripletString )
	{
		Scanner input = new Scanner( tripletString ).useDelimiter( "((\\s*\\,\\s*)|(\\s*\\)))" );
		input.skip( "\\(" );
		place = input.next();
		orientation = input.next();
		pause = Short.parseShort( input.next() );
	}
	
	public String getPlace()
	{
		return place;
	}
	
	public String getOrientation()
	{
		return orientation;
	}
	
	public Short getPause()
	{
		return pause;
	}
	
	public String getTaskTripletString()
	{
		return ( new String( "(" + getPlace() + ", " + getOrientation() + ", " + getPause() + ")" ) );
	}
	
	public static String getValidTripletPattern()
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.getValidTripletPattern();		
	}
		
	public void displayTripletElements()
	{
		System.out.println("\tPlace: " + getPlace() );
		System.out.println("\tOrientation: " + getOrientation() );
		System.out.println("\tPause: " + getPause() );
	}
	
	public static boolean isValidTriplet( String triplet )
	{
		boolean valid = false;
		ValidTripletElements vte = ValidTripletElements.getInstance();
		Scanner input = new Scanner( triplet ).useDelimiter( "((\\s*\\,\\s*)|(\\s*\\)))" );
		input.skip( "\\(" );
		valid = ( vte.isPlaceValid( input.next() ) && vte.isOrientationValid( input.next() ) && vte.isPauseValid( input.next() ) );
		return valid;
	}
	
	public static boolean isValidTripletElementPlace( String s )
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isPlaceValid( s );
	}
	
	public static boolean isValidTripletElementOrientation( String s )
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isOrientationValid( s );
	}
	
	public static boolean isValidTripletElementPause( String s )
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.isPauseValid( s );
	}
	
	public static boolean getValidTripletElements() throws Exception
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		return vte.readFromConfigFile();
	}
}