package fr.umlv.anaconda.command;

/*
 * Créé le 5 févr. 2004
 *
 */

import java.io.File;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.ChoozRep;

/**
 * Moving files
 * @author Anac team
 *
 */
public class Move implements Command {

	private File dest=null;
	private ArrayList selection;
	
	
	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#run()
	 */
	public void run() {
		
		selection = Main.getSelectionItems();
		if ( dest == null  || !dest.isDirectory() ) {
			dest = ChoozRep.frameChoozRep();
		}
		
		if (!dest.isDirectory() ) { 
			(new IsNotDirectoryException(dest)).show();
			return;
		}

		if (!dest.canWrite()){
			 (new CanNotWriteException(dest)).show();
			return;
		}
		
		
		File origin;
		for (int i=0; i<selection.size(); i++) {
			origin = (File) selection.get(i);
			origin.renameTo( new File( dest.getAbsolutePath()+File.separatorChar+origin.getName() ) );
		}
		Main.model.setFolder(Main.newCurrentFolder);
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#redo()
	 */
	public void redo() {
		// TODO pas encore fait

	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#undo()
	 */
	public void undo() {
		// TODO pas encore fait

	}
	
}
