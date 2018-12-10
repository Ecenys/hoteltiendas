package tiendadom;

import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author celestial97
 */
public class TiendaDOM {
    public static void main(String[] args) throws Exception{
        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocument("10.0.1.1", 80, "tienda", "10.0.1.2", 80, "cliente", "aviso", "<tipoEvento>ACK</tipoEvento><contenido>Agente iniciado correctamente</contenido>"); //prueba de llamada
        generadorDom.generarXML("prueba.xml");
        
    }
}
