/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Niko
 */
public interface RegistratorInterface extends Remote {
    public boolean registrar(RMIStationInterface RMIStation) throws RemoteException;
    public void setIP(String ipRegistry) throws RemoteException;
    //Falta devolver todas las estaciones
}
