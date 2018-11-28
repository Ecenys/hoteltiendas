package tienda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import tiendadom.*;

public class ServerPOST {

    private static final String POST_URLInitial = "10.0.10.10:80/init";
    private String POST_URL;

    //Constructor
    public ServerPOST() {

    }

    public void sendPOST() throws IOException, ParserConfigurationException, TransformerException {

        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocument("10.0.1.1", 80, "tienda", "10.0.1.2", 80, "cliente", "aviso", "valido");
        try {
            generadorDom.generarXML();
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
    }

    public void sendInitialPOST() throws IOException, ParserConfigurationException, TransformerException {

        GeneradorDOM generadorDom = new GeneradorDOM();
        generadorDom.generarDocument("10.0.1.1", 80, "tienda", "10.0.1.2", 80, "cliente", "aviso", "<tipoEvento>Evento</tipoEvento> <contenido>Connect</contenido>");
        try {
            generadorDom.generarXML();
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
    }

}
