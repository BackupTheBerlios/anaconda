package fr.umlv.anac;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 */
public class ListPanelModel extends AbstractListModel {
	/**********/
	/* FIELDS */
	/**********/
	private File currentFolder;
	//private ArrayList folderChilds;
	/**/private TreeSet folderChilds;
	private Comparator folderChildsComparator;
	private final HashMap cash;
	/****************/
	/* CONSTRUCTORS */
	/****************/
	/**
	 * 
	 */
	public ListPanelModel() {
		this(new File(File.separator));
	}
	/**
	 * 
	 */
	public ListPanelModel(File currentFolder) {
		this.currentFolder = currentFolder;
		//folderChilds = new ArrayList();
		/**/folderChildsComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				if(!(o1 instanceof File)   ||  !(o2 instanceof File)) return -1;
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
		/**/folderChilds = new TreeSet(folderChildsComparator);
		cash = new HashMap();
		addNewFolder(this.currentFolder);
	}
	/***********/
	/* METHODS */
	/***********/
	public File getCurrentFolder() {
		return currentFolder;
	}
	public int getSize() {
		return folderChilds.size();
	}
	public Object getElementAt(int index) {
		//return folderChilds.get(index);
		/**/return folderChilds.toArray()[index];
	}
	public void setCurrentFolder(File currentFolder) {
		if(this.currentFolder.equals(currentFolder)) return ;
		this.currentFolder = currentFolder;
		//ArrayList cashList = (ArrayList)cash.get(this.currentFolder.getAbsolutePath());
		/**/TreeSet cashList = (TreeSet)cash.get(this.currentFolder.getAbsolutePath());
		if(cashList != null) {
			folderChilds = cashList;
			if(getSize() > 0) fireContentsChanged(this, 0, getSize()-1);
			else fireContentsChanged(this, 0, 0);
		}
		else {
			//folderChilds = new ArrayList();
			/**/folderChilds = new TreeSet(folderChildsComparator);
			File parent = this.currentFolder.getParentFile();
			if(parent != null) folderChilds.add(parent);
			addNewFolder(this.currentFolder);
			if(getSize() > 0) fireContentsChanged(this, 0, getSize()-1);
			else fireContentsChanged(this, 0, 0);
		}
	}
	private void addNewFolder(File folder) {
		File[] childs = folder.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return !pathname.isHidden();
			}
		});
		if(childs != null)
			for(int i = 0; i < childs.length; i ++) folderChilds.add(childs[i]);
		cash.put(folder.getAbsolutePath(), folderChilds);
	}
}
