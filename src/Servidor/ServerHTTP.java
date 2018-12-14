package Servidor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

    public ServerHTTP(ArrayList<Tienda> listaTiendas) {
        this.listaTiendas = listaTiendas;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // parse request
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        File f = new File("inicilizacion.xml");
        BufferedWriter out = new BufferedWriter(new FileWriter("post.xml"));
        out.write(query);
        out.flush();
        out.close();

        try {
            s.Sax(f);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tipo = s.getTipo();
        switch (tipo) {
            case "inicializacion":
                id = s.getReceptor().getId();
                for (Tienda tienda : listaTiendas) {
                    if (tienda.getId() == id) {
                        tienda.setAlmacen(s.getListaProductos());
                        try {
                            tienda.Comunica(s.getEmisor().getIp(), s.getEmisor().getPuerto(), s.getEmisor().getRol(), s.getEmisor().getId(), "evento", "<tipoEvento>ACK</tipoEvento><contenido>Agente iniciado correctamente</contenido>");
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
                    try {
                        post.sendPOST(0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "conexion", "<estado> Ok </estado> <msg>Todo perfecto</msg>");
                        listaClientes.add(new Cliente(e.getIp(), e.getPuerto(), e.getId(), listaTiendasCliente));
                    } catch (ParserConfigurationException | TransformerException ex) {
                        Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                            tienda.Comunica(s.getEmisor().getIp(), s.getEmisor().getPuerto(), s.getEmisor().getRol(), s.getEmisor().getId(), "venta", resultado);
                            break;
                        } catch (ParserConfigurationException | TransformerException ex) {
                            Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
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
                try {
                    post.sendPOST(0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "conexion", tiendasconocidas);
                } catch (ParserConfigurationException | TransformerException ex) {
                    Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "fin":
                id = s.getEmisor().getId();
                for (Cliente cliente : listaClientes) {
                    if(cliente.getId() == id)
                        cliente.setTiendaactual(0);
                }
        {
            try {
                post.sendPOST(0, e.getIp(), e.getPuerto(), e.getId(), e.getRol(), "fin", "<msg>ACK</msg>");
            } catch (ParserConfigurationException | TransformerException ex) {
                Logger.getLogger(ServerHTTP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }

    }
}
