
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
	}
	
	public void addElement(File file){
		list.add(file);
		System.out.println("adding " + file.toString() + "  " );
		fireIntervalAdded(GarbageModel.this,getSize()-1,getSize()-1);
	}
	
	public int getSize(){
		return list.size();
	}
	
	public Object getElementAt(int position){
		return list.get(position);
	}

}
