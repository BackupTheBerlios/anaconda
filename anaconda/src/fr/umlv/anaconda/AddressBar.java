package fr.umlv.anaconda;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 févr. 2004 */

import java.util.Vector;
//import java.util.regex.Pattern;

import javax.swing.*;

//import fr.umlv.anaconda.exception.TooMuchFilesException;
//import java.util.*;
//import javax.swing.event.*;
import java.awt.event.*;
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
	public AddressBar(String file) {
		super(new AddressBarComboBoxModel(file));
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
		this.getEditor().setItem(txt);
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


}
