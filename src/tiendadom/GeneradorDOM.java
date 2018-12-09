package tiendadom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import java.util.Date;

/**
 *
 * @author Oscar-C97
 */
public class GeneradorDOM {

    //Creación de la raiz del documento
    private Document document;
    //Obtención de la hora actual
    private Date date = new Date();
    private DateFormat horaformato = new SimpleDateFormat("HH:mm:ss");
    private String horaa = horaformato.format(date);

    public GeneradorDOM() throws ParserConfigurationException {
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        document = builder.newDocument();
    }

    public void generarDocument(String ipemisor, int puertoemisor, String rolemisor, String ipreceptor, int puertoreceptor, String rolreceptor, String tipo, String cuerpo) {

        //Creacion de nodos
        Element mensaje = document.createElement("mensaje");
        Element emisor = document.createElement("emisor");
        Element receptor = document.createElement("receptor");
        Element tipoo = document.createElement("tipo");
        Element cuerpoo = document.createElement("cuerpo");
        Element direccionem = document.createElement("direccion");
        Element direccionre = document.createElement("direccion");
        Element ipem = document.createElement("ip");
        Element ipre = document.createElement("ip");
        Element puertoem = document.createElement("puerto");
        Element puertore = document.createElement("puerto");
        Element rolem = document.createElement("rol");
        Element rolre = document.createElement("rol");
        Element hora = document.createElement("hora");

        //Definicion de descendencias
        document.appendChild(mensaje);
        mensaje.appendChild(emisor);
        mensaje.appendChild(receptor);
        mensaje.appendChild(tipoo);
        mensaje.appendChild(hora);
        mensaje.appendChild(cuerpoo);
        emisor.appendChild(direccionem);
        receptor.appendChild(direccionre);
        direccionem.appendChild(ipem);
        direccionre.appendChild(ipre);
        direccionem.appendChild(puertoem);
        direccionre.appendChild(puertore);
        emisor.appendChild(rolem);
        receptor.appendChild(rolre);

        //Definicion de nodos hoja
        ipem.appendChild(document.createTextNode(ipemisor));
        ipre.appendChild(document.createTextNode(ipreceptor));
        puertoem.appendChild(document.createTextNode(String.valueOf(puertoemisor)));
        puertore.appendChild(document.createTextNode(String.valueOf(puertoreceptor)));
        rolem.appendChild(document.createTextNode(rolemisor));
        rolem.appendChild(document.createTextNode(rolreceptor));
        tipoo.appendChild(document.createTextNode(tipo));
        cuerpoo.appendChild(document.createTextNode(cuerpo));
        hora.appendChild(document.createTextNode(horaa));
    }

    public void generarXML(String archivo) throws TransformerConfigurationException, IOException, TransformerException {
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer = factoria.newTransformer();

        Source source = new DOMSource(document); // origen de los datos
        
        //Creacion y escritura del fichero físico
        File file = new File(archivo);
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        Result result = new StreamResult(pw);
        
        transformer.transform(source, result);
        
        fw.close();
        pw.close();
        
        //Arreglo de fallo en cabezera con caracteres especiales
        FileReader fileR = new FileReader(archivo);
        BufferedReader file2 = new BufferedReader(fileR);
        String arreglo = file2.readLine();
        arreglo = (arreglo.replace("&lt;", "<"));
        arreglo = (arreglo.replace("&gt;", ">"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(arreglo);
        bw.close();
    }
}
