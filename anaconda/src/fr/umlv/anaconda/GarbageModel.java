
package fr.umlv.anaconda;


import java.io.File;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GarbageModel extends AbstractListModel{
	
	private ArrayList list = new ArrayList();
	private int size = 0;
	private File home = new File(System.getProperty("user.home"));
	private File garbage = new File(home,"anaconda_garbage");
	
	public GarbageModel(){
		list.add(new File("Elements de la corbeille"));
	}
	
	public void addElement(File file){
		list.add(file);
		fireIntervalAdded(GarbageModel.this,getSize()-1,getSize()-1);
	}
	
	
	public void removeElement(File f){
		int indice;
		if((indice = list.indexOf(f)) != -1){
			fireIntervalRemoved(GarbageModel.this,indice,indice);			
			list.remove(indice);
		}
	}
	
	public int getSize(){
		return list.size();
	}
	
	public Object getElementAt(int position){
		if(position < list.size())
			return list.get(position);
		else
			
			return new File("");
	}
}
