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
import java.util.ArrayList;

/**
 *
 * @author Niko
 */
public class StaticRecurses {
    
    public static final ArrayList<String> TEXT = new ArrayList<String>() {{add("txt");add("html");}};
    public static final ArrayList<String> IMAGE = new ArrayList<String>() {{add("ico");add("png");add("jpg");}};
    
    private static void sendTextFile (Socket connection, FileReader in) throws IOException {
        BufferedReader br = new BufferedReader(in);

        String temp;
        String out = "";
        while ((temp = br.readLine()) != null) {
            out += temp;
        }

        SocketHandling.escribeSocket(connection, HTTPHandling.HTTPFormatText(out));
    }
    
    public static void sendFile (Socket connection, String folder, String file) {
        try (FileReader in = new FileReader(folder+file)) {
            String[] tokens = file.split("\\.");
            
            if (TEXT.contains(tokens[tokens.length-1])) {
                sendTextFile(connection, in);
            } else {
                SocketHandling.escribeSocket(connection, HTTPHandling.error415());
            }
            
        } catch (FileNotFoundException e) {
            SocketHandling.escribeSocket(connection, HTTPHandling.error404());
        } catch (IOException e) {
            System.err.println(e+"\n");
            SocketHandling.escribeSocket(connection, HTTPHandling.error404());
        }
        
    }
    
}
