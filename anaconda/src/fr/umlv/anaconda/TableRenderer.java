package fr.umlv.anaconda;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import fr.umlv.anaconda.tools.Extension;

public class TableRenderer extends DefaultTableCellRenderer {
	public static ImageIcon FATHER_ICON = IconsManager.big_father_icon;
	public static ImageIcon FOLDER_ICON = IconsManager.big_folder_icon;
	public static ImageIcon IMAGE_ICON = IconsManager.big_image_icon;
	public static ImageIcon EXE_ICON = IconsManager.big_exe_icon;
	public static ImageIcon FILE_ICON = IconsManager.big_file_icon;
/*	final private static ArrayList imagesExt = new ArrayList();
	static {
		imagesExt.add(".jpg");
		imagesExt.add(".jpeg");
		imagesExt.add(".gif");
		imagesExt.add(".png");
		imagesExt.add(".bmp");
	} */
	/**
	 * METHODES UTILES
	 */
	public static void setIconsSize(int size) {
		if(size == IconsManager.BIG_ICONS) {
			FATHER_ICON = IconsManager.big_father_icon;
			FOLDER_ICON = IconsManager.big_folder_icon;
			IMAGE_ICON = IconsManager.big_image_icon;
			EXE_ICON = IconsManager.big_exe_icon;
			FILE_ICON = IconsManager.big_file_icon;
		}
		else {
			FATHER_ICON = IconsManager.small_father_icon;
			FOLDER_ICON = IconsManager.small_folder_icon;
			IMAGE_ICON = IconsManager.small_image_icon;
			EXE_ICON = IconsManager.small_exe_icon;
			FILE_ICON = IconsManager.small_file_icon;
		}
	}
	/**
	 * METHODES POUR LE DefaultTableCellRenderer
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(value == null) return new JLabel("");
		File file = (File)value;
		((JLabel)c).setText(file.getName());
		((JLabel)c).setToolTipText(file.getAbsolutePath());
		((JLabel)c).setHorizontalAlignment(SwingConstants.CENTER);
		((JLabel)c).setVerticalAlignment(SwingConstants.CENTER);
		((JLabel)c).setHorizontalTextPosition(SwingConstants.CENTER);
		((JLabel)c).setVerticalTextPosition(SwingConstants.BOTTOM);
		if(((File)value).isDirectory()) ((JLabel)c).setIcon(FOLDER_ICON);
		else {
			int indexExt = file.getName().lastIndexOf('.');
			if(indexExt != -1) {
				//if(imagesExt.contains(file.getName().substring(indexExt))) ((JLabel) c).setIcon(IMAGE_ICON);
				if(Extension.isImage(file.getName())) ((JLabel) c).setIcon(IMAGE_ICON);
				else if(file.getName().endsWith(".exe")) ((JLabel) c).setIcon(EXE_ICON);
				else ((JLabel)c).setIcon(FILE_ICON);
			}
			else ((JLabel)c).setIcon(FILE_ICON);
		}
		int height = ((JLabel)c).getIcon().getIconHeight() + 20;
		if(table.getRowHeight(row) < height) table.setRowHeight(row, height);
		return c;
	}
}
