/*
 * Créé le 3 mars 2004
 */
package fr.umlv.anaconda;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.Component;
/**
 * @author ntesevic
 * @author Anac team
 */
public class AnacondaDrag {

	private DragSource dragSource;
	private DragGestureListener dgListener;
	private DragSourceListener dsListener;
	private int dragAction = DnDConstants.ACTION_COPY;
    
    
    
	/** Creates a new instance of AnacondaDrag */
	public AnacondaDrag(Component comp) {
        
        
		dragAction=DnDConstants.ACTION_MOVE ;
		dragSource = DragSource.getDefaultDragSource();
		dgListener = new DGListener();
		dsListener = new DSListener();
		dragSource.createDefaultDragGestureRecognizer(comp,dragAction,dgListener); 	
       
        
	}
    
    
    
    
       
    
	/**
	 * DGListener
	 * a listener that will start the drag.
	 * has access to top level's dsListener and dragSource
	 * @see java.awt.dnd.DragGestureListener
	 * @see java.awt.dnd.DragSource
	 * @see java.awt.datatransfer.StringSelection      
	 */
	class DGListener implements DragGestureListener {
		/**
		 * Start the drag if the operation is ok.
		 * uses java.awt.datatransfer.StringSelection to transfer
		 * the label's data
		 * @param e the event object
		 */
		public void dragGestureRecognized(DragGestureEvent e) {
			//System.out.println("draggestureRecognized:");
 
			//_env.addListSelection(Object o);

			// if the action is ok we go ahead
			// otherwise we punt
			//    System.out.println(e.getDragAction());
     

			if((e.getDragAction() & dragAction) == 0)
				return;
			//      System.out.println( "kicking off drag");
      
			// get the label's text and put it inside a Transferable
			// Transferable transferable = new StringSelection( DragLabel.this.getText() );
    

			Transferable transferable = new StringSelection(":");      





			// now kick off the drag
			try {
				// initial cursor, transferrable, dsource listener      
				e.startDrag(DragSource.DefaultCopyNoDrop,
							transferable,
							dsListener);

				// or if dragSource is a variable
				// dragSource.startDrag(e, DragSource.DefaultCopyDrop, transferable, dsListener);

	
				// or if you'd like to use a drag image if supported
	
				/*
				  if(DragSource.isDragImageSupported() )
				  // cursor, image, point, transferrable, dsource listener	
				  e.startDrag(DragSource.DefaultCopyDrop, image, point, transferable, dsListener);
				*/
	
			}catch( InvalidDnDOperationException idoe ) {
				System.err.println( idoe );
			}
		}
	}
  
	/**
	 * DSListener
	 * a listener that will track the state of the DnD operation
	 * 
	 * @see java.awt.dnd.DragSourceListener
	 * @see java.awt.dnd.DragSource
	 * @see java.awt.datatransfer.StringSelection      
	 */
	class DSListener implements DragSourceListener {
    
		/**
		 * @param e the event
		 */
		public void dragDropEnd(DragSourceDropEvent e) {
			//System.out.println("dragdropend");
   
			//     System.out.println("on est dragdropend");
			if( e.getDropSuccess() == false ) {
				//System.out.println( "not successful");
				return;
			}

			/*
			 * the dropAction should be what the drop target specified
			 * in acceptDrop
			 */
			// System.out.println( "dragdropend action " + e.getDropAction() );

			// this is the action selected by the drop target

			// if(e.getDropAction() == DnDConstants.ACTION_MOVE)
			//System.out.println("move");
    
     
      
		}

		/**
		 * @param e the event
		 */
		public void dragEnter(DragSourceDragEvent e) {
      
			//System.out.println("dragenter");
  
			//System.out.println( "draglabel enter " + e);
			DragSourceContext context = e.getDragSourceContext();
			//intersection of the users selected action, and the source and target actions
			int myaction = e.getDropAction();
			if( (myaction & dragAction) != 0) {	
				context.setCursor(DragSource.DefaultCopyDrop);	  
			} else {
				context.setCursor(DragSource.DefaultCopyNoDrop);	  	
			}
		}
		/**
		 * @param e the event
		 */
		public void dragOver(DragSourceDragEvent e) {
			//System.out.println("dragover");
			DragSourceContext context = e.getDragSourceContext();
			int sa = context.getSourceActions();
			int ua = e.getUserAction();
			int da = e.getDropAction();
			int ta = e.getTargetActions();
  
			//System.out.println("dl dragOver source actions" + sa);
			//System.out.println("user action" + ua);
			//System.out.println("drop actions" + da);
			//System.out.println("target actions" + ta);      
		}
		/**
		 * @param e the event
		 */
		public void dragExit(DragSourceEvent e) {
			//System.out.println("dragExit");
			//System.out.println( "draglabel exit " + e);      
			DragSourceContext context = e.getDragSourceContext();
		}

		/**
		 * for example, press shift during drag to change to
		 * a link action
		 * @param e the event     
		 */
		public void dropActionChanged (DragSourceDragEvent e) {
			//System.out.println("dropActionChanged");
			DragSourceContext context = e.getDragSourceContext();      
			context.setCursor(DragSource.DefaultCopyNoDrop);	  	
		}
	}

    
    
    
    
    
    
    
}
