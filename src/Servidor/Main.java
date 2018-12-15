package Servidor;

import com.sun.net.httpserver.HttpServer;
import tienda.Tienda;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import tienda.Producto;

//Clase principal que inicializa las distintas etapas del servidor
public class Main {

    private static int port = 80;
    private static ArrayList<Tienda> listaTiendas = new ArrayList<Tienda>();
    private static ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException, InterruptedException {
        System.out.println("Iniciando simulación, asignando tiendas");
        //PrepareEmulation();
        System.out.println("ID asignados, esperando inicialización");
        Listening();
    }

    private static void PrepareEmulation() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        //Instanciamos el objeto post, para poder crear y mandar el post de inicialización
        SendPOST post = new SendPOST();

        //Creacion de N tiendas
        int n = (int) (Math.random() * 11) + 5; // Esto crea un numero N entre 5 y 15 (incluidos)
        for (int i = 0; i < n; i++) {
            Tienda tienda = new Tienda();
            int id = post.sendInitialPOST();//se pide id a monitor y se asigna nuevoID (comprobar que tipoEvento es ACK)
            tienda.setId(id);
            listaTiendas.add(tienda);
        }

    }

    private static void Listening() throws IOException, ParserConfigurationException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Servidor esperando en puerto" + port);
        server.createContext("/", new ServerHTTP(listaTiendas));
        server.setExecutor(null);
        server.start();
    }

    
}
