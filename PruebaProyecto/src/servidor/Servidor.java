package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Esta clase sera nuestro servidor. Gracias al uso del pool de conexiones tenemos un servidor
 * multihilo que hace que todo funcione mas rápidamente. Esta clase se limita a llamar a la clase
 * auxiliar AtenderPeticion que es la que gestiona todo lo referente a la funcionalidad del servidor
 * con hilos.
 */
public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newCachedThreadPool();
		try(ServerSocket ss = new ServerSocket(50000)){
			while(true) {
				try {
					final Socket conexion = ss.accept();
					pool.execute(new AtenderPeticion(conexion));
				} catch(IOException e) {
					e.printStackTrace();
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Es importante cerrar el pool aqui y no dentro del while true, ¡Fallo importante!,
		 * ya que si lo cerramos dentro del while true nos va a cerrar el socket y salta excepcion
		 * puesto que intentamos enviar cosas a traves de un socket que esta cerrado.
		 */
		pool.shutdown();
	}

}
