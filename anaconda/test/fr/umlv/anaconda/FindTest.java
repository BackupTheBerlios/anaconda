/*
 * Created on 4 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.TooMuchFilesException;

import junit.framework.TestCase;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FindTest extends TestCase {
	
	public FindTest(){
		
	}
	
	public void testFindModel(){
		JFrame frame = new JFrame();
		FindModel model = new FindModel();
		JList list = new JList(model);
		JScrollPane scroll = new JScrollPane(list);
		frame.getContentPane().add(scroll);
		frame.setSize(800,600);
		frame.show();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		File f = new File("/");
		Find find = new Find();
		
		try {
			find.find(f,".*",model);
		} catch (TooMuchFilesException e) {
			JOptionPane.showMessageDialog(null, " Veuillez affiner votre recherche ", "  ", JOptionPane.OK_OPTION);
		}
		while(true);

	}

}
