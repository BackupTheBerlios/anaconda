/*
 * Créé le 3 mars 2004
 */
package fr.umlv.anaconda;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 * @author ntesevic
 * @author Anac team
 */
public class AnacondaDrop {
 
    
  
	private DropTarget dropTarget;
	private DropTargetListener dtListener;

	/**
	 * the actions supported by this drop target
	 */
  
	private int acceptableActions;  
	//  private int acceptableActions = DnDConstants.ACTION_MOVE;
	//  private int acceptableActions = DnDConstants.ACTION_COPY_OR_MOVE;
	Component comp;
  
     
	/** Creates a new instance of AnacondaDrop */
	public AnacondaDrop(Component comp) {
		this.comp=comp;
   
		acceptableActions =DnDConstants.ACTION_COPY;
    
    
		dtListener = new DTListener();
    
		// component, ops, listener, accepting
		dropTarget = new DropTarget(comp,acceptableActions,dtListener,
									true);
    
    
    
    
	}
    
	/**
	 * DTListener
	 * a listener that tracks the state of the operation
	 * @see java.awt.dnd.DropTargetListener
	 * @see java.awt.dnd.DropTarget
	 */
	class DTListener implements DropTargetListener {
    
		/**
		 * Called by isDragOk
		 * Checks to see if the flavor drag flavor is acceptable
		 * @param e the DropTargetDragEvent object
		 * @return whether the flavor is acceptable
		 */
		private boolean isDragFlavorSupported(DropTargetDragEvent e) {
			boolean ok=false;
  
			/* 
			   if (e.isDataFlavorSupported(StringTransferable.plainTextFlavor)) {
			   ok=true;
			   } else if (e.isDataFlavorSupported(
			   StringTransferable.localStringFlavor)) {
			   ok=true;
			   } else
			*/
      /*
			if (e.isDataFlavorSupported(DataFlavor.stringFlavor)) {	  
				ok=true;
			} else if (e.isDataFlavorSupported(DataFlavor.plainTextFlavor)) {
				ok=true;
			}
     */
			return ok;
		}
		/**
		 * Called by drop
		 * Checks the flavors and operations
		 * @param e the DropTargetDropEvent object
		 * @return the chosen DataFlavor or null if none match
		 */
		private DataFlavor chooseDropFlavor(DropTargetDropEvent e) {
			/* if (e.isLocalTransfer() == true &&
			   e.isDataFlavorSupported(StringTransferable.localStringFlavor)) {
			   return StringTransferable.localStringFlavor;
			   }*/
			DataFlavor chosen = null;      
			/*if (e.isDataFlavorSupported(StringTransferable.plainTextFlavor)) {
			  chosen = StringTransferable.plainTextFlavor;
			  } else if (e.isDataFlavorSupported(
			  StringTransferable.localStringFlavor)) {
			  chosen = StringTransferable.localStringFlavor;	
			  } else
			*/
			/*
			if (e.isDataFlavorSupported(DataFlavor.stringFlavor)) {	  
				chosen = DataFlavor.stringFlavor;
			} else if (e.isDataFlavorSupported(DataFlavor.plainTextFlavor)) {
				chosen = DataFlavor.plainTextFlavor;	
			}
			*/
			return chosen;
		}

		/**
		 * Called by dragEnter and dragOver
		 * Checks the flavors and operations
		 * @param e the event object
		 * @return whether the flavor and operation is ok
		 */
		private boolean isDragOk(DropTargetDragEvent e) {
			if(isDragFlavorSupported(e) == false) {
				//System.out.println( "isDragOk:no flavors chosen" );
				return false;
			}
      
			// the actions specified when the source
			// created the DragGestureRecognizer
			//      int sa = e.getSourceActions();
      
			// the docs on DropTargetDragEvent rejectDrag says that
			// the dropAction should be examined
			int da = e.getDropAction();      
			//System.out.print("dt drop action " + da);
			//System.out.println(" my acceptable actions " + acceptableActions);
      
			// we're saying that these actions are necessary      
			if ((da & acceptableActions) == 0)
				return false;
			return true;
		}

		/**
		 * start "drag under" feedback on component
		 * invoke acceptDrag or rejectDrag based on isDragOk
		 */
		public void dragEnter(DropTargetDragEvent e) {
			//System.out.println( "dtlistener dragEnter");    
			if(isDragOk(e) == false) {
				//System.out.println( "enter not ok");
				//	DropLabel.this.borderColor=Color.red;            
				//	showBorder(true);
				e.rejectDrag();      
				return;
			}
			//    DropLabel.this.borderColor=Color.green;      
			//  showBorder(true);
			//System.out.println( "dt enter: accepting " + e.getDropAction());
			e.acceptDrag(e.getDropAction());      
		}

