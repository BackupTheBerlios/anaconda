package fr.umlv.anaconda;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.io.File;


public class MyTabbedPane extends JPanel {
	private JTabbedPane tabbedPane;
    private JTable tableFiles; 
	private ModelTable tableModel; 
	
	public MyTabbedPane(ModelTable model,FindModel findModel,GarbageModel garbageModel) {
	
		tabbedPane = new JTabbedPane();
		tableModel = model;
		tableFiles = Main.table;
		tableFiles.setBackground(new Color(210,230,255));
		tableFiles.setSelectionBackground(new Color(180,200,245));
		/* Creation de l'onglet principal */
		Component panel1 = new JScrollPane(tableFiles);
		tabbedPane.addTab("Fichiers", null, panel1, "Contenu du repertoire courant");
		tabbedPane.setSelectedIndex(0); 
		
		/* Creation de l'onglet de recherche */
		JTable findTable = new JTable(findModel);
		TableRenderer findRenderer = new TableRenderer();
		findTable.setDefaultRenderer(File.class, findRenderer);
		Component panel2 = new JScrollPane(findTable);
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
	public JTable getTableFiles(){
		return tableFiles;
	}
	public ModelTable getListModel(){
		return 	tableModel;
	}

}
