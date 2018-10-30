/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
    
    @Override
    public void run() {
        System.out.println(SocketHandling.leeSocket(connection));
        System.out.println("Stage2");
        SocketHandling.escribeSocket(connection,
                "HTTP/1.1 200 OK\n"
               +"Connection: close\n"
               +"Content-Length: 4\n"
               +"Content-Type: text/html; charset=utf-8\n"
               +"Server: Practica 3 SD\n\n"
               +"hola"
        );
        System.out.println("Stage3");
        threads.release();
    }
}
