/*
 * Created on 8 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import javax.swing.ImageIcon;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IconsManager {


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
		big_father_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFather.gif"));
		big_folder_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFolder.gif"));
		big_image_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconImage.gif"));
		big_exe_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconExe.gif"));
		big_file_icon = new ImageIcon(IconsManager.class.getResource("/images/bigIconFile.gif"));
		
		small_father_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFather.gif"));
		small_folder_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFolder.gif"));
		small_image_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconImage.gif"));
		small_exe_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconExe.gif"));
		small_file_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFile.gif"));
		
		focus_icon = new ImageIcon(IconsManager.class.getResource("/images/smallIconFocus.gif"));
	}

}
