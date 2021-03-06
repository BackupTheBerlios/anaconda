/*
 * Cr?? le 5 f?vr. 2004
 *
 * Pour changer le mod?le de ce fichier g?n?r?, allez ? :
 * Fen?tre&gt;Pr?f?rences&gt;Java&gt;G?n?ration de code&gt;Code et commentaires
 */
package fr.umlv.anaconda;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

/**
 * @author ofiguero
 *
 * Pour changer le mod?le de ce commentaire de type g?n?r?, allez ? :
 * Fen?tre&gt;Pr?f?rences&gt;Java&gt;G?n?ration de code&gt;Code et commentaires
 */
public class FindPanel{

	public String name;
	public String root_file_name;

	final public static JPanel panel = new JPanel();
	JLabel label_title = new JLabel("RECHERCHE DE FICHIERS/REPERTOIRES ");
	JLabel label_name = new JLabel("fichier a trouver : ");
	JTextField field_name = new JTextField();
	JLabel label_root = new JLabel("repertoire de depart : ");
	JTextField field_root_name = new JTextField();
	JButton choose_button = new JButton("choisir");
	JButton ok_button = new JButton("OK");


	public FindPanel(final FindModel model) {
		//super("Recherche de fichiers");


		//TODO faire une fenetre de recherche plus jolie
		ok_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String root_name = field_root_name.getText();
					String name = field_name.getText();
					if (root_name != null && name != null) {
						File root_file = new File(root_name);
						Find find = new Find();

						if (find.set(root_file, name, model) == 0) {
							JOptionPane.showMessageDialog(
								null,
								" Une recherche est deja en cours ...");
						} else {
							find.start();
						}
					}
				} catch (TooMuchFilesException exception) {
					exception.show();
				} catch (DoNotExistFileException exception) {
					exception.show();
				}
			}
		});

	}
	
	public static JPanel getPanel(){
		return panel;
	}

	public String getNameToFind() {
		return field_name.getName();
	}

	public String getRootFileName() {
		return field_root_name.getName();
	}

}
