
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import fr.umlv.anaconda.FindModel;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;
/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Find extends Thread implements Command {

	private static boolean searching = false;
	private ArrayList list = null;
	private File root_file = null;
	private String name = null;
	private FindModel model;

	public void run(Object o) {

	}

	public void run() {

		searching = true;
		launchFind();
		searching = false;

	}

	public int set(File file, String name, FindModel model)
		throws TooMuchFilesException, DoNotExistFileException {
		if (searching == true)
			return 0;

		if (!file.exists())
			throw new DoNotExistFileException(file);
		model.init();
		this.model = model;
		list = new ArrayList();
		root_file = (File) file;
		this.name = name;
		return 1;

	}

	public void launchFind() {
		try {
			find(root_file, name, model);
		} catch (TooMuchFilesException e) {
			JOptionPane.showMessageDialog(
				null,
				"Veuillez cibler votre recherche");
		}
	}

	public void find(File root_file, String name, FindModel model)
		throws TooMuchFilesException {

		File[] children = root_file.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (Pattern.matches(name, children[i].getName())) {
					model.add(children[i]);
				}
				if (children[i].isDirectory())
					find(children[i], name, model);
			}
		}

	}

	public void undo() {

	}

	public void redo() {

	}


	public boolean canUndo() {
		return false;
	}
	

}
