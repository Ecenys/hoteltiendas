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

    public GeneradorDOM(Document document) throws ParserConfigurationException {
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        document = builder.newDocument();
    }
    
    public void generarDocument(){
        Element mensaje = document.createElement("mensaje");
        document.appendChild(mensaje);
        
        Element emisor = document.createElement("emisor");
        mensaje.appendChild(emisor);
        
        Element receptor = document.createElement("recetpr");
        mensaje.appendChild(receptor);
        
        Element tipo = document.createElement("tipo");
        mensaje.appendChild(tipo);
        
        Element cuerpo = document.createElement("cuerpo");
        mensaje.appendChild(cuerpo);
        
        Element direccion = document.createElement("direccion");
        emisor.appendChild(direccion);
        receptor.appendChild(direccion);
        
        Element ip = document.createElement("ip");
        direccion.appendChild(ip);
        
        Element puerto = document.createElement("puerto");
        direccion.appendChild(puerto);
        
        Element rol = document.createElement("rol");
        emisor.appendChild(rol);
        receptor.appendChild(rol);
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
