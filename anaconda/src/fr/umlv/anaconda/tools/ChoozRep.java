package fr.umlv.anaconda.tools;
/*
 * Created on 20 nov. 2003
 *
 * Matrise info 2003/2004
 * Interface Graphique
 * Projet Hoover
 * 
 */
import javax.swing.*;
import java.io.*;

/**
 * Browse the system to choose a directory
 * @author Figueroa Olivier
 * @author Bruneteau Adrien
 *
 */
public class ChoozRep {
	/** the directory */
	private static File dir;
	
	/**
	 * To create a window used to browse disk in order to choose a directory
	 * @return the choosen directory
	 */
	public static File frameChoozRep() {
		
		JFileChooser fileChooz = new JFileChooser();
		RepFileFilter filtre = new RepFileFilter(); 
		
		fileChooz.setFileFilter(filtre);
		fileChooz.setAcceptAllFileFilterUsed(false);
		fileChooz.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooz.setMultiSelectionEnabled(false);
		fileChooz.setDialogTitle("Choix du repertoire de destination");
		fileChooz.setApproveButtonText("Valider");
		fileChooz.setApproveButtonToolTipText("Valide le reperoire sélectionné");
		
		int returnVal = fileChooz.showSaveDialog(null); 
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			dir = fileChooz.getSelectedFile();
		}
		return dir;
	}
	
}
