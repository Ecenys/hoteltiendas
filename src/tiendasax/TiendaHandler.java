package tiendasax;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TiendaHandler extends DefaultHandler {
    //Necesarios para leer
    private Emisor emisor;
    private Receptor receptor;
    private String tipo;
    private ArrayList<Producto> listaProductos =  new ArrayList<Producto>();
    private String tipoEvento;
    
    //Necesarios para operar
    private String rol, ip;
    private int puerto, id;
    private Producto producto;
    private StringBuilder buffer = new StringBuilder();
    
    //Parseador
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        buffer.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        switch(qName){
            case "emisor":
                emisor.setIp(ip);
                emisor.setPuerto(puerto);
                emisor.setId(id);
                emisor.setRol(rol);
                break;
            case "receptor":
                receptor.setIp(ip);
                receptor.setPuerto(puerto);
                receptor.setId(id);
                receptor.setRol(rol);
                break;
            case "ip":
                ip = buffer.toString();
                break;
            case "puerto":
                puerto = Integer.parseInt(buffer.toString());
                break;
            case "idCliente":
            case "id":
                id = Integer.parseInt(buffer.toString());
                break;
            case "rol":
                rol = buffer.toString();
                break;
            case "tipo":
                tipo = buffer.toString();
                break;
            case "nombre":
                producto.setNombre(buffer.toString());
                break;
            case "cantidad":
                producto.setCantidad(Integer.parseInt(buffer.toString()));
                break;
            case "producto":
                listaProductos.add(producto);
                break;
            case "tipoEvento":
                tipoEvento = buffer.toString();
                break;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        switch(qName){
            case "emisor":
                ip = null;
                puerto = 0;
                id = 0;
                emisor = new Emisor();
                break;
            case "receptor":
                ip = null;
                puerto = 0;
                id = 0;
                receptor = new Receptor();
                break;
            case "idCliente":
                buffer.delete(0, buffer.length());
                break;
            case "tipoEvento":
            case "ip":
            case "puerto":
            case "id":
            case "rol":
            case "tipo":
            case "nombre":
            case "cantidad":
                buffer.delete(0, buffer.length());
                break;
            case "producto":
                producto = new Producto();
                break;
            case "cuerpo":
                try{
                    if (tipo == null) {
                        tipo = attributes.getValue("orden");
                    }
                }catch (Exception ex) {}
                break;
        }
    }

    //Gets
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
    
}
