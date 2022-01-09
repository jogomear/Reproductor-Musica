package interfaz;

import org.eclipse.swt.*;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cliente.Cliente;

public class Interfaz {

	public static void main(String[] args){
		
			//Ponemos la declaracion de todos los elementos a la vez por si, al añadir funcionalidad a alguno de ellos, se dependiera de otra componente
			//no haya problemas porque uno esté declarado más adelante que los Listener correspondientes
		
			Display display = new Display();
		    Shell shell = new Shell(display);
		    shell.setText("Reproductor de música");
		    shell.setSize(270, 450);
		    shell.setLayout(new GridLayout(1, false));
		    
		    //Los parámetros de Cliente se cambiarán en función del servidor al que se quiera conectar. En nuestro caso, lo hemos testeado en local
		    Cliente cl = new Cliente("localhost", 50000);
		    cl.borraCanciones();
		    
		    //3 grupos, uno para cada lista, y otro para los controles
		    Group g1 = new Group(shell, SWT.NONE);
		    Group g2 = new Group(shell, SWT.NONE);
		    Group g3 = new Group(shell, SWT.NONE);
		    
		    //Listas para las canciones disponibles y las canciones en la cola
		    final List list = new List(g1, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		    final List list2 = new List(g3, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		 
		    final Menu menu = new Menu(list);
		    
		    //Barra de herramientas contenedora de los "botones" play/pause y skip
		    ToolBar tb = new ToolBar(g2, SWT.BOTTOM);
		    
		    //"Botones" de control. Usamos ToolItems para que esten agrupados en la barra de herramientas y no se traten como botones separados ya que
		    //tienen una funcionalidad similar
		    ToolItem ti1 = new ToolItem(tb, SWT.PUSH);
			ToolItem ti2 = new ToolItem(tb, SWT.PUSH);
			
			//Iconos de los "botones"
			Image play = new Image(display, "play.png");
			Image pause = new Image(display, "pausa.png");
			Image skip = new Image(display, "skip.png");
			
			/*
			 * A partir de aqui, con todas las componentes declaradas, se aplica tanto la funcionalidad como la orientación/colocación de cada uno de 
			 * ellos. Así como la inicialización de los datos de cada uno en caso de que los contengan
			 */
		    
		    g1.setText("CancionesDisponibles");
		    g1.setLayout(new GridLayout(1, false));
		    g1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    
		    g2.setText("Controles");
		    g2.setLayout(new GridLayout(1, true));
		    g2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		    
		    g3.setText("CancionesEnCola");
		    g3.setLayout(new GridLayout(1, false));
		    g3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    
		    list.setLayoutData(new GridData(200,100));
		    
		    for (int loopIndex = 0; loopIndex < cl.listadoCanciones.size(); loopIndex++) {
		      list.add(cl.listadoCanciones.get(loopIndex));
		      //Con esto se añaden los titulos de las canciones contenidas por el servidor en la lista de la interfaz
		    }
	
		    list.addSelectionListener(new SelectionListener() {
		    	//Utilizamos el menu, para dar la opcion de añadir a la cola o reproducir la cancion directamente
		      public void widgetSelected(SelectionEvent event) {
		    	  list.getMenu().setVisible(true);
		      }
	
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  list.getMenu().setVisible(true);
		      }
		    });
		    
		    list2.setLayoutData(new GridData(200,100));
	
		    list2.addSelectionListener(new SelectionListener() {
		    	//Hemos añadido la funcionalidad de quitar canciones de la cola al hacer click sobre ellas
		      public void widgetSelected(SelectionEvent event) {
		    	  int selections = list2.getSelectionIndex();
			      list2.remove(selections);
		      }
	
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  int selections = list2.getSelectionIndex();
			      list2.remove(selections);
		      }
		    });
		    
		    list.setMenu(menu);
		    menu.addMenuListener(new MenuAdapter() {
		    	
		    	MenuItem mi1 = new MenuItem(menu, SWT.PUSH);
		    	MenuItem mi2 = new MenuItem(menu, SWT.PUSH);
		    	
		    	//Listener usado para añadir a la cola las canciones
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
	    		
	    		//Listener usado para reproducir directamente las canciones
	    		SelectionListener sl2 = new SelectionListener() {
	
	    			@Override
	    			public void widgetDefaultSelected(SelectionEvent arg0) {
	    				// TODO Auto-generated method stub
	    			}
	
	    			@Override
	    			public void widgetSelected(SelectionEvent arg0) {
	    				// TODO Auto-generated method stub
	    				ti1.setImage(pause);
	    				//Comparación para cuando se pida reproducir una canción mientras otra esta siendo reproducida.
	    				//En ese caso, se cierra la que se está reproduciendo y se reproduce la elegida por el usuario
	    				if (cl.clipAbierto()) {
	    					cl.cierraClip();
	    				}
	    				cl.reproduccion(list.getItem(list.getSelectionIndex()));
	    				cl.borraCanciones();
	    			}
	    			
	    		};
		    	
		    	public void menuShown(MenuEvent e) {
		    		mi1.removeSelectionListener(sl1); //Para que no se añadan infinitos SelectionListener (Uno por cada vez que se abra el menu)
		    		mi2.removeSelectionListener(sl2);
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
	
				//Dependiendo de si se pausa o se reproduce una canción, el icono del botón varía. En caso de que no se esté reproduciendo un clip,
				//no ocurrirá nada
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					if (cl.clipAbierto()) {
						if (ti1.getImage().equals(play)) {
							ti1.setImage(pause);
							cl.reproduce();
						}else {
							ti1.setImage(play);
							cl.parar();
						}
					}
				}
	
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					if (cl.clipAbierto()) {
						if (ti1.getImage().equals(play)) {
							ti1.setImage(pause);
							cl.reproduce();
						}else {
							ti1.setImage(play);
							cl.parar();
						}
					}
				}
				
			});
			ti2.addSelectionListener(new SelectionListener() {
	
				//Funcionalidad para el boton de skip
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					if (list2.getItems().length>0) {
						cl.cierraClip();
						cl.reproduccion(list2.getItem(0));
						list2.remove(0);
						cl.borraCanciones();
					}
				}
				
			});
			
		    shell.open();
		    while (!shell.isDisposed()) {
		    	//Esta comparación la usamos para que, cuando una canción acabe, se reproduzca automaticamente la siguiente en la cola.
		    	if (ti1.getImage().equals(pause)&&(!cl.estaReproduciendo())) {
		    		if (list2.getItemCount()!=0) {
		    			cl.cierraClip();
		    			String siguiente = list2.getItem(0);
		    			list2.remove(0);
		    			cl.reproduccion(siguiente);
		    			cl.borraCanciones();
		    		}
		    	}
		      if (!display.readAndDispatch())
		        display.sleep();
		    }
		    display.dispose();
		  
	}
}
