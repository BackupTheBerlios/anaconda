package fr.umlv.anaconda;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.umlv.anaconda.appearance.Themes;

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
		tableFiles.setBackground(Themes.getBgColor());
		tableFiles.setSelectionBackground(new Color(180,200,245));
		/* Creation de l'onglet principal */
		JPanel panelTemp1 = new JPanel();
		panelTemp1.setLayout(new BorderLayout());
		panelTemp1.add(tableFiles, BorderLayout.CENTER);
		panelTemp1.setBackground(Themes.getBgColor());
		
		Component panel1 = new JScrollPane(panelTemp1);
		panel1.setBackground( Themes.getBgColor());
		tabbedPane.addTab("Fichiers", null, panel1, "Contenu du repertoire courant");
		tabbedPane.setSelectedIndex(0); 
		
		/* Creation de l'onglet de recherche */
		JTable findTable = new JTable(findModel);
		TableRenderer findRenderer = new TableRenderer();
		findTable.setDefaultRenderer(File.class, findRenderer);
		
		JPanel panelTemp2 = new JPanel();
		panelTemp2.setLayout(new BorderLayout());
		panelTemp2.add(findTable, BorderLayout.CENTER);
		panelTemp2.setBackground(Themes.getBgColor());
		
		Component panel2 = new JScrollPane(panelTemp2);
		panel2.setBackground( Themes.getBgColor());
		tabbedPane.addTab("Rechercher", null, panel2, "Resultats de la recherche");

		/* Creation de l'onglet corbeille */
		
		JPanel panelTemp3 = new JPanel();
		panelTemp3.setLayout(new BorderLayout());
		panelTemp3.add(new JList(garbageModel), BorderLayout.CENTER);
		panelTemp3.setBackground(Themes.getBgColor());
		
		Component panel3 = new JScrollPane(panelTemp3);
		panel3.setBackground( Themes.getBgColor());
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
		setBackground( Themes.getBgColor());
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