		/**
		 * continue "drag under" feedback on component
		 * invoke acceptDrag or rejectDrag based on isDragOk
		 */
		public void dragOver(DropTargetDragEvent e) {
			//System.out.println( "dragover");
        
			if(isDragOk(e) == false) {
				//System.out.println( "dtlistener dragOver not ok" );
				//DropLabel.this.borderColor=Color.red;            
				//showBorder(true);
				e.rejectDrag();      
				return;
			}
			//System.out.println( "dt over: accepting");
			e.acceptDrag(e.getDropAction());            
		}
    
		public void dropActionChanged(DropTargetDragEvent e) {
        
			//System.out.println( "dropactionchanged");
			if(isDragOk(e) == false) {
				//System.out.println( "dtlistener changed not ok" );
				e.rejectDrag();      
				return;
			}
			// System.out.println( "dt changed: accepting"+e.getDropAction());
			e.acceptDrag(e.getDropAction());            
		}
    
		public void dragExit(DropTargetEvent e) {
			//System.out.println( "dtlistener dragExit");
			//DropLabel.this.borderColor=Color.green;            
			// showBorder(false);
		}

    
    
    
		public   boolean isAcceptable(char c)
		{
			if(c!=9)
				return true;
     
			/*
			  if(c>='a' && c<='z' || c>='A' && c<='Z'||c>='0'&& c<='9'||c==' '||c=='&'||c=='é'||c=='\''||c=='\"'
			  ||c=='#'|| c=='{' || c=='(' || c=='[' || c=='-' || c=='|' || c=='è'|| c== '`'|| c=='_'|| c=='\\'
			  ||c=='ç' || c=='à'||c=='   return true;
			  else
			  return false;
     
			*/
     
     
			return false;
		}
    
