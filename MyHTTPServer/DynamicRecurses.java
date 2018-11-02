/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import Common.SocketHandling;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

/**
 *
 * @author Niko
 */
public class DynamicRecurses {
    
    public static void sendRequest(Socket connection, String controller, int controllerPort, String request) {
        System.out.println(request);
        
        String[] datos = request.split("\\?");
        
        String message = datos[0]+"\n";
        datos = datos[1].split("&");
        for(int i=1; i<datos.length; i++) {
            message += datos[i].split("=")[0] + "\n" + datos[i].split("=")[1] + "\n";
        }
        
        try (Socket controlador = new Socket(controller, controllerPort)) {
            SocketHandling.escribeSocket(controlador, message);
        } catch (UnknownHostException e) {
            System.err.println("Controlador no encontrado.");
            SocketHandling.escribeSocket(connection, ErrorHandling.error404());
        } catch (IOException e) {
            System.err.println("Error contactando con controlador.");
            SocketHandling.escribeSocket(connection, ErrorHandling.error404());
        }
        
    }
    
}
