package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    private Socket socketServidor;
    private ServerSocket socketConexion;
    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;
    private boolean opcion = true;
    private Scanner scanner;

    public static void main(String[] args) {
        Servidor serv = new Servidor();
        serv.conexion(5555);
    }

    public void conexion(int numeroPuerto) {
        try {
            socketConexion = new ServerSocket(numeroPuerto);
            System.out.println("El servidor se esta escuchando en el puerto: " + numeroPuerto);
            socketServidor = socketConexion.accept();
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (opcion) {
                        System.out.print("SERVIDOR: ");
                        recibirDatos();
                    }
                }
            });
            hilo.start();
            
            while (opcion) {
                scanner = new Scanner(System.in);
                String escribir = scanner.nextLine();
                if (!escribir.equals("CLIENTE: fin")) {
                    enviarDatos("SERVIDOR: " + escribir);
                } else {
                    opcion = false;
                    cerrarTodo();
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Error al abrir los sockets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void enviarDatos(String datos) {
        try {
            salidaDatos = new DataOutputStream(socketServidor.getOutputStream());
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
        } 
        catch (IOException e) {
        	System.out.println("Error de entrada o de salida de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void recibirDatos() {
        try {
            entradaDatos = new DataInputStream(socketServidor.getInputStream());
            System.out.println(entradaDatos.readUTF());
        } 
        catch (IOException e) {
        	System.out.println("Error de entrada o de salida de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cerrarTodo() {
        try {
            salidaDatos.close();
            entradaDatos.close();
            socketConexion.close();
            socketServidor.close();
        } 
        catch (IOException e) {
        	System.out.println("Error de entrada o de salida de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
