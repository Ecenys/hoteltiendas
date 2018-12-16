package tiendasax;

import tienda.Producto;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tienda.Tienda;

/**
 * -Clase TiendaHandler- Handler del parseador, es el que se encarga de tratar
 * el archivo, como hablamos del parseador SAX, este handler se ocupa de
 * identificar el valor de las etiquedas y realizar acciones consecuentes a
 * estas
 * Parseador SAX: parseador de gran eficiencia (O(n), donde n es el numero
 * de caracteres del archivo) pero de alta complejidad en grandes proyectos
 *
 * @author Oskar-MSI
 */
public class TiendaHandler extends DefaultHandler {

    //Necesarios para leer
    private Emisor emisor;
    private Receptor receptor;
    private String tipo;
    private ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    private String tipoEvento;
    private int nuevoID;
    private ArrayList<Integer> listaTiendas = new ArrayList<Integer>();

    //Necesarios para operar
    private String rol, ip;
    private int puerto, id;
    private Producto producto;
    private StringBuilder buffer = new StringBuilder();
    private int aux;

    //Parseador
    /**
     * -Metodo characters- Metodo generado automaticamente por el parseador, que
     * lee y almacena en un buffer los nodos hoja (nodos sin <> o </>)
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        buffer.append(ch, start, length);
    }

    /**
     * -Metodo endElement- metodo generado automaticamente por el parseador, que
     * se encarga de detectar un nodo fin (denotado por la etiqueta </>) y
     * realizar una accion definida dependiendo de su qName (valor de atributo).
     * Normalmente se encarga de asignar los valores a las variables
     * correspondientes
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        try {
            switch (qName) {
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
                case "tienda":
                    listaTiendas.add(id);
                    id = aux;
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
                case "nuevoID":
                    nuevoID = Integer.parseInt(buffer.toString());
                    break;
            }
        } catch (NumberFormatException e) {
        }
    }

    /**
     * -Metodo startElement- metodo generado automaticamente por el parseador,
     * que se encarga de detectar un nodo inicio (denotado por la etiqueta <>) y
     * realizar una accion definida dependiendo de su qName (valor de atributo).
     * Normalmente se encarga de inicalizar y de vaciar el buffer.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        switch (qName) {
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
            case "nuevoID":
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
            case "tienda":
                buffer.delete(0, buffer.length());
                aux = id;
                break;
            case "producto":
                producto = new Producto();
                break;
            case "cuerpo":
                try {
                    if (tipo == null) {
                        tipo = attributes.getValue("orden");
                    }
                } catch (Exception ex) {
                }
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

    public int getNuevoID() {
        return nuevoID;
    }

    public ArrayList<Integer> getListaTiendas() {
        return listaTiendas;
    }

}
