package fr.umlv.anac;

import javax.swing.*;
import javax.swing.event.*;

/**
 * 
 */
public class ModelListAdapter extends AbstractListModel {
    final private Model model;
    public ModelListAdapter() {
        this(new Model());
    }
    public ModelListAdapter(ListModel root) {
        super();
        model = (Model)root;
        model.addListDataListener(new ListBridgeListener());
    }
    public Model getModel() {
        return model;
    }
    /* Methodes pour le AbstractListModel */
    public int getSize() {
        return model.getSize() + ((model.getFolderParent() != null)? 1: 0);
    }
    public Object getElementAt(int index) {
        if(index < 0 || getSize() <= index) return null;
        if(model.getFolderParent() != null) {
            if(index == 0) return model.getFolderParent();
            return model.getElementAt(index - 1);
        }
        return model.getElementAt(index);
    }
    /* Bridge */
    public class ListBridgeListener implements ListDataListener {
        public void intervalAdded(ListDataEvent e) {
            fireIntervalAdded(ModelListAdapter.this, e.getIndex0(), e.getIndex1());
        }
        public void intervalRemoved(ListDataEvent e) {
            fireIntervalRemoved(ModelListAdapter.this, e.getIndex0(), e.getIndex1());
        }
        public void contentsChanged(ListDataEvent e) {
            fireContentsChanged(ModelListAdapter.this, e.getIndex0(), e.getIndex1());
        }
    }
}
