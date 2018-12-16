package Servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import tiendadom.*;
import tiendasax.TiendaSAX;

/**
 * -Clase SendPost- Esta clase engloba todo lo relacionado con el envio de
 * archivos, por lo que podemos encontrar un metodo de generar el archivo xml y
 * el método para el envío mediante HTTP/POST de este
 */
public class SendPOST {

    //Atributos de conexion
    private static String iplocal = Main.getIplocal();
    private static int puertolocal = Main.getPort();
    private static String ipmonitor = Main.getIpmonitor();
    private static int puertomonitor = Main.getPuertomonitor();
    private static String rolmonitor = Main.getRolmonitor();
    //URL de monitor, donde contactamos por primera vez para inicializar la simulacion
    private static final String POST_URLInitial = "http://" + ipmonitor + ":" + puertomonitor + "/init";

    //Constructor
    public SendPOST() {
    }

    /**
     * -GeneraDOM- Metodo generador de archivo xml para el siguiente envío
     * mediante HTTP/POST Este método es llamado automaticamente desde el metodo
     * sendPOST
     *
     * @param generadorDom
     * @param id
     * @param ipdestino
     * @param iddestino
     * @param puertodestino
     * @param roldestino
     * @param tipo
     * @param cuerpo
     * @throws IOException
     */
    public void GeneraDOM(GeneradorDOM generadorDom, int id, String ipdestino, int iddestino, int puertodestino, String roldestino, String tipo, String cuerpo) throws IOException {
        generadorDom.generarDocumento(iplocal, puertolocal, id, "tienda", ipdestino, iddestino, puertodestino, roldestino, tipo, cuerpo);
        try {
            generadorDom.generarXML("sendPost.xml");
        } catch (TransformerConfigurationException e1) {
            e1.printStackTrace();
        } catch (TransformerException en) {
            en.printStackTrace();
        }
    }

    /**
     * -Metodo sendPOST- Envio de datos a un destinatario. Devuelve un id, en
     * caso de ser el mensaje inicial (pedir id a monitor). Si no, devuelve un 1
     * para indicar que la comunicacion es correcta. En cualquier caso, si
     * devuelve 0 indica fallo en al conexion.
     *
     * @param id
     * @param ipdestino
     * @param puertodestino
     * @param iddestino
     * @param roldestino
     * @param tipo
     * @param cuerpo
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     */
    public int sendPOST(int id, String ipdestino, int puertodestino, int iddestino, String roldestino, String tipo, String cuerpo) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        GeneradorDOM generadorDom = new GeneradorDOM();
        GeneraDOM(generadorDom, id, ipdestino, puertodestino, iddestino, roldestino, tipo, cuerpo);
        URL obj;
        if (roldestino == rolmonitor) {
            obj = new URL(POST_URLInitial);
        } else {
            obj = new URL("http://" + ipdestino + ":" + puertodestino);
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");
        // For POST only - START
        FileReader fileReader = new FileReader("initial.xml");
        BufferedReader b = new BufferedReader(fileReader);
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(b.readLine());
        wr.flush();
        wr.close();

        // For POST only - END
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            System.out.println("POST Response Code :: " + responseCode);
        } else {
            System.out.println("Conexión inicial correcta");
        }

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Escribimos el resultado de la comunicacion en un file xml, y la parseamos
            File f = new File("sendPost.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            out.write(response.toString());
            out.flush();
            out.close();

            TiendaSAX s = new TiendaSAX();
            s.Sax(f);
//            if (s.getTipo() == "evento") {
            return s.getNuevoID();
//            }

            //Temporalmente oculto, ya que no hace falta para el proyecto, pero necesario si queremos enviar mas de un mensaje POST, ademas del inicial
            //Reenvio a monitor del mensaje, en caso de que el mensaje no fuera para el
//            if (roldestino != rolmonitor) {
//                int exito = sendPOST(id, ipmonitor, puertomonitor, 0, rolmonitor, tipo, cuerpo);
//            }
//            
//            //Si conexion con monitor es fallida, tambien validamos como fallida esta
//            if(exito == 0)
//                return 0;
//            //Comunicacion total exitosa
//            return 1;
        } else {
            // Si comunicacion fallida, devolvemos 0
            System.out.println("Comunicación con " + roldestino + " fallida");
            return 0;
        }
    }

}
