/*
 * Créé le 3 févr. 2004
 *
 */
package fr.umlv.anaconda;

import java.util.*;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.*;

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
	/** ListDataListener List */
	private ArrayList list;

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
		list = new ArrayList();
	}
	
	public AddressBarComboBoxModel(String path) {
		super();
		history = new LinkedList();
		history.add(path);
//		completion = new LinkedList();
		current = new LinkedList();
		current = history;
		list = new ArrayList();
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElementAt(int)
	 */
	public void removeElementAt(int index) {
		current.remove(index);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#addElement(java.lang.Object)
	 */
	public void addElement(Object obj) {
		if (current.contains(obj)) {
			/* already present -> move it first */
			current.remove(obj);
			current.addFirst(obj);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 0, 0));
		}
		else {
			current.addFirst(obj);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 0, 0));

		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElement(java.lang.Object)
	 */
	public void removeElement(Object obj) {
		current.remove(obj);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));

	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#insertElementAt(java.lang.Object, int)
	 */
	public void insertElementAt(Object obj, int index) {
		current.add(index, obj);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public Object getSelectedItem() {
		return current.get(this.index);
		//TODO lancement auto du bon rep ?
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object anItem) {
		if (current.contains(anItem))	
			this.index = current.indexOf(anItem);
		else
			this.index = 1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return current.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return current.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener l) {
		// TODO Je ne suis pas sur...
		list.add(l);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		// TODO Je ne suis pas sur...
		if (list.contains(l))
			list.remove(l);
	}

	public void switch2comp() {
		this.current = this.completion;
	}

	public void switch2hist() {
		this.current = this.history; 
	}

}
