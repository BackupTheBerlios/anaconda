
package fr.umlv.anaconda;

import java.io.File;
import java.util.ArrayList;

import javax.swing.table.*;

import fr.umlv.anaconda.exception.TooMuchFilesException;


/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FindModel extends AbstractTableModel{
	ModelTable model;
	private ArrayList list = new ArrayList();
	public final int MAX_SIZE = 1000;
	
	public FindModel(ModelTable model) {
		this.model=model;
	}
	public void add(File file) throws TooMuchFilesException{
		if(getSize() == MAX_SIZE)
		   throw new TooMuchFilesException();
	
		list.add(file);
		//fireIntervalAdded(FindModel.this,getSize()-1,getSize()-1);
		model.setDimmension(1, getSize());		
	}
	public void init(){
		int size = list.size();
		list.clear();
		model.setDimmension(1, getSize());
		//fireContentsChanged(FindModel.this,0,size);
		//fireIntervalRemoved(FindModel.this,0,size);
	}
	public int getSize() {
		return list.size();
	}
	public int getColumnCount() {
		return model.getColumnCount();
	}
	public int getRowCount() {
		return model.getRowCount();
	}
	public Object getValueAt(int arg0, int arg1) {
		return model.getValueAt(arg0, arg1);
	}
}
