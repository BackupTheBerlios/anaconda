package fr.umlv.anaconda;

import java.io.*;
import java.util.*;
import javax.swing.tree.*;

/**
 * 
 */
public class TreePanelModel implements TreeNode {
	/**********/
	/* FIELDS */
	/**********/
	private final File currentNode;
	private final ArrayList nodeChilds;
	/****************/
	/* CONSTRUCTORS */
	/****************/
	/**
	 * 
	 */
	public TreePanelModel() {
		this(new File(File.separator));
	}
	/**
	 * 
	 */
	public TreePanelModel(File currentNode) {
		this.currentNode = currentNode;
		nodeChilds = new ArrayList();
		File[] childs = currentNode.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() && !pathname.isHidden();
			}
		});
		if(childs != null)
			for(int i = 0; i < childs.length; i ++) nodeChilds.add(childs[i]);
	}
	/***********/
	/* METHODS */
	/***********/
	public File getCurrentNode() {
		return currentNode;
	}
	public TreeNode getChildAt(int childIndex) {
		return new TreePanelModel((File)nodeChilds.get(childIndex));
	}
	public int getChildCount() {
		return nodeChilds.size();
	}
	public TreeNode getParent() {
		File parent = currentNode.getParentFile();
		if(parent == null) return null;
		return new TreePanelModel(parent);
	}
	public int getIndex(TreeNode node) {
		return nodeChilds.indexOf(((TreePanelModel)node).getCurrentNode());
	}
	public boolean getAllowsChildren() {
		return getChildCount() != 0;
	}
	public boolean isLeaf() {
		return !currentNode.isDirectory();
	}
	public Enumeration children() {
		return new Enumeration() {
			private Iterator it = nodeChilds.iterator();
			public boolean hasMoreElements() {
				return it.hasNext();
			}
			public Object nextElement() {
				return it.next();
			}
		};
	}
}
