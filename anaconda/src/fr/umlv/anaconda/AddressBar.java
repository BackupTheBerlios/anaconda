package fr.umlv.anaconda;

/* Maitrise info - genie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 fevr. 2004 */

import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Pattern;
//import java.util.regex.Pattern;

import javax.swing.*;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

//import fr.umlv.anaconda.exception.TooMuchFilesException;
//import java.util.*;
//import javax.swing.event.*;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
//import java.io.File;

/**
 *
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class AddressBar extends JComboBox {

	/**
	 * 
	 */
	public AddressBar(String file_name) {
		super(new AddressBarComboBoxModel(file_name));
		this.setEditable(true);

	}

	public AddressBar(File file) {
		super(new AddressBarComboBoxModel(file.getAbsolutePath()));
		this.setEditable(true);

	}

	/**
	 * @deprecated
	 */
	public AddressBar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @deprecated
	 */
	public AddressBar(Object[] arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @deprecated
	 */
	public AddressBar(Vector arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @deprecated
	 */
	public AddressBar(ComboBoxModel arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**@deprecated */
	public void setText(String txt) {
		//	this.getEditor().setItem(txt);
		this.getEditor().setItem((new File(txt)));
	}

	public void setText(File fich) {
		//	this.getEditor().setItem(txt);
		try {
			this.getEditor().setItem(fich.getCanonicalPath());
		} catch (IOException e) {
			new ErrorIOFileException(fich).show();
		}
	}
	public String getText() {
		//		return (String)this.getEditor().getItem();
		try {
			return (String) this.getEditor().getItem();
		} catch (Exception e) {
			try {
				return ((File) this.getEditor().getItem()).getCanonicalPath();
			} catch (Exception ex) {
				return ((File) this.getEditor().getItem()).getAbsolutePath();
			}
		}
	}

	/** a key listener factory for the combobox */
	public KeyListener listenerFactory() {
		return new KeyListener() {
			public void keyPressed(KeyEvent e) {
				System.out.println("key presed");
			}
			public void keyReleased(KeyEvent e) {
				System.out.println("key released");
			}
			public void keyTyped(KeyEvent e) {
				System.out.println("key typed");
				if (e.getKeyCode() == KeyEvent.VK_TAB)
					System.out.println("autocompletion ??");
			}
		};
	}

	/** a key listener factory for the combobox */
	public ActionListener actionListenerFactory() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				System.out.println(ae.paramString());
			}

			public void keyPressed(KeyEvent e) {
				System.out.println("key presed");
			}
			public void keyReleased(KeyEvent e) {
				System.out.println("key released");
			}
			public void keyTyped(KeyEvent e) {
				System.out.println("key typed");
				if (e.getKeyCode() == KeyEvent.VK_TAB)
					System.out.println("autocompletion ??");
			}
		};
	}

	public void Completion() {
		//			((AddressBarComboBoxModel)getModel()).switch2comp();
		showCompl((String) getEditor().getItem());
		//			boolean flag = true;
		//			setPopupVisible(true);

		//			while (this.hasFocus()) {
		//			while (isPopupVisible()) {
		//			while (flag) {

		//			setPopupVisible(true);
		//			}
		//		this.hidePopup();
		//			((AddressBarComboBoxModel)getModel()).switch2hist();

		//		removeAllItems();
	}

	private void showCompl(String str) {
		//		addItem(str);
		//		removeAllItems();
		getEditor().setItem(str);
		getEditor().getEditorComponent().requestFocus();
		int i = str.lastIndexOf(File.separator);
		if (i < 0)
			Main.refresh();
		else {
			File root = new File(str.substring(0, i));
			String name;
			if (i > str.length())
				name = str.substring(i + 1);
			else
				name = str.substring(i + 1);
			find(root, name);
			showPopup();
		}
	}

	/* recup de la cmd find */
	public void find(File root_file, String trunc_name) {
		((AddressBarComboBoxModel) getModel()).switch2comp();
		File[] children = root_file.listFiles();
		getEditor().setItem(root_file.getAbsolutePath() + trunc_name);
		getEditor().getEditorComponent().requestFocus();
		if (children != null) {
			((AddressBarComboBoxModel) getModel()).switch2comp();
			//			removeAllItems();
			for (int i = 0; i < children.length; i++) {
				if (children[i].getName().startsWith(trunc_name)) {
				addItem(children[i]);
				}
			}
			showPopup();
		} else {
			System.err.println("pas d'auto dispo...");
		}
	}
	/*
	public void firePopupMenuCanceled() {
		((AddressBarComboBoxModel)getModel()).switch2hist(); 
		super.firePopupMenuCanceled();
	//	repaint();
	}
	
	public void firePopupMenuWillBecomeInvisible() {
		((AddressBarComboBoxModel)getModel()).switch2hist(); 
		super.firePopupMenuWillBecomeInvisible();
	//		repaint();
	}
	*/
}
