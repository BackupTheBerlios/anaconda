/*
 * Created on 8 févr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.awt.Component;
import java.io.File;


import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import fr.umlv.anaconda.tools.Extension;




public class ListRenderer extends DefaultListCellRenderer {
	

	 
	private ImageIcon father_icon = IconsManager.big_father_icon;
	private ImageIcon folder_icon = IconsManager.big_folder_icon;
	private ImageIcon image_icon = IconsManager.big_image_icon;
	private ImageIcon exe_icon = IconsManager.big_exe_icon;
	private ImageIcon file_icon = IconsManager.big_file_icon;
	private boolean find = false;
	private boolean garbage = false;


	//ModelListAdapter listModel = null;

	public ListRenderer(String type){
		super();
		setIconsSize(IconsManager.SMALL_ICONS);
		if(type.equals("find")){
			find = true;
			garbage = false;
		}
		else{
			find =  false;
			garbage = true;
		}
	}
	
	/*public ListRenderer(ModelListAdapter listModel) {
		super();
		this.listModel = listModel;
	}*/

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
		File file = (File) value;
		String name = file.getName();
		if(name.length()>20){
			name = (name.substring(0,10)).concat("...");
		}
		((JLabel) c).setText(name);
		((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
		((JLabel) c).setVerticalAlignment(SwingConstants.TOP);
		((JLabel) c).setHorizontalTextPosition(SwingConstants.RIGHT);
		((JLabel) c).setVerticalTextPosition(SwingConstants.TOP);
		/** ********************* */
		if(((File)value).isDirectory()) ((JLabel)c).setIcon(TableRenderer.FOLDER_ICON);
		else {
			int indexExt = file.getName().lastIndexOf('.');
			if(indexExt != -1) {
				//if(imagesExt.contains(file.getName().substring(indexExt))) ((JLabel) c).setIcon(TableRenderer.IMAGE_ICON);
				if(Extension.isImage(file.getName())) ((JLabel) c).setIcon(TableRenderer.IMAGE_ICON);
				else if(file.getName().endsWith(".exe")) ((JLabel) c).setIcon(TableRenderer.EXE_ICON);
				else ((JLabel)c).setIcon(TableRenderer.FILE_ICON);
			}
			else ((JLabel)c).setIcon(TableRenderer.FILE_ICON);
		}
		/** ********************* */
		return c;
	}
}
