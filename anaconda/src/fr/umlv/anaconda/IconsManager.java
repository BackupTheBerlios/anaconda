package fr.umlv.anaconda;

import javax.swing.ImageIcon;

import fr.umlv.anaconda.appearance.Themes;

/**
 * Loads all the images to use
 *
 */
public class IconsManager {

	final public static ImageIcon LOGO;
	
	final public static ImageIcon BACK;
	final public static ImageIcon NEXT;
	final public static ImageIcon COPY;
	final public static ImageIcon CUT;
	final public static ImageIcon PASTE;
	final public static ImageIcon FIND;
	final public static ImageIcon HOME;
	final public static ImageIcon GARBAGE;
	final public static ImageIcon REFRESH;
	final public static ImageIcon ONFOCUSBACK;
	final public static ImageIcon ONFOCUSNEXT;
	final public static ImageIcon ONFOCUSCOPY;
	final public static ImageIcon ONFOCUSCUT;
	final public static ImageIcon ONFOCUSPASTE;
	final public static ImageIcon ONFOCUSFIND;
	final public static ImageIcon ONFOCUSHOME;
	final public static ImageIcon ONFOCUSGARBAGE;
	final public static ImageIcon ONFOCUSREFRESH;

	final public static ImageIcon big_father_icon ;	
	final public static ImageIcon big_folder_icon ;
	final public static ImageIcon big_image_icon ;
	final public static ImageIcon big_exe_icon ;
	final public static ImageIcon big_file_icon ;

	final public static ImageIcon small_father_icon;
	final public static ImageIcon small_folder_icon;
	final public static ImageIcon small_image_icon;
	final public static ImageIcon small_exe_icon ;
	final public static ImageIcon small_file_icon;
	
	final public static ImageIcon focus_icon;
	final public static int BIG_ICONS = 0;
	final public static int SMALL_ICONS = 1;

	final public static String images_path = Themes.getCurrentPath();
	
	static{
		
		/*CHARGEMENT DU LOGO */
		LOGO = new ImageIcon(IconsManager.class.getResource(images_path + "anaconda_logo.gif"));
		/* CHARGEMENT DES ICONES DE LA BARRE D'OUTILS */
		BACK = new ImageIcon(IconsManager.class.getResource(images_path + "cancel.gif"));
		NEXT = new ImageIcon(IconsManager.class.getResource(images_path + "redo.gif"));
		COPY = new ImageIcon(IconsManager.class.getResource(images_path + "copy.gif"));
		CUT = new ImageIcon(IconsManager.class.getResource(images_path + "cut.gif"));
		PASTE = new ImageIcon(IconsManager.class.getResource(images_path + "paste.gif"));
		FIND = new ImageIcon(IconsManager.class.getResource(images_path + "find.gif"));
		HOME = new ImageIcon(IconsManager.class.getResource(images_path + "home.gif"));
		GARBAGE = new ImageIcon(IconsManager.class.getResource(images_path + "garbage.gif"));
		REFRESH = new ImageIcon(IconsManager.class.getResource(images_path + "refresh.gif"));
		ONFOCUSBACK = new ImageIcon(IconsManager.class.getResource(images_path + "onfocuscancel.gif"));
		ONFOCUSNEXT = new ImageIcon(IconsManager.class.getResource(images_path + "onfocusredo.gif"));
		ONFOCUSCOPY = new ImageIcon(IconsManager.class.getResource(images_path + "onfocuscopy.gif"));
		ONFOCUSCUT = new ImageIcon(IconsManager.class.getResource(images_path + "onfocuscut.gif"));
		ONFOCUSPASTE = new ImageIcon(IconsManager.class.getResource(images_path + "onfocuspaste.gif"));
		ONFOCUSFIND = new ImageIcon(IconsManager.class.getResource(images_path + "onfocusfind.gif"));
		ONFOCUSHOME = new ImageIcon(IconsManager.class.getResource(images_path + "onfocushome.gif"));
		ONFOCUSGARBAGE = new ImageIcon(IconsManager.class.getResource(images_path + "onfocusgarbage.gif"));
		ONFOCUSREFRESH = new ImageIcon(IconsManager.class.getResource(images_path + "onfocusrefresh.gif"));
		/* CHARGEMENT DES ICONES */
		/* Grosses Icones */
		big_father_icon = new ImageIcon(IconsManager.class.getResource(images_path + "bigIconFather.gif"));
		big_folder_icon = new ImageIcon(IconsManager.class.getResource(images_path + "bigIconFolder.gif"));
		big_image_icon = new ImageIcon(IconsManager.class.getResource(images_path + "bigIconImage.gif"));
		big_exe_icon = new ImageIcon(IconsManager.class.getResource(images_path + "bigIconExe.gif"));
		big_file_icon = new ImageIcon(IconsManager.class.getResource(images_path + "bigIconFile.gif"));
		/* Petites Icones */
		small_father_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconFather.gif"));
		small_folder_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconFolder.gif"));
		small_image_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconImage.gif"));
		small_exe_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconExe.gif"));
		small_file_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconFile.gif"));
		
		focus_icon = new ImageIcon(IconsManager.class.getResource(images_path + "smallIconFocus.gif"));
	}

}
