
package fr.umlv.anaconda;

import javax.swing.ImageIcon;

/**
 * Loads all the images to use
 *
 */
public class IconsManager {

	final public static ImageIcon logo;
	
	final public static ImageIcon back;
	final public static ImageIcon next;
	final public static ImageIcon copy;
	final public static ImageIcon cut;
	final public static ImageIcon paste;
	final public static ImageIcon find;

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

	static{
		
		/*CHARGEMENT DU LOGO */
		logo = new ImageIcon(IconsManager.class.getResource("/images/anaconda_logo_2.gif"));
		/* CHARGEMENT DES ICONES DE LA BARRE D'OUTILS */
		back = new ImageIcon(IconsManager.class.getResource("/images/back.gif"));
		next = new ImageIcon(IconsManager.class.getResource("/images/next.gif"));
		copy = new ImageIcon(IconsManager.class.getResource("/images/copy.gif"));
		cut = new ImageIcon(IconsManager.class.getResource("/images/cut.gif"));
		paste = new ImageIcon(IconsManager.class.getResource("/images/paste.gif"));
		find = new ImageIcon(IconsManager.class.getResource("/images/find.gif"));
		/* CHARGEMENT DES ICONES */
		/* Grosses Icones */
		big_father_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFather.gif"));
		big_folder_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFolder.gif"));
		big_image_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconImage.gif"));
		big_exe_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconExe.gif"));
		big_file_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFile.gif"));
		/* Petites Icones */
		small_father_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFather.gif"));
		small_folder_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFolder.gif"));
		small_image_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconImage.gif"));
		small_exe_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconExe.gif"));
		small_file_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFile.gif"));
		
		focus_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFocus.gif"));
	}

}
