package fr.umlv.anac;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 */
public class Main {
	public static void main(String[] args) throws Exception {
		JFrame mainFrame = new JFrame("Anaconda");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 600);
		File root = (File.separatorChar == '/')? new File(File.separator): new RootFile();
		//File root = new File("F:\\");
		final JTree tree = new JTree(new TreePanelModel(root));
		final ListPanelModel listModel = new ListPanelModel(root);
		final JList list = new JList(listModel);
		/***********************/
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		/***********************/
		/* CREATION DE L'ARBRE */
		TreeSelectionModel treeSelection = new DefaultTreeSelectionModel();
		treeSelection.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(treeSelection);
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree,
                                              value,
                                              selected,
                                              expanded,
                                              leaf,
                                              row,
                                              hasFocus);
                
				String name = ((TreePanelModel)value).getCurrentNode().getName();
				if(name.compareTo("") == 0)
					name = ((TreePanelModel)value).getCurrentNode().getAbsolutePath();
				((JLabel)c).setText(name);
				/************************/
				ImageIcon iconFocus = new ImageIcon("./iconFocus.gif");
                ImageIcon iconOpen = new ImageIcon("./iconOpen.gif");
                ImageIcon iconNode = new ImageIcon("./iconNode.gif");
				((JLabel)c).setIcon(iconNode);
				if(hasFocus) ((JLabel)c).setIcon(iconFocus);
				else if(expanded) ((JLabel)c).setIcon(iconOpen);
				/************************/
				return c;
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					TreePanelModel model = (TreePanelModel)tree.getLastSelectedPathComponent();
					File file = model.getCurrentNode();
					listModel.setCurrentFolder(file);
				}
			}
		});
		/* CREATION DE LA LISTE */
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
                                              Object value,
                                              int index,
                                              boolean isSelected,
                                              boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list,
                                              value,
                                              index,
                                              isSelected,
                                              cellHasFocus);
				String name = ((File)value).getName();
				File parent = listModel.getCurrentFolder().getParentFile();
				if(parent == null) parent = listModel.getCurrentFolder();
				if(parent.getName().compareTo(name) == 0) name = "..";
				else if(name.compareTo("") == 0) name = ((File)value).getAbsolutePath();
				((JLabel)c).setText(name);
				/************************/
				ImageIcon iconRep = new ImageIcon("./iconRep.gif");
				ImageIcon iconFather = new ImageIcon("./iconFather.gif");
				ImageIcon iconText = new ImageIcon("./iconText.gif");
				ImageIcon iconImg = new ImageIcon("./iconImg.gif");
				ImageIcon iconExe = new ImageIcon("./iconExe.gif");
				
				if(((File)value).isDirectory()) ((JLabel)c).setIcon(iconRep);
				else { 
					((JLabel)c).setIcon(iconText);
					if(name.endsWith(".jpg")) ((JLabel)c).setIcon(iconImg);
					if(name.endsWith(".exe")) ((JLabel)c).setIcon(iconExe);
				}
				if(name == "..") ((JLabel)c).setIcon(iconFather);
				/************************/
				return c;
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					File file = (File)list.getSelectedValue();
					if(file.isDirectory()) listModel.setCurrentFolder(file);
				}
			}
		});
		JScrollPane scrollTree = new JScrollPane(tree);
		JScrollPane scrollList = new JScrollPane(list);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, scrollList);
		splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(mainFrame.getWidth()/3);
        
        /**********************************/
        /* MENUBAR */
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Fichier");
        JMenu edit = new JMenu("Edition");
        JMenu disp = new JMenu("Affichage");
        JMenu help = new JMenu("?");
        
        JMenu subMenuNew = new JMenu("Nouveau...");
        subMenuNew.add("Fichier    Ctrl+T");
        subMenuNew.add("Repertoire    Ctrl+R");
        subMenuNew.add("Fenetre d'exploration    Ctrl+E"); 
        file.add(subMenuNew);
        file.add(new JMenuItem("Rechercher    Ctrl+F"));
        file.add(new JMenuItem("Proprietes    Ctrl+P"));
     
        edit.add(new JMenuItem("Couper   Ctrl+X"));
        edit.add(new JMenuItem("Copier    Ctrl+C"));
        edit.add(new JMenuItem("Coller    Ctrl+V"));
     	edit.add(new JMenuItem("Dupliquer    Ctrl+Alt+C"));
     	edit.add(new JMenuItem("Deplacer    Ctrl+Alt+X"));
     	edit.add(new JMenuItem("Selectionner tout"));
     	edit.add(new JMenuItem("Renommer"));
     	edit.add(new JMenuItem("Supprimer"));
     	
     	disp.add(new JMenuItem("Actualiser"));
     	JMenu subMenuTri = new JMenu("Organiser par...");
     	subMenuTri.add("Nom");
     	subMenuTri.add("Type");
     	subMenuTri.add("Taille");
     	subMenuTri.add("Date");
     	JMenu subMenuType = new JMenu("Type d'affichage...");
     	subMenuType.add("Grandes icones");
     	subMenuType.add("Petites icones");
     	subMenuType.add("Liste");
     	subMenuType.add("Detail");
     	JMenu subMenuBar = new JMenu("Barres...");
     	subMenuBar.add("Barre d'outils");
     	subMenuBar.add("Barre d'adresse");
     	JMenu subMenuLangue = new JMenu("Langue...");
     	subMenuLangue.add("Francais");
     	subMenuLangue.add("Anglais");
     	subMenuLangue.add("Espagnol");
     	disp.add(subMenuTri);
        disp.add(subMenuType);
     	disp.add(subMenuBar);
     	disp.add(subMenuLangue);
     	
     	help.add(new JMenuItem("Aide"));
        help.add(new JMenuItem("A propos"));
     	
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(disp);	
        menuBar.add(help);
        
        /* TOOLBAR */
		JToolBar toolBar = new JToolBar();
        JButton back = new JButton(new ImageIcon("./back.gif"));
        JButton next = new JButton(new ImageIcon("./next.gif"));
        JButton cut = new JButton(new ImageIcon("./cut.gif"));
        JButton copy = new JButton(new ImageIcon("./copy.gif"));
        JButton paste = new JButton(new ImageIcon("./paste.gif"));
        JButton find = new JButton(new ImageIcon("./find.gif"));
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
        JButton del = new JButton("effacer");
        JLabel adr = new JLabel("adresse");
        JButton open = new JButton("ouvrir");
        JTextField adrZone = new JTextField(20);
        adressBar.add(del);
        adressBar.add(adr);
        adressBar.add(adrZone);
        adressBar.add(open);
        
        /* PANELBAR */
        JPanel panelBar = new JPanel();
        panelBar.setLayout(new BorderLayout());
        panelBar.add(toolBar, BorderLayout.NORTH);
        panelBar.add(adressBar, BorderLayout.CENTER);
        
        mainFrame.setJMenuBar(menuBar);
        //mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
        mainFrame.getContentPane().add(panelBar, BorderLayout.NORTH);
        mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
        /***********************************/
		//mainFrame.setContentPane(splitPane);
		mainFrame.show();
	}
}
