package fr.umlv.anaconda;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * 
 */
public class Model implements ListModel, TreeNode {
	/* pour trier par repertoire puis fichier ensuite le nom */
	final public static Comparator cmp = new Comparator() {
			public int compare(Object o1, Object o2) {
				if(o1 == null) return -1;
				if(o2 == null) return 1;
				File file1 = (File)o1;
				File file2 = (File)o2;
				if((file1.isDirectory()   &&   file2.isDirectory())   ||
				   (!file1.isDirectory()   &&   !file2.isDirectory()))
					return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
				if(file1.isDirectory()) return -1;
				return 1;
			}
			public boolean equals(Object obj) {
				return compare(this, obj) == 0;
			}
	};
	/* gestion du cash */
	final private static LinkedHashMap cash = new LinkedHashMap() {
			private static final int MAX_ENTRIES = 15;
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return size() > MAX_ENTRIES;
			}
	};
	private File folder;
	private File folderParent;
	private File[] folderChilds;
	private int[] filterList;
	private int[] filterTree;
	public Model() {
		this(new File(System.getProperty("user.home")));
	}
	public Model(File folder) {
		setFolder(folder);
	}
	public File getFolder() {
		return folder;
	}
	public File getFolderParent() {
		return folderParent;
	}
	public File[] getFolderChilds() {
		return folderChilds;
	}
	public int getFolderChildsSize() {
		return (folderChilds == null)? 0: folderChilds.length;
	}
	public int getFilterListSize() {
		return (filterList == null)? 0: filterList.length;
	}
	public int getFilterTreeSize() {
		return (filterTree == null)? 0: filterTree.length;
	}
	public void setFolder(File newFolder) {
		if(cmp.compare(folder, newFolder) == 0) return ;
		folder = newFolder;
		folderParent = folder.getParentFile();
		if(cash.containsKey(folder)) folderChilds = (File[])cash.get(folder);
		else {
			folderChilds = folder.listFiles();
			if(folderChilds != null) Arrays.sort(folderChilds, cmp);
			putFolderToCash();
		}
		setFilterList();
		setFilterTree();
		int size = getFolderChildsSize();
		if(size > 0) size --;
		EventListener[] eventList = listenerList.getListeners(ModelListAdapter.ListBridgeListener.class);
		for(int i = 0; i < eventList.length; i ++) {
			ModelListAdapter.ListBridgeListener lbl = (ModelListAdapter.ListBridgeListener)eventList[i];
			lbl.contentsChanged(new ListDataEvent(lbl, ListDataEvent.CONTENTS_CHANGED, 0, size));
		}
		eventList = listenerList.getListeners(ModelTreeAdapter.TreeBridgeListener.class);
		/* CONSTRUCTION LES PARAM DU TreeModelEvent */
		size = 0;
		for(File file = folder; file != null; file = file.getParentFile())
			size ++;
		Object[] pathObject = new Object[size --];
		for(File file = folder; file != null; file = file.getParentFile())
			pathObject[size --] = file;
		TreePath path = new TreePath(pathObject);
		int[] childIndices = new int[getChildCount()];
		for(int i = 0; i < getChildCount(); i ++) childIndices[i] = i;
		Object[] children = new Object[] { folder };
		for(int i = 0; i < eventList.length; i ++) {
			ModelTreeAdapter.TreeBridgeListener tbl = (ModelTreeAdapter.TreeBridgeListener)eventList[i];
			tbl.treeNodesChanged(new TreeModelEvent(tbl, path, childIndices, children));
		}
	}
	private void putFolderToCash() {
		cash.put(folder, folderChilds);
	}
	private void setFilterList() {
		int[] list = null;
		int size = 0;
		for(int i = 0; i < getFolderChildsSize(); i ++)
			if(!folderChilds[i].isHidden()) size ++;
		if(size != 0) {
			list = new int[size];
			for(int i = 0, j = 0; i < size; i ++, j ++) {
				while(j < getFolderChildsSize() && folderChilds[j].isHidden()) j ++;
				if(j < folderChilds.length) list[i] = j;
			}
		}
		filterList =  list;
	}
	private void setFilterTree() {
		int[] list = null;
		int size = 0;
		for(int i = 0; i < getFilterListSize(); i ++)
			if(folderChilds[filterList[i]].isDirectory()) size ++;
		if(size != 0) {
			list = new int[size];
			for(int i = 0, j = 0; i < size; i ++, j ++) {
				while(j < getFilterListSize() && !folderChilds[filterList[j]].isDirectory()) j ++;
				if(j < getFilterListSize()) list[i] = filterList[j];
			}
		}
		filterTree = list;
	}
	public boolean equals(Object obj) {
		return cmp.compare(folder, ((Model)obj).getFolder()) == 0;
	}
	public String toString() {
		return folder.toString();
	}
	protected EventListenerList listenerList = new EventListenerList();
	/* Methodes pour le ListModel */
	public int getSize() {
		return getFilterListSize();
	}
	public Object getElementAt(int index) {
		if(index < 0 || getSize() <= index) return null;
		return folderChilds[filterList[index]];
	}
	public void addListDataListener(ListDataListener l) {
		listenerList.add(l.getClass(), l);
	}
	public void removeListDataListener(ListDataListener l) {
		listenerList.remove(l.getClass(), l);
	}
	/* Methodes pour le TreeNode */
	public TreeNode getChildAt(int childIndex) {
		if(childIndex < 0 || getChildCount() <= childIndex) return null;
		return new Model(folderChilds[filterTree[childIndex]]);
	}
	public int getChildCount() {
		return getFilterTreeSize();
	}
	public TreeNode getParent() {
		if(folderParent == null) return null;
		return new Model(folderParent);
	}
	public int getIndex(TreeNode node) {
		if(!(node instanceof Model)) return -1;
		File file = ((Model)node).getFolder();
		for(int i = 0; i < getChildCount(); i ++)
			if(cmp.compare(folderChilds[filterTree[i]], file) == 0) return i;
		return -1;
	}
	public boolean getAllowsChildren() {
		return getChildCount() != 0;
	}
	public boolean isLeaf() {
		return !folder.isDirectory();
	}
	public Enumeration children() {
		return new Enumeration() {
				private int index = 0;
				public boolean hasMoreElements() {
					return index < getChildCount();
				}
				public Object nextElement() {
					return folderChilds[filterTree[index ++]];
				}
			};
	}
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(l.getClass(), l);
	}
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(l.getClass(), l);
	}
}
