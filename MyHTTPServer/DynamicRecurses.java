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
import java.net.BindException;

/**
 *
 * @author Niko
 */
public class DynamicRecurses {
    
    private static void readResponse(Socket connection, String response) {
        String code = response.substring(0,3);
        switch (code) {
            case "200":
                SocketHandling.escribeSocket(connection, HTTPHandling.HTTPFormat(response.substring(3)));
                break;
                
            case "400":
                SocketHandling.escribeSocket(connection, HTTPHandling.error400());
                break;
            
            case "404":
                SocketHandling.escribeSocket(connection, HTTPHandling.error404());
                break;
            
            case "503":
                SocketHandling.escribeSocket(connection, HTTPHandling.error503());
                break;
                
            default:
                SocketHandling.escribeSocket(connection, HTTPHandling.error404());
                break;
                
        }
    }
    
    public static void sendRequest(Socket connection, String controller, int controllerPort, String request) {
        System.out.println(request);
        
        String[] tokens1 = request.split("\\?");
        
        String message = tokens1[0]+"\n";
        
        String[] tokens2 = tokens1[1].split("&");
        
        for(String token : tokens2) {
            message += token.split("=")[0] + "\n" + token.split("=")[1] + "\n";
        }
        
        try (Socket controlador = new Socket(controller, controllerPort)) {
            SocketHandling.escribeSocket(controlador, message);
            System.out.println(message);
            readResponse(connection, SocketHandling.leeSocket(controlador));
        } catch (BindException e) {
            System.err.println(e);
            System.err.println("(Is other app using any of your ports? Are you using a port under 1024 without root privileges?)");
        } catch (UnknownHostException e) {
            System.err.println("Controlador no encontrado.");
            SocketHandling.escribeSocket(connection, HTTPHandling.error404());
        } catch (IOException e) {
            System.err.println("Error contactando con controlador.");
            SocketHandling.escribeSocket(connection, HTTPHandling.error404());
        }
        
    }
    
}
