package fr.umlv.anaconda;

/*
 * Cree le 3 fevr. 2004
 *
 */

import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;



/**
 * A model for the Anaconda ComboBox in the address bar
 * @author abrunete
 * @author Anac team
 *
 */
public class AddressBarComboBoxModel /*implements MutableComboBoxModel*/ extends DefaultComboBoxModel {
	
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
	 * @deprecated
	 */
	public AddressBarComboBoxModel() {
		super();
		history = new LinkedList();
		completion = new LinkedList();
		current = new LinkedList();
		current = history;
		list = new ArrayList();
	}
	
	public AddressBarComboBoxModel(String path) {
		super();
		history = new LinkedList();
		history.add(path);
		completion = new LinkedList();
		current = new LinkedList();
		current = history;
		list = new ArrayList();
	}

	public AddressBarComboBoxModel(File fich) {
		super();
		history = new LinkedList();
		history.add(fich);
		completion = new LinkedList();
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
		} else {
			current.addFirst(obj);
		}
		for (int i = 0; i < list.size(); i++) {
			((ListDataListener) list.get(i)).contentsChanged(
				new ListDataEvent(
					this,
					ListDataEvent.CONTENTS_CHANGED,
					0,
					current.size()));
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.MutableComboBoxModel#removeElement(java.lang.Object)
	 */
	public void removeElement(Object obj) {
		current.remove(obj);
		for (int i=0; i<list.size(); i++)
			((ListDataListener)list.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, 0, current.size()));

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
		if (index>0){
			return current.get(index);
		}
		return current.get(0);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(Object anItem) {
		if (current.contains(anItem)) {	
			addElement(anItem);
			index = current.indexOf(anItem);
		}
		else {
			index = 0;
		}
		switch2hist();
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
		list.add(l);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		if (list.contains(l))
			list.remove(l);
	}

	/** comboBox is wanted to show the auto-completion */
	public void switch2comp() {
		completion = new LinkedList();
		this.current = this.completion;
	}

	/** comboBox is wanted to show the history */
	public void switch2hist() {
		this.current = this.history; 
		index=0;
		fireContentsChanged(this, 0, current.size());
	}

	

	
}
