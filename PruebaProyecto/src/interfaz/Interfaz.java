package interfaz;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import cliente.Cliente;

public class Interfaz {
	
	public static void main(String[] args) {
		
		Cliente cl = new Cliente("localhost", 8000);
		Display d = new Display();
		Shell s = new Shell(d);
		s.setLayout(new RowLayout(SWT.VERTICAL));
		s.setText("TrabajoReproductorStreaming");
		
		//cl.getCanciones();
		
		final List listaDisponibles = new List(s, SWT.BORDER|SWT.V_SCROLL|SWT.SINGLE);
		listaDisponibles.setLayoutData(new RowData(240,100));
		
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		listaDisponibles.add("aaa");
		listaDisponibles.add("bbb");
		
		listaDisponibles.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent event) {
				int index = listaDisponibles.getSelectionIndex();
			}
		});
		
		
		ToolBar tb = new ToolBar(s, SWT.BOTTOM);
		ToolItem ti1 = new ToolItem(tb, SWT.PUSH);
		ToolItem ti2 = new ToolItem(tb, SWT.PUSH);
		ToolItem ti3 = new ToolItem(tb, SWT.PUSH);
		ti1.setText("Pause");
		ti2.setText("Play");
		ti3.setText("Siguiente");

		tb.pack();
		s.pack();
		s.open();
		while(!s.isDisposed()) {
			if (!d.readAndDispatch()) {
				d.sleep();
			}
		}
		d.dispose();
	}
}

