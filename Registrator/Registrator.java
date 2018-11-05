/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Registrator;

import Common.RegistratorInterface;
import Common.RMIStationInterface;
import java.io.Serializable;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Niko
 */

//Registry registry = LocateRegistry.getRegistry(ip, Registry.REGISTRY_PORT);
public class Registrator extends UnicastRemoteObject implements Serializable, RegistratorInterface {
        
    //Falta almacenar y cargar todas las estaciones
    
    Registry registry;
    
    public Registrator(Registry registro) throws RemoteException {
        registry = registro;
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
        return true;
    }
}
