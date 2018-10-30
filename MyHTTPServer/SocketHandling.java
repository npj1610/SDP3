/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyHTTPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Niko
 */
public class SocketHandling {
        public static String leeSocket(Socket connection) {
        try {
            
            String result = "";
            while(result.equals("")) {
                InputStream is = connection.getInputStream();
                while(is.available() != 0) {
                    result += (char) is.read();
                }
            }
            return result;
        } catch (IOException e) {
            System.err.println(e);
            return "";
        }
   }

   public static void escribeSocket (Socket connection, String datos) {
        try {
            OutputStream os = connection.getOutputStream();
            os.write(datos.getBytes(StandardCharsets.US_ASCII));
            os.flush();
        } catch (IOException e) {
           System.err.println(e);
        }
    }
}
