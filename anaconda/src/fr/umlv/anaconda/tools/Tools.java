package fr.umlv.anaconda.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

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
	public static boolean contains(File parent,String file_name,boolean isDirectory){
		File[] children = parent.listFiles();
		for(int i=0;i<children.length;i++){
			if(((children[i].isDirectory() && isDirectory) || (children[i].isFile() && !isDirectory)) && children[i].getName().equals(file_name)){
				return true;
			}
		}
		return false;
	}

	public static boolean contains(ArrayList afile, File file) {
		for (Iterator i = afile.iterator(); i.hasNext();) {
			File f = (File) i.next();
			if (((f.isDirectory() && file.isDirectory())
				|| (f.isFile() && file.isFile()))
				&& (file.getName().compareTo(f.getName())) == 0) {
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
