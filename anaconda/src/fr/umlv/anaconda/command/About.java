package fr.umlv.anaconda.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.umlv.anaconda.IconsManager;
import fr.umlv.anaconda.appearance.Themes;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 28 févr. 2004 */

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

	/**
	 * 
	 */
	public About() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
		final JFrame frame = new JFrame("A propos...");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		
		frame.getContentPane().add(new JLabel(LOGO), BorderLayout.WEST);
		frame.getContentPane().add(new JLabel(" - ANACONDA - "), BorderLayout.CENTER);
		frame.getContentPane().add(new JLabel(" Explorateur de fichier"), BorderLayout.EAST);
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

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#canUndo()
	 */
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

}
