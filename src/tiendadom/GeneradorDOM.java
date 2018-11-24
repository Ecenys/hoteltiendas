package tiendadom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Oskar-MSI
 */
public class GeneradorDOM {
    private Document document;
    private Element mensaje = document.createElement("mensaje");
    private Element emisor = document.createElement("emisor");
    private Element receptor = document.createElement("receptor");
    private Element tipo = document.createElement("tipo");
    private Element cuerpo = document.createElement("cuerpo");
    private Element direccionemisor = document.createElement("direccion");
    private Element direccionreceptor = document.createElement("direccion");
    private Element ipemisor = document.createElement("ip");
    private Element ipreceptor = document.createElement("ip");
    private Element puertoemisor = document.createElement("puerto");
    private Element puertoreceptor = document.createElement("puerto");
    private Element rolemisor = document.createElement("rol");
    private Element rolreceptor = document.createElement("rol");
    
    
    public GeneradorDOM(String ipem, int puertoem, String rolem, String ipre, int puertore, String rolre, String tipo) throws ParserConfigurationException {
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        document = builder.newDocument();
        
        
        ipemisor.appendChild(document.createTextNode(ipem));
        ipreceptor.appendChild(document.createTextNode(ipre));
        puertoemisor.appendChild(document.createTextNode(String.valueOf(puertoem)));
        puertoreceptor.appendChild(document.createTextNode(String.valueOf(puertore)));
        rolemisor.appendChild(document.createTextNode(rolem));
        rolemisor.appendChild(document.createTextNode(rolre));
        this.tipo.appendChild(document.createTextNode(tipo));
        
        
        
    }
    
    public void generarDocument(){
        
        document.appendChild(mensaje);
        
        mensaje.appendChild(emisor);
        
        mensaje.appendChild(receptor);
        
        mensaje.appendChild(tipo);
        
        mensaje.appendChild(cuerpo);
        
        emisor.appendChild(direccionemisor);
        
        receptor.appendChild(direccionreceptor);
        
        direccionemisor.appendChild(ipemisor);
        
        direccionreceptor.appendChild(ipreceptor);
        
        direccionemisor.appendChild(puertoemisor);
        
        direccionreceptor.appendChild(puertoreceptor);
        
        emisor.appendChild(rolemisor);
        
        receptor.appendChild(rolreceptor);
    }
    
    public void generarXML() throws TransformerConfigurationException, IOException, TransformerException{
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer = factoria.newTransformer();
        
        Source source = new DOMSource(document); // origen de los datos
        
        File file = new File("prueba"); //f = fichero, es para crear un fichero físico
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        Result result = new StreamResult(pw);
        
        
                
        transformer.transform(source, result);
    }
}
