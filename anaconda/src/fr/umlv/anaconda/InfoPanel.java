/*
 * Created on 22 f?vr. 2004
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package fr.umlv.anaconda;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.umlv.anaconda.command.Find;
import fr.umlv.anaconda.exception.DoNotExistFileException;
import fr.umlv.anaconda.exception.TooMuchFilesException;
import fr.umlv.anaconda.tools.Extension;

/**
 * @author FIGUEROA
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
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
		setBackground(Color.WHITE);
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
					//TODO verifier l'exception
				} catch (DoNotExistFileException exception) {
					exception.show();
				}
			}
		});

		setBorder(BorderFactory.createTitledBorder("RECHERCHE"));
		JPanel find_panel = new JPanel();
		JPanel title_panel = new JPanel();
		GridLayout layout = new GridLayout(4, 2);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title_panel.add(label_title);
		add(title_panel);
		find_panel.setSize(100, 100);
		find_panel.setLayout(layout);

		find_panel.add(label_name);
		find_panel.add(field_name);

		find_panel.add(label_root);
		find_panel.add(field_root_name);

		find_panel.add(new JLabel(""));
		JPanel panel_button = new JPanel();
		panel_button.setLayout(new GridLayout(1, 2));
		panel_button.add(ok_button);
		find_panel.add(panel_button);
		add(find_panel);
		revalidate();
	}

	public void setAsGeneral(File f, int nb_elements) {

		removeAll();
		repaint();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("INFORMATIONS FICHIERS"));
		JLabel label_nb_elements =
			new JLabel(" " + nb_elements + " elements selectionnes");
		add(label_nb_elements);
		if (f != null) {
			if (Extension.isImage(f.getName())) {
				Image image = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(200,200, Image.SCALE_FAST);
				ImageIcon image_icon = new ImageIcon(image);
				JLabel apercu =  new JLabel(image_icon);
				add(apercu);
			}
		}
		revalidate();
	}

	/*public class PropertiesThread extends Thread {
		ArrayList list;
		public PropertiesThread() {
	
		}
	
		public void setList(ArrayList list) {
			this.list = list;
		}
	
		public void run() {
			System.out.println("unr " + list.size());
			if (list.size() > 1) {
				System.out.println("debut du run");
				int cpt = 0;
				int files_cpt = 0;
				int folder_cpt = 0;
				for (Iterator it = list.iterator(); it.hasNext();) {
					cpt++;
					if (((File) it).exists()) {
						if (((File) it).isFile()) {
							files_cpt++;
						} else {
							folder_cpt++;
						}
					}
				}
				removeAll();
				repaint();
				setBorder(
					BorderFactory.createTitledBorder("INFORMATIONS FICHIERS"));
				setLayout(new BoxLayout(Main.info_panel, BoxLayout.Y_AXIS));
				JLabel nb_elements =
					new JLabel(" " + cpt + " elements selectionnes");
				add(nb_elements);
				JLabel nb_files =
					new JLabel(" " + files_cpt + " fichiers selectionnes");
				add(nb_files);
				JLabel nb_folders =
					new JLabel(" " + folder_cpt + " repertores selectionnes");
				add(nb_folders);
				revalidate();
			}
		}
	}*/

}
