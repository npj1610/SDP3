/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import Common.SocketHandling;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Niko
 */
public class StaticRecurses {
    
    public static void sendFile (Socket connection, String folder, String file) {
        try (FileReader in = new FileReader(folder+file)) {
            BufferedReader br = new BufferedReader(in);
            
            String temp;
            String out = "";
            while ((temp = br.readLine()) != null) {
                out += temp;
            }
            SocketHandling.escribeSocket(connection, HTTPHandling.HTTPFormat(out));
        } catch (FileNotFoundException e) {
            SocketHandling.escribeSocket(connection, HTTPHandling.error404());
        } catch (IOException e) {
            System.err.println(e);
        }
        
    }
    
}
