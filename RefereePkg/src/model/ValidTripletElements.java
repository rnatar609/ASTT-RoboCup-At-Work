package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * This class is implemented as a Singleton.
 */
public class ValidTripletElements {
	static private ValidTripletElements instance;
	private LinkedHashMap<String, Point> validBNTPositions;
	private List<String> validBNTOrientations;
	private List<String> validBNTPauses;
	private LinkedHashMap<String, Point> validBMTPositions;
	private List<String> validBMTConfigurations;
	private List<String> validBMTObjects;
	private LinkedHashMap<String, Point> validBTTPositions;
	private List<String> validBTTConfigurations;
	private List<String> validBTTObjects;
	private List<String> validBTTSituations;
	private LinkedHashMap<String, Point> validCTTPositions;
	private List<String> validCTTConfigurations;
	private List<String> validCTTObjects;
	private List<String> validCTTSituations;
	ConfigFile cfgFile;

	private ValidTripletElements() {
		validBNTPositions = new LinkedHashMap<String, Point>();
		validBNTOrientations = new ArrayList<String>();
		validBNTPauses = new ArrayList<String>();
		validBMTPositions = new LinkedHashMap<String, Point>();
		validBMTConfigurations = new ArrayList<String>();
		validBMTObjects = new ArrayList<String>();
		validBTTPositions = new LinkedHashMap<String, Point>();
		validBTTConfigurations = new ArrayList<String>();
		validBTTObjects = new ArrayList<String>();
		validBTTSituations = new ArrayList<String>();
		validCTTPositions = new LinkedHashMap<String, Point>();
		validCTTConfigurations = new ArrayList<String>();
		validCTTObjects = new ArrayList<String>();
		validCTTSituations = new ArrayList<String>();
	}

	private boolean populateLists(ConfigFile cfg) throws Exception {
		boolean allValid = true;
		Properties pr = cfg.getProperties();
		allValid &= populateValidPlaces(pr);
		allValid &= populateValidOrientations(pr);
		allValid &= populateValidPauses(pr);
		return allValid;
	}

