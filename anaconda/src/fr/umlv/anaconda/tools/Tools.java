package fr.umlv.anaconda.tools;

import java.io.File;

public class Tools {

	public static boolean contains(File parent, File file) {
		File[] children = parent.listFiles();
		for (int i = 0; i < children.length; i++) {
			if (((children[i].isDirectory() && file.isDirectory())
				|| (children[i].isFile() && file.isFile()))
				&& (file.getName().compareTo(children[i].getName())) == 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPointPoint(File f) {
		if (f.getName().compareTo("..") == 0)
			return true;
		return false;
	}
}
