/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import Common.SocketHandling;
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
    private final String controlador;
    private final int puertoControlador;
    private final File carpeta;
    private final int max_conexiones;

    public ServerConcurrent(int port, String controller, int controllerPort, File folder, int max_connections) {
        puerto = port;
        carpeta = folder;
        max_conexiones = max_connections;
        controlador = controller;
        puertoControlador = controllerPort;
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
                    Thread t = new ServerThread(threads, connection, controlador, puertoControlador, carpeta);
                    t.start();
                    System.out.println("\nPeticion servida");
                } else {
                    SocketHandling.escribeSocket(connection, HTTPHandling.error503());
                    System.out.println("Peticion rechazada");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
