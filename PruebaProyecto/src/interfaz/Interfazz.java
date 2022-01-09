package interfaz;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.swt.*;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cliente.Cliente;

public class Interfazz {

	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, InterruptedException {
		
		
		Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setText("Reproductor de música");
	    shell.setSize(270, 450);
	    shell.setLayout(new GridLayout(1, false));
	    
	    Cliente cl = new Cliente("localhost", 50000);
	    cl.borraCanciones();
	    
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
	    

	    for (int loopIndex = 0; loopIndex < cl.listadoCanciones.size(); loopIndex++) {
	      list.add(cl.listadoCanciones.get(loopIndex));
	    }

	    list.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	    	  list.getMenu().setVisible(true);
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	    	  list.getMenu().setVisible(true);
	      }
	    });
	    
	    final List list2 = new List(g3, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	    list2.setLayoutData(new GridData(200,100));

	    list2.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	    	  int selections = list2.getSelectionIndex();
		      list2.remove(selections);
	      }

	      public void widgetDefaultSelected(SelectionEvent event) {
	    	  int selections = list2.getSelectionIndex();
		      list2.remove(selections);
	      }
	    });
	    
	    final Menu menu = new Menu(list);
	    
	    ToolBar tb = new ToolBar(g2, SWT.BOTTOM);
	    
	    ToolItem ti1 = new ToolItem(tb, SWT.PUSH);
		ToolItem ti2 = new ToolItem(tb, SWT.PUSH);
		
		Image play = new Image(display, "play.png");
		Image pause = new Image(display, "pausa.png");
		Image skip = new Image(display, "skip.png");
	    
	    list.setMenu(menu);
	    menu.addMenuListener(new MenuAdapter() {
	    	
	    	MenuItem mi1 = new MenuItem(menu, SWT.PUSH);
	    	MenuItem mi2 = new MenuItem(menu, SWT.PUSH);
	    	
	    	SelectionListener sl1 = new SelectionListener() {

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
    		
    		SelectionListener sl2 = new SelectionListener() {

    			@Override
    			public void widgetDefaultSelected(SelectionEvent arg0) {
    				// TODO Auto-generated method stub
    			}

    			@Override
    			public void widgetSelected(SelectionEvent arg0) {
    				// TODO Auto-generated method stub
    				ti1.setImage(pause);
    				cl.reproduccionSinGuardar(list.getItem(list.getSelectionIndex()));
    				cl.borraCanciones();
    			}
    			
    		};
	    	
	    	public void menuShown(MenuEvent e) {
	    		mi1.removeSelectionListener(sl1); //Para que no se añadan infinitos SelectionListener (Uno por cada vez que se abra el menu)
	    		mi2.removeSelectionListener(sl2);
	    		int index = list.getFocusIndex();
	    		mi1.setText("Añadir a la cola");
	    		mi2.setText("Reproducir");
	    		mi1.addSelectionListener(sl1);
	    		mi2.addSelectionListener(sl2);
	    	}
	    });
	    
	    GridLayout gl = new GridLayout();
	    gl.numColumns = 2;
	    gl.makeColumnsEqualWidth = true;
	    tb.setLayout(gl);
	    tb.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		
		ti1.setImage(play);
		ti2.setImage(skip);
		
		ti1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (ti1.getImage().equals(play)) {
					ti1.setImage(pause);
					cl.reproduce();
				}else {
					ti1.setImage(play);
					cl.parar();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (ti1.getImage().equals(play)) {
					ti1.setImage(pause);
					cl.reproduce();
				}else {
					ti1.setImage(play);
					cl.parar();
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
				if (list2.getItems().length>0) {
					cl.cierraClip();
					cl.reproduccionSinGuardar(list2.getItem(0));
					list2.remove(0);
					cl.borraCanciones();
				}
			}
			
		});
		
	    shell.open();
	    while (!shell.isDisposed()) {
	    	if (ti1.getImage().equals(pause)&&(!cl.estaReproduciendo())) {
	    		if (list2.getItemCount()!=0) {
	    			cl.cierraClip();
	    			String siguiente = list2.getItem(0);
	    			list2.remove(0);
	    			cl.reproduccionSinGuardar(siguiente);
	    			cl.borraCanciones();
	    		}
	    	}
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  
	}
	
	
}
