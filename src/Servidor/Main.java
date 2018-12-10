package Servidor;

import tienda.Tienda;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import tienda.Producto;



//Clase principal que hara uso del servidor
public class Main
{
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException
    {
        ServerHTTP serv = new ServerHTTP(); //Se crea el servidor
        ServerPOST post = new ServerPOST();
        PrepareEmulation();
        post.sendInitialPOST();
        while(){
            //Esperar mensaje de inicio de simulación
        }
        System.out.println("Iniciando servidor\n");
        serv.init(); //Se inicia el servidor
    }
    
    private static void PrepareEmulation(){
        ArrayList<Tienda> listaTiendas = new ArrayList<Tienda>();
        ArrayList<Producto> listaProductos = new ArrayList<Producto>();
        
        //Crear tiendas
        int r = (int) (Math.random() * 10) + 1;
        for (int i = 0; i < r; i++) {
            Tienda tienda = new Tienda();
            int id = //se pide id a monitor y se asigna nuevoID (comprobar que tipoEvento es ACK)
            tienda.setId(id);
            listaProductos = // se recibe la lista de productos automaticamente, mediante otro xml con tipo inicializacion
            tienda.setAlmacen(listaProductos);
            // Enviar confirmacion con cuerpo: <tipoEvento>ACK</tipoEvento><contenido>Agente iniciado correctamente</contenido>
            listaTiendas.add(tienda);
        }
        
    }
}