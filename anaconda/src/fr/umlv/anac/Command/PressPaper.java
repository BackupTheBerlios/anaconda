package fr.umlv.anac.Command;

import java.util.*;

/**
 * Contains all usefull methods.
 */
public class PressPaper {
	private static ArrayList selectedFiles = new ArrayList();
	private static boolean willDeleted;

	public static void addToPressPaper(ArrayList selection, boolean deleted){
		selectedFiles.clear();
		selectedFiles.addAll(selection);
		willDeleted = deleted;
	}
	
	public static ArrayList getSelectedFiles(){
		return selectedFiles;
	}
	
	public static boolean toDelete(){
		return willDeleted;
	}
	
	public static boolean isEmpty(){
		return selectedFiles.isEmpty();
	}
	
	public static void clear(){
		selectedFiles.clear();
	}
	/*public static void main(String[] args) throws Exception {
		PressPaper pp = new PressPaper();
		
		for(int i = 0; i < args.length - 1; i ++) selectedFiles.add(new File(args[i]));
		willDeleted = (new Boolean(args[args.length - 1])).booleanValue();
		File test = new File("PASTE_FOLDER");
		test.mkdir();
		Paste.paste(test);*/
		/*
		System.out.println("toString <"+test.toString()+">");
		System.out.println("getName <"+test.getName()+">");
		System.out.println("getAbsolutePath <"+test.getAbsolutePath()+">");
		System.out.println("getPath <"+test.getPath()+">");
		System.out.println("isDirectory <"+test.isDirectory()+">");
		System.out.println("isFile <"+test.isFile()+">");
		*/
	//}
}
