package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.tools.ChoozRep;

/*
 * Créé le 5 févr. 2004
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
		// TODO Raccord de méthode auto-généré

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {
		// TODO Raccord de méthode auto-généré

	}

	public static void main(String[] args) {
	}
}
