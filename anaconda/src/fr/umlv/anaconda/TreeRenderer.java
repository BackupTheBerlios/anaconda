/*
 * Created on 9 févr. 2004
 */
package fr.umlv.anaconda;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import fr.umlv.anaconda.appearance.Themes;

/**
 * @author FIGUEROA
 */
public class TreeRenderer extends DefaultTreeCellRenderer {
	
	private ImageIcon open_node_icon = IconsManager.small_father_icon;
	private ImageIcon close_node_icon = IconsManager.small_folder_icon;
	private ImageIcon focus_icon = IconsManager.focus_icon;
//	protected Color backgroundNonSelectionColor = Themes.getBgColor();
	
	
	public TreeRenderer(){
		backgroundNonSelectionColor = Themes.getBgColor();
	}
	
	public Component getTreeCellRendererComponent(
		JTree tree,
		Object value,
		boolean selected,
		boolean expanded,
		boolean leaf,
		int row,
		boolean hasFocus) {
		Component c =
			super.getTreeCellRendererComponent(
				tree,
				value,
				selected,
				expanded,
				leaf,
				row,
				hasFocus);
		if(value == null) return new JLabel("");
		File file = (File)value;
		if(file.getName().compareTo("") == 0)
			((JLabel)c).setText(file.getAbsolutePath());
		else ((JLabel)c).setText(file.getName());
		((JLabel)c).setToolTipText(file.getAbsolutePath());
		if(selected) ((JLabel)c).setIcon(focus_icon);
		else if(expanded) ((JLabel)c).setIcon(open_node_icon);
		else ((JLabel)c).setIcon(close_node_icon);
		return c;
	}

}
