package MyHTTPServer;

import java.io.File;


class Main {
    
    private final static int DEF_MAX_CONNECTIONS = 10;
    private final static String DEF_FOLDER = "http";
    private final static String CURRENT_FOLDER = System.getProperty("user.dir");
    
    private static void usage() {
        System.err.println("Usage:\n");
        System.err.println("java MyHTTPServer.Main PortNumber ControllerIP ControllerPort [MaxConnections]\n");
        System.err.println("\tMaxConnections default value: "+DEF_MAX_CONNECTIONS+"\n");
    }
    
    public static void main(String[] args){
        int port;
        String controller;
        int controllerPort;
        File folder;
        int max_connections = DEF_MAX_CONNECTIONS;
        
        //Set server port, controller IP and controller port
        try {
            port = Integer.parseInt(args[0]);
            controller = args[1];
            controllerPort = Integer.parseInt(args[2]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid arguments.\n");
            usage();
            return;
        }
        
        if (port < 0 || controllerPort<0) {
            System.err.println("Invalid port number.\n");
            return;
        }
        
        //Set server folder
        folder = new File(CURRENT_FOLDER+"/"+DEF_FOLDER);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Folder "+folder+" does not exist.\n");
            return;
        }
        
        //Set maximum connection number
        if (3 < args.length) {
            try {
                max_connections = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.err.println(e+"\n");
                usage();
                return;
            }
        }
        
        if (max_connections < 0) {
            System.err.println("Invalid number of conections.\n");
            return;
        }
        
        //Print info
        System.out.println("Server Port: "+port);
        System.out.println("Controller IP: "+controller);
        System.out.println("Controller Port: "+controllerPort);
        System.out.println("Folder: "+folder);
        System.out.println("Maximum Connection Number: "+max_connections+"\n");
        
        //Start server
        ServerConcurrent server = new ServerConcurrent(port, controller, controllerPort, folder.toString(), max_connections);
        server.start();
    }
}