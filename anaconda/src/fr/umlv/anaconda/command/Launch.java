package fr.umlv.anaconda.command;

/* Maitrise info - génie logiciel */
/* Anaconda (Livingstone project) */
/* Created on 21 févr. 2004 */

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Action;

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
		if (selected_file!=null && !selected_file.isDirectory()) {
			try {
				r.exec(selected_file.getCanonicalPath());
				// sinon lancer avec l'appli qui va bien...
			} catch (IOException ex)
			{
				File exec = new ChoozExec().frameChoozRep();
				if (exec != null) {
					try {
						r.exec(exec.getCanonicalPath()+" \""+selected_file.getAbsolutePath()+"\"");
					} catch (IOException e) {
						new ErrorIOFileException(selected_file).show();
					}
				}
			}
		}
	}
	
	public void redo() {
	}

	public void undo() {
	}

	public boolean canUndo() {
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.umlv.anaconda.command.Command#getAction()
	 */
	public Action getAction() {
		return new AbstractAction(){
			public void actionPerformed(ActionEvent arg0) {
				run();
			}
		};
	}
}
