package fr.umlv.anaconda.command;

/* Maitrise info - g?nie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 28 f?vr. 2004 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fr.umlv.anaconda.IconsManager;
import fr.umlv.anaconda.appearance.Themes;

/**
 * Manage the view of the "About" window
 *
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class About implements Command {

	final public static String images_path = Themes.getCurrentPath();
	final public static ImageIcon LOGO = new ImageIcon(IconsManager.class.getResource(images_path + "anaconda_logo.gif"));

	/** default constructor */
	public About() {
		super();
	}

	public void run() {
		final JFrame frame = new JFrame("A propos...");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		frame.getContentPane().add(new JLabel(LOGO), BorderLayout.WEST);
		JLabel lblTitle = new JLabel(" - ANACONDA - ");
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setOpaque(true);
		frame.getContentPane().add(lblTitle, BorderLayout.CENTER);
		JLabel lblDesc = new JLabel(" Explorateur de fichiers  ");
		lblDesc.setBackground(Color.WHITE);
		lblDesc.setOpaque(true);
		frame.getContentPane().add(lblDesc, BorderLayout.EAST);
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		}
				);
		frame.getContentPane().add(btnOK, BorderLayout.SOUTH);
		
		frame.pack();
		frame.show();
	}

	public void redo() {
	}

	public void undo() {
	}

	public boolean canUndo() {	
		return false;
	}

}