		public ArrayList getListSelectionne(String st)
		{
                         
			ArrayList list=new ArrayList();
			int j=0;
			for(int i=0;i<st.length();i++)
				{
					//System.out.println("<"+(int)st.charAt(i)+">");
					if(!isAcceptable(st.charAt(i)))
						{
							String stlist=st.substring(j,i);
    
							//System.out.println("on copie12<"+stlist+">");

							if(list.size()==0)
								list.add(stlist);
							else
								if(!stlist.equalsIgnoreCase((String)list.get(list.size()-1)))
									list.add(stlist);
 
 
							while((i<st.length())&&!isAcceptable(st.charAt(i)))i++;
							j=i;
							i--;
 
						}
 
				}
 
			return list;
		}
 /*
		public void dropJTree(JTree jtree,String st,Point point)
		{
			TreePath tph=jtree.getPathForLocation((int)point.getX(),(int)point.getY());
			String sep=java.io.File.separator;
			if (tph!=null)
				{
					Object [] o=tph.getPath();
					String res="";
					for(int i=1;i<o.length-1;i++)
						res+=o[i]+sep;
					res+=o[o.length-1];
  
					env.setReferenceSelectionnee(getListSelectionne(st));
                           
					env.copier();
					env.setCurrent(res); 	
					env.coller();		
    
				}
          
			///System.out.println("c un tree");
		}
  
		public void dropJTable(JTable jtable,String st,Point point)
		{
       
			String sep=java.io.File.separator;
        
		env.setReferenceSelectionnee(getListSelectionne(st));
			String res="";

			int row=jtable.rowAtPoint(point) ;
			int col=jtable.columnAtPoint(point) ;
			if(row<0||col<0)
				return;
			Object o=jtable.getValueAt(row,col);
       
			if(o!=null)
				res=o.toString();
         
	if(res.equalsIgnoreCase(""))
				res=env.getRepCurrent().getProperty().getPath();
		env.setReferenceSelectionnee(getListSelectionne(st));
   
			File rep=new File(res);
			if(!rep.isDirectory())
				{System.out.println("c pa bon <"+res+">");
				return;
          
				}
			System.out.println("c un jtable1<"+res+">");
			env.copier();
			env.setCurrent(res); 	
			env.coller();		
   
          
          
			System.out.println("c un jtable"+res);
		}  
    */
		/**
		 * perform action from getSourceActions on
		 * the transferrable
		 * invoke acceptDrop or rejectDrop
		 * invoke dropComplete
		 * if its a local (same JVM) transfer, use StringTransferable.localStringFlavor
		 * find a match for the flavor
		 * check the operation
		 * get the transferable according to the chosen flavor
		 * do the transfer
		 */
		public void drop(DropTargetDropEvent e) {
			//System.out.println( "dtlistener drop");
      
			DataFlavor chosen = chooseDropFlavor(e);
			if (chosen == null) {
				//System.err.println( "No flavor match found" );
				e.rejectDrop();      	
				return;
			}
			//System.err.println( "Chosen data flavor is " + chosen.getMimeType());

			// the actual operation
			int da = e.getDropAction();
			// the actions that the source has specified with DragGestureRecognizer
			int sa = e.getSourceActions();      
			//System.out.println( "drop: sourceActions: " + sa);
			//System.out.println( "drop: dropAction: " + da);
      
			if ( ( sa & acceptableActions ) == 0 ) {
				//System.err.println( "No action match found" );
				e.rejectDrop();      		
				//showBorder(false);      		
				return;
			}
    
			Object data=null;
			try {
				/*
				 * the source listener receives this action in dragDropEnd.
				 * if the action is DnDConstants.ACTION_COPY_OR_MOVE then
				 * the source receives MOVE!
				 */
				e.acceptDrop(acceptableActions);
				// e.acceptDrop(DnDConstants.ACTION_MOVE);
				//e.acceptDrop(DnDConstants.ACTION_COPY);
				//e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE); 
  
				data = e.getTransferable().getTransferData(chosen);
	
				if (data == null)
					throw new NullPointerException();
			} catch ( Throwable t ) {
				//System.err.println( "Couldn't get transfer data: " + t.getMessage());
				t.printStackTrace();
				e.dropComplete(false);
				//showBorder(false);      		
				return;
			}
			//System.out.println( "Got data: " + data.getClass().getName() );
      
			Point point=e.getLocation();
			/**/
			// faire le copier / coller 
			if(comp instanceof JTree)
				{
					//dropJTree((JTree)comp,data.toString(),point);
				}
			else
				if(comp instanceof JTable)
					{ // System.out.println("table");
					//dropJTable((JTable)comp,data.toString(),point);
					}
			/**/       
               
				else
					if (data instanceof String ) {
						String s = (String) data;
						//DropLabel.this.setText(s);	
        
        
	
        
						//System.out.println("on copy 5"+s);
      
					} else if (data instanceof InputStream) {
						//System.out.println("inpustream");
						InputStream input = (InputStream)data;
						InputStreamReader isr = null;
						//	BufferedReader br = null;
						try {
							//	  br = new BufferedReader(isr=new InputStreamReader(input,"Unicode"));
							isr=new InputStreamReader(input,"Unicode");	  
						} catch(UnsupportedEncodingException uee) {
							isr=new InputStreamReader(input);	  	  
						}

						StringBuffer str = new StringBuffer();
						int in=-1;
						try {
							while((in = isr.read()) >= 0 ) {
								//System.out.println("read: " + in);
								if (in != 0)
									str.append((char)in);
							}

							/* you get garbage chars this way
							   try {
							   String line=null;	  
							   while( (line = br.readLine()) != null) {
							   str.append(line);
							   str.append('\n');	    
							   System.out.println( "read: " + line);
							   System.out.println( "read: " +
							   (int)line.charAt(line.length()-1)); 
							   }

							   br.close();
							*/
							// DropLabel.this.setText(str.toString());
							//System.out.println("on copy 3");
						} catch(IOException ioe) {
							/*
							  bug #4094987
							  sun.io.MalformedInputException: Missing byte-order mark
							  e.g. if dragging from MS Word 97
							  still a bug in 1.2 final
							*/
	  
							System.err.println( "cannot read" + ioe);
							e.dropComplete(false);      	  
							//showBorder(false);
							String message = "Bad drop\n" + ioe.getMessage();
							JOptionPane.showMessageDialog(null,
														  message,
														  "Error",
														  JOptionPane.ERROR_MESSAGE);
							return;
						}

					} else {
						//System.out.println( "drop: rejecting");
						e.dropComplete(false);      	  	
						//showBorder(false);      	
						return;
					}
      
			e.dropComplete(true);      
			//showBorder(false);      	      
		}

	}
}
