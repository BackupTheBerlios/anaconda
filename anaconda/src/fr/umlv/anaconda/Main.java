package fr.umlv.anaconda;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 */
public class Main {
	/**/
	public static File oldCurrentFolder;
	public static File newCurrentFolder;
	final public static Model model = new Model();
	final public static ModelTreeAdapter treeModel =
		new ModelTreeAdapter(model);
	final public static JTree tree = new JTree(treeModel);
	/*final public static ModelListAdapter listModel =
		new ModelListAdapter(model);
	final public static JList list = new JList(listModel);*/
	final public static MyTabbedPane tabb = new MyTabbedPane(model);
		   final public static ModelListAdapter listModel = tabb.getListModel();
		   final public static JList list = tabb.getListFiles();
	/* RECUPERATION DE LA SELECTION */
	public static ArrayList getSelectionItems() {
		ArrayList selection_items = new ArrayList();
		if (list.hasFocus()){
			Object[] o = list.getSelectedValues();
			for (int i=0;i<o.length;i++){
				if (Model.cmp.compare(o[i], model.getFolderParent()) != 0)
					selection_items.add(o[i]);
			}
		}
		else if (tree.hasFocus()){
			selection_items.add(((Model) tree.getLastSelectedPathComponent()).getFolder());
		}
		return selection_items;
	}
	/* VARIABLES POUR LES ICONES */
	final public static String iconesResourcePath = "/images/";
	final public static String[] iconesPath = { "anaconda_logo.gif",
		"back.gif", "copy.gif", "cut.gif", "find.gif", "iconExe.gif",
		"iconFather.gif", "iconFocus.gif", "iconImg.gif", "iconNode.gif",
		"iconOpen.gif", "iconRep.gif", "iconText.gif", "next.gif", "paste.gif" };
	final public static int LOGO_ICONE = 0;
	final public static int BACK_ICONE = 1;
	final public static int COPY_ICONE = 2;
	final public static int CUT_ICONE = 3;
	final public static int FIND_ICONE = 4;
	final public static int EXE_ICONE = 5;
	final public static int FATHER_ICONE = 6;
	final public static int FOCUS_ICONE = 7;
	final public static int IMG_ICONE = 8;
	final public static int NODE_ICONE = 9;
	final public static int OPEN_ICONE = 10;
	final public static int REP_ICONE = 11;
	final public static int TEXT_ICONE = 12;
	final public static int NEXT_ICONE = 13;
	final public static int PASTE_ICONE = 14;
	final public static URL[] icones = new URL[iconesPath.length];
	static {
		for(int i = 0; i < iconesPath.length; i ++)
			icones[i] = Main.class.getResource(iconesResourcePath + iconesPath[i]);
	}
	/* METHODE MAIN */
	public static void main(String[] args) throws Exception {
		final JFrame mainFrame = new JFrame("Anaconda");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		oldCurrentFolder = newCurrentFolder = model.getFolder();
		/* CREATION DE L'ARBRE */
		TreeSelectionModel treeSelection = new DefaultTreeSelectionModel();
		treeSelection.setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(treeSelection);
		tree.setSelectionRow(0);
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean selected,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {
				Component c =
					super.getTreeCellRendererComponent(
						tree,
						value,
						selected,
						expanded,
						leaf,
						row,
						hasFocus);
				String name = ((Model) value).getFolder().getName();
				if (name.compareTo("") == 0)
					name = ((Model) value).getFolder().getAbsolutePath();
				((JLabel) c).setText(name);
				if (selected)
					((JLabel) c).setIcon(new ImageIcon(icones[FOCUS_ICONE]));
				else if (expanded)
					((JLabel) c).setIcon(new ImageIcon(icones[OPEN_ICONE]));
				else
					((JLabel) c).setIcon(new ImageIcon(icones[NODE_ICONE]));
				return c;
			}
		});
		/* CREATION DE LA LISTE */
		/***********************/
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		/***********************/
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		list.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(
				JList list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {
				Component c =
					super.getListCellRendererComponent(
						list,
						value,
						index,
						isSelected,
						cellHasFocus);
				String name = ((File) value).getName();
				File parent = listModel.getModel().getFolder().getParentFile();
				if (parent == null)
					parent = listModel.getModel().getFolder();
				if (parent.getAbsolutePath().compareTo(((File) value).getAbsolutePath()) == 0)
					name = "..";
				else if (name.compareTo("") == 0)
					name = ((File) value).getAbsolutePath();
				((JLabel) c).setText(name);
				/************************/
				if (((File) value).isDirectory()) {
					if (name.compareTo("..") == 0)
						((JLabel) c).setIcon(new ImageIcon(icones[FATHER_ICONE]));
					else
						((JLabel) c).setIcon(new ImageIcon(icones[REP_ICONE]));
				}
				else {
					if (name.endsWith(".jpg"))
						((JLabel) c).setIcon(new ImageIcon(icones[IMG_ICONE]));
					else if (name.endsWith(".exe"))
						((JLabel) c).setIcon(new ImageIcon(icones[EXE_ICONE]));
					else
						((JLabel) c).setIcon(new ImageIcon(icones[TEXT_ICONE]));
				}
				/************************/
				return c;
			}
		});
		JScrollPane scrollTree = new JScrollPane(tree);
		//JScrollPane scrollList = new JScrollPane(list);
		/******************************/
		JPanel infoPanel = new JPanel();
		ImageIcon anacondaLogo = new ImageIcon(icones[LOGO_ICONE]);
		infoPanel.add(new JLabel(anacondaLogo));
		infoPanel.setBackground(Color.WHITE);

		JSplitPane splitTreeInfo =
			new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree, infoPanel);
		splitTreeInfo.setOneTouchExpandable(true);
		splitTreeInfo.setDividerLocation(260);
		splitTreeInfo.setResizeWeight(1);

		Dimension size = new Dimension(200, 200);
		infoPanel.setMinimumSize(size);
		infoPanel.setMaximumSize(size);
		/******************************/
		JSplitPane splitPane =
			new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				splitTreeInfo /*scrollTree*/
		, /*scrollList*/tabb);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(mainFrame.getWidth() / 3);
		/**********************************/
		/* LES ACTIONS */
		final Action copyAction = new AbstractAction("Copier    Ctrl+C") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action cutAction = new AbstractAction("Couper   Ctrl+X") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action pasteAction = new AbstractAction("Coller    Ctrl+V") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action dupAction = new AbstractAction("Dupliquer    Ctrl+Alt+C") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action moveAction = new AbstractAction("Deplacer    Ctrl+Alt+X") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action selectAllAction = new AbstractAction("Selectionner tout") {
			public void actionPerformed(ActionEvent e) {
				list.setSelectionInterval(0, listModel.getSize() - 1);
			}
		};
		final Action renameAction = new AbstractAction("Renommer") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		final Action deleteAction = new AbstractAction("Supprimer") {
			public void actionPerformed(ActionEvent e) {
				//TODO action du paste
			}
		};
		/* MENUBAR */
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Fichier");
		JMenu edit = new JMenu("Edition");
		JMenu disp = new JMenu("Affichage");
		JMenu help = new JMenu("?");

		/* Fichier */
		JMenu subMenuNew = new JMenu("Nouveau...");
		JMenuItem newFileItem = new JMenuItem("Fichier    Ctrl+T");
		JMenuItem newFolderItem = new JMenuItem("Repertoire    Ctrl+R");
		JMenuItem newFrameItem =
			new JMenuItem("Fenetre d'exploration    Ctrl+E");
		subMenuNew.add(newFileItem);
		subMenuNew.add(newFolderItem);
		subMenuNew.add(newFrameItem);
		file.add(subMenuNew);
		JMenuItem findItem = new JMenuItem("Rechercher    Ctrl+F");
		JMenuItem propertiesItem = new JMenuItem("Proprietes    Ctrl+P");
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?"," Quitter ",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
					System.exit(1);	
				// TODO gérer proprement, vérifier si on est pas en train de travailler ?..
			}
		}
				);
		
		file.add(findItem);
		file.add(propertiesItem);
		file.add(quitter);
		/* Edition */
		JMenuItem copyItem = new JMenuItem(copyAction);
		JMenuItem cutItem = new JMenuItem(cutAction);
		JMenuItem pasteItem = new JMenuItem(pasteAction);
		JMenuItem dupItem = new JMenuItem(dupAction);
		JMenuItem moveItem = new JMenuItem(moveAction);
		JMenuItem selectAllItem = new JMenuItem(selectAllAction);
		JMenuItem renameItem = new JMenuItem(renameAction);
		JMenuItem deleteItem = new JMenuItem(deleteAction);
		edit.add(copyItem);
		edit.add(cutItem);
		edit.add(pasteItem);
		edit.add(dupItem);
		edit.add(moveItem);
		edit.add(selectAllItem);
		edit.add(renameItem);
		edit.add(deleteItem);
		/* Affichage */
		JMenuItem reloadItem = new JMenuItem("Actualiser");
		JMenu subMenuTri = new JMenu("Organiser par...");
		JMenuItem triName = new JMenuItem("Nom");
		JMenuItem triType = new JMenuItem("Type");
		JMenuItem triSize = new JMenuItem("Taille");
		JMenuItem triDate = new JMenuItem("Date");
		subMenuTri.add(triName);
		subMenuTri.add(triType);
		subMenuTri.add(triSize);
		subMenuTri.add(triDate);
		JMenu subMenuType = new JMenu("Type d'affichage...");
		JMenuItem typeBig = new JMenuItem("Grandes icones");
		JMenuItem typeSamll = new JMenuItem("Petites icones");
		JMenuItem typeList = new JMenuItem("Liste");
		JMenuItem typeDetail = new JMenuItem("Detail");
		subMenuType.add(typeBig);
		subMenuType.add(typeSamll);
		subMenuType.add(typeList);
		subMenuType.add(typeDetail);
		JMenu subMenuBar = new JMenu("Barres...");
		JMenuItem barTools = new JMenuItem("Barre d'outils");
		JMenuItem barAdr = new JMenuItem("Barre d'adresse");
		subMenuBar.add(barTools);
		subMenuBar.add(barAdr);
		JMenu subMenuLangue = new JMenu("Langue...");
		JMenuItem french = new JMenuItem("Francais");
		JMenuItem english = new JMenuItem("Anglais");
		JMenuItem spanish = new JMenuItem("Espagnol");
		subMenuLangue.add(french);
		subMenuLangue.add(english);
		subMenuLangue.add(spanish);
		disp.add(reloadItem);
		disp.add(subMenuTri);
		disp.add(subMenuType);
		disp.add(subMenuBar);
		disp.add(subMenuLangue);
		/* ? */
		help.add(new JMenuItem("Aide"));
		help.add(new JMenuItem("A propos"));

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(disp);
		menuBar.add(help);

		/* TOOLBAR */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		final JButton back = new JButton(new ImageIcon(icones[BACK_ICONE]));
		final JButton next = new JButton(new ImageIcon(icones[NEXT_ICONE]));
		JButton cut = new JButton(new ImageIcon(icones[CUT_ICONE]));
		JButton copy = new JButton(new ImageIcon(icones[COPY_ICONE]));
		JButton paste = new JButton(new ImageIcon(icones[PASTE_ICONE]));
		JButton find = new JButton(new ImageIcon(icones[FIND_ICONE]));

		//back.setBackground(Color.WHITE);
		//next.setBackground(Color.WHITE);
		//cut.setBackground(Color.WHITE);
		//copy.setBackground(Color.WHITE);
		//paste.setBackground(Color.WHITE);
		//find.setBackground(Color.WHITE);
		toolBar.add(back);
		toolBar.add(next);
		toolBar.add(cut);
		toolBar.add(copy);
		toolBar.add(paste);
		toolBar.add(find);
		//toolBar.setBackground(Color.WHITE);
		toolBar.setRollover(true);

		/* ADRESSBAR */
		JToolBar adressBar = new JToolBar();
		adressBar.setFloatable(false);
		JButton delAdr = new JButton("effacer");
		JLabel adr = new JLabel("adresse");
		JButton openAdr = new JButton("ouvrir");
		String fileName = model.getFolder().getAbsolutePath();
		final JComboBox adrZone = new JComboBox(new AddressBarComboBoxModel(
			fileName
				+ ((fileName.endsWith(File.separator)) ? "" : File.separator)));
		adrZone.setEditable(true);
		
		adressBar.add(delAdr);
		adressBar.add(adr);
		adressBar.add(adrZone);
		adressBar.add(openAdr);

		/* PANELBAR */
		JPanel panelBar = new JPanel();
		panelBar.setLayout(new BorderLayout());
		panelBar.add(toolBar, BorderLayout.NORTH);
		panelBar.add(adressBar, BorderLayout.CENTER);

		/* Listeners des BAR */
		back.setEnabled(false);
		next.setEnabled(false);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setFolder(oldCurrentFolder);
				String fileName = oldCurrentFolder.getAbsolutePath();
				adrZone.getEditor().setItem(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));
				back.setEnabled(false);
				next.setEnabled(true);
			}
		});
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setFolder(newCurrentFolder);
				String fileName = newCurrentFolder.getAbsolutePath();
				adrZone.getEditor().setItem(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));
				back.setEnabled(true);
				next.setEnabled(false);
			}
		});
		adrZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ACTION".concat(e.toString())); //TODO récup le bon évenement
				File file = new File((String)adrZone.getEditor().getItem()); 
				if (file.exists()) {
					oldCurrentFolder = model.getFolder();
					newCurrentFolder = file;
					model.setFolder(file);
					back.setEnabled(true);
					next.setEnabled(false);
					adrZone.addItem(new String(file.getAbsolutePath()));
				} else
					JOptionPane.showMessageDialog(
						null,
						"Le fichier/repertoire <"
							+ file.getAbsolutePath()
							+ "> n'a pas ete trouve.",
						"Fichier/repertoire non trouve",
						JOptionPane.ERROR_MESSAGE);
				String fileName = model.getFolder().getAbsolutePath();
				adrZone.getEditor().setItem( 
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));
			}
		});
		openAdr.addActionListener((adrZone.getActionListeners())[0]);
		delAdr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adrZone.getEditor().setItem(""); 
			}
		});
		mainFrame.setJMenuBar(menuBar);
		//mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(panelBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
		/***********************************/
		/* LISTERNER SUR L'ARBRE ET LA LISTE */
		final JPopupMenu clickInFile = new JPopupMenu();
		clickInFile.add(new JMenuItem("Nouveau Fichier    Ctrl+T"));
		clickInFile.add(new JMenuItem("Nouveau Repertoire    Ctrl+R"));
		clickInFile.add(new JMenuItem("Nouvelle Fenetre d'exploration    Ctrl+E"));
		final JPopupMenu clickOutFile = new JPopupMenu();
		clickOutFile.add(new JMenuItem(copyAction));
		clickOutFile.add(new JMenuItem(cutAction));
		clickOutFile.add(new JMenuItem(pasteAction));
		clickOutFile.add(new JMenuItem(dupAction));
		clickOutFile.add(new JMenuItem(moveAction));
		clickOutFile.add(new JMenuItem(selectAllAction));
		clickOutFile.add(new JMenuItem(renameAction));
		clickOutFile.add(new JMenuItem(deleteAction));
		/***********************************************/
		//pasteMenu.addActionListener(pasteAction);
		//pasteItem.addActionListener(pasteAction);
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectAllAction.setEnabled(false);
				pasteAction.setEnabled(true);
				int index = tree.getRowForLocation(e.getX(), e.getY());
				File file = (File) listModel.getElementAt(index);
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 :
						if (file != null && e.getClickCount() == 1) {
							oldCurrentFolder = model.getFolder();
							back.setEnabled(true);
							next.setEnabled(false);
							newCurrentFolder = file;
							if (Model.cmp.compare(oldCurrentFolder, file)
								!= 0) {
								model.setFolder(file);
								String fileName = file.getAbsolutePath();
								adrZone.getEditor().setItem( 
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator));
								list.setSelectedIndex(0);
							}
						}
						break;
					case MouseEvent.BUTTON2 :
						break;
					case MouseEvent.BUTTON3 :
						if (file == null) {
							clickInFile.show(e.getComponent(), e.getX(), e.getY());
						} else {
							tree.setSelectionRow(index);
							clickOutFile.show(e.getComponent(), e.getX(), e.getY());
						}
						break;
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectAllAction.setEnabled(true);
				pasteAction.setEnabled(false);
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 :
						if (e.getClickCount() == 2) {
							File file = (File) list.getSelectedValue();
							oldCurrentFolder = model.getFolder();
							back.setEnabled(true);
							next.setEnabled(false);
							if (file.isDirectory()) {
								newCurrentFolder = file;
								if(model.equals(treeModel.getRoot()))
									tree.expandRow(0);
								model.setFolder(file);
								TreePath path = new TreePath(treeModel.getPathToRoot(model));
								if(path != null) {
									int row;
									Model pathModel = (Model)path.getLastPathComponent();
									for(row = 0; row < tree.getVisibleRowCount(); row ++) {
										TreePath pathRow = tree.getPathForRow(row);
										if(pathRow != null && model.equals(pathRow.getLastPathComponent()))
											break;
									}
									if(row < tree.getVisibleRowCount()) {
										tree.expandRow(row);
										tree.setSelectionRow(row);
										tree.validate();
										tree.repaint();
									}
								}
								String fileName = file.getAbsolutePath();
								adrZone.getEditor().setItem( 
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator));
								list.setSelectedIndex(0);
							}
						}
						break;
					case MouseEvent.BUTTON2 :
						break;
					case MouseEvent.BUTTON3 :
						int index = list.locationToIndex(new Point(e.getX(), e.getY()));
						File file = (File) listModel.getElementAt(index);
						if (file == null) {
							clickInFile.show(e.getComponent(), e.getX(), e.getY());
						} else {
							list.setSelectedIndex(index);
							pasteAction.setEnabled(file.isDirectory());
							clickOutFile.show(e.getComponent(), e.getX(), e.getY());
						}
						break;
				}
			}
		});
		mainFrame.show();
	}
}
