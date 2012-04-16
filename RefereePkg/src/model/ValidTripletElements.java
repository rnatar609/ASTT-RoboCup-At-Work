package model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 This class is implemented as a Singleton. 
*/
public class ValidTripletElements
{
	static private ValidTripletElements instance;
	List<String> validPlaces;
	List<String> validOrientations;
	List<Short> validPauses;
		
	private ValidTripletElements()
	{
		validPlaces = new ArrayList< String >();
		validOrientations = new ArrayList< String >();
		validPauses = new ArrayList< Short >();
	}
	
	private boolean populateLists() throws Exception
	{
		boolean allValid = true;
		ConfigFile cfg = new ConfigFile();
		cfg.loadProperties();
		Properties pr = cfg.getProperties();
		allValid &= populateValidPlaces( pr );
		allValid &= populateValidOrientations( pr );
		allValid &= populateValidPauses( pr );
		return allValid;
	}
	
	private boolean populateValidPlaces( Properties props )
	{
		boolean allValid = true;
		String str = props.getProperty( "places" );
		Scanner scnr = new Scanner( str ).useDelimiter( "(\\s*\\,\\s*)" );
		while ( scnr.hasNext() )
		{
			String s = new String( scnr.next() );
			if ( s.length() == 2 && Character.isLetter( s.charAt( 0 ) ) && Character.isUpperCase( s.charAt( 0 ) ) && Character.isDigit( s.charAt( 1 ) ) )
			{
				validPlaces.add( s );
			}	
			else
			{
				System.out.println( "Ivalid place label " + s + " in Config File. Place label format should be ([A-Z][0-9])." );
				allValid = false;
			}
		}
		return allValid;
	}
	
	private boolean populateValidOrientations( Properties props )
	{
		boolean allValid = true;
		String str = props.getProperty( "orientations" );
		String[] dir = getCompassDirectionStrings();
		Scanner scnr = new Scanner( str ).useDelimiter( "(\\s*\\,\\s*)" );
		int i;
		while ( scnr.hasNext() )
		{
			String s = new String( scnr.next() );
			for ( i = 0; i < 32; i++ )
			{
				if ( s.equals( dir[ i ] ) )
				{
					validOrientations.add( s );
					break;
				}
			}
			if ( i == 32 )
			{
				System.out.println( "Unrecognised compass direction string " + s );
				allValid = false;
			}
		}
		return allValid;
	}
	
	private boolean populateValidPauses( Properties props )
	{
		boolean allValid = true;
		String str = props.getProperty( "pauses" );
		Scanner scnr = new Scanner( str ).useDelimiter( "(\\s*\\,\\s*)" );
		while ( scnr.hasNextShort() )
		{
			short i = scnr.nextShort();
			if ( i > 0 && i < 10 )
			{
				validPauses.add( i );
			}
			else
			{
				System.out.println( "Invalid pause duration " + i + " in Config File. Pause should be an integer between 1 and 9 (both inclusive).");
				allValid = false;
			}
		}
		return allValid;
	}
	
	private String[] getCompassDirectionStrings()
	{
		String str = new String( "N, E, S, W, NE, SE, SW, NW, NNE, ENE, ESE, SSE, SSW, WSW, WNW, NNW, NbE, EbN, EbS, SbE, SbW, WbS, WbN, NbW, NEbN, NEbE, SEbE, SEbS, SWbS, SWbW, NWbW, NWbN" );
		String[] dir = new String[ 32 ];
		Scanner scnr =  new Scanner( str ).useDelimiter( "(\\, )" );
		for ( int i = 0; i < 32 && scnr.hasNext() ; i++ )
		{
			dir[ i ] = new String( scnr.next() ); 
		}
		return dir;
	}
	
	public boolean readFromConfigFile() throws Exception
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		boolean allValid = vte.populateLists();
		if ( !allValid )
		{
			System.out.println( "There are invalid values in the Config File." );
		}
		return allValid;
	}
	
	public static void displayValidTripletElements()
	{
		ValidTripletElements vte = ValidTripletElements.getInstance();
		System.out.println( "Here are the valid model Elements:" );
		System.out.print( "Valid Places: " );
		for ( int i = 0; i < vte.validPlaces.size(); i++ )
		{
			System.out.print( vte.validPlaces.get( i ) + " " );
		}
		System.out.println();
		System.out.print( "Valid Orientations: ");
		for ( int i = 0; i < vte.validOrientations.size(); i++ )
		{
			System.out.print( vte.validOrientations.get( i ) + " " );
		}
		System.out.println();
		System.out.print( "Valid Pauses: " );
		for ( int i = 0; i < vte.validPauses.size(); i++)
		{
			System.out.print( vte.validPauses.get( i ) + " ");
		}
		System.out.println();
	}
	
	public boolean isPlaceValid( String s )
	{
		//System.out.println( "User-given place " + s );
		Iterator<String> iterator = validPlaces.iterator();
		while ( iterator.hasNext( ))
		{
			if ( s.equals( iterator.next() ) )
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isOrientationValid( String s )
	{
		//System.out.println( "User-given Orientation: " + s );
		Iterator<String> iterator = validOrientations.iterator();
		while ( iterator.hasNext() )
		{
			if ( s.equals( iterator.next() ) ) 
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isPauseValid( String s )
	{
		//System.out.println( "User-given Pause " + s );
		short in = Short.parseShort( s );
		Iterator<Short> iterator = validPauses.iterator();
		while ( iterator.hasNext() )
		{
			if ( iterator.next() == in )
			{
				return true;
			}
		}
		return false;
	}
	
	public static ValidTripletElements getInstance()
	{
		if ( instance == null )
		{
			instance = new ValidTripletElements();
		}
		return instance;
	}
	
	
	public List<String> getValidPlaces() 
	{			
		return validPlaces;
	}

	public List<String> getValidOrientations() 
	{
		return validOrientations;
	}

	public List<Short> getValidPauses() 
	{
		return validPauses;
	}
}
