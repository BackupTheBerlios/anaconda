package fr.umlv.anaconda;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 févr. 2004 */

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

	public void setText(String txt) {
		//	this.getEditor().setItem(txt);
		try {
			this.getEditor().setItem((new File(txt)).getCanonicalPath());
		} catch (IOException e) {
			new ErrorIOFileException(new File(txt)).show();
		}
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
		return (String)this.getEditor().getItem();
	}

	/** a key listener factory for the combobox */
	public KeyListener listenerFactory() {
		return new KeyListener() {
			public void keyPressed(KeyEvent e) {
				System.out.println ("key presed");
			}
			public void keyReleased(KeyEvent e){
					System.out.println ("key released");
			}
			public void keyTyped(KeyEvent e){
				System.out.println ("key typed");
				if (e.getKeyCode() == KeyEvent.VK_TAB)
					System.out.println ("autocompletion ??");	
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
				System.out.println ("key presed");
			}
			public void keyReleased(KeyEvent e){
					System.out.println ("key released");
			}
			public void keyTyped(KeyEvent e){
				System.out.println ("key typed");
				if (e.getKeyCode() == KeyEvent.VK_TAB)
					System.out.println ("autocompletion ??");	
			}
		};
	}

		public void Completion() {
			((AddressBarComboBoxModel)getModel()).switch2comp();
//			this.grabFocus();
//			this.requestFocus();
//			for (long i=0; i<100000000L; ++i)
//				;
			boolean flag = true;
			setPopupVisible(true);
//			while (this.hasFocus()) {
//			while (isPopupVisible()) {
//			while (flag) {
				showCompl((String)getEditor().getItem());
				//showPopup();
				setPopupVisible(true);
//			}
	//		this.hidePopup();
//			((AddressBarComboBoxModel)getModel()).switch2hist();
		}
		
		
		private void showCompl(String str) {
			int i = str.lastIndexOf(File.separator);
			File root = new File(str.substring(0, i));
			String name = str.substring(i);
			LinkedList list = new LinkedList();
			find(root, name/*, list*/);
	//		addItem(list);
		}
		

	/* récup de la cmd find */
	public void find(File root_file, String trunc_name) {
		removeAllItems();
		File[] children = root_file.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				//	if (Pattern.matches(trunc_name, children[i].getName())) {
					if (children[i].getName().startsWith(trunc_name)) {
					addItem(children[i].getAbsoluteFile());
				}
			}
		}
	}
	
}
