package fr.umlv.anaconda;

import java.io.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class ModelTable extends AbstractTableModel {
	private Comparator comparator;
	private TreeSet table;
	private int row;
	private int column;
	/**
	 * CONSTRUCTEURS
	 */
	public ModelTable() {
		this(Main.root);
	}
	public ModelTable(File root) {
		this(root, ComparatorsManager.sortedByName);
	}
	public ModelTable(Comparator comparator) {
		this(Main.root, comparator);
	}
	public ModelTable(File root, Comparator comparator) {
		this(root, comparator, (root.listFiles() != null)? root.listFiles().length: 0, 1);
	}
	public ModelTable(File root, Comparator comparator, int row, int column) {
		super();
		this.comparator = comparator;
		this.row = row;
		this.column = column;
		table = new TreeSet(this.comparator);
		setFolder(root);
	}
	/**
	 * METHODES UTILES
	 */
	public Comparator getComparator() {
		return comparator;
	}
	public TreeSet getTable() {
		return table;
	}
	public int getSize() {
		return table.size();
	}
	public void setDimmension(int row, int column) {
		if(column < 1) column = 6;
		if(row < 1) {
			double rowD = (double)getSize() / (double)column;
			this.row = (int)rowD;
			this.row += ((rowD >= this.row)? 1: 0);
		}
		else this.row = row;
		this.column = column;
		fireTableStructureChanged();
	}
	public void setFolder(File file) {
		table.clear();
		if(file.getParentFile() != null) table.add(file.getParentFile());
		table.add(file);
		File[] childs = file.listFiles();
		if(childs != null) {
			for(int i = 0; i < childs.length; i ++) {
				table.add(childs[i]);
			}
		}
		double rowD = (double)getSize() / (double)getColumnCount();
		row = (int)rowD;
		row += ((rowD >= row)? 1: 0);
		setDimmension(row, column);
	}
	/**
	 * METHODES POUR LE AbstractTableModel
	 */
	public int getColumnCount() {
		return column;
	}
	public int getRowCount() {
		return row;
	}
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public Class getColumnClass(int columnIndex) {
		if(columnIndex < 0 || getSize() <= columnIndex) return Object.class;
		return table.toArray()[columnIndex].getClass();
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex < 0 || getRowCount() <= rowIndex || columnIndex < 0 || getColumnCount() <= columnIndex) return null;
		int index = rowIndex * getColumnCount() + columnIndex;
		if(index < 0 || getSize() <= index) return null;
		return table.toArray()[index];
	}
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		int index = rowIndex * getColumnCount() + columnIndex;
		if(index < 0 || getSize() <= index) return ;
		for(Iterator it = table.iterator(); it.hasNext(); it.next()) {
			if(index == -1) {
				it.remove();
				break;
			}
			index --;
		}
		table.add(aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	public String getColumnName(int columnIndex) {
		return "";
	}
	/**
	 * PONT POUR LES TABLES
	 */
	public class TableBridgeListener implements TableModelListener {
		public void tableChanged(TableModelEvent e) {
			fireTableChanged(e);
		}
	}
	
}
