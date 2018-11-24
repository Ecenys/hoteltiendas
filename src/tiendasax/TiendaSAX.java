package tiendasax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class TiendaSAX {

    public void Sax(File file) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        //File file = new File(f); //f = fichero, si se lee desde un archivo local, si no, quitar esta linea
        TiendaHandler th = new TiendaHandler();
        saxParser.parse(file, th); //file = fichero, dh = manejador
        
        Emisor emisor = th.getEmisor();
        Receptor receptor = th.getReceptor();
        String tipo = th.getTipo();
        ArrayList<Producto> listaProductos = th.getListaProductos();
        String tipoEvento = th.getTipoEvento();
        
        //System.out.println("Emisor: "+emisor+", Receptor: "+receptor+", Tipo: "+tipo+", Lista de productos: "+listaProductos+", tipo de evento: "+tipoEvento);
        
    }
}