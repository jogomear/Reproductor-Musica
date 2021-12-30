package cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	private String host;
	private int puerto;
	
	public Cliente(String s, int n) {
		host = s;
		puerto = n;
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

}
