/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Common.RMIStationInterface;
import Common.RegistratorInterface;
import Common.SocketHandling;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Niko
 */
public class ControllerThread extends Thread {
    
    private final Semaphore threads;
    private final Socket connection;
    private final String registro;
    private final int puertoRegistro;
    private final ArrayList<String> nonStation = new ArrayList<String>() {{add("index.html");}};
    
    public ControllerThread(Semaphore sempahore, Socket socket, String registry, int registryPort) {
        threads = sempahore;
        connection = socket;
        registro = registry;
        puertoRegistro = registryPort;
    }
    
    private String askStation(RMIStationInterface station, String attribute, String value, boolean write) {
        try {
            //Generate HTML code.
            switch(attribute) {
                case "temperatura":
                    if (write) {
                        if(station.setTemperatura(Integer.parseInt(value))) {
                            return "200";
                        } else {
                            return "503";
                        }
                    } else {
                        return "200"+station.getTemperatura();
                    }
                case "humedad":
                    if (write) {
                        if(station.setHumedad(Integer.parseInt(value))) {
                            return "200";
                        } else {
                            return "503";
                        }
                    } else {
                        return "200"+station.getHumedad();
                    }
                case "luminosidad":
                    if (write) {
                        if(station.setLuminosidad(Integer.parseInt(value))) {
                            return "200";
                        } else {
                            return "503";
                        }
                    } else {
                        return "200"+station.getLuminosidad();
                    }
                case "pantalla":
                    if (write) {
                        if(station.setPantalla(URLDecoder.decode(value, StandardCharsets.US_ASCII.name()))) {
                            return "200";
                        } else {
                            return "503";
                        }
                    } else {
                        return "200"+station.getPantalla();
                    }
                default:
                    return "400";
            }
        } catch (RemoteException | UnsupportedEncodingException e) {
            System.err.println("Error communicating with station:");
            System.err.println(e+"\n");
            return "404";
        }
    }
    
    private String contactStation(String attribute, int stationNumber, String value, boolean write) {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry;
        RMIStationInterface station;
        
        try {
            registry = LocateRegistry.getRegistry(registro, puertoRegistro);
        } catch (RemoteException e) {
            System.err.println("Error connecting with registry:");
            System.err.println(e+"\n");
            return "503";
        }
        
        try {
            station = (RMIStationInterface) registry.lookup("/estacion"+stationNumber);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error connecting with station:");
            System.err.println(e+"\n");
            return "404";
        }
        
        return askStation(station, attribute, value, write);
    }
    
    private String askIndex() {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry;
        RegistratorInterface registrator;
        String out;
        
        try {
            registry = LocateRegistry.getRegistry(registro, puertoRegistro);
        } catch (RemoteException e) {
            System.err.println("Error connecting with registry:");
            System.err.println(e+"\n");
            return "503";
        }
        
        try {
            registrator = (RegistratorInterface) registry.lookup("/Registrator");
            out = registrator.listRMIs();
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Error connecting with Registrator:");
            System.err.println(e+"\n");
            e.printStackTrace();
            return "404";
        }
        
        return "200"+out;
    }
    
    private String callRMI(String attribute, int station, String value, boolean write) {
        switch (attribute) {
            case "index.html": 
                return askIndex();
            default:
                return contactStation(attribute, station, value, write);
        }
    }
    
    private void readRequest(String request) {
        String[] tokens = request.split("\\n");
        String attribute = tokens[0];
        Integer station = null;
        String value = null;
        
        try {
            //nonStation options
            if (nonStation.contains(attribute)) {
                if (1 < tokens.length) {
                    throw new IllegalArgumentException();
                }
            } else {
                //Station options
                for(int i=1; i<tokens.length; i += 2) {
                    switch (tokens[i]) {
                        //Station argument
                        case "station":
                            if(station != null) {
                                throw new IllegalArgumentException();
                            } else {
                                station = Integer.parseInt(tokens[i+1]);
                            }
                            break;
                        //Value argument
                        case "value":
                            if(value != null) {
                                throw new IllegalArgumentException();
                            } else {
                                value = tokens[i+1];
                                if (!attribute.equals("pantalla")) {
                                    Integer.parseInt(value);
                                }
                            }
                            break;
                        //Unexpected arguments
                        default:
                            throw new IllegalArgumentException();
                    }
                }
                if (station == null) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (NumberFormatException e) {
            SocketHandling.escribeSocket(connection, "404");
            System.err.println("Atributo numerico mal formateado\n");
            return;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            SocketHandling.escribeSocket(connection, "400");
            return;
        }
        
        if (station == null) {
            station = 0;
        }
        
        boolean write = (value!=null);
        if (!write) {
            value = "";
        }
        
        SocketHandling.escribeSocket(connection, callRMI(attribute, station, value, write));
    }
    
    @Override
    public void run() {
        readRequest(SocketHandling.leeSocket(connection));
        threads.release();
    }
    
}
