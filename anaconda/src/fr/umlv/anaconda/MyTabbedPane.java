package fr.umlv.anaconda;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;



import fr.umlv.anaconda.appearance.Themes;
import fr.umlv.anaconda.command.AllCommand;


public class MyTabbedPane extends JPanel {
	private JTabbedPane tabbedPane;
	private JTable tableFiles; 
	private ModelTable tableModel; 
	private JList garbage_list;
	
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
		final JList findList = new JList(findModel);
		findList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				Object[] selected_files = findList.getSelectedValues();	
				if(selected_files.length > 0){
					Main.info_panel.setAsGeneral((File)selected_files[0],selected_files.length);
				}
			}
		});
		TableRenderer findRenderer = new TableRenderer();
		findList.setCellRenderer(new ListRenderer("find"));
		
		
		JPanel panelTemp2 = new JPanel();
		panelTemp2.setLayout(new BorderLayout());
		panelTemp2.add(findList, BorderLayout.CENTER);
		panelTemp2.setBackground(Themes.getBgColor());
		
		Component panel2 = new JScrollPane(panelTemp2);
		panel2.setBackground( Themes.getBgColor());
		tabbedPane.addTab("Rechercher", null, panel2, "Resultats de la recherche");

		/* Creation de l'onglet corbeille */
		
		JPanel panelTemp3 = new JPanel();
		panelTemp3.setLayout(new BorderLayout());
		garbage_list = new JList(garbageModel);
		garbage_list.setCellRenderer(new ListRenderer("garbage"));
		final JPopupMenu clickInGarbage = new JPopupMenu();
		final Action restoreAction = AllCommand.getAction("restore");
		clickInGarbage.add(new JMenuItem(restoreAction));
		garbage_list.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {

			switch (e.getButton()) {

				case MouseEvent.BUTTON3 :
					int index = garbage_list.locationToIndex(new Point(e.getX(), e.getY()));
					File file = (File) Main.garbage_model.getElementAt(index);
					clickInGarbage.show(
					e.getComponent(),
					e.getX(),
					e.getY());

					break;
			}
		}
	});

		panelTemp3.add(garbage_list, BorderLayout.CENTER);
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
	
	public ArrayList getGarbageSelectedFiles(){
		Object[] o  = garbage_list.getSelectedValues();
		ArrayList file_list = new ArrayList();
		for(int i = 0;i<o.length;i++){
			file_list.add((File)o[i]);
		}
		return file_list;
	}

}