	private boolean parsePositions(String str,
			CompetitionIdentifier competitionID) {
		boolean allValid = true;
		LinkedHashMap<String, Point> hashMap = null;
		if (competitionID == CompetitionIdentifier.BNT)
			hashMap = validBNTPositions;
		else if (competitionID == CompetitionIdentifier.BMT)
			hashMap = validBMTPositions;
		else if (competitionID == CompetitionIdentifier.BTT)
			hashMap = validBTTPositions;
		else if (competitionID == CompetitionIdentifier.CTT)
			hashMap = validCTTPositions;
		// Scanner scnr = new Scanner( str ).useDelimiter( "(\\s*,\\,\\s*)" );
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String tri = new String(scnr.next());
			Scanner scnr1 = new Scanner(tri)
					.useDelimiter("(\\s*[\\/|\\(|\\)]\\s*)");
			String s = new String(scnr1.next());
			if (s.length() == 2 && Character.isLetter(s.charAt(0))
					&& Character.isUpperCase(s.charAt(0))
					&& Character.isDigit(s.charAt(1))) {
				String sx = new String(scnr1.next());
				int x = 0;
				try {
					x = Integer.parseInt(sx);
				} catch (NumberFormatException e) {
					System.out
							.println("Invalid place label "
									+ s
									+ " in Config File. Place label format should be ([A-Z][0-9]).");
					allValid = false;
				}
				String sy = new String(scnr1.next());
				int y = 0;
				try {
					y = Integer.parseInt(sy);
				} catch (NumberFormatException e) {
					System.out
							.println("Invalid place label "
									+ s
									+ " in Config File. Place label format should be ([A-Z][0-9]).");
					allValid = false;
				}
				// validBNTPositions.put(s, new Point(x, y));
				hashMap.put(s, new Point(x, y));
			} else {
				System.out
						.println("Invalid place label "
								+ s
								+ " in Config File. Place label format should be ([A-Z][0-9]).");
				allValid = false;
			}

		}
		return allValid;
	}

	private boolean populateValidPlaces(Properties props) {

		return parsePositions(props.getProperty("bnt.places"),
				CompetitionIdentifier.BNT);
	}

	private boolean populateValidOrientations(Properties props) {
		boolean allValid = true;
		String str = props.getProperty("bnt.orientations");
		String[] dir = getCompassDirectionStrings();
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		int i;
		while (scnr.hasNext()) {
			String s = new String(scnr.next());
			for (i = 0; i < 32; i++) {
				if (s.equals(dir[i])) {
					validBNTOrientations.add(s);
					break;
				}
			}
			if (i == 32) {
				System.out
						.println("Unrecognised compass direction string " + s);
				allValid = false;
			}
		}
		return allValid;
	}

	private boolean populateValidPauses(Properties props) {
		boolean allValid = true;
		String str = props.getProperty("bnt.pauses");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNextShort()) {
			String i = new String(scnr.next());
			validBNTPauses.add(i);
		}
		return allValid;
	}

	private String[] getCompassDirectionStrings() {
		String str = new String(
				"N, E, S, W, NE, SE, SW, NW, NNE, ENE, ESE, SSE, SSW, WSW, WNW, NNW, NbE, EbN, EbS, SbE, SbW, WbS, WbN, NbW, NEbN, NEbE, SEbE, SEbS, SWbS, SWbW, NWbW, NWbN");
		String[] dir = new String[32];
		Scanner scnr = new Scanner(str).useDelimiter("(\\, )");
		for (int i = 0; i < 32 && scnr.hasNext(); i++) {
			dir[i] = new String(scnr.next());
		}
		return dir;
	}

	private String constructPlacePattern() {
		String str = new String("(");
		int placescount = validBNTPositions.size();
		Set<Entry<String, Point>> positions = validBNTPositions.entrySet();
		Iterator<Entry<String, Point>> itr = positions.iterator();
		for (int i = 0; i < placescount; i++) {
			str = str.concat(itr.next().getKey());
			// System.out.println(validPositions.entrySet());
			if (i < placescount - 1) {
				str = str.concat("|");
			}
		}
		str = str.concat(")");
		System.out.println("Valid place pattern: " + str);
		return str;
	}

	private String constructOrientationPattern() {
		String str = new String("(");
		Iterator<String> iterator = validBNTOrientations.iterator();
		while (iterator.hasNext()) {
			str = str.concat(iterator.next());
			if (iterator.hasNext()) {
				str = str.concat("|");
			}
		}
		str = str.concat(")");
		System.out.println("Valid orientation pattern: " + str);
		return str;
	}

	private String constructPausePattern() {
		String str = new String("(");
		Iterator<String> iterator = validBNTPauses.iterator();
		while (iterator.hasNext()) {
			str = str.concat(iterator.next().toString());
			if (iterator.hasNext()) {
				str = str.concat("|");
			}
		}
		str = str.concat(")");
		System.out.println("Valid pause pattern: " + str);
		return str;
	}

	public boolean readFromConfigFile(ConfigFile cfg) throws Exception {
		cfgFile = cfg;
		ValidTripletElements vte = ValidTripletElements.getInstance();
		boolean allValid = vte.populateLists(cfg);
		if (!allValid) {
			System.out.println("There are invalid values in the Config File.");
		}
		return allValid;
	}

	/*
	 * boolean isPlaceValid(String s) { // System.out.println(
	 * "User-given place " + s ); Iterator<Position> iterator =
	 * validPositions.iterator(); while (iterator.hasNext()) { if
	 * (s.equals(iterator.next())) { return true; } } return false; }
	 */

	boolean isOrientationValid(String s) {
		// System.out.println( "User-given Orientation: " + s );
		Iterator<String> iterator = validBNTOrientations.iterator();
		while (iterator.hasNext()) {
			if (s.equals(iterator.next())) {
				return true;
			}
		}
		return false;
	}

	boolean isPauseValid(String s) {
		// System.out.println( "User-given Pause " + s );
		String in = s;
		Iterator<String> iterator = validBNTPauses.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(in)) {
				return true;
			}
		}
		return false;
	}

	public String getValidBNTPattern() {
		String patStr = new String("\\(");
		patStr = patStr.concat(constructPlacePattern());
		patStr = patStr.concat("\\," + constructOrientationPattern());
		patStr = patStr.concat("\\," + constructPausePattern() + "\\)");
		System.out.println("Valid triplet pattern " + patStr);
		return patStr;
	}

	public static ValidTripletElements getInstance() {
		if (instance == null) {
			instance = new ValidTripletElements();
		}
		return instance;
	}

	public LinkedHashMap<String, Point> getValidBNTPositions() {
		return validBNTPositions;
	}

	public List<String> getValidBNTOrientations() {
		return validBNTOrientations;
	}

	public List<String> getValidBNTPauses() {
		return validBNTPauses;
	}

	public LinkedHashMap<String, Point> getValidBMTPositions() {
		parsePositions(cfgFile.getPropertyByName("bmt.places"),
				CompetitionIdentifier.BMT);
		return validBMTPositions;
	}

	public List<String> getValidBMTConfigurations() {
		String str = cfgFile.getPropertyByName("bmt.configurations");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validBMTConfigurations.add(i);
		}
		return validBMTConfigurations;
	}

	public List<String> getValidBMTObjects() {
		String str = cfgFile.getPropertyByName("bmt.objects");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validBMTObjects.add(i);
		}
		return validBMTObjects;
	}

	public LinkedHashMap<String, Point> getValidBTTPositions() {
		parsePositions(cfgFile.getPropertyByName("btt.places"),
				CompetitionIdentifier.BTT);
		return validBTTPositions;
	}

	public List<String> getValidBTTConfigurations() {
		String str = cfgFile.getPropertyByName("btt.configurations");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		validBTTConfigurations.add(" "); // empty is a must for possible for
											// initial tasks.
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validBTTConfigurations.add(i);
		}
		return validBTTConfigurations;
	}

	public List<String> getValidBTTObjects() {
		String str = cfgFile.getPropertyByName("btt.objects");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validBTTObjects.add(i);
		}
		return validBTTObjects;
	}

	public List<String> getValidBTTSituations() {
		String str = cfgFile.getPropertyByName("btt.situations");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validBTTSituations.add(i);
		}
		return validBTTSituations;
	}

	public LinkedHashMap<String, Point> getValidCTTPositions() {
		parsePositions(cfgFile.getPropertyByName("ctt.places"),
				CompetitionIdentifier.CTT);
		return validCTTPositions;
	}

	public List<String> getValidCTTConfigurations() {
		String str = cfgFile.getPropertyByName("ctt.configurations");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validCTTConfigurations.add(i);
		}
		return validCTTConfigurations;
	}

	public List<String> getValidCTTObjects() {
		String str = cfgFile.getPropertyByName("ctt.objects");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validCTTObjects.add(i);
		}
		return validCTTObjects;
	}

	public List<String> getValidCTTSituations() {
		String str = cfgFile.getPropertyByName("ctt.situations");
		Scanner scnr = new Scanner(str).useDelimiter("(\\s*\\,\\s*)");
		while (scnr.hasNext()) {
			String i = new String(scnr.next());
			validCTTSituations.add(i);
		}
		return validCTTSituations;
	}
}
