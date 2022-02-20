package EjemploTCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
	public static void main(String argv[]) throws Exception {
		String frase;
		String fraseModificada;
		Socket clientSocket;
		
		for(int i=1; i<=100; i++) {
			
			// Creamos un socket cliente en el puerto 5008 y el host
			clientSocket = new Socket("localhost", 5008);
			
			// Creamos un input stream y output stream en el socket
			//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			//Leemos y mandamos la frase del usuario
			frase = "Hola";
			System.out.println("CLIENTE: Palabra recogida por teclado: " + frase);
			outToServer.writeBytes(frase + '\n');
			
			// Creamos un input stream para obtener informacion del socket servidor
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			// Leemos linea del servidor
			fraseModificada = inFromServer.readLine();

			System.out.println("Palabra recibida del Servidor: " + fraseModificada+ "\n");
		}
	}
}
