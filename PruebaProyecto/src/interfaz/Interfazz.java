package interfaz;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Interfazz {

	public static void main(String[] args) {
		Display d = new Display();
		Shell s = new Shell(d);
		s.setText("maestro");
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
