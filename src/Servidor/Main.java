package Servidor;

import com.sun.net.httpserver.HttpServer;
import tienda.Tienda;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * Clase principal que inicializa las distintas etapas del servidor
 * GNU GENERAL PUBLIC LICENSE
 * @author Oskar-C97
 */ 
public class Main {

    //Datos del servidor
    private static final String iplocal = "10.0.69.175";
    private static final int port = 80;
    private static final String rollocal = "tienda";
    //Datos monitor
    private static final String ipmonitor = "10.0.69.39";
    private static final int puertomonitor = 3000;
    private static final int idmonitor = 0; //Aunque monitor no tiene id, lo identificaremos como 0, para evitar que algun otro agente que no lo tenga contemplado y de error de ejecucion
    private static final String rolmonitor = "monitor";
    //Array donde almacenamos las tiendas que creamos
    private static ArrayList<Tienda> listaTiendas = new ArrayList<Tienda>();    
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException, InterruptedException {
        System.out.println("Iniciando simulación, asignando tiendas");
        // Para empezar, preparamos la emulación, indicando al monitor cuantas tiendas existen
        PrepareEmulation();
        System.out.println("ID asignados, esperando inicialización");
        //Una vez que estamos inicializados, ponemos a la escucha permanente el servidor HTTP
        Listening();
    }

    /**
     * -Metodo PrepareEmulation- Inicialización de N tiendas y asignacion de ID
     * 
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException 
     */
    private static void PrepareEmulation() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        //Instanciamos el objeto post, para poder crear y mandar el post de inicialización
        SendPOST post = new SendPOST();

        //Creacion de N tiendas
        int n = (int) (Math.random() * 11) + 5; // Esto crea un numero N entre 5 y 15 (incluidos)
        for (int i = 0; i < n; i++) {
            Tienda tienda = new Tienda();
            int id = post.sendPOST(0, ipmonitor, puertomonitor, idmonitor, rolmonitor, "evento", "<tipoEvento>Evento</tipoEvento> <contenido>Connect</contenido>");//se pide id a monitor y se asigna nuevoID (comprobar que tipoEvento es ACK)
            tienda.setId(id);
            listaTiendas.add(tienda);
        }

    }

    /**
     * -Metodo Listening- Inicializacion del servidor con los parametros oportunos
     * 
     * @throws IOException
     * @throws ParserConfigurationException 
     */
    private static void Listening() throws IOException, ParserConfigurationException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Servidor esperando en puerto" + port);
        server.createContext("/", new ServerHTTP(listaTiendas));
        server.setExecutor(null);
        server.start();
    }

    //Gets
    public static String getIplocal() {
        return iplocal;
    }

    public static int getPort() {
        return port;
    }

    public static String getRollocal() {
        return rollocal;
    }

    public static String getIpmonitor() {
        return ipmonitor;
    }

    public static int getPuertomonitor() {
        return puertomonitor;
    }

    public static int getIdmonitor() {
        return idmonitor;
    }

    public static String getRolmonitor() {
        return rolmonitor;
    }

    
}
