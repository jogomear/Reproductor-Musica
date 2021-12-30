package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newCachedThreadPool();
		try(ServerSocket ss = new ServerSocket(55555)){
			while(true) {
				try {
					final Socket conexion = ss.accept();
					pool.execute(new AtenderPeticion(conexion));
				} catch(IOException e) {
					e.printStackTrace();
				} finally {
					pool.shutdown();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.shutdown();
	}

}
