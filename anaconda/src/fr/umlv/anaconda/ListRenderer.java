/*
 * Created on 8 f�vr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;


public class ListRenderer extends DefaultListCellRenderer {
	
	private static ArrayList imagesExt = new ArrayList();
	static {
		imagesExt.add(".jpg");
		imagesExt.add(".jpeg");
		imagesExt.add(".gif");
		imagesExt.add(".bmp");
		imagesExt.add(".png");
	}
	 
	private ImageIcon father_icon = IconsManager.big_father_icon;
	private ImageIcon folder_icon = IconsManager.big_folder_icon;
	private ImageIcon image_icon = IconsManager.big_image_icon;
	private ImageIcon exe_icon = IconsManager.big_exe_icon;
	private ImageIcon file_icon = IconsManager.big_file_icon;


	ModelListAdapter listModel = null;

	public ListRenderer(){
		super();
		setIconsSize(IconsManager.SMALL_ICONS);
	}
	
	public ListRenderer(ModelListAdapter listModel) {
		super();
		this.listModel = listModel;
	}

	public void setIconsSize(int size) {
		if (size == IconsManager.BIG_ICONS) {
			father_icon = IconsManager.big_father_icon;
			folder_icon = IconsManager.big_folder_icon;
			image_icon = IconsManager.big_image_icon;
			exe_icon = IconsManager.big_exe_icon;
			file_icon = IconsManager.big_file_icon;
		}
		else{
			father_icon = IconsManager.small_father_icon;
			folder_icon = IconsManager.small_folder_icon;
			image_icon = IconsManager.small_image_icon;
			exe_icon = IconsManager.small_exe_icon;
			file_icon = IconsManager.small_file_icon;
		}
	}

	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
		boolean cellHasFocus) {
		Component c =
			super.getListCellRendererComponent(
				list,
				value,
				index,
				isSelected,
				cellHasFocus);
		String name = ((File) value).getName();
		if (listModel != null) {
			File parent = listModel.getModel().getFolder().getParentFile();
			if (parent == null)
				parent = listModel.getModel().getFolder();
			if (parent
				.getAbsolutePath()
				.compareTo(((File) value).getAbsolutePath())
				== 0)
				name = "..";
			else if (name.compareTo("") == 0)
				name = ((File) value).getAbsolutePath();
		}
		((JLabel) c).setText(name);
		((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
		((JLabel) c).setVerticalAlignment(SwingConstants.CENTER);
		((JLabel) c).setHorizontalTextPosition(SwingConstants.CENTER);
		((JLabel) c).setVerticalTextPosition(SwingConstants.BOTTOM);
		/** ********************* */
		
		//URLConnection url_connex = new URLConnection();
		
		
		
		if (((File) value).isDirectory()) {
			if (name.compareTo("..") == 0)
				 ((JLabel) c).setIcon(father_icon);
			else
				 ((JLabel) c).setIcon(folder_icon);
		} else {
			int extIndex = name.lastIndexOf('.');
			if(extIndex != -1) {
				if (imagesExt.contains(name.substring(extIndex)))
					((JLabel) c).setIcon(image_icon);
				else if (name.endsWith(".exe"))
					 ((JLabel) c).setIcon(exe_icon);
				else
					 ((JLabel) c).setIcon(file_icon);
			}
			else
				 ((JLabel) c).setIcon(file_icon);
		}
		/** ********************* */
		return c;
	}
}
