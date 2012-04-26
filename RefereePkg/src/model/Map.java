package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.MapArea;
import view.Utils;

public class Map {
	private static final String imageFormat = new String("png");
	private static BufferedImage map;
	private static String mapFullName;

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
	
	public static boolean saveTaskSpecMap(File file, MapArea mapArea) {

		file = Utils.correctFile(file);
		try {
			BufferedImage image = new BufferedImage(mapArea.getMapPane().getWidth(),
					mapArea.getMapPane().getHeight(), BufferedImage.TYPE_INT_RGB);
			mapArea.getMapPane().paintAll(image.getGraphics());
			ImageIO.write(image, imageFormat, new File(file.getPath().substring(0, file.getPath().length()-4) + "." + imageFormat));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
		return true;
	}
}
