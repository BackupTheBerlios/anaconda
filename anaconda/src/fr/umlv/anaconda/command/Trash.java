/*
 * Created on 3 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Trash /*implements Command*/{

	
	/*public void run(Object o){
		//TODO Enlever la methode run de command
	}*/
	private ArrayList list = new ArrayList();
	private int size = 0;
	private File home = new File(System.getProperty("user.home"));
	private File garbage = new File(home,"anaconda_garbage");
	private File garbage_history = new File(home,"garbage_history");
	
	public Trash() {
		if (!garbage.exists())
			garbage.mkdir();
		else {
			loadGarbageHistory();
		}
	}
	
	
	public class FileInformation implements Serializable{
		private String old_path;
		private File file;
		public FileInformation(File file,String old_path){
			this.file = file;
			this.old_path = old_path;
		}
		
		public String getOldPath(){
			return old_path;
		}
		
		public File getFile(){
			return file;
		}
	}
	
	public void run(Object o){
		ArrayList files = (ArrayList)o;
	}
	
	public void addFile(File file,String old_path){
		list.add(new FileInformation(file,old_path));
	}
	
	
	/**
	 * Save the garbage history
	 * @throws IOException
	 */
	public void saveGarbageHistory() throws IOException  {
		if(!garbage_history.exists()){
			garbage_history.createNewFile();
		}
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(garbage_history)); 
		out.writeObject ( new Integer(list.size())) ;
		for(Iterator it = list.iterator();it.hasNext();){
			out.writeObject((FileInformation)it.next());
		}
		
	}
	
	
	/**
	 * Loads the garbage history
	 *
	 */
	public void loadGarbageHistory(){
		if(garbage_history.exists()){
			try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(garbage_history));

			int tmp_size = ((Integer)in.readObject()).intValue();
			for(int i = 0;i<size;i++){
				list.add((FileInformation)in.readObject());
			}
			}catch(FileNotFoundException e){
			}
			catch(IOException e){
			}
			catch(ClassNotFoundException e){
			}
		}
	}
	
}
