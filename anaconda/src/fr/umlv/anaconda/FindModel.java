
package fr.umlv.anaconda;

import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import fr.umlv.anaconda.exception.TooMuchFilesException;


/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FindModel extends AbstractListModel{

	private ArrayList list = new ArrayList();
	public final int MAX_SIZE = 1000;
	

	public void add(File file) throws TooMuchFilesException{
		if(getSize() == MAX_SIZE)
		   throw new TooMuchFilesException();
		list.add(file);
		fireIntervalAdded(FindModel.this,getSize()-1,getSize()-1);		
	}
	
	public int getSize() {
		return list.size();
	}
	
	public Object getElementAt(int position) {
		return list.get(position);
	}
}
