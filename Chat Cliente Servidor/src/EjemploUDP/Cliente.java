package EjemploUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
	public static void main(String argv[]) throws Exception {
		String frase;
		String fraseModificada;
		DatagramSocket clientSocket;
		
		for(int i=1; i<=100; i++) {
			
			// Creamos un socket cliente en el puerto 5008 y el host
			clientSocket = new DatagramSocket();
			
			// Traducimos nombre del host a direccion IP usando servidor DNS
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] mandarDatos = new byte[1024];
			byte[] recibirDatos = new byte[1024];
			
			frase = "Hola";
			System.out.println("CLIENTE: Palabra recogida por teclado: " + frase);
			mandarDatos = frase.getBytes();
			
			//Creamos datagrama con los datos, longitud de datos, IP, puerto
			DatagramPacket mandarPaquete = new DatagramPacket(mandarDatos, mandarDatos.length, IPAddress, 2109);
			
			// Mandamos el datagrama al servidor
			clientSocket.send(mandarPaquete);
			DatagramPacket receivePacket = new DatagramPacket(recibirDatos, recibirDatos.length);
			
			// Leemos el datagrama del servidor
			clientSocket.receive(receivePacket);
			fraseModificada = new String(receivePacket.getData());
			System.out.println("Palabra recibida del Servidor: " + fraseModificada+ "\n");		
		}
	}
}
