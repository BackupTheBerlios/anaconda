/*
 * Cr�� le 3 f�vr. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import java.io.*;
/**
 * @author ofiguero
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class Delete {
	
	public void run(Object o){
		File file = (File)o;
		delete(file);
	}
	
	public void delete(File file) {
		if(file.isDirectory()){
			File[] children = file.listFiles();
			for(int i=0;i<children.length;i++){
				delete(children[i]);
			}
			file.delete();
		}
		else{
			file.delete();
		}
	}
	
	public void redo(){
		
	}
	
	public void undo(){
		
	}
}
