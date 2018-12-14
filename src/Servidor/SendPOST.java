package Servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import tiendadom.*;
import tiendasax.TiendaSAX;

public class SendPOST {

    private static final String iplocal = "10.0.69.175";
    private static final int puertolocal = 80;
    private static final String ipmonitor = "10.0.69.39";
    private static final int puertomonitor = 3000;
    private static final String POST_URLInitial = "http://"+ipmonitor+":"+puertomonitor+"/init";
    private String POST_URL;

    //Constructor
    public SendPOST() {

    }

    public void sendPOST(int id, String ipdestino, int puertodestino, int iddestino, String roldestino, String tipo, String cuerpo) throws IOException, ParserConfigurationException, TransformerException {

        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocumento(iplocal, puertolocal, id, "tienda", ipdestino, iddestino, puertodestino, roldestino, tipo, cuerpo);
        try {
            generadorDom.generarXML("sendPost.xml");
        } catch (TransformerConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (TransformerException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");

        // For POST only - START
        String cadena;
        FileReader fileReader = new FileReader("prueba.xml");
        BufferedReader b = new BufferedReader(fileReader);
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        while ((cadena = b.readLine()) != null) {
            os.write(cadena.getBytes());
        }
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
        
        //Enviar duplicado a monitor automaticamente
        if(roldestino != "monitor")
            sendPOST(id, ipmonitor, puertomonitor, 0, "monitor", tipo, cuerpo);
    }

    public int sendInitialPOST() throws IOException, ParserConfigurationException, TransformerException, SAXException {

        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocumento(iplocal, puertolocal, 0, "tienda", "10.0.69.39", 3000, 0, "monitor", "evento", "<tipoEvento>Evento</tipoEvento> <contenido>Connect</contenido>");
        try {
            generadorDom.generarXML("sendPost.xml");
        } catch (TransformerConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (TransformerException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        URL obj = new URL(POST_URLInitial);
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
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            File f = new File("post.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter("post.xml"));
            out.write(response.toString());
            out.flush();
            out.close();
            
            TiendaSAX s = new TiendaSAX();
            s.Sax(f);
            return s.getNuevoID();
        } else {
            System.out.println("POST request not worked");
            return 0;
        }
    }

}
