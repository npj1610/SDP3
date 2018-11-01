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
    
    Semaphore threads;
    Socket connection;
    File carpeta;
    

    
    
    public ServerThread (Semaphore sempahore, Socket socket, File carpeta) {
        threads = sempahore;
        connection = socket;
        this.carpeta = carpeta;
    }
    
    private void delegateRequest (String file) {
        if (file.matches("/controladorSD/.*")) {
            DynamicRecurses.sendRequest(connection, file.substring(15));
        } else {
            StaticRecurses.sendFile(connection, carpeta.toString(), file);
        }
    }
    
    private void readRequest (String request) {
        String[] lines = request.split("\n");
        String[] stateLine = lines[0].split(" ");
        System.out.println(lines[0]);
        if(!stateLine[0].equals("GET")) {
            SocketHandling.escribeSocket(connection, ErrorHandling.error405());
        } else {
            delegateRequest(stateLine[1]);
        }
        
    }
    
    @Override
    public void run() {
        String request = SocketHandling.leeSocket(connection);
        //System.out.println(request);
        readRequest(request);
        threads.release();
    }
}
