package fr.umlv.anaconda.tools;

import java.util.ArrayList;

public class Extension {

	private static ArrayList imagesExt = new ArrayList();
	static {
		imagesExt.add(".jpg");
		imagesExt.add(".jpeg");
		imagesExt.add(".gif");
		imagesExt.add(".bmp");
		imagesExt.add(".png");
	}

	public static boolean isImage(String file_name) {
		int extIndex = file_name.lastIndexOf('.');
		if (extIndex != -1) {
			if (imagesExt.contains(file_name.substring(extIndex)))
				return true;

		}
		return false;
	}

}
