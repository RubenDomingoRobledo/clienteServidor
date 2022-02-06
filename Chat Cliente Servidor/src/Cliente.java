
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private Socket socketCliente;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;
    private boolean opcion = true;
    private Scanner sc;
    
    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.conexion(5555, "localhost");
    }
    
    public void conexion(int numeroPuerto, String ipMaquina) {
        try {
            socketCliente = new Socket(ipMaquina, numeroPuerto);
            Thread hilo1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (opcion) {
                        escucharDatos(socketCliente);
                        System.out.print("CLIENTE: ");
                    }
                }
            });
            hilo1.start();
            
            while (opcion) {
                sc = new Scanner(System.in);
                String escribir = sc.nextLine();
                if (!escribir.equals("SERVIDOR: fin")) {
                    enviarDatos("CLIENTE: " + escribir);
                } 
                else {
                    opcion = false;
                    cerrarTodo();
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Error al abrir los sockets de cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void escucharDatos(Socket socket) {
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            System.out.println(entradaDatos.readUTF());
        } 
        catch (IOException e) {
        	System.out.println("Error de entrada o de salida de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void enviarDatos(String datos) {
        try {
            salidaDatos = new DataOutputStream(socketCliente.getOutputStream());
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
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
            socketCliente.close();
            sc.close();
        } 
        catch (IOException e) {
        	System.out.println("Error de entrada o de salida de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}