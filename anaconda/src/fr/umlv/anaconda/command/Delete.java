/*
 * Créé le 3 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import java.io.*;
/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
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
