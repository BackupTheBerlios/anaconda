package fr.umlv.anaconda;

import java.io.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class ModelTree extends DefaultTreeModel {
	private Comparator comparator;
	private TreeSet tree;
	private File root;
	private File oldParent;
	/**
	 * CONSTRUCTEURS
	 */
	public ModelTree() {
		this(Main.root);
	}
	public ModelTree(File root) {
		this(root, ComparatorsManager.sortedByName);
	}
	public ModelTree(Comparator comparator) {
		this(Main.root, comparator);
	}
	public ModelTree(File root, Comparator comparator) {
		super(new DefaultMutableTreeNode(root));
		this.root = root;
		oldParent = null;
		tree = new TreeSet(this.comparator);
		setFolder(root);
	}
	/**
	 * METHODES UTILES
	 */
	public Comparator getComparator() {
		return comparator;
	}
	public TreeSet getTree() {
		return tree;
	}
	public void setFolder(File file) {
		getChildCount(file);
		Object[] pathTmp = new Object[1024];
		pathTmp[0] = file;
		int count = 1;
		while(!file.equals(root) && file.getParentFile() != null) {
			file = file.getParentFile();
			pathTmp[count ++] = file;
		}
		Object[] path = new Object[count];
		for(int i = 0; i < count; i ++) {
			path[i] = pathTmp[count - i -1];
		}
		int[] indice = new int[file.listFiles().length];
		for(int i = 0; i < indice.length; i ++) {
			indice[i] = i;
		}
		fireTreeStructureChanged(this, path, indice, file.listFiles());
	}
	/**
	 * METHODES POUR LE DefaultTreeModel
	 */
	public Object getRoot() {
		return root;
	}
	public int getChildCount(Object parent) {
		if(oldParent == null || !oldParent.equals(parent)) {
			oldParent = ((File)parent);
			File[] childs = oldParent.listFiles();
			tree.clear();
			if(childs != null) {
				for(int i = 0; i < childs.length; i ++) {
					if(!isLeaf(childs[i])) tree.add(childs[i]);
				}
			}
		}
		return tree.size();
	}
	public boolean isLeaf(Object node) {
		return !((File)node).isDirectory();
	}
	public Object getChild(Object parent, int index) {
		if(index < 0 || getChildCount(parent) <= index) return null;
		return tree.toArray()[index];
	}
	public int getIndexOfChild(Object parent, Object child) {
		if(getChildCount(parent) == 0) return -1;
		int index = 0;
		for(Iterator it = tree.iterator(); it.hasNext(); ) {
			File file = (File)it.next();
			if(file.equals(child)) return index;
			index ++;
		}
		return -1;
	}
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
	}
	/**
	 * PONT POUR LES ARBRES
	 */
	public class TreeBridgeListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			fireTreeNodesChanged(ModelTree.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeNodesInserted(TreeModelEvent e) {
			fireTreeNodesInserted(ModelTree.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeNodesRemoved(TreeModelEvent e) {
			fireTreeNodesRemoved(ModelTree.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeStructureChanged(TreeModelEvent e) {
			fireTreeStructureChanged(ModelTree.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
	}
}
