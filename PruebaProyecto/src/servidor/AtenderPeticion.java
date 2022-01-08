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

public class AtenderPeticion implements Runnable{

	private Socket sCliente;
	
	@Override
	public void run() {
		try(DataInputStream dis = new DataInputStream(sCliente.getInputStream())){
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
				try(	OutputStream o = sCliente.getOutputStream();
						OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("listaCanciones.txt"))){
					
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
		File canc = new File("Canciones/" + nombreCancion + ".wav");
		return canc;
	}

}

