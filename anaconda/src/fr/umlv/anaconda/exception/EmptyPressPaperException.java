package fr.umlv.anaconda.exception;

import javax.swing.JOptionPane;

public class EmptyPressPaperException extends Exception {

	public EmptyPressPaperException() {
	}

	public void show() {
		JOptionPane.showMessageDialog(
			null,
			"Pas de s�lection",
			"Pas d'�l�ment � coller.",
			JOptionPane.WARNING_MESSAGE);
	}

}
