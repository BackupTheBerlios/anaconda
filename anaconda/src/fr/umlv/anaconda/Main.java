package fr.umlv.anaconda;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

import fr.umlv.anaconda.appearance.Themes;
import fr.umlv.anaconda.command.AllCommand;
import fr.umlv.anaconda.command.Launch;
import fr.umlv.anaconda.command.Trash;
//import fr.umlv.anaconda.exception.DoNotExistFileException;
//import fr.umlv.anaconda.exception.ErrorIOFileException;

public class Main {
	public static boolean readyToPaste = false;
	/* VARIABLE DE NAVIGATION */
	final public static File root = new File(System.getProperty("user.home"));
	protected static File currentFolder = root;
	protected static File lastFolder = null;
	/* VARIABLE D'APPLICATION */
	final public static ModelTree treeModel = new ModelTree(root);
	final public static JTree tree = new JTree(treeModel);
	final public static ModelTable tableModel = new ModelTable(currentFolder);
	final public static JTable table = new JTable(tableModel);
	final public static GarbageModel garbage_model = new GarbageModel();
	final public static FindModel find_model = new FindModel();
	final public static MyTabbedPane tabb =
		new MyTabbedPane(tableModel, find_model, garbage_model);
	final public static InfoPanel info_panel = new InfoPanel();
	final public static int TABLE_FOCUS = 0;
	final public static int TREE_FOCUS = 1;
	final public static int NONE_FOCUS = 2;
	protected static int lastFocused = NONE_FOCUS;
	private static ArrayList selection_items = new ArrayList();
	protected static AddressBar adrZone=null;
	/* PILE DE PRECEDENT SUIVANT */
	final public static Stack backFolderStack = new Stack();
	final public static Stack nextFolderStack = new Stack();
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
	
	public static void refresh() {
		TreePath currentPath = tree.getSelectionPath();
		treeModel.setFolder(currentFolder);
		tree.setSelectionPath(currentPath);
		tree.expandPath(currentPath);
		tree.scrollPathToVisible(currentPath);
		tableModel.setFolder(currentFolder);
	}
	public static void setFolder(File newFolder) {
		lastFolder = currentFolder;
		currentFolder = newFolder;
		adrZone.setText(newFolder);

		refresh();
	}
	
	public static File getFolder() {
		return currentFolder;
	}
	
	public static TreePath getTreeSelectionPath() {
		return tree.getSelectionPath();
	} 
	
