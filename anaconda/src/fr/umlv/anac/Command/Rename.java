/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anac.Command;

import java.io.File;

public class Rename {
	private File file;
	private String origin_name;

	public Rename(File file, String new_name) {
		this.file = file;
		origin_name = new String(file.getName());
		File new_file = new File(new_name);
		file.renameTo(new_file);
	}

	public void undo() {
		File origin_file = new File(origin_name);
		file.renameTo(origin_file);
	}

}
