/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Common.SocketHandling;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Niko
 */
public class ControllerConcurrent {
    
    private final int puerto;
    private final String registro;
    private final int puertoRegistro;
    private final int max_conexiones;
    
    public ControllerConcurrent(int port, String registry, int registryPort, int max_connections) {
        puerto = port;
        registro = registry;
        puertoRegistro = registryPort;
        max_conexiones = max_connections;
    }
    
    public void start() {
        try {
            ServerSocket socket = new ServerSocket(puerto);
            System.out.println("Escuchando en puerto "+puerto+"...");
            Semaphore threads = new Semaphore(max_conexiones);
            
            while(true) {
                Socket connection = socket.accept();
                if (threads.tryAcquire()) {
                    Thread t = new ControllerThread(threads, connection, registro, puertoRegistro);
                    t.start();
                    System.out.println("\nPeticion servida");
                } else {
                    SocketHandling.escribeSocket(connection, "503");
                    System.out.println("Peticion rechazada");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
