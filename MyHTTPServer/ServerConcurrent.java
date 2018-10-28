/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import java.io.File;

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
}
