/*
 * Created on 22 fevr. 2004
 */
package fr.umlv.anaconda;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Date;

import javax.swing.*;

import fr.umlv.anaconda.appearance.Themes;
import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;
import fr.umlv.anaconda.tools.Extension;
import fr.umlv.anaconda.tools.Tools;

/**
 * @author FIGUEROA
 */
public class InfoPanel extends JPanel {

	JLabel label_title = new JLabel("RECHERCHE DE FICHIERS/REPERTOIRES ");
	JLabel label_name = new JLabel("fichier a trouver : ");
	JTextField field_name = new JTextField();
	JLabel label_root = new JLabel("repertoire de depart : ");
	JTextField field_root_name = new JTextField();
	JButton choose_button = new JButton("choisir");
	JButton ok_button = new JButton("OK");
	//PropertiesThread t = new PropertiesThread();

	public InfoPanel() {
		setAsDefault();
	}

	public void setAsDefault() {
		/*t.interrupt();*/
		removeAll();
		repaint();
		setBorder(BorderFactory.createTitledBorder("INFO PANEL"));
		ImageIcon anacondaLogo = IconsManager.LOGO;
		add(new JLabel(anacondaLogo));
//		setBackground(Color.WHITE);
		setBackground(Themes.getBgColor());
		revalidate();
	}

	public void setAsProperties(
		String name,
		boolean can_read,
		boolean can_write,
		boolean can_delete,
		boolean is_directory,
		boolean is_hidden,
		long size,
		long last_modified) {
		/*t.interrupt();*/
		removeAll();
		repaint();
		setBorder(BorderFactory.createTitledBorder("PROPRIETES de " + name));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		/* Ajout du titre */
		/*
		 * JLabel title = new JLabel(" PROPRIETES de " + name);
		 * title.setForeground(new Color(50,0,0));
		 */
		/* Derniere modification */
		JLabel label_creation =
			new JLabel(
				" "+(new Date(last_modified)).toString());
		/* Type */
		String type = " Type : ";
		if (is_directory)
			type = type.concat("repertoire");
		else
			type = type.concat("fichier");
		JLabel label_type = new JLabel(type);
		add(label_type);
		/* Taille */
		JLabel label_size = new JLabel(" Taille : " + size + " octets");
		add(label_size);
		/* Droits */
		String droits = " Droits : ";
		if (can_read)
			droits = droits.concat(" - lecture ");
		if (can_write)
			droits = droits.concat(" - ecriture ");
		JLabel label_droits = new JLabel(droits);
		add(label_droits);
		/* Supprimable */
		JLabel label_supprimable;
		/*if (can_delete)
			label_supprimable = new JLabel(" " + name + " est supprimable");
		else
			label_supprimable =
				new JLabel(" " + name + " n'est pas supprimable");
		add(label_supprimable);*/
		/* Cache */
		if (is_hidden) {
			JLabel label_cache = new JLabel(" " + name + " est cache");
			add(label_cache);
		}
		add(label_creation);	
		revalidate();
	}

	public void setAsFind(final FindModel model) {
		/*t.interrupt();*/
		removeAll();
		repaint();
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

		
		
		
		setBorder(BorderFactory.createTitledBorder("RECHERCHE"));
		/*JPanel find_panel = new JPanel();
		JPanel title_panel = new JPanel();*/
		//GridLayout layout = new GridLayout(4, 2);
		
		//add(label_title);
		//add(title_panel);

		JPanel find_panel = new JPanel();
		find_panel.setBackground(Themes.getBgColor());
		find_panel.setLayout(new BoxLayout(find_panel, BoxLayout.Y_AXIS));
		find_panel.setMaximumSize(new Dimension(200,100));
		find_panel.add(label_name);
		find_panel.add(field_name);
		
		find_panel.add(label_root);
		find_panel.add(field_root_name);
		find_panel.add(ok_button);
		add(find_panel,BorderLayout.CENTER);
		//add(find_panel);
		revalidate();
	}

	public void setAsGeneral(File f, int nb_elements) {

		removeAll();
		repaint();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("INFORMATIONS FICHIERS"));
		JLabel label_nb_elements;
		if(nb_elements <= 1)
			label_nb_elements = new JLabel(" " + nb_elements + " element selectionne");
		else
			label_nb_elements = new JLabel(" " + nb_elements + " elements selectionnes");
		add(label_nb_elements);
		if (f != null) {
			if (Extension.isImage(f.getName())) {
				Image img = new ImageIcon(f.getAbsolutePath()).getImage();
				Image image = Tools.resizeImg(img, 145, 190, label_nb_elements);
				ImageIcon image_icon = new ImageIcon(image);
				JLabel apercu =  new JLabel(image_icon);
				add(apercu);
			}
		}
		revalidate();
	}

}
