package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import view.MapArea;
import view.Utils;

public class Map {
	private static final String imageFormat = new String("png");
	private static BufferedImage map;
	private static URL url;

	public static BufferedImage loadBackgroundMap() {
		url = Class.class.getResource("/recources/backgroundMap.png");
		;
		try {
			map = ImageIO.read(url);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
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
