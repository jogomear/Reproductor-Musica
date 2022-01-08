package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AtenderPeticion implements Runnable{

	private Socket sCliente;
	private String dirHome = ".";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		try(DataInputStream dis = new DataInputStream(sCliente.getInputStream());
//				DataOutputStream dos = new DataOutputStream(sCliente.getOutputStream())){
//			String petic = dis.readLine();
//			File f = buscaCancion(petic);
//			if(f.exists()) {
//				try(DataInputStream is = new DataInputStream(new FileInputStream(f))){
//					
//				}
//			}else {
//				dos.writeBytes("La cancion introducida no esta disponible");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try(DataInputStream dis = new DataInputStream(sCliente.getInputStream())){
			/*String nombreCancion = dis.readLine();
			File cancion = buscaCancion(nombreCancion);
			try(OutputStream o = sCliente.getOutputStream();
					FileInputStream i = new FileInputStream(cancion)){
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
			}*/
			String peticion = dis.readLine();
			switch(peticion) {
			case "1":
				String nombreCancion = dis.readLine();
				File cancion = buscaCancion(nombreCancion);
				try(OutputStream o = sCliente.getOutputStream();
						FileInputStream i = new FileInputStream(cancion)){
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
			case "2":
				try(DataOutputStream dos = new DataOutputStream(this.sCliente.getOutputStream())){
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case "3":
				
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
		File canc = new File(dirHome + "\\" + nombreCancion + ".mp3");
		return canc;
	}
	
	private /*String[]*/File listarCanciones() {
		File contenidoDirectorio = new File(this.dirHome + "\\canciones");
		return contenidoDirectorio/*.list()*/;
	}

}

