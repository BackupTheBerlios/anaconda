/*
 * Créé le 3 févr. 2004
 * 
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda.command;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;

import fr.umlv.anaconda.Main;
import fr.umlv.anaconda.exception.CanNotDeleteException;
/**
 * @author ofiguero
 * 
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Delete extends AbstractAction {

	public void run(Object o) throws CanNotDeleteException {
		ArrayList array_file = (ArrayList) o;
		for (Iterator i = array_file.iterator(); i.hasNext();)
			delete((File) i.next());
	}

	public void delete(File file) throws CanNotDeleteException {
		if (file.isDirectory()) {
			File[] children = file.listFiles();

			for (int i = 0; i < children.length; i++)
				delete(children[i]);
			try {
				file.delete();
			} catch (SecurityException e) {
				throw new CanNotDeleteException(file);
			}
		} else
			try {
				file.delete();
			} catch (SecurityException e) {
				throw new CanNotDeleteException(file);
			}
	}

	public void redo() {

	}

	public void undo() {

	}

	public void actionPerformed(ActionEvent arg0) {
		ArrayList selected_file = Main.getSelectionItems();
		if (selected_file.size() < 1)
			//TODO cas ou on n a rien selectionne.
			// (new NoSelectedFilesException()).show();
			// return;
			;

		try {
			run(Main.getSelectionItems());
		} catch (CanNotDeleteException e) {
			e.show();
		}

	}

}
