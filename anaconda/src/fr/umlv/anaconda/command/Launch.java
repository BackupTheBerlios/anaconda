package fr.umlv.anaconda.command;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;

public class Launch implements Command {

	//	a utiliser pour savoir comment lancer...
	private static final String os = System.getProperty("os.name");
	private Runtime r = Runtime.getRuntime();
	
	public Launch() {
		super();
	}
	
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

	public void run(File selected_file) {
		// verifier que c'est un executable
		try {
			r.exec(selected_file.getCanonicalPath());
		} catch (IOException ex)
		{
			new ErrorIOFileException(selected_file).show();
		}
		// sinon lancer avec l'appli qui va bien...
	}
	
	public void redo() {
	}

	public void undo() {
	}

	public boolean canUndo() {
		return false;
	}
}
