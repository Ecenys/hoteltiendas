package tiendasax;

import tienda.Producto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class TiendaSAX {

    Emisor emisor;
    Receptor receptor;
    String tipo;
    ArrayList<Producto> listaProductos;
    String tipoEvento;
    int nuevoID;
    ArrayList<Integer> listaTiendas;
    
    public void Sax(File file) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        //File file = new File(f); //f = fichero, si se lee desde un archivo local, si no, quitar esta linea
        TiendaHandler th = new TiendaHandler();
        saxParser.parse(file, th); //file = fichero, dh = manejador

        emisor = th.getEmisor();
        receptor = th.getReceptor();
        tipo = th.getTipo();
        listaProductos = th.getListaProductos();
        tipoEvento = th.getTipoEvento();
        nuevoID = th.getNuevoID();

        //System.out.println("Emisor: "+emisor+", Receptor: "+receptor+", Tipo: "+tipo+", Lista de productos: "+listaProductos+", tipo de evento: "+tipoEvento);
    }

    // Gets
    public Emisor getEmisor() {
        return emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public int getNuevoID() {
        return nuevoID;
    }

    public ArrayList<Integer> getListaTiendas() {
        return listaTiendas;
    }

}
