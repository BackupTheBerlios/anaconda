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
	public MyTabbedPane(Model model,FindModel findModel) {
	
		tabbedPane = new JTabbedPane();
		listModel = new ModelListAdapter(model);
		listFiles = new JList(listModel);
		/* Creation d'un onglet */
		Component panel1 = new JScrollPane(listFiles);/*new JList(listModel);*/
		tabbedPane.addTab("Fichiers", null, panel1, "Contenu du repertoire courant");
		// marque l'onglet d'indice 0 comme selectionné par defaut
		tabbedPane.setSelectedIndex(0); 
		/**/

		/* Creation d'un autre onglet */
		Component panel2 = new JScrollPane(new JList(findModel));
		tabbedPane.addTab("Rechercher", null, panel2, "Resultats de la recherche");
		/**/

		/* Encore un onglet */
		Component panel3 = new JList(listModel);
		tabbedPane.addTab("Corbeille", null, panel3, "Contient les elements supprimés");
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
	// creation Dynamique : permet de creer un onglet a la vollée
	// peut etre pa util ds Anaconda mais on sai jamais
	/*
	public  void createTab(JTabbedPane tabbedPane, String text, ImageIcon icon, Component panel, String toolTipText){
		panel = makeListPanel(toolTipText);
		if(panel == null) return;
		tabbedPane.addTab(text, icon, panel, toolTipText);
	}
	*/
}
