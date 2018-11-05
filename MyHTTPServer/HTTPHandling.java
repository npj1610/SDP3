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
public class HTTPHandling {
    public static String error405 () {
        return "HTTP/1.1 405 Method Not Allowed\r\n"
               +"Connection: close\r\n"
               +"Content-Type: text/plain\r\n"
               +"Content-Length: 20\r\n"
               +"Allow: GET\r\n"
               +"Server: Practica 3 SD\r\n\r\n"
               +"Method Not Allowed.\n"
                ;
    }
    
    
    public static String error503 () {
        return "HTTP/1.1 503 Service Unavailable\r\n"
               +"Connection: close\r\n"
               +"Content-Type: text/plain\r\n"
               +"Content-Length: 45\r\n"
               +"Server: Practica 3 SD\r\n\r\n"
               +"Service unavailable. Please try again later.\n"
                ;
    }
    
    
    public static String error404 () {
        return "HTTP/1.1 404 Not Found\r\n"
               +"Connection: close\r\n"
               +"Content-Type: text/plain\r\n"
               +"Content-Length: 16\r\n"
               +"Server: Practica 3 SD\r\n\r\n"
               +"File not found.\n"
                ;
    }
    
    public static String error400 () {
        return "HTTP/1.1 400 Bad Request\r\n"
               +"Connection: close\r\n"
               +"Content-Type: text/plain\r\n"
               +"Content-Length: 13\r\n"
               +"Server: Practica 3 SD\r\n\r\n"
               +"Bad Request.\n"
                ;
    }
    
    public static String error415 () {
        return "HTTP/1.1 415 Unsupported Media Type\r\n"
               +"Connection: close\r\n"
               +"Content-Type: text/plain\r\n"
               +"Content-Length: 24\r\n"
               +"Server: Practica 3 SD\r\n\r\n"
               +"Unsupported Media Type.\n"
                ;
    }

    public static String HTTPFormatText(String message) {
        return
                "HTTP/1.1 200 OK\r\n"
            +   "Connection: close\r\n"
            +   "Content-Length: " + message.length() + "\r\n"
            +   "Content-Type: text/html; charset=utf-8\r\n"
            +   "Server: Practica 3 SD\r\n\r\n"
            +   message;
    }
    
    
}
