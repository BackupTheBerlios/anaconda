/*
 * Created on 9 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TreeRenderer extends DefaultTreeCellRenderer {
	
	private ImageIcon open_node_icon = IconsManager.small_father_icon;
	private ImageIcon close_node_icon = IconsManager.small_folder_icon;
	private ImageIcon focus_icon = IconsManager.focus_icon;
	
	public TreeRenderer(){
		
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
		String name = ((Model) value).getFolder().getName();
		if (name.compareTo("") == 0)
			name = ((Model) value).getFolder().getAbsolutePath();
		((JLabel) c).setText(name);
		if (selected)
			 ((JLabel) c).setIcon(focus_icon);
		else if (expanded)
			 ((JLabel) c).setIcon(open_node_icon);
		else
			 ((JLabel) c).setIcon(close_node_icon);
		backgroundNonSelectionColor = Main.getBgColor();
		return c;
	}

}
