package interfaz;

import org.eclipse.swt.*;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class Interfazz {

	public static void main(String[] args) {
		Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setText("List Example");
	    shell.setSize(270, 450);
	    shell.setLayout(new GridLayout(1, false));
	    
	    Group g1 = new Group(shell, SWT.NONE);
	    g1.setText("CancionesDisponibles");
	    g1.setLayout(new GridLayout(1, false));
	    g1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    
	    Group g2 = new Group(shell, SWT.NONE);
	    g2.setText("Controles");
	    g2.setLayout(new GridLayout(1, true));
	    g2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	    
	    Group g3 = new Group(shell, SWT.NONE);
	    g3.setText("CancionesEnCola");
	    g3.setLayout(new GridLayout(1, false));
	    g3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    
	    final List list = new List(g1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    list.setLayoutData(new GridData(200,100));
	    

	    for (int loopIndex = 0; loopIndex < 100; loopIndex++) {
	      list.add("Item " + loopIndex);
	    }

	    list.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	    	  int selections = list.getSelectionIndex();
		        String outText = "";
		        
		        outText += selections + " ";
		        System.out.println("You selected: " + outText);
		        list.getMenu().setVisible(true);
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	        int selections = list.getSelectionIndex();
	        String outText = "";
	        
	        outText += selections + " ";
	        System.out.println("You selected: " + outText);
	        list.getMenu().setVisible(true);
	      }
	    });
	    
	    final List list2 = new List(g3, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    list2.setLayoutData(new GridData(200,100));

	    list2.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	    	  int selections = list2.getSelectionIndex();
		        String outText = "";
		        
		        outText += selections + " ";
		        System.out.println("You selected: " + outText);
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	        int selections = list2.getSelectionIndex();
	        String outText = "";
	        
	        outText += selections + " ";
	        System.out.println("You selected: " + outText);
	      }
	    });
	    
	    final Menu menu = new Menu(list);
	    
	    
	    list.setMenu(menu);
	    menu.addMenuListener(new MenuAdapter() {
	    	
	    	MenuItem mi1 = new MenuItem(menu, SWT.PUSH);
	    	MenuItem mi2 = new MenuItem(menu, SWT.PUSH);
	    	
	    	SelectionListener sl = new SelectionListener() {

    			@Override
    			public void widgetDefaultSelected(SelectionEvent arg0) {
    				// TODO Auto-generated method stub
    			}

    			@Override
    			public void widgetSelected(SelectionEvent arg0) {
    				// TODO Auto-generated method stub
    				int index = list.getSelectionIndex();
    				if (index>=0) {
    					list2.add(list.getItem(index));
    				}
    			}
    			
    		};
	    	
	    	public void menuShown(MenuEvent e) {
	    		mi1.removeSelectionListener(sl); //Para que no se a�adan infinitos SelectionListener (Uno por cada vez que se abra el menu)
	    		int index = list.getFocusIndex();
	    		System.out.println(index);
	    		mi1.setText("A�adir a la cola");
	    		mi2.setText("Reproducir");
	    		
	    		mi1.addSelectionListener(sl);
	    	}
	    });
	    
	    
	    ToolBar tb = new ToolBar(g2, SWT.BOTTOM);
	    GridLayout gl = new GridLayout();
	    gl.numColumns = 2;
	    gl.makeColumnsEqualWidth = true;
	    tb.setLayout(gl);
	    tb.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
	    
		ToolItem ti1 = new ToolItem(tb, SWT.PUSH);
		ToolItem ti2 = new ToolItem(tb, SWT.PUSH);
		
		Image play = new Image(display, "play.png");
		Image pause = new Image(display, "pausa.png");
		Image skip = new Image(display, "skip.png");
		
		ti1.setImage(play);
		ti2.setImage(skip);
		
		ti1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (ti1.getImage().equals(play)) {
					ti1.setImage(pause);
				}else {
					ti1.setImage(play);
				}
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (ti1.getImage().equals(play)) {
					ti1.setImage(pause);
				}else {
					ti1.setImage(play);
				}
			}
			
		});
		ti2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  
	}
	
}
