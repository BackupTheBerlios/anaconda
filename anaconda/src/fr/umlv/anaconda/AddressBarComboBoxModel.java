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
	private LinkedList completion;
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
		completion = new LinkedList();
		current = new LinkedList();
		current = history;
	}
	
	public AddressBarComboBoxModel(String path) {
		super();
		history = new LinkedList();
		history.add(path);
		completion = new LinkedList();
		current = new LinkedList();
		current = history;
	//	current.add(path);
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElementAt(int)
	 */
	public void removeElementAt(int index) {
		  current.remove(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#addElement(java.lang.Object)
	 */
	public void addElement(Object obj) {
		System.out.println("addElement");
//		if (current.contains(obj)) {
			/* already present move it first */
//			current.remove(obj);
			current.addFirst(obj);
//		}
//		else {
			current.addFirst(obj);
//		}
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
		System.out.println("insertElementAt");
		current.add(index, obj);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem() {
		System.out.println("getSelectedItem");
		return current.get(this.index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object anItem) {
		System.out.println("setSelectedItem");
		this.index = current.indexOf(anItem);	
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		System.out.println("getSize");
		return current.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		System.out.println("getElementAt");
		return current.get(index); 
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener l) {
		System.out.println("addListDataListener");
		// TODO Raccord de méthode auto-généré

	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		System.out.println("removeListDataListener");
		// TODO Raccord de méthode auto-généré

	}

}
