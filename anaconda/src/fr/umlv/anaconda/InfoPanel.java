/*
 * Created on 22 févr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;

/**
 * @author FIGUEROA
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class InfoPanel extends JPanel{


	JLabel label_title = new JLabel("RECHERCHE DE FICHIERS/REPERTOIRES ");
	JLabel label_name = new JLabel("fichier a trouver : ");
	JTextField field_name = new JTextField();
	JLabel label_root = new JLabel("repertoire de depart : ");
	JTextField field_root_name = new JTextField();
	JButton choose_button = new JButton("choisir");
	JButton ok_button = new JButton("OK");

	
	public InfoPanel(){
		setAsDefault();
	}

	
	public void setAsDefault(){
		removeAll();
		ImageIcon anacondaLogo = IconsManager.LOGO;
		add(new JLabel(anacondaLogo));
		setBackground(Color.WHITE);
	}
	
	
	public void setAsFind(final FindModel model){
		removeAll();
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
					//TODO verifier l'exception
				} catch (DoNotExistFileException exception) {
					exception.show();
				}
			}
		});
		GridLayout layout = new GridLayout(4, 2);
		setLayout(layout);

		add(label_title);
		add(new JLabel(""));

		add(label_name);
		add(field_name);

		add(label_root);
		add(field_root_name);

		add(new JLabel(""));
		JPanel panel_button = new JPanel();
		panel_button.setLayout(new GridLayout(1, 2));
		panel_button.add(ok_button);
		add(panel_button);
		repaint();

	}
	
}
