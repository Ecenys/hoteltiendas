package Servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import Servidor.ServerPOST;
import tiendasax.*;

public class ServerHTTP {

    private static final String GET_URL = "http://10.0.69.39:80/init";

    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        ServerPOST sp = new ServerPOST();
        sp.sendInitialPOST();
    }
    
    public void init() throws IOException, ParserConfigurationException, SAXException{
            while (true) {
            sendGET();
        }
    }

    private static void sendGET() throws IOException, ParserConfigurationException, SAXException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            File file = new File("get.xml");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(response.toString());
            fileWriter.flush();
            fileWriter.close();

            TiendaSAX ts = new TiendaSAX();
            ts.Sax(file);

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

}
