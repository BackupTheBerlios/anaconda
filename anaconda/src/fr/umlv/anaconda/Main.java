package fr.umlv.anaconda;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 */
public class Main {
    /**/
    public static File oldCurrentFolder;
    public static File newCurrentFolder;
	final public static Model model = new Model();
	final public static ModelTreeAdapter treeModel = new ModelTreeAdapter(model);
	final public static JTree tree = new JTree(treeModel);
	final public static ModelListAdapter listModel = new ModelListAdapter(model);
	final public static JList list = new JList(listModel);
	public static Object[] getSelectionItems() {
		Object[] o = list.getSelectedValues();
		if(o.length == 1) {
			File file = (File)o[0];
			if(Model.cmp.compare(file, model.getFolderParent()) == 0)
				o[0] = ((Model)tree.getLastSelectedPathComponent()).getFolder();
		}
		else {
			// virer le rep .. du tableau
		}
		return o;
	}
    /**/ 
    public static void main(String[] args) throws Exception {
        final JFrame mainFrame = new JFrame("Anaconda");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        oldCurrentFolder = newCurrentFolder  = model.getFolder();

        /* CREATION DE L'ARBRE */
        TreeSelectionModel treeSelection = new DefaultTreeSelectionModel();
        treeSelection.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setSelectionModel(treeSelection);
        tree.setSelectionRow(0);
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
                    String name = ((Model)value).getFolder().getName();
                    if(name.compareTo("") == 0)
                        name = ((Model)value).getFolder().getAbsolutePath();
                    ((JLabel)c).setText(name);
                    if(selected) ((JLabel)c).setIcon(new ImageIcon(Main.class.getResource("/images/iconFocus.gif")));
                    else if(expanded) ((JLabel)c).setIcon(new ImageIcon(Main.class.getResource("/images/iconOpen.gif")));
                    else ((JLabel)c).setIcon(new ImageIcon(Main.class.getResource("/images/iconNode.gif")));
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
                    File parent = listModel.getModel().getFolder().getParentFile();
                    if(parent == null) parent = listModel.getModel().getFolder();
                    if(parent.getName().compareTo(name) == 0) name = "..";
                    else if(name.compareTo("") == 0) name = ((File)value).getAbsolutePath();
                    ((JLabel)c).setText(name);
				/************************/
                    ImageIcon iconRep = new ImageIcon(Main.class.getResource("/images/iconRep.gif"));
                    ImageIcon iconFather = new ImageIcon(Main.class.getResource("/images/iconFather.gif"));
                    ImageIcon iconText = new ImageIcon(Main.class.getResource("/images/iconText.gif"));
                    ImageIcon iconImg = new ImageIcon(Main.class.getResource("/images/iconImg.gif"));
                    ImageIcon iconExe = new ImageIcon(Main.class.getResource("/images/iconExe.gif"));
				
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
        JScrollPane scrollTree = new JScrollPane(tree);
        JScrollPane scrollList = new JScrollPane(list);
        /******************************/
        JPanel infoPanel = new JPanel();
        ImageIcon anacondaLogo = new ImageIcon(Main.class.getResource("/images/anaconda_logo.gif"));
        infoPanel.add(new JLabel(anacondaLogo));
        infoPanel.setBackground(Color.WHITE);
		
        JSplitPane splitTreeInfo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree, infoPanel);
        splitTreeInfo.setOneTouchExpandable(true);
        splitTreeInfo.setDividerLocation(260);
        splitTreeInfo.setResizeWeight(1);
		
        Dimension size = new Dimension(200, 200);
        infoPanel.setMinimumSize(size);
        infoPanel.setMaximumSize(size);
        
