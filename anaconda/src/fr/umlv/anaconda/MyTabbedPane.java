package fr.umlv.anaconda;
import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class MyTabbedPane extends JPanel {
	private JTabbedPane tabbedPane;
    private JList listFiles; 
	private ModelListAdapter listModel; 
	/**
	 * 
	 */
	public MyTabbedPane(Model model,FindModel findModel,GarbageModel garbageModel) {
	
		tabbedPane = new JTabbedPane();
		listModel = new ModelListAdapter(model);
		listFiles = new JList(listModel);
		listFiles.setBackground(new Color(210,230,255));
		listFiles.setSelectionBackground(new Color(180,200,245));
		/* Creation d'un onglet */
		Component panel1 = new JScrollPane(listFiles);/*new JList(listModel);*/
		tabbedPane.addTab("Fichiers", null, panel1, "Contenu du repertoire courant");
		// marque l'onglet d'indice 0 comme selectionn? par defaut
		tabbedPane.setSelectedIndex(0); 
		/**/

		/* Creation d'un autre onglet */
		JList findList = new JList(findModel);
		ListRenderer findRenderer = new ListRenderer();
		findList.setCellRenderer(findRenderer);
		Component panel2 = new JScrollPane(findList);
		tabbedPane.addTab("Rechercher", null, panel2, "Resultats de la recherche");
		/**/

		/* Encore un onglet */
		Component panel3 = new JScrollPane(new JList(garbageModel));
		tabbedPane.addTab("Corbeille", null, panel3, "Contient les elements supprim?s");
		/**/

		/* Ca extends JPanel, pratique */
		setLayout(new BorderLayout()); 
		add(tabbedPane, BorderLayout.CENTER);
	}
	/**
	 * UTILE
	 */
	// accesseur
	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}
	public JList getListFiles(){
		return listFiles;
	}
	public ModelListAdapter getListModel(){
		return 	listModel;
	}

	/**
	 * PEUT ETRE UTILE
	 */
	// creation Dynamique : permet de creer un onglet a la voll?e
	// peut etre pa util ds Anaconda mais on sai jamais
	/*
	public  void createTab(JTabbedPane tabbedPane, String text, ImageIcon icon, Component panel, String toolTipText){
		panel = makeListPanel(toolTipText);
		if(panel == null) return;
		tabbedPane.addTab(text, icon, panel, toolTipText);
	}
	*/
}
