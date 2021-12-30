package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class AtenderPeticion implements Runnable{

	private Socket sCliente;
	private String dirHome = ".";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try(DataInputStream dis = new DataInputStream(sCliente.getInputStream());
				DataOutputStream dos = new DataOutputStream(sCliente.getOutputStream())){
			String petic = dis.readLine();
			File f = buscaCancion(petic);
			if(f.exists()) {
				try(DataInputStream is = new DataInputStream(new FileInputStream(f))){
					
				}
			}else {
				dos.writeBytes("La cancion introducida no esta disponible");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
