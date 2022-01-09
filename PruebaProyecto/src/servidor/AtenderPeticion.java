package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * Esta clase sera la encarcagada de dar funcionalidad a nuestro servidor usando hilos
 */

public class AtenderPeticion implements Runnable{

	private Socket sCliente;
	
	@Override
	public void run() {
		try(DataInputStream dis = new DataInputStream(sCliente.getInputStream());
				OutputStream o = sCliente.getOutputStream()){
			/* Este primer readline es porque segun si queremos reproducir una cancion
			 * o listar las disponibles, cada una de estas dos funciones esta configurada
			 * para mandar un 1 si queremos reproducir y un 2 si queremos listar las canciones,
			 * de esta forma nuestro servidor actuara de una forma u otra segun lo recibido
			 */
			String peticion = dis.readLine();
			switch(peticion) {
				/*
				 * Si entra aqui al 1, es que el cliente quiere que el servidor envie una cancion
				 */
				case "1":{
					/*La siguiente linea en este caso (enviada por el cliente) sera el nombre
					 * de la cancion que quiere que el servidor nos envie
					 */
					String nombreCancion = dis.readLine();
					File cancion = buscaCancion(nombreCancion);
					/*
					 * Llegados a este punto, comenzamos con el envio de la cancion. La cancion a fin de
					 * cuentas es un fichero, por lo que el programa lo que hace es ire leyendo bytes de un fichero
					 * y enviarlos a traves de un outputstream (declarado previamente)
					 */
					try(FileInputStream i = new FileInputStream(cancion)){
						byte[] b = new byte[1024*10];
						int leidos = i.read(b);
						while (leidos!=-1) {
							o.write(b,0,leidos);
							leidos = i.read(b);
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case "2":{
					/*
					 * En esta parte entramos si lo que nos solicita el cliente es la lista de canciones
					 * que el servidor tiene disponibles para reproducir.
					 * 
					 * En este try que tenemos debajo, lo que hacemos es crear un fichero de texto que contiene
					 * solo los nombres de las canciones (un titulo por linea y sin las extensiones .wav), que 
					 * mas tarde el cliente utilizara para mostrar en la interfaz en forma de lista al usuario
					 */
					try(OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("listaCanciones.txt"))){
						File canciones = new File("Canciones");
						String[] s = canciones.list();
						for (int i = 0; i < s.length; i++) {
							out.write((s[i].split("\\."))[0] + "\r\n");
						}
						out.flush();
						FileInputStream in = new FileInputStream("listaCanciones.txt");
						byte[] b = new byte[1024*10];
						int leidos = in.read(b);
						while (leidos!=-1) {
							o.write(b,0,leidos);
							leidos = in.read(b);
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	AtenderPeticion(Socket s){
		super();
		this.sCliente = s;
	}
	
	private File buscaCancion(String nombreCancion) {
		/*
		 * Esta funcion auxiliar simplemente nos devuelve un file (en este caso sera
		 * una canción), que estara alojada en la carpetta de canciones disponibles de nuestro
		 * servidor, a partir de un string que introducimos como parametro (este sera el nombre
		 * de la cancion que estamos buscando).
		 */
		File canc = new File("Canciones/" + nombreCancion + ".wav");
		return canc;
	}

}

