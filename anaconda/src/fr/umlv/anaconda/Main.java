package fr.umlv.anaconda;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

import fr.umlv.anaconda.command.AllCommand;
import fr.umlv.anaconda.command.Help;
import fr.umlv.anaconda.command.Launch;

public class Main {
	/* VARIABLE DE NAVIGATION */
	final public static File root = new File(System.getProperty("user.home"));
	public static File currentFolder = root;
	/* VARIABLE D'APPLICATION */
	final public static ModelTree treeModel = new ModelTree(root);
	final public static JTree tree = new JTree(treeModel);
	final public static ModelTable tableModel = new ModelTable(currentFolder);
	final public static JTable table = new JTable(tableModel);
	final public static GarbageModel garbage_model = new GarbageModel();
	final public static FindModel find_model = new FindModel(tableModel);
	final public static MyTabbedPane tabb =
		new MyTabbedPane(tableModel, find_model, garbage_model);
	final public static InfoPanel info_panel = new InfoPanel();
	final public static int TABLE_FOCUS = 0;
	final public static int TREE_FOCUS = 1;
	final public static int NONE_FOCUS = 2;
	private static int lastFocused = NONE_FOCUS;
	private static ArrayList selection_items = new ArrayList();
	private static Color bg_color = new Color(210, 230, 255);
	/* PILE DE PRECEDENT SUIVANT */
	final public static Stack backFolderStack = new Stack();
	final public static Stack nextFolderStack = new Stack();
	public static File oldCurrentFolder;
	public static File newCurrentFolder;
	/* MENU DEROULANT */
	final static JPopupMenu clickInFile = new JPopupMenu();
	final static JPopupMenu clickOutFile = new JPopupMenu();
	/*static {
		clickInFile.add(new JMenuItem("Nouveau Fichier    Ctrl+T"));
		clickInFile.add(new JMenuItem("Nouveau Repertoire    Ctrl+R"));
		clickInFile.add(new JMenuItem("Nouvelle Fenetre d'exploration    Ctrl+E"));
		clickOutFile.add(new JMenuItem("Copier    Ctrl+C"));
		clickOutFile.add(new JMenuItem("Couper   Ctrl+X"));
		clickOutFile.add(new JMenuItem("Coller    Ctrl+V"));
		clickOutFile.add(new JMenuItem("Dupliquer    Ctrl+Alt+C"));
		clickOutFile.add(new JMenuItem("Deplacer    Ctrl+Alt+X"));
		clickOutFile.add(new JMenuItem("Selectionner tout"));
		clickOutFile.add(new JMenuItem("Renommer"));
		clickOutFile.add(new JMenuItem("Supprimer"));
	}*/
	/* METHODES */
	public static ArrayList getSelectionItems() {
		selection_items.clear();
		switch (lastFocused) {
			case TABLE_FOCUS :
				int[] rows = table.getSelectedRows();
				int rowsCount = table.getSelectedRowCount();
				int[] cols = table.getSelectedColumns();
				int colsCount = table.getSelectedColumnCount();
				for(int i = 0; i < rowsCount; i ++) {
					for(int j = 0; j < colsCount; j ++) {
						if(table.isCellSelected(rows[i], cols[j])) {
							selection_items.add((File)tableModel.getValueAt(rows[i], cols[j]));
						}
					}
				}
				break;
			case TREE_FOCUS :
				selection_items.add(tree.getLastSelectedPathComponent());
				break;
		}
		return selection_items;
	}
	public static Color getBgColor() {
		return bg_color;
	}
	public static void refresh() {
		treeModel.setFolder(currentFolder);
		tableModel.setFolder(currentFolder);
	}
	public static void setFolder(File newFolder) {
		currentFolder = newFolder;
		refresh();
	}
	public static void initTree() {
		tree.setExpandsSelectedPaths(true);
		tree.setScrollsOnExpand(true);
		TreeSelectionModel treeSelection = new DefaultTreeSelectionModel();
		treeSelection.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(treeSelection);
		/**************************************************************/
		/***/
		if(currentFolder.getAbsolutePath().startsWith(root.getAbsolutePath())) {
			String homeName = currentFolder.getAbsolutePath();
			TreePath homePath = new TreePath(root);
			int currentSeparator = root.getAbsolutePath().length();
			int indexSeparator = homeName.indexOf(File.separatorChar, currentSeparator);
			if(indexSeparator == currentSeparator) {
				currentSeparator = indexSeparator + 1;
				indexSeparator = homeName.indexOf(File.separatorChar, currentSeparator);
			}
			while(indexSeparator != -1) {
				homePath = homePath.pathByAddingChild(new File(homeName.substring(0, indexSeparator)));
				tree.expandPath(homePath);
				currentSeparator = indexSeparator + 1;
				indexSeparator = homeName.indexOf(File.separatorChar, currentSeparator);
			}
			if(!currentFolder.equals(root)) homePath = homePath.pathByAddingChild(currentFolder);
			tree.setSelectionPath(homePath);
			tree.expandPath(homePath);
			tree.scrollPathToVisible(homePath);
		}
		/***/
		/**************************************************************/
		/* POSITIONNEMENT DU RENDU */
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				if(value == null) return new JLabel("");
				File file = (File)value;
				if(file.getName().compareTo("") == 0) ((JLabel)c).setText(file.getAbsolutePath());
				else ((JLabel)c).setText(file.getName());
				if(selected) ((JLabel)c).setIcon(IconsManager.focus_icon);
				else if(expanded) ((JLabel)c).setIcon(IconsManager.small_father_icon);
				else ((JLabel)c).setIcon(IconsManager.small_folder_icon);
				return c;
			}
		});
		/* POSITIONNEMENT DES EVENEMENT SOURIS */
		tree.addMouseListener(new MouseAdapter() {
			private TreePath oldPath = null;
			public void mouseClicked(MouseEvent e) {
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				File file = null;
				if(path != null) file = (File)path.getLastPathComponent();
				else if(oldPath != null) {
					tree.setSelectionPath(oldPath);
					tree.scrollPathToVisible(path);
					oldPath = null;
				}
				switch(e.getButton()) {
					case MouseEvent.BUTTON1 :
					if(file != null && e.getClickCount() == 1) {
						tree.setSelectionPath(path);
						tree.expandPath(path);
						if(!file.equals(currentFolder)) setFolder(file);
					}
					break;
					
					case MouseEvent.BUTTON2 :
					break;
					
					case MouseEvent.BUTTON3 :
					if(file == null) clickInFile.show(e.getComponent(), e.getX(), e.getY());
					else {
						if(oldPath == null) oldPath = tree.getSelectionPath();
						tree.setSelectionPath(path);
						tree.scrollPathToVisible(path);
						clickOutFile.show(e.getComponent(), e.getX(), e.getY());
					}
					break;
				}
			}
		});
	}
	public static void initTable() {
		table.setCellSelectionEnabled(true);
		table.setShowGrid(false);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		/* POSITIONNEMENT DU RENDU */
		TableRenderer.setIconsSize(IconsManager.BIG_ICONS);
		table.setDefaultRenderer(File.class, new TableRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if(value == null) return new JLabel("");
				File file = (File)value;
				if(file.equals(currentFolder)) {
					((JLabel)c).setText(".");
					((JLabel)c).setIcon(TableRenderer.FATHER_ICON);
				}
				else if(file.equals(currentFolder.getParentFile())) {
					((JLabel)c).setText("..");
					((JLabel)c).setIcon(TableRenderer.FATHER_ICON);
				}
				if(tableModel.getColumnCount() < 2) {
					((JLabel)c).setHorizontalAlignment(SwingConstants.LEFT);
					((JLabel)c).setVerticalAlignment(SwingConstants.CENTER);
					((JLabel)c).setHorizontalTextPosition(SwingConstants.RIGHT);
					((JLabel)c).setVerticalTextPosition(SwingConstants.CENTER);
				}
				return c;
			}
		});
		/* POSITIONNEMENT DES EVENEMENT SOURIS */
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Point p = new Point(e.getX(), e.getY());
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				File file = (File)tableModel.getValueAt(row, column);
				if(file == null) {
					row = column = 0;
					if(!currentFolder.equals(table.getValueAt(0, 0))) {
						if(tableModel.getColumnCount() > 1) column = 1;
						else row = 1;
					}
				}
				switch(e.getButton()) {
					case MouseEvent.BUTTON1 :
					if(e.getClickCount() == 2) {
						if(file == null || file.equals(currentFolder)) break;
						if(file.isDirectory()) {
							boolean down = currentFolder.equals(file.getParentFile());
							setFolder(file);
							TreePath currentPath = tree.getSelectionPath();
							if(currentPath != null) {
								if(down) currentPath = currentPath.pathByAddingChild(currentFolder);
								else currentPath = currentPath.getParentPath();
								tree.setSelectionPath(currentPath);
								tree.expandPath(currentPath);
								tree.scrollPathToVisible(currentPath);
							}
						}
					}
					break;
					
					case MouseEvent.BUTTON2 :
					break;
					
					case MouseEvent.BUTTON3 :
					if(file == null || file.equals(currentFolder) || file.equals(currentFolder.getParentFile())) {
						if(table.getSelectedRow() != row || table.getSelectedColumn() != column) {
							table.setRowSelectionInterval(row, row);
							table.setColumnSelectionInterval(column, column);
						}
						clickInFile.show(e.getComponent(), e.getX(), e.getY());
						break;
					}
					int[] rows = table.getSelectedRows();
					int[] columns = table.getSelectedColumns();
					boolean selectItem = false;
					if(rows.length == 1 && columns.length == 1) selectItem = true;
					else {
						boolean inRows = false;
						for(int i = 0; i < rows.length && !inRows; i ++){
							if(rows[i] == row) inRows = true;
						}
						if(!inRows) selectItem = true;
						else {
							boolean inColumns = false;
							for(int i = 0; i < columns.length && !inColumns; i ++) {
								if(columns[i] == column) inColumns = true;
							}
							selectItem = !inColumns;
						}
					}
					if(selectItem && (table.getSelectedRow() != row || table.getSelectedColumn() != column)) {
						table.setRowSelectionInterval(row, row);
						table.setColumnSelectionInterval(column, column);
					}
					clickOutFile.show(e.getComponent(), e.getX(), e.getY());
					break;
				}
			}
		});
	}
	public static void main(String[] args) {
		IconsManager im = new IconsManager();
		final JFrame mainFrame = new JFrame(" - Anaconda - ");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		oldCurrentFolder = newCurrentFolder = currentFolder;
		/* INITIALISATION DE L'ARBRE ET DE LA TABLE */
		initTree();
		initTable();/*****/tableModel.setDimmension(50, 5);
		
		JScrollPane scrollTree = new JScrollPane(tree);
		final JPanel infoPanel = new JPanel();

		JSplitPane splitTreeInfo =
			new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree, info_panel);
		splitTreeInfo.setOneTouchExpandable(true);
		splitTreeInfo.setDividerLocation(260);
		splitTreeInfo.setResizeWeight(1);
		splitTreeInfo.setDividerSize(2);

		Dimension size = new Dimension(200, 200);
		infoPanel.setMinimumSize(size);
		infoPanel.setMaximumSize(size);
		/******************************/
		//JTabbedPane tabb = new JTabbedPane();
		JSplitPane splitPane =
			new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				splitTreeInfo, tabb);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(mainFrame.getWidth() / 3);
		splitPane.setDividerSize(2);
		/**********************************/
		/* LES ACTIONS */
		final Action refreshAction = new AbstractAction("Actualiser") {
			public void actionPerformed(ActionEvent e) {
				setFolder(currentFolder);
			}
		};
		final Action createFile = new AbstractAction("Cree Fichier") {
			public void actionPerformed(ActionEvent e) {
					//(new CreateFile()).run();
	AllCommand.get("createfile").run();
				refreshAction.actionPerformed(e);
			}
		};
		final Action createFolder = new AbstractAction("Cree Repertoire") {
			public void actionPerformed(ActionEvent e) {
				TreePath path = tree.getSelectionPath();
				//(new CreateFolder()).run();
				AllCommand.get("createfolder").run();
				refreshAction.actionPerformed(e);
				setFolder(currentFolder);
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
		final Action dupAction =
			new AbstractAction("Dupliquer    Ctrl+Alt+C") {
			public void actionPerformed(ActionEvent e) {
					//(new Clone()).run();
	AllCommand.get("clone").run();
			}
		};
		final Action moveAction =
			new AbstractAction("Deplacer    Ctrl+Alt+X") {
			public void actionPerformed(ActionEvent e) {
					//(new Move()).run();
	AllCommand.get("move").run();
			}
		};
		final Action selectAllAction =
			new AbstractAction("Selectionner tout") {
			public void actionPerformed(ActionEvent e) {
				table.selectAll();
				info_panel.setAsGeneral(null, tableModel.getSize() - 1);
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
		final Action findAction = new AbstractAction("Rechercher") {
			public void actionPerformed(ActionEvent e) {
					//FindPanel find_panel = new FindPanel(find_model);
		//info_panel.setPanel(FindPanel.getPanel());
	info_panel.setAsFind(find_model);
			}

		};
		final Action bigIconsAction = new AbstractAction("Grandes Icones") {
			public void actionPerformed(ActionEvent e) {
				TableRenderer.setIconsSize(IconsManager.BIG_ICONS);
				tableModel.setDimmension(tableModel.getRowCount(), tableModel.getColumnCount());
				refreshAction.actionPerformed(e);
			}
		};
		final Action smallIconsAction = new AbstractAction("Petites Icones") {
			public void actionPerformed(ActionEvent e) {
				TableRenderer.setIconsSize(IconsManager.SMALL_ICONS);
				tableModel.setDimmension(tableModel.getRowCount(), tableModel.getColumnCount());
				refreshAction.actionPerformed(e);
			}
		};

		final Action showPropertiesAction = new AbstractAction("Proprietes") {
			public void actionPerformed(ActionEvent e) {
				AllCommand.get("showproperties").run();
			}
		};

		final Action aboutAction = new AbstractAction("A propos") {
			public void actionPerformed(ActionEvent e) {
				AllCommand.get("about").run();
			}
		};

	/*	final Action helpAction = new AbstractAction("Aide") {
			public void actionPerformed(ActionEvent e) {
				AllCommand.get("help").run();
			}
		};
*/
		final Action helpAction = ((Help)(AllCommand.get("help"))).getAction();

		
		final Action showByNameAction = new AbstractAction("Nom") {
					public void actionPerformed(ActionEvent e) {
						ComparatorsManager.addCmp("by_name");
						setFolder(newCurrentFolder);
					}
				};

		final Action showBySizeAction = new AbstractAction("Taille") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_size");
				setFolder(newCurrentFolder);
			}
		};

		final Action showByTypeAction = new AbstractAction("Type") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_type");
				setFolder(newCurrentFolder);
			}
		};
		final Action showByDateAction = new AbstractAction("Date") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_date");
				setFolder(newCurrentFolder);
			}
		};

		final Action undoAction = new AbstractAction("Annuler") {
			public void actionPerformed(ActionEvent e) {
				AllCommand.undoLastCommand();
			}
		};

		final Action redoAction = new AbstractAction("Refaire") {
			public void actionPerformed(ActionEvent e) {
				AllCommand.redoLastCommand();
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
		propertiesItem.addActionListener(showPropertiesAction);
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane
					.showConfirmDialog(
						null,
						"Voulez vous vraiment quitter ?",
						" Quitter ",
						JOptionPane.OK_CANCEL_OPTION)
					== JOptionPane.OK_OPTION)
					System.exit(1);
			}
		});

		file.add(findItem);
		file.add(propertiesItem);
		file.add(quitter);
		/* Edition */
		JMenuItem undoItem = new JMenuItem(undoAction);
		JMenuItem redoItem = new JMenuItem(redoAction);
		JMenuItem copyItem = new JMenuItem(copyAction);
		JMenuItem cutItem = new JMenuItem(cutAction);
		JMenuItem pasteItem = new JMenuItem(pasteAction);
		JMenuItem dupItem = new JMenuItem(dupAction);
		JMenuItem moveItem = new JMenuItem(moveAction);
		JMenuItem selectAllItem = new JMenuItem(selectAllAction);
		JMenuItem renameItem = new JMenuItem(renameAction);
		JMenuItem deleteItem = new JMenuItem(deleteAction);
		edit.add(undoItem);
		edit.add(redoItem);
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
		JCheckBoxMenuItem triName = new JCheckBoxMenuItem(showByNameAction);
		JCheckBoxMenuItem triType = new JCheckBoxMenuItem(showByTypeAction);
		JCheckBoxMenuItem triSize = new JCheckBoxMenuItem(showBySizeAction);
		JCheckBoxMenuItem triDate = new JCheckBoxMenuItem(showByDateAction);
		subMenuTri.add(triName);
		subMenuTri.add(triType);
		subMenuTri.add(triSize);
		subMenuTri.add(triDate);
		JMenu subMenuType = new JMenu("Type d'affichage...");
		ButtonGroup grpType = new ButtonGroup(); // pour activer un seul a la fois
		JCheckBoxMenuItem typeBig = new JCheckBoxMenuItem("Grandes icones");
		grpType.add(typeBig);
		typeBig.setState(true);
		typeBig.addActionListener(bigIconsAction);
		JCheckBoxMenuItem typeSmall = new JCheckBoxMenuItem("Petites icones");
		grpType.add(typeSmall);
		typeSmall.addActionListener(smallIconsAction);
		JCheckBoxMenuItem typeList = new JCheckBoxMenuItem("Liste");
		grpType.add(typeList);
		JCheckBoxMenuItem typeDetail = new JCheckBoxMenuItem("Detail");
		grpType.add(typeDetail);
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
		JMenuItem itemHelp = new JMenuItem("Aide");
		itemHelp.addActionListener(helpAction);
		help.add(itemHelp);
		JMenuItem itemAbout = new JMenuItem("A propos");
		itemAbout.addActionListener(aboutAction);
		help.add(itemAbout);

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(disp);
		menuBar.add(help);

		/* TOOLBAR */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorderPainted(false);
		final JButton back = new JButton(IconsManager.BACK);
		back.setRolloverIcon(IconsManager.ONFOCUSBACK);
		final JButton refresh = new JButton(IconsManager.REFRESH);
		refresh.setRolloverIcon(IconsManager.ONFOCUSREFRESH);
		final JButton next = new JButton(IconsManager.NEXT);
		next.setRolloverIcon(IconsManager.ONFOCUSNEXT);
		final JButton cut = new JButton(IconsManager.CUT);
		cut.setRolloverIcon(IconsManager.ONFOCUSCUT);
		final JButton copy = new JButton(IconsManager.COPY);
		copy.setRolloverIcon(IconsManager.ONFOCUSCOPY);
		final JButton paste = new JButton(IconsManager.PASTE);
		paste.setRolloverIcon(IconsManager.ONFOCUSPASTE);
		final JButton find = new JButton(IconsManager.FIND);
		find.setRolloverIcon(IconsManager.ONFOCUSFIND);
		final JButton home = new JButton(IconsManager.HOME);
		home.setRolloverIcon(IconsManager.ONFOCUSHOME);
		final JButton garbage = new JButton(IconsManager.GARBAGE);
		garbage.setRolloverIcon(IconsManager.ONFOCUSGARBAGE);
		find.addActionListener(findAction);

		toolBar.add(home);
		toolBar.add(back);
		toolBar.add(refresh);
		toolBar.add(next);
		toolBar.addSeparator();
		toolBar.add(cut);
		toolBar.add(copy);
		toolBar.add(paste);
		toolBar.addSeparator();
		toolBar.add(garbage);
		toolBar.add(find);
		toolBar.setRollover(true);
		toolBar.setFloatable(true);

		/* ADRESSBAR */
		JToolBar adressBar = new JToolBar();
		adressBar.setFloatable(false);
		JButton delAdr = new JButton("effacer");
		JLabel adr = new JLabel("adresse");
		JButton openAdr = new JButton("ouvrir");
		String fileName = currentFolder.getAbsolutePath();
		final AddressBar adrZone =
			new AddressBar(
				fileName
					+ ((fileName.endsWith(File.separator))
						? ""
						: File.separator));
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
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFolder(root);
			}
		});
		back.setEnabled(false);
		next.setEnabled(false);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oldCurrentFolder = (File) backFolderStack.pop();
				nextFolderStack.push(currentFolder);
				next.setEnabled(true);
				setFolder(oldCurrentFolder);
				String fileName = oldCurrentFolder.getAbsolutePath();
				adrZone.setText(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));
				if (backFolderStack.empty())
					back.setEnabled(false);

			}
		});
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCurrentFolder = (File) nextFolderStack.pop();
				backFolderStack.push(currentFolder);
				back.setEnabled(true);
				setFolder(newCurrentFolder);
				String fileName = newCurrentFolder.getAbsolutePath();
				adrZone.setText(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));

				if (nextFolderStack.empty())
					next.setEnabled(false);
			}
		});
		adrZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				System.out.println(e.paramString());
				File file = new File(adrZone.getText());
				if (file.exists()) {
					oldCurrentFolder = currentFolder;
					newCurrentFolder = file;
					setFolder(file);
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
				String fileName = currentFolder.getAbsolutePath();
				adrZone.setText(
					fileName
						+ ((fileName.endsWith(File.separator))
							? ""
							: File.separator));
			}
		});
		adrZone.addKeyListener(adrZone.listenerFactory());
		adrZone.getEditor().addActionListener(adrZone.actionListenerFactory());
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
		//final JPopupMenu clickInFile = new JPopupMenu();
		clickInFile.add(new JMenuItem("Nouveau Fichier    Ctrl+T"));
		clickInFile.add(new JMenuItem("Nouveau Repertoire    Ctrl+R"));
		clickInFile.add(
			new JMenuItem("Nouvelle Fenetre d'exploration    Ctrl+E"));
		//final JPopupMenu clickOutFile = new JPopupMenu();
		clickOutFile.add(new JMenuItem(copyAction));
		clickOutFile.add(new JMenuItem(cutAction));
		clickOutFile.add(new JMenuItem(pasteAction));
		clickOutFile.add(new JMenuItem(dupAction));
		clickOutFile.add(new JMenuItem(moveAction));
		clickOutFile.add(new JMenuItem(selectAllAction));
		clickOutFile.add(new JMenuItem(renameAction));
		clickOutFile.add(new JMenuItem(deleteAction));
		clickOutFile.add(new JMenuItem(showPropertiesAction));
		/***********************************************/
		/* LISTERNER SUR L'ARBRE ET LA LISTE */
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = TREE_FOCUS;
				selectAllAction.setEnabled(false);
				pasteAction.setEnabled(true);
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				File file = null;
				if (path != null)
					file = (File) path.getLastPathComponent();
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 :
						if (file != null && e.getClickCount() == 1) {
							if (!tree.isExpanded(path))
								tree.expandPath(path);
							else if (
								!((File) path.getLastPathComponent()).equals(
									treeModel.getRoot()))
								tree.collapsePath(path);
							tree.setSelectionPath(path);
							tree.scrollPathToVisible(path);
							oldCurrentFolder = currentFolder;
							backFolderStack.push(oldCurrentFolder);
							back.setEnabled(true);
							next.setEnabled(false);

							if (ComparatorsManager.cmp.compare(oldCurrentFolder, file)
								!= 0) {
								String fileName = file.getAbsolutePath();
								adrZone.setText(
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator));
							}
						}
						break;
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = TABLE_FOCUS;
				selectAllAction.setEnabled(true);
				pasteAction.setEnabled(false);
				switch (e.getButton()) {
					case MouseEvent.BUTTON1 :
					Point p = new Point(e.getX(), e.getY());
					int row = table.rowAtPoint(p);
					int column = table.columnAtPoint(p);
					File file = (File)tableModel.getValueAt(row, column);
					if(file == null || file.equals(currentFolder)) break;
						info_panel.setAsGeneral( file, getSelectionItems().size());
						if (e.getClickCount() == 2) {
							oldCurrentFolder = currentFolder;
							backFolderStack.push(oldCurrentFolder);
							back.setEnabled(true);
							next.setEnabled(false);
							if (file.isDirectory()) {
								String fileName = file.getAbsolutePath();
								adrZone.setText(
									fileName
										+ ((fileName.endsWith(File.separator))
											? ""
											: File.separator));
							} else {
								new Launch().run(file);
							}
						}
						break;
				}
			}
		});
		tree.setForeground(getBgColor());
		tree.setBackground(getBgColor());
		mainFrame.show();
	}
}
