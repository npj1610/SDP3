/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registrator;

import Common.RegistratorInterface;
import Common.RMIStationInterface;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Niko
 */

//Registry registry = LocateRegistry.getRegistry(ip, Registry.REGISTRY_PORT);
public class Registrator extends UnicastRemoteObject implements Serializable, RegistratorInterface {
        
    //Falta almacenar y cargar todas las estaciones
    
    Registry registry;
    ArrayList<RMIStationInterface> RMIStations;
    
    public Registrator(Registry registro) throws RemoteException {
        registry = registro;
        RMIStations = new ArrayList<>();
    }
    
    @Override
    public boolean registrar(RMIStationInterface RMIStation) throws RemoteException {
        String rmiName;
        try {
            System.setSecurityManager(new RMISecurityManager());
            rmiName = RMIStation.getName();
            registry.rebind("/"+rmiName, RMIStation);
        } catch (RemoteException e) {
            System.err.println(e+"\n");
            return false;
        }
        
        System.out.println("Registered: /"+rmiName+"\n");
        RMIStations.add(RMIStation);
        return true;
    }
    
    @Override
    public String listRMIs() throws RemoteException {
        String out = "";
        
        for(RMIStationInterface RMIStation : RMIStations) {
            out += RMIStation.getName() + "\n<br>\n";
        }
        
        return out;
    }
    
    @Override
    public void desregistrar(RMIStationInterface RMIStation) throws RemoteException {
        try {
            registry.unbind("/"+RMIStation.getName());
            RMIStations.remove(RMIStation);
        } catch (NotBoundException e) {
            System.err.println("Station not bound!\n");
        }
    }
}
