package tiendadom;

import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author celestial97
 */
public class TiendaDOM {
    public static void main(String[] args) throws Exception{
        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocumento("10.0.1.1", 80, 10, "tienda", "10.0.1.2", 80, 20, "cliente", "aviso", "<tipoEvento>ACK</tipoEvento><contenido>Agente iniciado correctamente</contenido>"); //prueba de llamada
        generadorDom.generarXML("prueba.xml");
        
    }
}
