/*
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.appearance;

import java.awt.Color;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Themes {
	
	
	final public static String PATH_MODERN = "/images/modern/";
	public final static Color COLOR_MODERN = new Color(210, 230, 255);
	
	final public static String path_current = PATH_MODERN;
	public static final Color color_current = COLOR_MODERN;
	
	public static String getCurrentPath(){
		return path_current;
	}

	public static Color getBgColor(){
		return color_current;
	}
	
}
