package fr.umlv.anaconda.command;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 févr. 2004 */

import java.io.*;
import java.util.*;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.*;
import fr.umlv.anaconda.tools.ChoozExec;

/**
 * Manage to launch files
 * 
 * @author Figeroa Olivier
 * @author Yam Jean Paul
 * @author Tesevic Novak
 * @author Roger Giao Long 
 * @author Bruneteau Adrien
 */
public class Launch implements Command {

	//	a utiliser pour savoir comment lancer...
	//private static final String os = System.getProperty("os.name");
	private Runtime r = Runtime.getRuntime();
	
	/**default constructor */
	public Launch() {
		super();
	}
	
	/** to run the command on a selection of files*/
	public void run() {// pas encore
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		for (Iterator i = selected_file.iterator(); i.hasNext();) {
			File fich = (File) i.next();
			run(fich);
		}
	}
	/**
	 * to run the command on a single file
	 * @param selected_file the selected file
	 */
	public void run(File selected_file) {
		if (!selected_file.isDirectory()) {
			try {
				r.exec(selected_file.getCanonicalPath());
			} catch (IOException ex)
			{
				//new ErrorIOFileException(selected_file).show();
				File exec = new ChoozExec().frameChoozRep();
				try {
					r.exec(exec.getCanonicalPath()+" "+selected_file.getCanonicalPath());
				} catch (IOException e) {
					new ErrorIOFileException(selected_file).show();
				}
			}
			// sinon lancer avec l'appli qui va bien...
		}
	}
	
	public void redo() {
	}

	public void undo() {
	}

	public boolean canUndo() {
		return false;
	}
	
	public static boolean isExe(File f) {
		// comment gérer bien ?
		return true;
	}
}
