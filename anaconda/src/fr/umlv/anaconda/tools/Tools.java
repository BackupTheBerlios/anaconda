package fr.umlv.anaconda.tools;

import java.awt.Image;
import java.awt.image.ImageObserver;
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
	
	/**
	 * Returns a new Image to the correct rectangular size
	 * @param img the original Image
	 * @param width the wanted width
	 * @param height the wanted height
	 * @param io the linked ImageObserver
	 * @return the resized Image
	 */
	public static Image resizeImg(Image img, int width, int height, ImageObserver io) {
		int H = img.getHeight(io);
		int W = img.getWidth(io);
		Image image;
		if (H<=W){
			image = img.getScaledInstance(-1,height, Image.SCALE_FAST);
			// reduit pour le "nbe elts selectionnes"
		}
		else {
			image = img.getScaledInstance(width,-1, Image.SCALE_FAST);
		}
		return image;
	}
	
	/**
	 * Returns a new Image to the correct squarre size
	 * @param img the original Image
	 * @param size the wanted size
	 * @param io the linked ImageObserver
	 * @return the resized Image
	 */
	public static Image resizeImg(Image img, int size, ImageObserver io) {
		return resizeImg(img, size, size, io);
	}
	
	/**
	 * Tells wether the os is windows or not
	 * @return true if the OS is windows 
	 */
	public static boolean isWin(){
		//	a utiliser pour savoir comment lancer ?..
		final String os = System.getProperty("os.name");
		return (os.endsWith("NT")||os.endsWith("2000")||os.endsWith("XP"));
	}
}
