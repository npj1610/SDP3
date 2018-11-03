package RMIStation;

import Common.RMIStationInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIStation extends UnicastRemoteObject implements Serializable, RMIStationInterface {
    
    String name;
    File station;
    int temperatura = 0;
    int humedad = 0;
    int luminosidad = 0;
    String pantalla = "";
    
    private void actualizar() {
          try (FileReader in = new FileReader(station)) {
            BufferedReader br = new BufferedReader(in);
            
            String temp;
            String out = "";
            while ((temp = br.readLine()) != null) {
                out += temp+"\n";
            }
            String[] tokens = out.split("\n");
            temperatura = Integer.parseInt(tokens[0].substring(12));
            humedad = Integer.parseInt(tokens[1].substring(8));
            luminosidad = Integer.parseInt(tokens[2].substring(12));
            pantalla = tokens[3].substring(9);
            //In case the pantalla string has \n chars in it
            for(int i=4; i<tokens.length; i++) {
                pantalla += "\n"+tokens[i];
            }
        } catch (IOException e) {
            System.err.println("Error accesing the data");
            System.err.println(e);
        }
    }
    
    public RMIStation(String string, File file) throws RemoteException {
        name = string;
        station = file;
        
    }
    
    @Override
    public int getTemperatura() throws RemoteException {
        actualizar();
        return temperatura;
    }
    
    @Override
    public int getHumedad() throws RemoteException {
        actualizar();
        return humedad;
    }
    
    @Override
    public int getLuminosidad() throws RemoteException {
        actualizar();
        return luminosidad;
    }
    
    @Override
    public String getPantalla() throws RemoteException {
        actualizar();
        return pantalla;
    }
    
    @Override
    public boolean setTemperatura(int temperatura) throws RemoteException {
        actualizar();
        this.temperatura = temperatura;
        return escribir();
    }
    
    @Override
    public boolean setHumedad(int humedad) throws RemoteException {
        actualizar();
        this.humedad = humedad;
        return escribir();
    }
    
    @Override
    public boolean setLuminosidad(int luminosidad) throws RemoteException {
        actualizar();
        this.luminosidad = luminosidad;
        return escribir();
    }
    
    @Override
    public boolean setPantalla(String pantalla) throws RemoteException {
        actualizar();
        this.pantalla = pantalla;
        return escribir();
    }
    
    private boolean escribir() {
        boolean out = true;
        if(station.delete()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(station.toString()));) {
                
                writer.write(
                        "Temperatura="+temperatura+"\r\n" +
                        "Humedad="+humedad+"\r\n" +
                        "Luminosidad="+luminosidad+"\r\n" +
                        "Pantalla="+pantalla
                );
                writer.close();
                
            } catch (IOException e) {
                System.err.println("Error trying to change file:");
                System.err.println(e);
                out = false;
            }
        } else {
            out = false;
        }
        return out;
    }
    
    @Override
    public String getName() throws RemoteException {
        return name;
    }
}