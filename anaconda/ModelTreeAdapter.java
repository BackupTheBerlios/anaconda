import java.io.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * 
 */
public class ModelTreeAdapter extends DefaultTreeModel {
    final private Model model;
    final private Model rootModel;
    //private int[] filterList;
    public ModelTreeAdapter() {
        this(new Model());
    }
    public ModelTreeAdapter(TreeNode root) {
        super(root);
        model = (Model)root;
        rootModel = new Model(model.getFolder());
        //setFilterList();
    }
    public Model getModel() {
        return model;
    }
    /*
    private void setFilterList() {
        int[] list = null;
        int size = 0;
        for(int i = 0; i < model.getSize(); i ++)
            if(!model.getFolderChilds()[i].isHidden() && 
               model.getFolderChilds()[i].isDirectory()) size ++;
        if(size != 0) {
            list = new int[size];
            for(int i = 0, j = 0; i < size; i ++, j ++) {
                while(j < model.getSize() && 
                      model.getFolderChilds()[j].isHidden() || 
                      !model.getFolderChilds()[j].isDirectory()) j ++;
                if(j < model.getSize()) list[i] = j;
            }
        }
        filterList = list;
    }
    public int getSize() {
        return filterList.length;
    }
    public int[] getFilterList() {
        return filterList;
    }
    */
    /* Methodes pour le DefaultTreeModel */
    public Object getRoot() {
        return rootModel;
    }
    /*
    public Object getChild(Object parent, int index) {
        ModelTreeAdapter mta = new ModelTreeAdapter((Model)parent);
        if(index < 0 || mta.getSize() <= index) return null;
        File file = (File)((Model)parent).getElementAt(mta.getFilterList()[index]);
        if(file == null) return null;
        return new Model(file);
    }
    public int getChildCount(Object parent) {
        return (new ModelTreeAdapter((Model)parent)).getSize();
    }
    public boolean isLeaf(Object node) {
        return !((Model)node).getFolder().isDirectory();
    }
    public void valueForPathChanged(TreePath path, Object newValue) {
        // A FAIRE
    }
    public int getIndexOfChild(Object parent, Object child) {
        ModelTreeAdapter mta = new ModelTreeAdapter((Model)parent);
        File[] parentChilds = ((Model)parent).getFolderChilds();
        File file = ((Model)child).getFolder();
        for(int i = 0; i < mta.getSize(); i ++)
            if(Model.cmp.compare(parentChilds[mta.getFilterList()[i]], file) == 0) return i;
        return -1;
    }
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(l.getClass(), l);
    }
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(l.getClass(), l);
    }
    */
}
