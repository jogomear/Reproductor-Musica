package cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Cliente {
	
	private String host;
	private int puerto;
	public ArrayList<String> listadoCanciones;
	private Clip clip;
	
	public Cliente(String s, int n){
		host = s;
		puerto = n;
		this.listadoCanciones = new ArrayList<>();
		listarCanciones();
	}
	
	public void reproduccionSinGuardar(String cancion) {
		try (Socket s = new Socket(host, puerto);
				InputStream in = s.getInputStream();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				FileOutputStream fos = new FileOutputStream("Cancioness/" + cancion + ".wav")){
			dos.writeBytes("1\r\n");
			dos.writeBytes(cancion + "\r\n");
			byte[] b = new byte[1024*10];
			int leido = in.read(b);
			while (leido!=-1) {
				fos.write(b, 0, leido);
				leido = in.read(b);
			}
			File canc = new File("Cancioness/" + cancion + ".wav");
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(canc));
			clip.start();
			Thread.sleep(1000);//Si no se pone, hay veces que se acaba el programa antes de que se reproduzca el audio
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listarCanciones() {
		try(Socket s = new Socket(this.host, this.puerto);
				DataOutputStream dos = new DataOutputStream(s.getOutputStream())){
			dos.writeBytes("2\r\n");
			String leidos;
			try(BufferedReader recibido = new BufferedReader(new InputStreamReader(s.getInputStream()))/*;FileOutputStream nuevo = new FileOutputStream("listadoCanciones.txt");*/){
				while ((leidos = recibido.readLine()) != null) {
					/*nuevo.write(buff, 0, numBytesLeidos);*/
					this.listadoCanciones.add(leidos);
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
	
	public void parar() {
		clip.stop();
	}
	public void reproduce() {
		clip.start();
	}
	public boolean estaReproduciendo() {
		return clip.isRunning();
	}
	public void cierraClip() {
		clip.close();
	}

}
