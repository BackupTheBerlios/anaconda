/*
 * Créé le 3 févr. 2004
 *
 */
package fr.umlv.anaconda;

import java.util.LinkedList;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * @author abrunete
 *
 */
public class AddressBarComboBoxModel implements MutableComboBoxModel {
	
	/** List to store the navigation history with an order and no duplicate element **/
	private LinkedList history;
	/** to store the element of the auto-completion **/
//	private LinkedList completion;
	/** the current list */
	private LinkedList current;
	/** current selected index */
	private int index;

	/**
	 * default constructor
	 */
	public AddressBarComboBoxModel() {
		super();
		history = new LinkedList();
		history.add("");
//		completion = new LinkedList();
		current = new LinkedList();
		current = history;
	}
	
	public AddressBarComboBoxModel(String path) {
		super();
		history = new LinkedList();
		history.add(path);
//		completion = new LinkedList();
		current = new LinkedList();
		current = history;
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElementAt(int)
	 */
	public void removeElementAt(int index) {
		System.out.println("removeElement : ");
		  current.remove(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#addElement(java.lang.Object)
	 */
	public void addElement(Object obj) {
		System.out.println("addElement : ".concat((String)obj));
		if (current.contains(obj)) {
			/* already present move it first */
			current.remove(obj);
			current.addFirst(obj);
		}
		else {
			current.addFirst(obj);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElement(java.lang.Object)
	 */
	public void removeElement(Object obj) {
		System.out.println("removeElement");
		current.remove(obj);
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#insertElementAt(java.lang.Object, int)
	 */
	public void insertElementAt(Object obj, int index) {
		System.out.println("insertElementAt : ".concat(new Integer(index).toString()));
		current.add(index, obj);
	//	this.index = index;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem() {
		System.out.println("getSelectedItem : ".concat(new Integer(this.index).toString()));
		return current.get(this.index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object anItem) {
		if (current.contains(anItem))	
			this.index = current.indexOf(anItem);
		else
			this.index = 1;
//		System.out.println("setSelectedItem : ".concat(new Integer( current.indexOf(anItem)).toString()));
		System.out.println("setSelectedItem : ".concat(new Integer(this.index).toString()));
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		int tmp = current.size();
		System.out.println("getSize : ".concat(new Integer(tmp).toString()));
	//	return current.size();
	return tmp;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		System.out.println("getElementAt : ".concat(new Integer(index).toString()));
		return current.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener l) {
		System.out.println("addListDataListener");
		// TODO Je ne vois pas encore quoi mettre ici

		//throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		System.out.println("removeListDataListener");
		// TODO Je ne vois pas encore quoi mettre ici

		//throw new UnsupportedOperationException();
	}

}
