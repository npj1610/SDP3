/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registrator;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Niko
 */
public class Main {

    private final static int DEF_PORT = Registry.REGISTRY_PORT;
    
    private static void usage() {
        System.err.println("Usage:\n");
        System.err.println("java Registrator.Main ExternalRegistryIP [RegistryPort]\n");
        System.err.println("\tRegistryPort default value: "+DEF_PORT+"\n");
    }
    
    public static void main(String[] args) {
        int port = DEF_PORT;
        String ip;
        
        try {
            ip = args[0];
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Invalid arguments.");
            usage();
            return;
        }
        
        try {
            port = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e) {
        } catch (NumberFormatException e) {
            System.err.println("Invalid arguments.");
            usage();
            return;
        }
        
        System.setProperty("java.rmi.server.hostname", ip);
        
        System.out.println("External registry IP: "+ip);
        System.out.println("Puerto: "+port+"\n");
        
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            System.setSecurityManager(new RMISecurityManager());
            registry.rebind("/Registrator", new Registrator(registry));
            System.out.println("Servidor de objeto preparado.\n");
        } catch (RemoteException e) {
            System.err.println("Error connecting with registry:");
            System.err.println(e+"\n");
        }
    }
    
}
