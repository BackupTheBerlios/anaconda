package fr.umlv.anaconda.command;

import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;


//import fr.umlv.anaconda.exception.ErrorIOFileException;

/* Maitrise info - g?nie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 1 mars 2004 */

/**
 *	Manage the view of the help
 *
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class Help implements Command {
	/** where the help main page should be found */
	//private static final String HELP_LOCATION = "http://developer.berlios.de/projects/anaconda/";
	private static final String HELP_LOCATION = "http://etudiant.univ-mlv.fr/~abrunete/anac/aide_anaconda.html";
	// chez moi en attendant mieux...

	protected boolean isAvailable = true;
	
	private Action act; 
	
	/** Default constructor */
	public Help() {
		super();
		
		act = new AbstractAction(){
			
		//	public static final String ACCELERATOR_KEY = /*KeyEvent.VK_F1*/;
		//	public final String ACCELERATOR_KEY = KeyStroke.getKeyStroke("VK_F1").toString();
			//this.putValue(Action.ACCELERATOR)
			
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(KeyStroke.getKeyStroke("VK_F1").toString());
				run();
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
		String os = System.getProperties().getProperty("os.name");
		Runtime r = Runtime.getRuntime();
		try
		{
			if (os.endsWith("NT")||os.endsWith("2000")||os.endsWith("XP"))
				r.exec("cmd /c start "+HELP_LOCATION);
			else
			//	r.exec("start "+HELP_LOCATION);
			// pas sur ke ca marche pour linux...
			r.exec("mozilla "+HELP_LOCATION);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		//	new ErrorIOFileException().show();
		}
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#canUndo()
	 */
	public boolean canUndo() {
		return false;
	}
	
	public Action getAction_old() {
		return new Action(){

			public boolean isEnabled() {
				return isAvailable;
			}

			public void setEnabled(boolean arg0) {
				isAvailable = arg0;
			}

			public void addPropertyChangeListener(PropertyChangeListener arg0) {
				//Rien a faire ?
			}

			public void removePropertyChangeListener(PropertyChangeListener arg0) {
				//Rien a faire ?
			}

			public Object getValue(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			public void putValue(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}};
	}

	public Action getAction() {
		return act;
	}
	
}
