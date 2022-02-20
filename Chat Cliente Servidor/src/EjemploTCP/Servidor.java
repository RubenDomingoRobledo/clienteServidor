package EjemploTCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String argv[]) throws Exception {
		String frase;
		String fraseModificada;
		
		// Creacion socket en el puerto 2009
		ServerSocket serverSocket = new ServerSocket(5008);
		System.out.println("Creado socket de servidor en puerto " + serverSocket.getLocalPort() + ". Esperando conexiones de clientes.\n");
		
		while (true) {
			// Esperamos hasta que llegue un cliente y le aceptamos cuando llega
			Socket connectionSocket = serverSocket.accept();
			System.out.println("Cliente conectado desde " + connectionSocket.getRemoteSocketAddress());
			
			// Creamos un input stream y output stream en el socket
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			// Leemos linea del cliente y la modificamos
			frase = inFromClient.readLine();
			System.out.println("SERVER fraseCliente: " + frase);
			fraseModificada = frase.toUpperCase() + '\n';
			System.out.println("SERVER fraseModificada: " + fraseModificada);
			
			// Mandamos la linea modificada al cliente
			outToClient.writeBytes(fraseModificada);
			
			// Cerramos bucle y esperamos a que se conecte otro cliente
		}
	}
}
