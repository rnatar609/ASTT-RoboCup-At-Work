package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	static private Map instance;
	static BufferedImage map;

	static String mapFullName;

	public static BufferedImage loadBackgroundMap() {
		 mapFullName = System.getProperty("user.home") + File.separator
							+ "backgroundMap.png";		
		File file = new File(mapFullName);
		try {
		map = ImageIO.read(file);
			return map;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map getInstance()
	{
		if ( instance == null )
		{
			instance = new Map();
		}
		return instance;
	}
}
