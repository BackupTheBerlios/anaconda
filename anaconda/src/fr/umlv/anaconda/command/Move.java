package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.tools.ChoozRep;

/*
 * Cr�� le 5 f�vr. 2004
 *
 */

/**
 * 
 * @author abrunete
 *
 */
public class Move implements Command {

	private File dest;
	private ArrayList selection;
	
	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
		selection = Main.getSelectionItems();
		if ( dest == null  || !dest.isDirectory() ) {
			dest = ChoozRep.frameChoozRep();
		}
		
		File origin;
		for (int i=0; i<selection.size(); i++) {
			origin = (File) selection.get(i);
			origin.renameTo(new File( dest.getAbsolutePath()+origin.getName() ));
		}
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {
		// TODO Raccord de m�thode auto-g�n�r�

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {
		// TODO Raccord de m�thode auto-g�n�r�

	}

	public static void main(String[] args) {
	}
}
