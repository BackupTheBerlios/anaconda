package fr.umlv.anaconda.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;
import fr.umlv.anaconda.exception.NoSelectedFilesException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

public class CreateFile implements Command {

	private File current_folder = null;
	private final String default_name = "fichier";
	private String current_name = null;

	public void run() {
		ArrayList selected_file = Main.getSelectionItems();

		if (selected_file.size() < 1) {
			(new NoSelectedFilesException()).show();
			return;
		}

		if (selected_file.size() > 1) {
			(new TooMuchFilesException()).show();
			return;
		}

		current_folder = (File) selected_file.get(0);
		if(!current_folder.isDirectory())
			 current_folder = current_folder.getParentFile();

		if (!current_folder.isDirectory()) {
			(new IsNotDirectoryException(current_folder)).show();
			return;
		}

		if (!current_folder.canWrite()) {
			(new CanNotWriteException(current_folder)).show();
			return;
		}

		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}
		try {
			f.createNewFile();
		} catch (SecurityException e) {
			(new CanNotWriteException(f)).show();
		} catch (IOException e) {
			(new ErrorIOFileException(f)).show();
		}
	}

	public void run(File current_folder) {
		
		this.current_folder = current_folder;
		
		if (!current_folder.isDirectory()) {
			(new IsNotDirectoryException(current_folder)).show();
			return;
		}

		if (!current_folder.canWrite()) {
			(new CanNotWriteException(current_folder)).show();
			return;
		}

		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}
		try {
			f.createNewFile();
		} catch (SecurityException e) {
			(new CanNotWriteException(f)).show();
		} catch (IOException e) {
			(new ErrorIOFileException(f)).show();
		}
	}

	public void redo() {
		File f;
		int i = 1;
		current_name = default_name;
		while ((f = new File(this.current_folder, current_name)).exists()) {
			current_name = default_name.concat("" + i + "");
			i++;
		}
		try {
			f.createNewFile();
		} catch (SecurityException e) {
			(new CanNotWriteException(f)).show();
		} catch (IOException e) {
			(new ErrorIOFileException(f)).show();
		}
	}

	public void undo() {
		File file = new File(current_folder, current_name);
		try {
			file.delete();
		} catch (SecurityException e) {
			(new CanNotDeleteException(file)).show();
		}
	}
}
