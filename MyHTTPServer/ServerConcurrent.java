/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Niko
 */
public class ServerConcurrent {
    
    private final int puerto;
    private final File carpeta;
    private final int max_conexiones;

    public ServerConcurrent(int port, File folder, int max_connections) {
        puerto = port;
        carpeta = folder;
        max_conexiones = max_connections;
    }
    
    public void start() {
        //Socket thingies
        try {
            ServerSocket socket = new ServerSocket(puerto);
            System.out.println("Escuchando en puerto "+puerto+"...");
            Semaphore threads = new Semaphore(max_conexiones);
            
            while(true) {
                Socket connection = socket.accept();
                if (threads.tryAcquire()) {
                    Thread t = new ServerThread(threads, connection, carpeta);
                    t.start();
                    System.out.println("Peticion servida");
                } else {
                    SocketHandling.escribeSocket(connection, ErrorHandling.error503());
                    System.out.println("Peticion rechazada");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
