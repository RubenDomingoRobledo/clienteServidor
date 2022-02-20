package EjemploUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
	public static void main(String argv[]) throws Exception {
		// Creacion socket en el puerto 2009
		DatagramSocket serverSocket = new DatagramSocket(2109);
		byte[] recibirDatos = new byte[1024];
		byte[] mandarDatos = new byte[1024];
		System.out.println("Creado socket de servidor en puerto " + serverSocket.getLocalPort()
				+ ". Esperando llegada de paquetes.\n");

		while (true) {
			// Creamos espacio para recibir el paquete
			DatagramPacket paqueteRecibido = new DatagramPacket(recibirDatos, recibirDatos.length);

			// Recibimos datagrama
			serverSocket.receive(paqueteRecibido);
			String frase = new String(paqueteRecibido.getData());
			System.out.println("SERVER fraseCliente: " + frase);

			// Obtenemos direccion IP, y numero de puerto del envio
			InetAddress IPAddress = paqueteRecibido.getAddress();
			int port = paqueteRecibido.getPort();
			String fraseModificada = frase.toUpperCase();
			System.out.println("SERVER fraseModificada: " + fraseModificada);
			mandarDatos = fraseModificada.getBytes();

			// Creamos datagrama para mandarselo al cliente
			DatagramPacket sendPacket = new DatagramPacket(mandarDatos, mandarDatos.length, IPAddress, port);

			// Enviamos datgrama
			serverSocket.send(sendPacket);

			// Cerramos bucle y esperamos a que se conecte otro cliente
		}
	}
}
