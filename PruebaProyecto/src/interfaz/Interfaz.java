package interfaz;

import org.eclipse.swt.widgets.*;

public class Interfaz {
	public static void main(String[] args) {
		Display d = new Display();
        Shell s = new Shell(d);
        s.setText("Reproductor de musica en Streaming");
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