	static void goOut() {
		
		if (JOptionPane
				.showConfirmDialog(
						null,
						"Voulez vous vraiment quitter ?",
						" Quitter ",
						JOptionPane.OK_CANCEL_OPTION)
				== JOptionPane.OK_OPTION){
			((Trash)AllCommand.get("trash")).stop();
			System.exit(1);
				}
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
		tree.setCellRenderer(new TreeRenderer());
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
					if(file == null) {
						lastFolder = currentFolder;
					}
					else if(e.getClickCount() == 1) {
						backFolderStack.push(currentFolder);
						if(!nextFolderStack.isEmpty()) nextFolderStack.clear();
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
					if(e.getClickCount() == 1) {
						info_panel.setAsGeneral(file, getSelectionItems().size());
					}
					else if(e.getClickCount() == 2) {
						if(file == null || file.equals(currentFolder)) {
							lastFolder = currentFolder;
							break;
						} 
						if(file.isDirectory()) {
							backFolderStack.push(currentFolder);
							if(!nextFolderStack.isEmpty()) nextFolderStack.clear();
							boolean down = currentFolder.equals(file.getParentFile());
							TreePath currentPath = tree.getSelectionPath();
							setFolder(file);
							if(currentPath != null) {
								if(down) currentPath = currentPath.pathByAddingChild(currentFolder);
								else currentPath = currentPath.getParentPath();
								tree.setSelectionPath(currentPath);
								tree.expandPath(currentPath);
								tree.scrollPathToVisible(currentPath);
							}
						}
						else {
							new Launch().run(file);
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
		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mainFrame.setSize(800, 600);
		mainFrame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent evt) {
							goOut();
						}
				});

		/* INITIALISATION DE L'ARBRE ET DE LA TABLE */
		initTree();
		initTable();
		tableModel.setDimmension(-1, -1);
		
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
		JSplitPane splitPane =
			new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
				splitTreeInfo, tabb);
		splitPane.setBackground(Themes.getBgColor());
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(mainFrame.getWidth() / 3);
		splitPane.setDividerSize(2);
		/**********************************/
		/* LES ACTIONS */
		final Action refreshAction = AllCommand.getAction("refresh");
		final Action createFile = AllCommand.getAction("createfile");
		final Action createFolder = AllCommand.getAction("createfolder");
		final Action copyAction = AllCommand.getAction("copy");
		final Action cutAction = AllCommand.getAction("cut");
		final Action pasteAction = AllCommand.getAction("paste");
		final Action dupAction = AllCommand.getAction("clone");
		final Action moveAction = AllCommand.getAction("move");
		final Action selectAllAction =
			new AbstractAction("Selectionner tout") {
			public void actionPerformed(ActionEvent e) {
				table.selectAll();
				info_panel.setAsGeneral(null, tableModel.getSize() - 1);
			}
		};
		final Action renameAction = AllCommand.getAction("rename");
		final Action trashAction = AllCommand.getAction("trash");
		final Action findAction = new AbstractAction("Rechercher") {
			public void actionPerformed(ActionEvent e) {
				info_panel.setAsFind(find_model);
			}
			
		};
		final Action bigIconsAction = new AbstractAction("Grandes Icones") {
			public void actionPerformed(ActionEvent e) {
				TableRenderer.setIconsSize(IconsManager.BIG_ICONS);
				tableModel.setDimmension(tableModel.getRowCount(), tableModel.getColumnCount());
				refresh();
			}
		};
		final Action smallIconsAction = new AbstractAction("Petites Icones") {
			public void actionPerformed(ActionEvent e) {
				TableRenderer.setIconsSize(IconsManager.SMALL_ICONS);
				tableModel.setDimmension(tableModel.getRowCount(), tableModel.getColumnCount());
				refresh();
			}
		};
		final Action showPropertiesAction = AllCommand.getAction("showproperties");
		final Action aboutAction = AllCommand.getAction("about");
		final Action helpAction = AllCommand.getAction("help");
		final Action showByNameAction = new AbstractAction("Nom") {
					public void actionPerformed(ActionEvent e) {
						ComparatorsManager.addCmp("by_name");
						setFolder(currentFolder);
					}
				};
		final Action showBySizeAction = new AbstractAction("Taille") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_size");
				setFolder(currentFolder);
			}
		};
		final Action showByTypeAction = new AbstractAction("Type") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_type");
				setFolder(currentFolder);
			}
		};
		final Action showByDateAction = new AbstractAction("Date") {
			public void actionPerformed(ActionEvent e) {
				ComparatorsManager.addCmp("by_date");
				setFolder(currentFolder);
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
		JMenuItem newFileItem = new JMenuItem(createFile);
		JMenuItem newFolderItem = new JMenuItem(createFolder);
		subMenuNew.add(newFileItem);
		subMenuNew.add(newFolderItem);
		file.add(subMenuNew);
		file.add(new JSeparator());
		JMenuItem findItem = new JMenuItem(findAction);
		JMenuItem propertiesItem = new JMenuItem(showPropertiesAction);
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goOut();
			}
		});

		file.add(findItem);
		file.add(propertiesItem);
		file.add(new JSeparator());
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
		JMenuItem trashItem = new JMenuItem(trashAction);
		edit.add(undoItem);
		edit.add(redoItem);
		edit.add(new JSeparator());
		edit.add(copyItem);
		edit.add(cutItem);
		edit.add(pasteItem);
		edit.add(new JSeparator());
		edit.add(dupItem);
		edit.add(moveItem);
		edit.add(new JSeparator());
		edit.add(selectAllItem);
		edit.add(renameItem);
		edit.add(trashItem);
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

		disp.add(reloadItem);
		disp.add(subMenuTri);
		disp.add(subMenuType);
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
		menuBar.add(Box.createGlue());
		menuBar.add(help);

		/* TOOLBAR */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorderPainted(false);
		final JButton back = new JButton(IconsManager.BACK);
		back.setRolloverIcon(IconsManager.ONFOCUSBACK);
		back.setToolTipText("Precedent");
		final JButton refresh = new JButton(IconsManager.REFRESH);
		refresh.setRolloverIcon(IconsManager.ONFOCUSREFRESH);
		refresh.addActionListener(refreshAction);
		refresh.setToolTipText("Actualiser");
		final JButton next = new JButton(IconsManager.NEXT);
		next.setRolloverIcon(IconsManager.ONFOCUSNEXT);
		next.setToolTipText("Suivant");
		final JButton cut = new JButton(IconsManager.CUT);
		cut.setRolloverIcon(IconsManager.ONFOCUSCUT);
		cut.addActionListener(cutAction);
		cut.setToolTipText("Couper");
		final JButton copy = new JButton(IconsManager.COPY);
		copy.setRolloverIcon(IconsManager.ONFOCUSCOPY);
		copy.addActionListener(copyAction);
		copy.setToolTipText("Copier");
		final JButton paste = new JButton(IconsManager.PASTE);
		paste.setRolloverIcon(IconsManager.ONFOCUSPASTE);
		paste.setAction(pasteAction);
		paste.setIcon(IconsManager.PASTE);
		paste.setText("");
		paste.setToolTipText("Coller");
		final JButton find = new JButton(IconsManager.FIND);
		find.setRolloverIcon(IconsManager.ONFOCUSFIND);
		find.addActionListener(findAction);
		find.setToolTipText("Rechercher");
		final JButton home = new JButton(IconsManager.HOME);
		home.setRolloverIcon(IconsManager.ONFOCUSHOME);
		home.setToolTipText("Maison");
		final JButton garbage = new JButton(IconsManager.GARBAGE);
		garbage.setRolloverIcon(IconsManager.ONFOCUSGARBAGE);
		garbage.addActionListener(trashAction);
		garbage.setToolTipText("Supprimer");
		

		toolBar.add(home);
		toolBar.add(back);
		toolBar.add(refresh);
		toolBar.add(next);
		toolBar.add(cut);
		toolBar.add(copy);
		toolBar.add(paste);
		toolBar.add(garbage);
		toolBar.add(find);
		toolBar.setRollover(true);
		toolBar.setFloatable(true);

		/* ADRESSBAR */
		JToolBar adressBar = new JToolBar();
		adressBar.setFloatable(false);
		JButton delAdr = new JButton(" X ");
		JLabel adr = new JLabel(" Adresse : ");
		JButton openAdr = new JButton(" GO ");
		adrZone = new AddressBar(currentFolder);
		adressBar.add(adr);
		adressBar.add(adrZone);
		adressBar.add(openAdr);
		adressBar.add(delAdr);

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
				nextFolderStack.push(currentFolder);
				setFolder((File) backFolderStack.pop());
				next.setEnabled(true);
				if (backFolderStack.empty())
					back.setEnabled(false);
			}
		});
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backFolderStack.push(currentFolder);
				setFolder((File) nextFolderStack.pop()); 
				back.setEnabled(true);
				if (nextFolderStack.empty())
					next.setEnabled(false);
			}
		});
		adrZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(adrZone.getText());
				if (file.exists()) {
					setFolder(file);
					back.setEnabled(true);
					next.setEnabled(false);
					adrZone.addItem(file.getAbsolutePath());
				} 
				else{
					adrZone.Completion();
				}
				adrZone.setText(currentFolder);	
			}
		});
		openAdr.addActionListener((adrZone.getActionListeners())[0]);
		delAdr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adrZone.getEditor().setItem("");
				adrZone.getEditor().getEditorComponent().requestFocus();
				((AddressBarComboBoxModel)adrZone.getModel()).switch2hist();
			}
		});
		mainFrame.setJMenuBar(menuBar);
		mainFrame.getContentPane().add(panelBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
		/***********************************************/
		/* MENU DEROULANT */
		clickInFile.add(refreshAction);
		clickInFile.add(new JSeparator());
		clickInFile.add(pasteAction);
		clickInFile.add(new JSeparator());
		clickInFile.add(createFile);
		clickInFile.add(createFolder);
		clickOutFile.add(new JMenuItem(selectAllAction));
		clickOutFile.add(new JSeparator());
		clickOutFile.add(new JMenuItem(copyAction));
		clickOutFile.add(new JMenuItem(cutAction));
		clickOutFile.add(new JMenuItem(pasteAction));
		clickOutFile.add(new JSeparator());
		clickOutFile.add(new JMenuItem(dupAction));
		clickOutFile.add(new JMenuItem(moveAction));
		clickOutFile.add(new JSeparator());
		clickOutFile.add(new JMenuItem(trashAction));
		clickOutFile.add(new JMenuItem(renameAction));
		clickOutFile.add(new JMenuItem(showPropertiesAction));
		
		
		/***********************************************/
		/* LISTERNER SUR L'ARBRE ET LA LISTE */
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = TREE_FOCUS;
				selectAllAction.setEnabled(false);
				pasteAction.setEnabled(readyToPaste);
				if (e.getButton() == MouseEvent.BUTTON1 &&
					e.getClickCount() == 1 && lastFolder != null &&
					!currentFolder.equals(lastFolder)) {
					back.setEnabled(true);
					next.setEnabled(false);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lastFocused = TABLE_FOCUS;
				selectAllAction.setEnabled(true);
				if (e.getButton() == MouseEvent.BUTTON1 &&
					e.getClickCount() == 2 && lastFolder != null &&
					!currentFolder.equals(lastFolder)) {
					back.setEnabled(true);
					next.setEnabled(false);
					pasteAction.setEnabled(readyToPaste);
				}
				else if(e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					int column = table.columnAtPoint(p);
					File file = (File)tableModel.getValueAt(row, column);
					if(file == null) file = currentFolder; 
					pasteAction.setEnabled(readyToPaste && file.isDirectory());
				}
				else {
					pasteAction.setEnabled(false);
				}
			}
		});
		tree.setForeground(Themes.getBgColor());
		tree.setBackground(Themes.getBgColor());
		AnacondaDrag dragTable = new AnacondaDrag(table);
		AnacondaDrop dropTable = new AnacondaDrop(table);
		AnacondaDrag dragTree = new AnacondaDrag(tree);
		AnacondaDrop dropTree = new AnacondaDrop(tree);
		mainFrame.show();
	}
}
