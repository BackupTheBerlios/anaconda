package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

public class EmptyPressPaperException extends Exception {

	public EmptyPressPaperException() {
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Pas de sélection",
			"Pas d'élément à coller.",
			JOptionPane.WARNING_MESSAGE);
	}

}
