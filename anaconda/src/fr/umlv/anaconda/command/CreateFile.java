package fr.umlv.anaconda.command;

import java.io.File;
import java.io.IOException;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
import fr.umlv.anaconda.exception.CanNotWriteException;
import fr.umlv.anaconda.exception.ErrorIOFileException;
import fr.umlv.anaconda.exception.IsNotDirectoryException;

public class CreateFile implements Command {

	private File current_folder = null;
	private final String default_name = "fichier";
	private String current_name = null;

	public void run() {

		this.current_folder = Main.model.getFolder();

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

	public boolean canUndo() {
		return true;
	}
}
