package fr.umlv.anaconda;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;


public class MyTabbedPane extends JPanel {
	private JTabbedPane tabbedPane;
    private JList listFiles; 
	private ModelListAdapter listModel; 
	
	public MyTabbedPane(Model model,FindModel findModel,GarbageModel garbageModel) {
	
		tabbedPane = new JTabbedPane();
		listModel = new ModelListAdapter(model);
		listFiles = new JList(listModel);
		listFiles.setBackground(new Color(210,230,255));
		listFiles.setSelectionBackground(new Color(180,200,245));
		/* Creation de l'onglet principal */
		Component panel1 = new JScrollPane(listFiles);
		tabbedPane.addTab("Fichiers", null, panel1, "Contenu du repertoire courant");
		tabbedPane.setSelectedIndex(0); 
		
		/* Creation de l'onglet de recherche */
		JList findList = new JList(findModel);
		ListRenderer findRenderer = new ListRenderer();
		findList.setCellRenderer(findRenderer);
		Component panel2 = new JScrollPane(findList);
		tabbedPane.addTab("Rechercher", null, panel2, "Resultats de la recherche");

		/* Creation de l'onglet corbeille */
		Component panel3 = new JScrollPane(new JList(garbageModel));
		tabbedPane.addTab("Corbeille", null, panel3, "Contient les elements supprimes");

		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int index = tabbedPane.getSelectedIndex();
				switch(index){
					case 0: Main.info_panel.setAsDefault();break;
					case 1: Main.info_panel.setAsFind(Main.find_model);break;
				}
				
			}
		});
		
		setLayout(new BorderLayout()); 
		add(tabbedPane, BorderLayout.CENTER);
	}

	public JTabbedPane getTabbedPane(){
		return tabbedPane;
	}
	public JList getListFiles(){
		return listFiles;
	}
	public ModelListAdapter getListModel(){
		return 	listModel;
	}

}
