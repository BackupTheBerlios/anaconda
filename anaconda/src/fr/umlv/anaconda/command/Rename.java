/*
 * Created on 1 févr. 2004
 */
package fr.umlv.anaconda.command;

import java.io.File;

public class Rename {
	private File file;
	private String origin_name;
	private String new_name;

	public Rename(){
	}
	
	public void run(Object o,String new_name){
		File file = (File)o;
		File tmp_file = new File(new_name);
		file.renameTo(tmp_file);
	}

public void redo(){
	File tmp_file = new File(new_name);
	file.renameTo(tmp_file);
}

	public void undo() {
		File origin_file = new File(origin_name);
		file.renameTo(origin_file);
	}
}
