/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import Common.SocketHandling;
import java.net.Socket;

/**
 *
 * @author Niko
 */
public class DynamicRecurses {
    
    public static void sendRequest(Socket connection, String request) {
        System.out.println(request);
        SocketHandling.escribeSocket(connection, ErrorHandling.error404());
    }
    
}
