package fr.umlv.anaconda.tools;

import java.io.File;
import java.util.ArrayList;

public class Extension {

	private static ArrayList imagesExt = new ArrayList();
	static {
		imagesExt.add(".jpg");
		imagesExt.add(".JPG");
		imagesExt.add(".jpeg");
		imagesExt.add(".JPEG");
		imagesExt.add(".gif");
		imagesExt.add(".GIF");
		imagesExt.add(".bmp");
		imagesExt.add(".BMP");
		imagesExt.add(".png");
		imagesExt.add(".PNG");
	}

	public static boolean isImage(String file_name) {
		int extIndex = file_name.lastIndexOf('.');
		if (extIndex != -1) {
			if (imagesExt.contains(file_name.substring(extIndex)))
				return true;

		}
		return false;
	}
	
	/**
	 * Tells wether the file is runnable or not
	 * @param f the file to analyse
	 * @return tru if the file is runnable
	 */
	public static boolean isExe(File f) {
		if (Tools.isWin())
			if (f.getName().endsWith(".exe"))
				return true;
			else
				return false;
		else // on n'est pas sous windows... a quoi se fier ? droits ?
			return true;
	}


}
