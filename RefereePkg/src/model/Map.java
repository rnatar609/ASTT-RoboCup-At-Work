package model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import view.MapArea;
import view.Utils;

public class Map {
	private static final String imageFormat = new String("png");
	private static BufferedImage map;

	public static BufferedImage loadBackgroundMap(ConfigFile cfgFile, String pathPrefix) {
		String filePath = null;
		try {
			String fileName = cfgFile.getProperties().getProperty("map");
			filePath = pathPrefix + System.getProperty("file.separator") + fileName;
			File backgroundFile = new File(filePath);
			map = ImageIO.read(backgroundFile);
			System.out.println("background file "
					+ backgroundFile.getAbsolutePath() + " exists.");
			return map;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage() + " " + filePath);
			return null;
		}

	}

	public static boolean saveTaskSpecMap(File file, MapArea mapArea) {
		file = Utils.correctFile(file);
		try {
			BufferedImage image = new BufferedImage(mapArea.getMapPane()
					.getWidth(), mapArea.getMapPane().getHeight(),
					BufferedImage.TYPE_INT_RGB);
			mapArea.getMapPane().paintAll(image.getGraphics());
			ImageIO.write(image, imageFormat, new File(file.getPath()
					.substring(0, file.getPath().length() - 4)
					+ "."
					+ imageFormat));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
		return true;
	}
}
