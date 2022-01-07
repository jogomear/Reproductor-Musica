package interfaz;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Interfaz {
	public static void main(String[] args) {
		Display d = new Display();
		Shell s = new Shell(d);
		s.setText("Practica SWT");
		TabFolder tf = new TabFolder(s, SWT.TOP);
		TabItem ti1 = new TabItem(tf, SWT.NONE);
		TabItem ti2 = new TabItem(tf, SWT.NONE);
		TabItem ti3 = new TabItem(tf, SWT.NONE);
		ti1.setText("Pestaaskdjbaskfbskdbf");
		ti2.setText("Pestaña-2");
		ti3.setText("Pestaña-3");
		tf.pack();
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