        /******************************/
		
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitTreeInfo/*scrollTree*/, scrollList);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(mainFrame.getWidth()/3);
        
        /**********************************/
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
        JMenuItem newFrameItem = new JMenuItem("Fenetre d'exploration    Ctrl+E");
        subMenuNew.add(newFileItem);
        subMenuNew.add(newFolderItem);
        subMenuNew.add(newFrameItem);
        file.add(subMenuNew);
        JMenuItem findItem = new JMenuItem("Rechercher    Ctrl+F");
        JMenuItem propertiesItem = new JMenuItem("Proprietes    Ctrl+P");
        file.add(findItem);
        file.add(propertiesItem);
        /* Edition */
        JMenuItem cutItem = new JMenuItem("Couper   Ctrl+X");
        JMenuItem copyItem = new JMenuItem("Copier    Ctrl+C");
        JMenuItem pasteItem = new JMenuItem("Coller    Ctrl+V");
        JMenuItem dupItem = new JMenuItem("Dupliquer    Ctrl+Alt+C");
        JMenuItem moveItem = new JMenuItem("Deplacer    Ctrl+Alt+X");
        JMenuItem selectAllItem = new JMenuItem("Selectionner tout");
        JMenuItem renameItem = new JMenuItem("Renommer");
        JMenuItem deleteItem = new JMenuItem("Supprimer");
        edit.add(cutItem);
        edit.add(copyItem);
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
        final JButton back = new JButton(new ImageIcon(Main.class.getResource("/images/back.gif")));
        final JButton next = new JButton(new ImageIcon(Main.class.getResource("/images/next.gif")));
        JButton cut = new JButton(new ImageIcon(Main.class.getResource("/images/cut.gif")));
        JButton copy = new JButton(new ImageIcon(Main.class.getResource("/images/copy.gif")));
        JButton paste = new JButton(new ImageIcon(Main.class.getResource("/images/paste.gif")));
        JButton find = new JButton(new ImageIcon(Main.class.getResource("/images/find.gif")));
        
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
        JButton delAdr = new JButton("effacer");
        JLabel adr = new JLabel("adresse");
        JButton openAdr = new JButton("ouvrir");
        final JTextField adrZone = new JTextField(20);
        String fileName = model.getFolder().getAbsolutePath();
        adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
       
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
        back.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                  model.setFolder(oldCurrentFolder);
                  String fileName = oldCurrentFolder.getAbsolutePath(); 
				  adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
                  back.setEnabled(false);
                  next.setEnabled(true);
                }
            });
        next.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    model.setFolder(newCurrentFolder);
					String fileName = newCurrentFolder.getAbsolutePath(); 
					adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
                    back.setEnabled(true);
                    next.setEnabled(false);
                }
            });
        adrZone.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    //System.out.println(adrZone.getText());
                    File file = new File(adrZone.getText());
                    if(file.exists()){
                    	oldCurrentFolder = model.getFolder();
                        newCurrentFolder = file;
                        model.setFolder(file);
                        back.setEnabled(true);
                        next.setEnabled(false);
                    }
                    else
                    	JOptionPane.showMessageDialog(null,
								"Le fichier/repertoire <" + file.getAbsolutePath() + "> n'a pas ete trouve.",
								"Fichier/repertoire non trouve",
								JOptionPane.ERROR_MESSAGE);
					String fileName = model.getFolder().getAbsolutePath(); 
					adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
                }
            });
        openAdr.addActionListener((adrZone.getActionListeners())[0]);
        delAdr.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    adrZone.setText("");
                }
            });
        mainFrame.setJMenuBar(menuBar);
        //mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
        mainFrame.getContentPane().add(panelBar, BorderLayout.NORTH);
        mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
        /***********************************/
        /* LISTERNER SUR L'ARBRE ET LA LISTE */
        tree.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if(e.getClickCount() == 1) {
                            File file = ((Model)tree.getLastSelectedPathComponent()).getFolder();
                            oldCurrentFolder = model.getFolder();
                            back.setEnabled(true);
                            next.setEnabled(false);
                            newCurrentFolder = file;
                            if(Model.cmp.compare(oldCurrentFolder, file) != 0) {
                                model.setFolder(file);
								String fileName = file.getAbsolutePath(); 
								adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
                                list.setSelectedIndex(0);
                            }
                        }
                        break;
                    case MouseEvent.BUTTON2:
                        break;
                    case MouseEvent.BUTTON3:
                        break;
                    }
                }
            });
        list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if(e.getClickCount() == 2) {
                            File file = (File)list.getSelectedValue();
                            oldCurrentFolder = model.getFolder();
                            back.setEnabled(true);
                            next.setEnabled(false);
                            if(file.isDirectory()) {
                                newCurrentFolder = file;
                                model.setFolder(file);
                                TreePath path = new TreePath(model);
                                tree.expandPath(path);
                                tree.setSelectionPath(path);
								tree.validate();
                                tree.repaint();
								tree.repaint(0, 0, 0, tree.getWidth(), tree.getHeight());
								String fileName = file.getAbsolutePath();
								adrZone.setText(fileName+((fileName.endsWith(File.separator))? "": File.separator));
                                list.setSelectedIndex(0);
                            }
                        }
                        break;
                    case MouseEvent.BUTTON2:
                        break;
                    case MouseEvent.BUTTON3:
                        JPopupMenu popup = new JPopupMenu();
                        int index = list.locationToIndex(new Point(e.getX(), e.getY()));
                        File file = (File)listModel.getElementAt(index);
                        if(file == null) {
                            JMenuItem newFileItemPop = new JMenuItem("Nouveau Fichier    Ctrl+T");
                            JMenuItem newFolderItemPop = new JMenuItem("Nouveau Repertoire    Ctrl+R");
                            JMenuItem newFrameItemPop = new JMenuItem("Nouvelle Fenetre d'exploration    Ctrl+E");
                            popup.add(newFileItemPop);
                            popup.add(newFolderItemPop);
                            popup.add(newFrameItemPop);

                        }
                        else {
                            list.setSelectedIndex(index);
                            JMenuItem cutItemPop = new JMenuItem("Couper   Ctrl+X");
                            JMenuItem copyItemPop = new JMenuItem("Copier    Ctrl+C");
                            JMenuItem pasteItemPop = new JMenuItem("Coller    Ctrl+V");
                            JMenuItem dupItemPop = new JMenuItem("Dupliquer    Ctrl+Alt+C");
                            JMenuItem moveItemPop = new JMenuItem("Deplacer    Ctrl+Alt+X");
                            JMenuItem selectAllItemPop = new JMenuItem("Selectionner tout");
                            JMenuItem renameItemPop = new JMenuItem("Renommer");
                            JMenuItem deleteItemPop = new JMenuItem("Supprimer");
                            popup.add(cutItemPop);
                            popup.add(copyItemPop);
                            popup.add(pasteItemPop);
                            pasteItemPop.setEnabled(file.isDirectory()); 
                            popup.add(dupItemPop);
                            popup.add(moveItemPop);
                            popup.add(renameItemPop);
                            popup.add(deleteItemPop);
                        }
                        popup.show(e.getComponent(), e.getX(), e.getY());
                        break;
                    }
                }
            });
        mainFrame.show();
    }
}
