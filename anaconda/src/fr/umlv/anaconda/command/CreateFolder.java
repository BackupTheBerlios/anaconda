/*
 * Created on 3 f�vr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.io.File;


/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CreateFolder {
	private File current_folder = null;
		private final String default_name = "repertoire";
		private String current_name = null;


		public void run(Object current_folder){
			this.current_folder = (File)current_folder;
			File f;
			int i = 1;
			current_name = default_name;
			while((f = new File(this.current_folder,current_name)).exists()){
				current_name = default_name.concat(""+i+"");
				i++;
			}
	
				f.mkdir();

		}


		public void redo(){
			File f;
			int i = 1;
			current_name = default_name;
			while((f = new File(this.current_folder,current_name)).exists()){
				current_name = default_name.concat(""+i+"");
				i++;
			}

				f.mkdir();

		}


		public void undo(){
			File file = new File(current_folder,current_name);
			file.delete();
		}

	
}
