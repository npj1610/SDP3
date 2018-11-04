/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIStation;

import Common.RMIStationInterface;
import Common.RegistratorInterface;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Niko
 */
public class Main {
    
    private final static String CURRENT_FOLDER = System.getProperty("user.dir");
        
    private static void usage() {
        System.err.println("Usage:\n");
        System.err.println("java RMIStation.Main StationFile Registry RegistryPort\n");
    }
    
    public static void main(String[] args) {
        //Argument processing
        File station;
        String nombre;
        String registro;
        int puerto;
        
        try {
            station = new File(CURRENT_FOLDER+"/"+args[0]);
            registro = args[1];
            puerto = Integer.parseInt(args[2]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid arguments.\n");
            usage();
            return;
        }
        
        if (puerto < 0) {
            System.err.println("Invalid port number.");
            return;
        }
        
        if (!station.exists() || station.isDirectory()) {
            System.err.println("File "+station+" does not exist.");
            return;
        }
        
        String[] tokens1 = station.toString().split("/");
        String[] tokens2 = tokens1[tokens1.length-1].split("\\\\");
        nombre = tokens2[tokens2.length-1].split("\\.")[0];
        
        System.out.println("Archivo: "+station);
        System.out.println("Nombre: "+nombre);
        System.out.println("Registro: "+registro);
        System.out.println("Puerto registro: "+puerto);
        
        //Connect with registrator
        System.setSecurityManager(new RMISecurityManager());
        Registry registry;
        RegistratorInterface registrator;
        
        try {
            registry = LocateRegistry.getRegistry(registro, puerto);
        } catch (RemoteException e) {
            System.err.println("Error connecting with registry:");
            System.err.println(e);
            return;
        }
        
        try {
            registrator = (RegistratorInterface) registry.lookup("/Registrator");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error connecting with registrator:");
            System.err.println(e);
            return;
        }
        
        //Check if already bound
        try {
            registry.lookup("/"+nombre);
            System.err.println("Name "+nombre+" already bound.");
            return;
        } catch (RemoteException | NotBoundException e) {}
        
        //Register RMI
        try {
            RMIStationInterface rmiStation = new RMIStation(nombre, station);
            if(!registrator.registrar(registro, rmiStation)) {
                throw new RemoteException("Registrator returned false.");
            }
        } catch (RemoteException e) {
            System.err.println("Error connecting with registrator:");
            System.err.println(e);
            return;
        }
        
        System.err.println("MATAME");
    }
    
}
