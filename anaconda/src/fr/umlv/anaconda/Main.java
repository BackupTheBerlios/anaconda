package fr.umlv.anaconda;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import fr.umlv.anaconda.command.*;

import java.awt.*;
import java.awt.event.*;
/*
 * Ajouter la commande annuler et refaire.
 * Pour annuler: AllCommand.undoLastCommand();
 * Pour refaire: AllCommand.redoLastCommand();
 * Pour savoir si on peut annuler: AllCommand.canUndo() => boolean;
 * Pour savoir si on peut refaire: AllCommand.canRedo() => boolean;
 */
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


	final public static GarbageModel garbage_model = new GarbageModel();
	final public static FindModel find_model = new FindModel();
	final public static MyTabbedPane tabb = new MyTabbedPane(model,find_model,garbage_model);
		   final public static ModelListAdapter listModel = tabb.getListModel();
		   final public static JList list = tabb.getListFiles();
	final public static ListRenderer listCellRenderer = new ListRenderer(listModel);
	final public static TreeRenderer treeRenderer = new TreeRenderer();
	final private static int LIST_FOCUS = 0;
	final private static int TREE_FOCUS = 1;
	final private static int NONE_FOCUS = 2;
	private static int lastFocused = NONE_FOCUS;
	private static ArrayList selection_items = new ArrayList();
	
	/* RECUPERATION DE LA SELECTION */
	public static ArrayList getSelectionItems() {
		selection_items.clear();
		
		switch(lastFocused) {
		case LIST_FOCUS:
			Object[] o = list.getSelectedValues();
			for (int i=0;i<o.length;i++)
					selection_items.add(o[i]);
		break;
		case TREE_FOCUS:
			selection_items.add(((Model) tree.getLastSelectedPathComponent()).getFolder());
			break;
		}
		return selection_items;
	}

	/* METHODE MAIN */
	public static void main(String[] args) throws Exception {
		IconsManager im = new IconsManager();
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
		tree.setCellRenderer(treeRenderer);
		/* CREATION DE LA LISTE */
		/***********************/
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		/***********************/
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		
		list.setCellRenderer(listCellRenderer);

		JScrollPane scrollTree = new JScrollPane(tree);
		/******************************/
		JPanel infoPanel = new JPanel();
		ImageIcon anacondaLogo = IconsManager.logo;
		infoPanel.add(new JLabel(anacondaLogo));
		infoPanel.setBackground(Color.WHITE);

		JSplitPane splitTreeInfo =
			new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree, infoPanel);
		splitTreeInfo.setOneTouchExpandable(true);
		splitTreeInfo.setDividerLocation(260);
		splitTreeInfo.setResizeWeight(1);
		splitTreeInfo.setDividerSize(2);

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
		splitPane.setDividerSize(2);
		/**********************************/
		/* LES ACTIONS */
		final Action refreshAction = new AbstractAction("Actualiser"){
					public void actionPerformed(ActionEvent e){
						model.setFolder(model.getFolder());
					}
		};
		final Action createFile = new AbstractAction("Cree Fichier"){
			public void actionPerformed(ActionEvent e){
				//(new CreateFile()).run();
				AllCommand.get("createfile").run();
				refreshAction.actionPerformed(e);
			}
		};
		final Action createFolder = new AbstractAction("Cree Repertoire"){
			public void actionPerformed(ActionEvent e){
				TreePath path = tree.getSelectionPath();
				//(new CreateFolder()).run();
				AllCommand.get("createfolder").run();
				refreshAction.actionPerformed(e);
				treeModel.reload(model);
				tree.expandPath(path);
				tree.setSelectionPath(path);
				tree.scrollPathToVisible(path);
			}
		};
		final Action copyAction = new AbstractAction("Copier    Ctrl+C") {
			public void actionPerformed(ActionEvent e) {
				//(new Copy()).run();
				AllCommand.get("copy").run();
			}
		};
		final Action cutAction = new AbstractAction("Couper   Ctrl+X") {
			public void actionPerformed(ActionEvent e) {
				//(new Cut()).run();
				AllCommand.get("cut").run();
			}
		};
		final Action pasteAction = new AbstractAction("Coller    Ctrl+V") {
			public void actionPerformed(ActionEvent e) {
				//(new Paste()).run();
				AllCommand.get("paste").run();
			}
		};
		final Action dupAction = new AbstractAction("Dupliquer    Ctrl+Alt+C") {
			public void actionPerformed(ActionEvent e) {
				//(new Clone()).run();
				AllCommand.get("clone").run();
			}
		};
		final Action moveAction = new AbstractAction("Deplacer    Ctrl+Alt+X") {
			public void actionPerformed(ActionEvent e) {
				//(new Move()).run();
				AllCommand.get("move").run();
			}
		};
		final Action selectAllAction = new AbstractAction("Selectionner tout") {
			public void actionPerformed(ActionEvent e) {
				list.setSelectionInterval(0, listModel.getSize() - 1);
			}
		};
		final Action renameAction = new AbstractAction("Renommer") {
			public void actionPerformed(ActionEvent e) {
				//(new Rename()).run();
				AllCommand.get("rename").run();
				refreshAction.actionPerformed(e);
			}
		};
		final Action deleteAction = new AbstractAction("Supprimer") {
			public void actionPerformed(ActionEvent e) {
				//Delete de la corbeille
				//(new Delete()).run();
				//AllCommand.get("delete").run();
				//refreshAction.actionPerformed(e);
				AllCommand.get("trash").run();
			}
		};
		final Action findAction = new AbstractAction("Rechercher"){
			public void actionPerformed(ActionEvent e){
				FindFrame frame = new FindFrame(find_model);
				frame.show();
			}
		};
		final Action bigIconsAction = new AbstractAction("Grandes Icones"){
			public void actionPerformed(ActionEvent e){
				listCellRenderer.setIconsSize(IconsManager.BIG_ICONS);
				refreshAction.actionPerformed(e);
			}
		};
		final Action smallIconsAction = new AbstractAction("Petites Icones"){
			public void actionPerformed(ActionEvent e){
				listCellRenderer.setIconsSize(IconsManager.SMALL_ICONS);
				refreshAction.actionPerformed(e);
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
		newFileItem.addActionListener(createFile);
		JMenuItem newFolderItem = new JMenuItem("Repertoire    Ctrl+R");
		newFolderItem.addActionListener(createFolder);
		JMenuItem newFrameItem =
			new JMenuItem("Fenetre d'exploration    Ctrl+E");
		subMenuNew.add(newFileItem);
		subMenuNew.add(newFolderItem);
		subMenuNew.add(newFrameItem);
		file.add(subMenuNew);
		JMenuItem findItem = new JMenuItem("Rechercher    Ctrl+F");
		findItem.addActionListener(findAction);
		JMenuItem propertiesItem = new JMenuItem("Proprietes    Ctrl+P");
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Voulez vous vraiment quitter ?"," Quitter ",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
					System.exit(1);	
				// TODO g?rer proprement, v?rifier si on est pas en train de travailler ?..
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
		JMenuItem reloadItem = new JMenuItem(refreshAction);
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
		typeBig.addActionListener(bigIconsAction);
		JMenuItem typeSmall = new JMenuItem("Petites icones");
		typeSmall.addActionListener(smallIconsAction);
		JMenuItem typeList = new JMenuItem("Liste");
		JMenuItem typeDetail = new JMenuItem("Detail");
		subMenuType.add(typeBig);
		subMenuType.add(typeSmall);
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
		final JButton back = new JButton(IconsManager.back);
		final JButton next = new JButton(IconsManager.next);
		JButton cut = new JButton(IconsManager.cut);
		JButton copy = new JButton(IconsManager.copy);
		JButton paste = new JButton(IconsManager.paste);
		JButton find = new JButton(IconsManager.find);
		find.addActionListener(findAction);

		toolBar.add(back);
		toolBar.add(next);
		toolBar.add(cut);
		toolBar.add(copy);
		toolBar.add(paste);
		toolBar.add(find);
		toolBar.setRollover(true);

		/* ADRESSBAR */
		JToolBar adressBar = new JToolBar();
		adressBar.setFloatable(false);
		JButton delAdr = new JButton("effacer");
		JLabel adr = new JLabel("adresse");
		JButton openAdr = new JButton("ouvrir");
		String fileName = model.getFolder().getAbsolutePath();
/*		final JComboBox adrZone = new JComboBox(
					new AddressBarComboBoxModel(
								fileName
				+ ((fileName.endsWith(File.separator)) ? "" : File.separator)
													   ));

        adrZone.setEditable(true);
        */
		final AddressBar adrZone = new AddressBar(fileName + ((fileName.endsWith(File.separator)) ? "" : File.separator));
		//adrZone.addKeyListener(((AddressBarComboBoxModel)adrZone.getModel()).listenerFactory());
		adrZone.setActionCommand("test");
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
		/*		adrZone.getEditor().setItem(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator)); */
				adrZone.setText(
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
		/*		adrZone.getEditor().setItem(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator)); */
				adrZone.setText(
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
				System.out.println(e.getActionCommand());
				System.out.println(e.paramString());
			//	if (e.getActionCommand().)
				File file = new File(adrZone.getText()); 
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
		/*		adrZone.getEditor().setItem( 
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator)); */
				adrZone.setText(
						fileName
						+ ((fileName.endsWith(File.separator))
								? ""
								: File.separator));
			}
		});
		adrZone.addKeyListener(adrZone.listenerFactory());
		openAdr.addActionListener((adrZone.getActionListeners())[0]); //TODO
		delAdr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adrZone.getEditor().setItem(""); 
			}
		});
		mainFrame.setJMenuBar(menuBar);
		mainFrame.getContentPane().add(panelBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
		/***********************************************/
		/* MENU DEROULANT */
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
		/* LISTERNER SUR L'ARBRE ET LA LISTE */
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = TREE_FOCUS;
				selectAllAction.setEnabled(false);
				pasteAction.setEnabled(true);
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				File file = null;
				if(path != null) file = ((Model)path.getLastPathComponent()).getFolder();
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 :
						if (file != null && e.getClickCount() == 1) {
							if(!tree.isExpanded(path)) tree.expandPath(path);
							else if(!((Model)path.getLastPathComponent()).equals(treeModel.getRoot()))
								tree.collapsePath(path);
							tree.setSelectionPath(path);
							tree.scrollPathToVisible(path);
							oldCurrentFolder = model.getFolder();
							back.setEnabled(true);
							next.setEnabled(false);
							newCurrentFolder = file;
							if (Model.cmp.compare(oldCurrentFolder, file) != 0) {
								model.setFolder(file);
								String fileName = file.getAbsolutePath();
						/*		adrZone.getEditor().setItem( 
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator)); */
								adrZone.setText(
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
							tree.setSelectionPath(path);
							tree.scrollPathToVisible(path);
							clickOutFile.show(e.getComponent(), e.getX(), e.getY());
						}
						break;
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = LIST_FOCUS;
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
								model.setFolder(file);
								TreePath path = tree.getSelectionPath();
								if(path != null) {
									TreePath parent = path.getParentPath();
									if(parent != null) {
										Model parentModel = (Model)parent.getLastPathComponent();
										if(parentModel.getIndex(model.getParent()) != -1)
											path = parent.pathByAddingChild(model.getParent());
										else
											path = parent;
									}
									if(((Model)path.getLastPathComponent()).getIndex(model) != -1)
										path = path.pathByAddingChild(model);
									tree.expandPath(path);
									tree.setSelectionPath(path);
									tree.scrollPathToVisible(path);
								}
								String fileName = file.getAbsolutePath();
							/*	adrZone.getEditor().setItem( 
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator)); */
								adrZone.setText(
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
		tree.setForeground(new Color(210,230,255));
		tree.setBackground(new Color(210,230,255));
		mainFrame.show();
	}
}
