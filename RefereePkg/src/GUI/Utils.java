package GUI;

import java.io.File;

public class Utils {

	public final static String taskSpecExtension = "tsp";

	/*
	 * Get the extension of the tsp file.
	 */
	public static String getExtension(File f) {
		String extension = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			extension = s.substring(i + 1).toLowerCase();
		}
		return extension;
	}
	
	public static File correctFile(File file) {
		String extension = getExtension(file);
		if (extension == null
				|| !extension.equals(Utils.getTaskspecextension())) {
			String s = file.getName();
			int i = s.lastIndexOf('.');
			File newFile;
			if (i > 0) {
				newFile = new File(file.getParent() + File.separator
						+ s.substring(0, i) + "."
						+ Utils.getTaskspecextension());
			} else {
				newFile = new File(file.getAbsolutePath() + "."
						+ Utils.getTaskspecextension());
			}
			file = newFile;
		}
		return file;
	}


	public static String getTaskspecextension() {
		return taskSpecExtension;
	}
}