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
public interface RMIStationInterface extends Remote{
    public String getName() throws RemoteException;
    public int getTemperatura() throws RemoteException;
    public int getHumedad() throws RemoteException;
    public int getLuminosidad() throws RemoteException;
    public String getPantalla() throws RemoteException;
    public boolean setTemperatura(int temperatura) throws RemoteException;
    public boolean setHumedad(int humedad) throws RemoteException;
    public boolean setLuminosidad(int luminosidad) throws RemoteException;
    public boolean setPantalla(String pantalla) throws RemoteException;
}
