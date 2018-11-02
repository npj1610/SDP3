package Controller;

public class Main {
    private final static int DEF_MAX_CONEXIONES = 10;
    
    private static void usage() {
        System.err.println("Usage:\n");
        System.err.println("java Controller.Main PortNumber RegistryIP RegistryPort [MaxConnections]\n");
        System.err.println("\tMaxConnections default value: "+DEF_MAX_CONEXIONES);
    }
    
    public static void main(String[] args){
        int puerto;
        String registro;
        int puertoRegistro;
        int max_conexiones = DEF_MAX_CONEXIONES;
        
        try {
            puerto = Integer.parseInt(args[0]);
            registro = args[1];
            puertoRegistro = Integer.parseInt(args[2]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Invalid arguments.\n");
            usage();
            return;
        }
        
        if (puerto < 0 || puertoRegistro<0) {
            System.err.println("Invalid port number.");
            return;
        }
        
        if (3 < args.length) {
            try {
                max_conexiones = Integer.parseInt(args[3]);
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
        System.out.println("Controlador: "+registro);
        System.out.println("Puero controlador: "+puertoRegistro);
        System.out.println("Conexiones: "+max_conexiones);
        ControllerConcurrent server = new ControllerConcurrent(puerto, registro, puertoRegistro, max_conexiones);
        server.start();
    }
}