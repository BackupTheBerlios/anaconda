/*
 * Créé le 5 févr. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package fr.umlv.anaconda;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

/**
 * @author ofiguero
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FindFrame extends JFrame {

	public String name;
	public String root_file_name;

	JPanel panel = new JPanel();
	JLabel label_title = new JLabel("RECHERCHE DE FICHIERS/REPERTOIRES ");
	JLabel label_name = new JLabel("fichier a trouver : ");
	JTextField field_name = new JTextField();
	JLabel label_root = new JLabel("repertoire de depart : ");
	JTextField field_root_name = new JTextField();
	JButton choose_button = new JButton("choisir");
	JButton ok_button = new JButton("OK");
	JButton cancel_button = new JButton("Annuler");

	public FindFrame(final FindModel model) {
		super("Recherche de fichiers");
		//		GridBagLayout layout = new GridBagLayout();
		//	    Constraints constraints = new Constraints();
		//
		//		panel.setLayout(layout);

		//		/*TITLE*/
		//		constraints.setConstraints( 0 , 0 , 5 , 1 , 1 , 1 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(label_title);
		//		
		//		/*FILE TO FIND*/
		//		constraints.setConstraints( 0 , 1 , 2 , 1 , 1 , 2 ,Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(label_name);
		//		
		//		constraints.setConstraints( 2 , 1 , 3 , 1 , 1 , 2 ,Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(field_name);
		//		
		//		/*ROOT FILE*/
		//		constraints.setConstraints( 0 , 2 , 2 , 1 , 1 , 3 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(label_root);
		//		
		//		constraints.setConstraints( 2 , 2 , 2 , 1 , 1 , 3 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(field_root_name);
		//		
		//		constraints.setConstraints( 4 , 2 , 1 , 1 , 1 , 3 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(choose_button);
		//		
		//		/*CONFIRM BUTTONS*/
		//		constraints.setConstraints( 3 , 3 , 1 ,  1 , 1 , 4 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(ok_button);
		//		
		//		constraints.setConstraints( 4 , 3 , 1 ,  1 , 1 , 4 , Constraints.HORIZONTAL,Constraints.CENTER);
		//		layout.setConstraints(label_title,constraints);
		//		panel.add(cancel_button);

		//TODO faire une fenetre de recherche plus jolie
		ok_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String root_name = field_root_name.getText();
					String name = field_name.getText();
					if (root_name != null && name != null) {
						File root_file = new File(root_name);
						Find find = new Find();
						find.set(root_file,name,model);
						find.start();
					}
				} catch (TooMuchFilesException exception) {
					//TODO verifier l'exception
				} catch (DoNotExistFileException exception) {

				}
				FindFrame.this.dispose();
			}
		});
		GridLayout layout = new GridLayout(4, 2);
		panel.setLayout(layout);

		panel.add(label_title);
		panel.add(new JLabel(""));

		panel.add(label_name);
		panel.add(field_name);

		panel.add(label_root);
		panel.add(field_root_name);

		panel.add(new JLabel(""));
		JPanel panel_button = new JPanel();
		panel_button.setLayout(new GridLayout(1, 2));
		panel_button.add(ok_button);
		panel_button.add(cancel_button);
		panel.add(panel_button);

		getContentPane().add(panel);
		setSize(350, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getNameToFind() {
		return field_name.getName();
	}

	public String getRootFileName() {
		return field_root_name.getName();
	}

}
