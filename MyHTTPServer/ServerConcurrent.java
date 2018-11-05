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
import java.net.BindException;

/**
 *
 * @author Niko
 */
public class ServerConcurrent {
    
    private final int port;
    private final String controller;
    private final int controllerPort;
    private final String folder;
    private final int max_connections;

    public ServerConcurrent(int puerto, String controlador, int puertoControlador, String carpeta, int max_conexiones) {
        port = puerto;
        folder = carpeta;
        max_connections = max_conexiones;
        controller = controlador;
        controllerPort = puertoControlador;
    }
    
    public void start() {
        try {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("Listening on port "+port+"..."+"\n");
            Semaphore threads = new Semaphore(max_connections);
            
            while(true) {
                Socket connection = socket.accept();
                if (threads.tryAcquire()) {
                    //If a new connection is available, it creates one
                    Thread thread = new ServerThread(threads, connection, controller, controllerPort, folder);
                    thread.start();
                    System.out.println("Request served\n");
                } else {
                    //If not, it rejects the request
                    SocketHandling.escribeSocket(connection, HTTPHandling.error503());
                    System.out.println("Request rejected\n");
                }
            }
        } catch (BindException e) {
            System.err.println(e);
            System.err.println("(Is other app using any of your ports? Are you using a port under 1024 without root privileges?)\n");
        } catch (IOException e) {
            System.err.println(e+"\n");
        }
    }
}
