/*
 * Created on 3 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;


import fr.umlv.anaconda.FindModel;
import fr.umlv.anaconda.exception.TooMuchFilesException;
/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Find implements Command{
	
	private ArrayList list = null;

	
	public void run(Object o){
		
	}
	
	public void run(Object file,String name,FindModel model) throws TooMuchFilesException{
		list = new ArrayList();
		File root_file = (File)file;
		//Pattern file_name = Pattern.compile(name);
		find(root_file,name,model);
	}
	
	public void find(File root_file,String name,FindModel model) throws TooMuchFilesException{
		File[] children = root_file.listFiles();
		for(int i = 0;i< children.length;i++){
			if(Pattern.matches(name,children[i].getName())){
				model.add(children[i]);
			}
			if(children[i].isDirectory())
				find(children[i],name,model);
		}
	}
	
	public void showResult(){
		for(Iterator it = list.iterator();it.hasNext();){
			System.out.println(((File)it.next()).toString());
		}
	}
	
	public void undo(){
		
	}
	
	public void redo(){
		
	}

}
