package fr.umlv.anaconda;

//import java.io.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * 
 */
public class ModelTreeAdapter extends DefaultTreeModel {
	final private Model model;
	final private Model rootModel;
	public ModelTreeAdapter() {
		this(new Model());
	}
	public ModelTreeAdapter(TreeNode root) {
		super(root);
		model = (Model)root;
		rootModel = new Model(model.getFolder()/*new File(File.separator)*/);
		model.addTreeModelListener(new TreeBridgeListener());
	}
	public Model getModel() {
		return model;
	}
	/* Methodes pour le DefaultTreeModel */
	public Object getRoot() {
		return rootModel;
	}
	/*
	public TreeNode[] getPathToRoot(TreeNode aNode) {
		if(aNode == null) return new TreeNode[] {};
		int size = 1;
		for(Model m = (Model)aNode; !m.equals(getRoot()); m = (Model)m.getParent())
			size ++;
		TreeNode[] path = new TreeNode[size];
		for(size --; size >=0; size --) {
			path[size] = aNode;
			aNode = aNode.getParent();
		}
		return path;
	}
	*/
	/* Bridge */
	public class TreeBridgeListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			fireTreeNodesChanged(ModelTreeAdapter.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeNodesInserted(TreeModelEvent e) {
			fireTreeNodesInserted(ModelTreeAdapter.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeNodesRemoved(TreeModelEvent e) {
			fireTreeNodesRemoved(ModelTreeAdapter.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
		public void treeStructureChanged(TreeModelEvent e) {
			fireTreeStructureChanged(ModelTreeAdapter.this, e.getPath(), e.getChildIndices(), e.getChildren());
		}
	}
}
