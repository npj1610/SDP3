package MyHTTPServer;

import java.io.File;


class Server {
    
    private final static int DEF_MAX_CONEXIONES = 10;
    private final static String DEF_CARPETA = "http";
    private final static String CURRENT_FOLDER = System.getProperty("user.dir");
    
    private static void usage() {
        System.err.println("Usage:\n");
        System.err.println("java MyHTTPServer.Server PortNumber [Folder] [MaxConnections]\n");
        System.err.println("\tFolder default value: "+DEF_CARPETA);
        System.err.println("\tMaxConnections default value: "+DEF_MAX_CONEXIONES);
    }
    
    public static void main(String[] args){
        int puerto;
        File carpeta;
        int max_conexiones = DEF_MAX_CONEXIONES;
        
        try {
            puerto = Integer.parseInt(args[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid arguments.\n");
            usage();
            return;
        }
        
        if (puerto < 0) {
            System.err.println("Invalid port number.");
            return;
        }
        
        carpeta = new File(CURRENT_FOLDER+"/"+(1 < args.length?args[1]:DEF_CARPETA));
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.err.println("Folder "+carpeta+" does not exist.");
            return;
        }
        
        if (2 < args.length) {
            try {
                max_conexiones = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println(e);
                usage();
                return;
            }
        }
        
        if (max_conexiones < 0) {
            System.err.println("Invalid number of conections.");
            return;
        }
        
        System.out.println("Puerto: "+puerto);
        System.out.println("Carpeta: "+carpeta);
        System.out.println("Conexiones: "+max_conexiones);
        ServerConcurrent server = new ServerConcurrent(puerto, carpeta, max_conexiones);
        server.start();
    }
}