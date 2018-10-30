/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

/**
 *
 * @author Niko
 */
public class ErrorHandling {
    public static String error405 () {
        return "HTTP/1.1 405 Method Not Allowed\n"
               +"Connection: close\n"
               +"Content-Type: text/plain"
               +"Content-Length: 19"
               +"Allow: GET\n"
               +"Server: Practica 3 SD\n\n"
               +"Method Not Allowed."
                ;
    }
    
    
    public static String error503 () {
        return "HTTP/1.1 503 Service Unavailable\n"
               +"Connection: close\n"
               +"Content-Type: text/plain"
               +"Content-Length: 62"
               +"Server: Practica 3 SD\n\n"
               +"Maximum number of connections reached. Please try again later."
                ;
    }
    
}
