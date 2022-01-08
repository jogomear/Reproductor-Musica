package cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Cliente {
	
	private String host;
	private int puerto;
	public ArrayList<String> listadoCanciones;
	
	public Cliente(String s, int n) {
		host = s;
		puerto = n;
		this.listadoCanciones = new ArrayList<>();
	}
	
	public void reproduccionSinGuardar() {
		try (Socket s = new Socket(host, puerto);
				InputStream in = s.getInputStream()){
			byte[] b = new byte[1024*10];
			int leido = in.read(b);
			while (leido!=-1) {
				leido = in.read(b);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Cliente cl = new Cliente("localhost", 8000);
		cl.reproduccionSinGuardar();
	}
	
	public void listarCanciones() {
		try(Socket s = new Socket(this.host, this.puerto)){
			int numBytesLeidos;
			byte[]  buff = new byte[1024*32];
			try(InputStream recibido = s.getInputStream()/*;FileOutputStream nuevo = new FileOutputStream("listadoCanciones.txt");*/){
				while ((numBytesLeidos = recibido.read(buff)) != -1) {
					/*nuevo.write(buff, 0, numBytesLeidos);*/
					this.listadoCanciones.add(new String(buff));
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
