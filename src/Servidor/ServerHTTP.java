package Servidor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import tienda.Cliente;
import tienda.Tienda;
import tiendadom.GeneradorDOM;
import tiendasax.Emisor;
import tiendasax.TiendaSAX;

public class ServerHTTP implements HttpHandler {

    int id;
    Emisor e = new Emisor();
    TiendaSAX s = new TiendaSAX();
    ArrayList<Tienda> listaTiendas = new ArrayList<>();
    ArrayList<Integer> listaTiendasCliente = new ArrayList<>();
    ArrayList<Cliente> listaClientes = new ArrayList<>();
    SendPOST post = new SendPOST();
    String response = "";
    private GeneradorDOM generadorDom;

    public ServerHTTP(ArrayList<Tienda> listaTiendas) throws ParserConfigurationException {
        this.listaTiendas = listaTiendas;
        generadorDom = new GeneradorDOM();
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // parse request
        InputStreamReader isr = new InputStreamReader(he.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        String cadena;
        File f = new File("post.xml");
        BufferedWriter out = new BufferedWriter(new FileWriter(f));
        while ((cadena = br.readLine()) != null) {
            out.write(cadena);
        }
        out.flush();
        out.close();

        try {
            s.Sax(f);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tipo = s.getTipo();
        switch (tipo) {

            case "prueba":
                HTTPResponse(he, response);
                break;

            case "inicializacion":
                id = s.getReceptor().getId();
                for (Tienda tienda : listaTiendas) {
                    if (tienda.getId() == id) {
                        tienda.setAlmacen(s.getListaProductos());
                        try {
                            System.out.println(s.getEmisor().toString());
                            tienda.Comunica(generadorDom, s.getEmisor().getIp(), s.getEmisor().getPuerto(), s.getEmisor().getRol(), s.getEmisor().getId(), "evento", "<tipoEvento>ACK</tipoEvento><contenido>Agente iniciado correctamente</contenido>");
                        } catch (ParserConfigurationException | TransformerException ex) {
                            Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                }
                break;

            case "conexion":
                int i = 0;
                e = s.getEmisor();
                id = s.getReceptor().getId();
                listaTiendasCliente = s.getListaTiendas();
                for (Tienda tienda : listaTiendas) {
                    if (tienda.getId() == id) {
                        i++;
                    }
                }
                if (listaTiendas.size() == i) {
                    post.GeneraDOM(generadorDom, 0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "conexion", "<estado> Ok </estado> <msg>Todo perfecto</msg>");
                    listaClientes.add(new Cliente(e.getIp(), e.getPuerto(), e.getId(), listaTiendasCliente));
                } else {
                    try {
                        post.sendPOST(0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "conexion", "<estado> Error </estado> <msg> Tiendas introducidas no correctas</msg>");
                    } catch (ParserConfigurationException | TransformerException ex) {
                        Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "compra":
                String resultado;
                id = s.getReceptor().getId();
                for (Tienda tienda : listaTiendas) {
                    if (tienda.getId() == id) {
                        try {
                            resultado = tienda.Comprar(s.getListaProductos());
                            tienda.Comunica(generadorDom, s.getEmisor().getIp(), s.getEmisor().getPuerto(), s.getEmisor().getRol(), s.getEmisor().getId(), "venta", resultado);
                            break;
                        } catch (ParserConfigurationException | TransformerException ex) {
                            Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;

            case "entrar a tienda":
                String tiendasconocidas = "";
                id = s.getEmisor().getId();
                for (Cliente cliente : listaClientes) {
                    if (cliente.getTiendaactual() != 0) {
                        if (s.getReceptor().getId() == cliente.getTiendaactual()) {
                            tiendasconocidas += cliente.getListaTiendas();
                        }
                    }
                }
                post.GeneraDOM(generadorDom, 0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "conexion", tiendasconocidas);
                break;

            case "fin":
                id = s.getEmisor().getId();
                for (Cliente cliente : listaClientes) {
                    if (cliente.getId() == id) {
                        cliente.setTiendaactual(0);
                    }
                }
                post.GeneraDOM(generadorDom, 0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "fin", "<msg>ACK</msg>");
                break;
        }
    }

    private void HTTPResponse(HttpExchange he, String response) throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("post.xml"));
        response = re.readLine();
        re.close();

        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }
}
