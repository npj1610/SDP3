/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import Common.SocketHandling;
import java.io.File;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Niko
 */
public class ServerThread extends Thread {
    
    private final Semaphore threads;
    private final Socket guestConnection;
    private final String controllerIP;
    private final int controllerPort;
    private final String folder;
    
    public ServerThread (Semaphore hilos, Socket conexionCliente, String ipControlador, int puertoControlador, String carpeta) {
        threads = hilos;
        guestConnection = conexionCliente;
        controllerIP = ipControlador;
        controllerPort = puertoControlador;
        folder = carpeta;
    }
    
    private void delegateGETRequest (String resource) {
        if (resource.matches("/controladorSD/.*")) {
            DynamicRecurses.sendRequest(guestConnection, controllerIP, controllerPort, resource.substring(15));
        } else {
            StaticRecurses.sendFile(guestConnection, folder, resource);
        }
    }
    
    private void readRequest (String request) {
        String[] lines = request.split("\n");
        String[] stateLine = lines[0].split(" ");
        
        //Show info
        System.out.println("Request: ");
        System.out.println(lines[0]+"\n");
        
        //Handle request type
        try {
            switch (stateLine[0]) {
                case "GET":
                    delegateGETRequest(stateLine[1]);
                    break;
                default:
                    SocketHandling.escribeSocket(guestConnection, HTTPHandling.error405());
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Bad request: wrong format.");
            System.err.println(e+"\n");
            SocketHandling.escribeSocket(guestConnection, HTTPHandling.error400());
        }
        
    }
    
    @Override
    public void run() {
        readRequest(SocketHandling.leeSocket(guestConnection));
        threads.release();
    }
}
